package com.example.course.SupportClasses.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.course.DBClasses.AdditionClasses.FullReceipt;
import com.example.course.DBClasses.Receipt;
import com.example.course.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapterReceipts extends RecyclerView.Adapter<RecyclerAdapterReceipts.ViewHolder> {

    public List<FullReceipt> receiptList;
    public Context context;
    public OnReceiptItemClick onReceiptItemClick;

    public interface OnReceiptItemClick{
        void onClick(int position);
    }

    public RecyclerAdapterReceipts(List<FullReceipt> receiptList, Context context, OnReceiptItemClick onReceiptItemClick) {
        this.receiptList = receiptList;
        this.context = context;
        this.onReceiptItemClick = onReceiptItemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(context).inflate(R.layout.item_history, parent, false);
        return new ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FullReceipt receipt = receiptList.get(position);
        holder.numCheck.setText("Чек № "+receipt.id);
        Date date = new Date(receipt.date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy 'в' HH:mm");
        DecimalFormat format = new DecimalFormat("0.00");
        holder.date.setText(dateFormat.format(date) + "  " + format.format(receipt.sum));
    }

    @Override
    public int getItemCount() {
        return receiptList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView numCheck;
        TextView date;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.item_date);
            numCheck = itemView.findViewById(R.id.num_of_check);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onReceiptItemClick.onClick(getAdapterPosition());
                }
            });
        }
    }
}
