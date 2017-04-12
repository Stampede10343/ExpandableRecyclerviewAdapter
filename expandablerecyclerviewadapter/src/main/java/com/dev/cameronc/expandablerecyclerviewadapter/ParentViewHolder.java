package com.dev.cameronc.expandablerecyclerviewadapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

class ParentViewHolder extends RecyclerView.ViewHolder
{
    private boolean isExpanded = false;

    public ParentViewHolder(View itemView)
    {
        super(itemView);
    }

    public boolean isExpanded()
    {
        return isExpanded;
    }

    public void setExpanded(boolean expanded)
    {
        isExpanded = expanded;
    }
}
