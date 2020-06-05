package com.example.course.SupportClasses.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.course.DBClasses.AdditionClasses.SupplyBucket;
import com.example.course.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapterSupplies extends RecyclerView.Adapter<RecyclerAdapterSupplies.ViewHolder> {

    public Context context;
    public List<SupplyBucket> bucketList;
    public OnBucketItemClick onBucketItemClick;

    public interface OnBucketItemClick{
        void onLongClick(int position);
    }

    public RecyclerAdapterSupplies(Context context, List<SupplyBucket> bucketList, OnBucketItemClick onBucketItemClick) {
        this.context = context;
        this.bucketList = bucketList;
        this.onBucketItemClick = onBucketItemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(context).inflate(R.layout.item_supply, parent, false);
        return new ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SupplyBucket bucket = bucketList.get(position);
        holder.name.setText(bucket.name);
        holder.count.setText(bucket.count + "шт");
        DecimalFormat format = new DecimalFormat("0.00");
        holder.price.setText(format.format(bucket.cost) + " грн");
        if (bucket.photo != null) Picasso.get().load(bucket.photo).centerCrop().resize(600, 600).into(holder.photo);
    }

    @Override
    public int getItemCount() {
        return bucketList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView photo;
        TextView name;
        TextView price;
        TextView count;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            count = itemView.findViewById(R.id.count);
            photo = itemView.findViewById(R.id.detail_photo);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onBucketItemClick.onLongClick(getAdapterPosition());
                    return true;
                }
            });
        }
    }
}
