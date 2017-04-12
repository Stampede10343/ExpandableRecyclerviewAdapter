package com.dev.cameronc.expandablerecyclerview.ExpandableAdapter;


import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.cameronc.expandablerecyclerviewadapter.ExpandableRecyclerviewAdapter;

import java.util.List;

/**
 * Created by ccord on 4/11/2017.
 */

public class ExpandableAdapter extends ExpandableRecyclerviewAdapter<ParentVH, ChildVH, ParentItem, ChildItem>
{
    public ExpandableAdapter(List<ParentItem> parentItems)
    {
        super(parentItems);
    }

    @Override
    protected void onBindParentViewHolder(final ParentVH holder, final int position)
    {
        holder.textView.setText(getParentAt(position).getData());
        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onToggleParent(holder);
            }
        });
    }

    @Override
    protected void onBindChildViewHolder(ChildVH holder, int position)
    {
        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            holder.textView.setTextAppearance(holder.itemView.getContext(), android.R.style.TextAppearance_Material_Small);
        }
        holder.textView.setText(getChildAt(position).getData());
    }

    @Override
    protected ParentVH onCreateParentViewHolder(ViewGroup parent)
    {
        View simpleItem = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ParentVH(simpleItem);
    }

    @Override
    protected ChildVH onCreateChildViewHolder(ViewGroup parent)
    {
        View simpleItem = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ChildVH(simpleItem);
    }
}
