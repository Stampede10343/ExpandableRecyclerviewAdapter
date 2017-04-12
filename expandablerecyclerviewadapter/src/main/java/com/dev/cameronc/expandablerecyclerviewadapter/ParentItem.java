package com.dev.cameronc.expandablerecyclerviewadapter;

import java.util.List;

class ParentItem<C> implements Parent<C>
{
    private List<C> children;

    ParentItem(List<C> childItems)
    {
        children = childItems;
    }

    @Override
    public List<C> getChildrenItems()
    {
        return children;
    }

    @Override
    public boolean isExpandedByDefault()
    {
        return false;
    }
}
