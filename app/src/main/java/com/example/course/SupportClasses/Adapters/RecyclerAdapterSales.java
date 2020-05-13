package com.example.course.SupportClasses.Adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.course.DBClasses.AdditionClasses.SaleFull;
import com.example.course.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapterSales extends RecyclerView.Adapter<RecyclerAdapterSales.ViewHolder> {

    public Context context;
    public List<SaleFull> sales;

    public RecyclerAdapterSales(Context context, List<SaleFull> sales) {
        this.context = context;
        this.sales = sales;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(context).inflate(R.layout.item_history_view, parent, false);
        return new ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SaleFull sale = sales.get(position);
        holder.imageView.setImageURI(sale.image);
        holder.provider.setText(sale.provName);
        String s = sale.count +" шт.";
        holder.sold.setText(s);
        holder.type.setText(sale.type);
        s = sale.cost + "грн";
        holder.material.setText(sale.material);
        holder.price.setText(s);
        s = sale.detailName + " ("+ sale.vendor + ')';
        holder.name.setText(s);
    }

    @Override
    public int getItemCount() {
        return sales.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name;
        TextView price;
        TextView material;
        TextView type;
        TextView sold;
        TextView provider;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            material = itemView.findViewById(R.id.material);
            type = itemView.findViewById(R.id.type);
            sold = itemView.findViewById(R.id.sold);
            provider = itemView.findViewById(R.id.provider);
        }
    }
}
