package com.example.course.Activities.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.course.Activities.ReturnActivity;
import com.example.course.DBClasses.Detail;
import com.example.course.R;
import com.example.course.SupportClasses.Adapters.RecyclerAdapterWarehouse;
import com.example.course.SupportClasses.App;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.app.Activity.RESULT_OK;

public class WarehouseFragment extends Fragment implements RecyclerAdapterWarehouse.OnDetailItemClick{

    RecyclerView list;
    LinearLayoutManager layoutManager;
    List<Detail> details;
    RecyclerAdapterWarehouse adapterWarehouse;

    SharedPreferences sp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_warehouse, container, false);
        list = view.findViewById(R.id.list_warehouse);
        sp = getActivity().getPreferences(Context.MODE_PRIVATE);
        details = App.getInstance().getDatabase().detailDao().detailsInWarehouse("");
        layoutManager = new LinearLayoutManager(view.getContext());
        list.setLayoutManager(layoutManager);
        adapterWarehouse = new RecyclerAdapterWarehouse(view.getContext(), details,  this);
        list.setAdapter(adapterWarehouse);
        return view;
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(getContext(), ReturnActivity.class);
        intent.putExtra("DetId",details.get(position).id);
        sp.edit().putInt("DetPos", position).apply();
        startActivityForResult(intent, 20);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            details.get(sp.getInt("DetPos",0)).count -= data.getIntExtra("Count",0);
            adapterWarehouse.notifyDataSetChanged();
        }
    }
}
