package com.example.course.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.os.Bundle;

import com.example.course.DBClasses.AdditionClasses.SupplyBucket;
import com.example.course.R;
import com.example.course.SupportClasses.Adapters.RecyclerAdapterSupplies;
import com.example.course.SupportClasses.App;

import java.util.List;

public class SupplyViewActivity extends AppCompatActivity implements RecyclerAdapterSupplies.OnBucketItemClick {

    @BindView(R.id.list_supplies) RecyclerView list;
    LinearLayoutManager layoutManager;
    RecyclerAdapterSupplies adapterSupplies;
    List<SupplyBucket> supplyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supply_view);
        ButterKnife.bind(this);

        supplyList = App.getInstance().getDatabase().supplyDao().getSup(getIntent().getIntExtra("SupId", 0));
        layoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(layoutManager);
        adapterSupplies = new RecyclerAdapterSupplies(this, supplyList,this);
        list.setAdapter(adapterSupplies);
    }

    @Override
    public void onLongClick(int position) {

    }
}
