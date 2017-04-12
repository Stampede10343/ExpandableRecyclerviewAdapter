package com.dev.cameronc.expandablerecyclerviewadapter;

import android.support.annotation.Nullable;

public class ExpandableItemWrapper<P extends Parent<Child>, Child>
{
    private final boolean isParent;
    private P parent;
    @Nullable
    private Child child;

    ExpandableItemWrapper(P parent)
    {
        isParent = true;
        this.parent = parent;
    }

    ExpandableItemWrapper(P parent, Child child)
    {
        this.parent = parent;
        this.child = child;
        isParent = false;
    }

    public boolean isParent()
    {
        return isParent;
    }

    public P getParent()
    {
        return parent;
    }

    @Nullable
    public Child getChild()
    {
        return child;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof ExpandableItemWrapper)
        {
            ExpandableItemWrapper wrapper = ((ExpandableItemWrapper) obj);
            return getParent().equals(wrapper.getParent()) && isParent() == wrapper.isParent();
        }
        else
        {
            return super.equals(obj);
        }
    }
}
