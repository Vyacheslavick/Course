package com.example.course.Activities.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.course.DBClasses.AdditionClasses.SuppCount;
import com.example.course.R;
import com.example.course.SupportClasses.Adapters.RecyclerAdapterHistory;
import com.example.course.SupportClasses.Adapters.RecyclerAdapterReceipts;
import com.example.course.SupportClasses.App;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SupplyHistoryFragment extends Fragment implements RecyclerAdapterHistory.OnReceiptItemClick{

    RecyclerView listHistorySupply;
    LinearLayoutManager layoutManager;
    RecyclerAdapterHistory adapterHistory;
    List<SuppCount> suppCounts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_supply, container, false);
        listHistorySupply = view.findViewById(R.id.list_history_supply);

        suppCounts = App.getInstance().getDatabase().supplyDao().supplies();


        layoutManager = new LinearLayoutManager(view.getContext());
        listHistorySupply.setLayoutManager(layoutManager);
        adapterHistory = new RecyclerAdapterHistory(view.getContext(), suppCounts,  this);
        listHistorySupply.setAdapter(adapterHistory);

        return view;
    }

    @Override
    public void onClick(int position){

    }
}
