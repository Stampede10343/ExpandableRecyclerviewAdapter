package com.dev.cameronc.expandablerecyclerviewadapter;

import java.util.List;

public interface Parent<Child>
{
    List<Child> getChildrenItems();
    boolean isExpandedByDefault();
}
