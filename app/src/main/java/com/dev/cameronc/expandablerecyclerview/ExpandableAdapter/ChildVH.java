package com.dev.cameronc.expandablerecyclerview.ExpandableAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by ccord on 4/11/2017.
 */

public class ChildVH extends RecyclerView.ViewHolder
{
    TextView textView;

    public ChildVH(View itemView)
    {
        super(itemView);
        textView = (TextView) itemView.findViewById(android.R.id.text1);
    }
}
