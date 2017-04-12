package com.dev.cameronc.expandablerecyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dev.cameronc.expandablerecyclerview.ExpandableAdapter.ExpandableAdapter;
import com.dev.cameronc.expandablerecyclerview.ExpandableAdapter.ParentItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    RecyclerView expandableRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expandableRecyclerView = (RecyclerView) findViewById(R.id.expandable_recyclerview);
        List<ParentItem> items = new ArrayList<>();
        items.add(new ParentItem("Parent1"));
        items.add(new ParentItem("Parent2"));
        expandableRecyclerView.setAdapter(new ExpandableAdapter(items));
        expandableRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
