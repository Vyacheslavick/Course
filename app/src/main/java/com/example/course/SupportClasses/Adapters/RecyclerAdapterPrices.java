package com.example.course.SupportClasses.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.course.DBClasses.AdditionClasses.ProvidersPrice;
import com.example.course.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapterPrices extends RecyclerView.Adapter<RecyclerAdapterPrices.ViewHolder> {

    public Context context;
    public List<ProvidersPrice> prices;
    public OnPriceItemClick onPriceItemClick;

    public interface OnPriceItemClick{
        void onLongClick(int position);
    }

    public RecyclerAdapterPrices(Context context, List<ProvidersPrice> prices, OnPriceItemClick onPriceItemClick) {
        this.context = context;
        this.prices = prices;
        this.onPriceItemClick = onPriceItemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(context).inflate(R.layout.item_provider_price,parent,false);
        return new ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProvidersPrice price = prices.get(position);
        String s = price.name.concat(" ").concat(price.price + "").concat("грн");
        holder.price.setText(s);
    }

    @Override
    public int getItemCount() {
        return prices.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            price = itemView.findViewById(R.id.price);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onPriceItemClick.onLongClick(getAdapterPosition());
                    return true;
                }
            });
        }
    }
}
