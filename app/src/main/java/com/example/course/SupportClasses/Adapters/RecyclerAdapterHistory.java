package com.example.course.SupportClasses.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.course.DBClasses.SupReceipt;
import com.example.course.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapterHistory extends RecyclerView.Adapter<RecyclerAdapterHistory.VieHolder> {

    Context context;
    List<SupReceipt> supReceipts;
    OnReceiptItemClick onReceiptItemClick;

    public interface OnReceiptItemClick{
        void onClick(int position);
    }

    public RecyclerAdapterHistory(Context context, List<SupReceipt> supReceipts, OnReceiptItemClick onReceiptItemClick) {
        this.context = context;
        this.supReceipts = supReceipts;
        this.onReceiptItemClick = onReceiptItemClick;
    }

    @NonNull
    @Override
    public VieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(context).inflate(R.layout.item_history, parent, false);
        return new VieHolder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull VieHolder holder, int position) {
        SupReceipt supReceipt = supReceipts.get(position);
        holder.num.setText("Номер заказа "+supReceipt.id);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy в HH:mm");
        Date date = new Date(supReceipt.date);
        holder.date.setText(format.format(date));
    }

    @Override
    public int getItemCount() {
        return supReceipts.size();
    }

    public class VieHolder extends RecyclerView.ViewHolder {
        TextView num;
        TextView date;
        public VieHolder(@NonNull View itemView) {
            super(itemView);
            num = itemView.findViewById(R.id.num_of_check);
            date = itemView.findViewById(R.id.item_date);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onReceiptItemClick.onClick(getAdapterPosition());
                }
            });
        }
    }
}
