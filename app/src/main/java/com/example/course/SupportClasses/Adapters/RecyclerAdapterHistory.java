package com.example.course.SupportClasses.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.course.DBClasses.AdditionClasses.SuppCount;
import com.example.course.DBClasses.SupReceipt;
import com.example.course.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapterHistory extends RecyclerView.Adapter<RecyclerAdapterHistory.VieHolder> {

    Context context;
    List<SuppCount> suppCounts;
    OnReceiptItemClick onReceiptItemClick;

    public interface OnReceiptItemClick{
        void onClick(int position);
    }

    public RecyclerAdapterHistory(Context context, List<SuppCount> suppCounts, OnReceiptItemClick onReceiptItemClick) {
        this.context = context;
        this.suppCounts = suppCounts;
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
        SuppCount suppCount = suppCounts.get(position);
        holder.num.setText("Номер заказа "+suppCount.idSup);
        holder.count.setText("Кол-во заказов " + suppCount.count);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date(suppCount.date);
        holder.date.setText(format.format(date));
    }

    @Override
    public int getItemCount() {
        return suppCounts.size();
    }

    public class VieHolder extends RecyclerView.ViewHolder {
        TextView num;
        TextView count;
        TextView date;
        public VieHolder(@NonNull View itemView) {
            super(itemView);
            num = itemView.findViewById(R.id.num_of_check);
            count = itemView.findViewById(R.id.count);
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
