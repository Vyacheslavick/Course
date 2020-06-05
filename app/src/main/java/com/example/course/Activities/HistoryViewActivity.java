package com.example.course.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;


import android.os.Bundle;

import com.example.course.R;
import com.example.course.SupportClasses.App;
import com.example.course.SupportClasses.Adapters.RecyclerAdapterSales;

public class HistoryViewActivity extends AppCompatActivity {

    @BindView(R.id.list_history_view) RecyclerView list;
    LinearLayoutManager layoutManager;
    RecyclerAdapterSales adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_view);
        ButterKnife.bind(this);

        layoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapterSales(this, App.getInstance().getDatabase().salesDao().viewSale(getIntent().getIntExtra("View result", 1)));
        list.setAdapter(adapter);
    }
}
