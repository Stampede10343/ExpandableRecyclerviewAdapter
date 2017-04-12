package com.dev.cameronc.expandablerecyclerviewadapter;

import java.util.List;

/**
 * Created by ccord on 4/10/2017.
 */

public interface Parent<Child>
{
    List<Child> getChildrenItems();
    boolean isExpandedByDefault();
}
