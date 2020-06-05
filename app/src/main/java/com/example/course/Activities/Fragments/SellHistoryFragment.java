package com.example.course.Activities.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.course.Activities.HistoryViewActivity;
import com.example.course.DBClasses.AdditionClasses.FullReceipt;
import com.example.course.DBClasses.Receipt;
import com.example.course.R;
import com.example.course.SupportClasses.App;
import com.example.course.SupportClasses.Adapters.RecyclerAdapterReceipts;


import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;


public class SellHistoryFragment extends Fragment implements RecyclerAdapterReceipts.OnReceiptItemClick{


    @BindView(R.id.list_history) RecyclerView list;
    @BindView(R.id.sum) TextView sumOfMonth;
    RecyclerAdapterReceipts adapter;
    LinearLayoutManager layoutManager;
    List<FullReceipt> receiptList;
    double sum = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        ButterKnife.bind(this, view);

        receiptList = App.getInstance().getDatabase().salesDao().receipts();

        layoutManager = new LinearLayoutManager(getContext());
        list.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapterReceipts(receiptList, getContext(), this);
        list.setAdapter(adapter);

        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.MONTH, -1);
        Date monthAgo = calendar.getTime();
        for (FullReceipt fullReceipt : receiptList){
            if (monthAgo.before(new Date(fullReceipt.date))) sum += fullReceipt.sum;
        }
        DecimalFormat format = new DecimalFormat("0.00");
        sumOfMonth.setText(format.format(sum) + "грн");
        return view;
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(getContext(), HistoryViewActivity.class);
        intent.putExtra("View result",receiptList.get(position).id);
        startActivityForResult(intent, 99);
    }
}
