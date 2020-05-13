package com.example.course.Activities.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.course.Activities.HistoryViewActivity;
import com.example.course.DBClasses.Receipt;
import com.example.course.R;
import com.example.course.SupportClasses.App;
import com.example.course.SupportClasses.Adapters.RecyclerAdapterReceipts;


import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class SellHistoryFragment extends Fragment implements RecyclerAdapterReceipts.OnReceiptItemClick{


    RecyclerView list;
    RecyclerAdapterReceipts adapter;
    LinearLayoutManager layoutManager;
    List<Receipt> receiptList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        receiptList = App.getInstance().getDatabase().salesDao().receipts();
        list = view.findViewById(R.id.list_history);

        layoutManager = new LinearLayoutManager(getContext());
        list.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapterReceipts(receiptList, getContext(), this);
        list.setAdapter(adapter);
        return view;
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(getContext(), HistoryViewActivity.class);
        intent.putExtra("View result",receiptList.get(position).id);
        startActivityForResult(intent, 99);
    }
}
