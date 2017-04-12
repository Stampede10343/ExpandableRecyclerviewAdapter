package com.dev.cameronc.expandablerecyclerview.ExpandableAdapter;

import com.dev.cameronc.expandablerecyclerviewadapter.Parent;

import java.util.ArrayList;
import java.util.List;

public class ParentItem implements Parent<ChildItem>
{
    private String data = "PARENT!";

    public ParentItem(String data)
    {
        this.data = data;
    }

    @Override
    public List<ChildItem> getChildrenItems()
    {
        List<ChildItem> children = new ArrayList<>();
        children.add(new ChildItem("asdf"));
        children.add(new ChildItem("daf"));
        children.add(new ChildItem("fda"));
        children.add(new ChildItem("fafdac"));

        return children;
    }

    @Override
    public boolean isExpandedByDefault()
    {
        return true;
    }

    public String getData()
    {
        return data;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof ParentItem)
        {
            ParentItem item = (ParentItem)obj;
            return this.data.equals(item.data);
        }
        else
        {
            return super.equals(obj);
        }
    }
}
