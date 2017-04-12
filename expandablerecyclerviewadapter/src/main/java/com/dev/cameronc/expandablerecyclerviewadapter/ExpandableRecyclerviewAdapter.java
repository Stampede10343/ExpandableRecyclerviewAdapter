package com.dev.cameronc.expandablerecyclerviewadapter;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ExpandableRecyclerviewAdapter<ParentVH extends RecyclerView.ViewHolder, ChildVH extends RecyclerView.ViewHolder, ParentItem extends Parent<ChildItem>, ChildItem> extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private static final int PARENT_ITEM = 1;
    private static final int CHILD_ITEM = 2;

    private List<ParentItem> parentItemList;
    private List<ExpandableItemWrapper<ParentItem, ChildItem>> allItemsList;
    private Map<ParentItem, Boolean> parentExpandedState = new HashMap<>();

    public ExpandableRecyclerviewAdapter(List<ParentItem> parentItems)
    {
        super();
        parentItemList = parentItems;
        allItemsList = createAllItemsList(parentItemList);
    }

    private List<ExpandableItemWrapper<ParentItem, ChildItem>> createAllItemsList(List<ParentItem> parentItemList)
    {
        List<ExpandableItemWrapper<ParentItem, ChildItem>> allItems = new ArrayList<>();

        int parentIndex = 0;
        for (ParentItem parent : parentItemList)
        {
            allItems.add(parentIndex, new ExpandableItemWrapper<>(parent));

            if (parent.isExpandedByDefault())
            {
                List<ExpandableItemWrapper<ParentItem, ChildItem>> children = wrapChildItems(parent, parent.getChildrenItems());
                allItems.addAll(parentIndex + 1, children);
                parentIndex += children.size() + 1;
                parentExpandedState.put(parent, true);
            }
            else
            {
                parentIndex += 1;
                parentExpandedState.put(parent, false);
            }
        }

        return allItems;
    }

    private List<ExpandableItemWrapper<ParentItem, ChildItem>> wrapChildItems(ParentItem parent, List<ChildItem> childrenItems)
    {
        List<ExpandableItemWrapper<ParentItem, ChildItem>> wrappedChildren = new ArrayList<>();
        for (ChildItem child : childrenItems)
        {
            wrappedChildren.add(new ExpandableItemWrapper<>(parent, child));
        }

        return wrappedChildren;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if (viewType == PARENT_ITEM)
        {
            return onCreateParentViewHolder(parent);
        }
        else if (viewType == CHILD_ITEM)
        {
            return onCreateChildViewHolder(parent);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        if (isParentItem(position))
        {
            onBindParentViewHolder((ParentVH) holder, position);
        }
        else
        {
            onBindChildViewHolder((ChildVH) holder, position);
        }
    }

    private boolean isParentItem(int position)
    {
        return allItemsList.get(position).isParent();
    }

    protected abstract void onBindParentViewHolder(ParentVH holder, int position);

    protected abstract void onBindChildViewHolder(ChildVH holder, int position);

    @Override
    public int getItemViewType(int position)
    {
        ExpandableItemWrapper wrappedItem = allItemsList.get(position);
        return wrappedItem.isParent() ? PARENT_ITEM : CHILD_ITEM;
    }

    @Override
    public int getItemCount()
    {
        return allItemsList.size();
    }

    protected abstract ParentVH onCreateParentViewHolder(ViewGroup parent);

    protected abstract ChildVH onCreateChildViewHolder(ViewGroup parent);

    protected ParentItem getParentAt(int position)
    {
        return allItemsList.get(position).getParent();
    }

    protected ChildItem getChildAt(int position)
    {
        return allItemsList.get(position).getChild();
    }

    public List<ParentItem> getParentItems()
    {
        return parentItemList;
    }

    protected void onToggleParent(ParentVH viewHolder)
    {
        ExpandableItemWrapper<ParentItem, ChildItem> parentItemWrapper = allItemsList.get(viewHolder.getAdapterPosition());
        if (parentItemWrapper.isParent())
        {
            boolean isExpanded = parentExpandedState.get(parentItemWrapper.getParent());
            parentExpandedState.put(parentItemWrapper.getParent(), !isExpanded);
            rebuildItemList();
        }
    }

    private void rebuildItemList()
    {
        final List<ExpandableItemWrapper<ParentItem, ChildItem>> newItemList = new ArrayList<>();
        for (ExpandableItemWrapper<ParentItem, ChildItem> itemWrapper : allItemsList)
        {
            if (itemWrapper.isParent())
            {
                newItemList.add(itemWrapper);
                boolean isExpanded = parentExpandedState.get(itemWrapper.getParent());
                if (isExpanded)
                {
                    for (ChildItem childItem : itemWrapper.getParent().getChildrenItems())
                    {
                        newItemList.add(new ExpandableItemWrapper<>(itemWrapper.getParent(), childItem));
                    }
                }
                else
                {
                    for (int i = 0; i < allItemsList.size(); i++)
                    {
                        ExpandableItemWrapper<ParentItem, ChildItem> item = allItemsList.get(i);
                    }
                }
            }
        }

        DiffUtil.calculateDiff(new ExpandableItemDiffCallback<>(allItemsList, newItemList)).dispatchUpdatesTo(this);

        allItemsList = newItemList;
    }

    protected void setParentExpanded(ParentVH vh, boolean isExpanded)
    {
        ExpandableItemWrapper<ParentItem, ChildItem> parentItemWrapper = allItemsList.get(vh.getAdapterPosition());
        if (parentItemWrapper.isParent())
        {
            parentExpandedState.put(parentItemWrapper.getParent(), isExpanded);
            rebuildItemList();
        }
    }

    private static class ExpandableItemDiffCallback<P extends Parent<C>, C> extends DiffUtil.Callback
    {
        private final List<ExpandableItemWrapper<P, C>> newItemList;
        private final List<ExpandableItemWrapper<P, C>> oldItemList;

        ExpandableItemDiffCallback(List<ExpandableItemWrapper<P, C>> oldItems, List<ExpandableItemWrapper<P, C>> newItemList)
        {
            this.oldItemList = oldItems;
            this.newItemList = newItemList;
        }

        @Override
        public int getOldListSize()
        {
            return oldItemList.size();
        }

        @Override
        public int getNewListSize()
        {
            return newItemList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition)
        {
            ExpandableItemWrapper<P, C> oldItem = oldItemList.get(oldItemPosition);
            ExpandableItemWrapper<P, C> newItem = newItemList.get(newItemPosition);
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition)
        {
            ExpandableItemWrapper<P, C> oldItem = oldItemList.get(oldItemPosition);
            ExpandableItemWrapper<P, C> newItem = newItemList.get(newItemPosition);
            //return oldItem.getParent() == newItem.getParent();
            return true;
        }
    }
}
