package com.example.course.SupportClasses.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.course.DBClasses.Detail;
import com.example.course.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapterWarehouse extends RecyclerView.Adapter<RecyclerAdapterWarehouse.ViewHolder> {

    Context context;
    List<Detail> details;
    OnDetailItemClick onDetailItemClick;

    public RecyclerAdapterWarehouse(Context context, List<Detail> details, OnDetailItemClick onDetailItemClick) {
        this.context = context;
        this.details = details;
        this.onDetailItemClick = onDetailItemClick;
    }

    public interface OnDetailItemClick{
        void onClick(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(context).inflate(R.layout.item_detail, parent, false);
        return new ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Detail detail = details.get(position);
        holder.type.setText(detail.type);
        holder.material.setText(detail.material);
        holder.count.setText(detail.count+"шт");
        holder.name.setText(detail.name);
        if (detail.photo != null)  Picasso.get().load(detail.photo).centerCrop().resize(600, 600).into(holder.photo);
    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView photo;
        TextView name;
        TextView count;
        TextView material;
        TextView type;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.detail_photo);
            name = itemView.findViewById(R.id.detail_name);
            count = itemView.findViewById(R.id.detail_price);
            material = itemView.findViewById(R.id.detail_material);
            type = itemView.findViewById(R.id.detail_type);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDetailItemClick.onClick(getAdapterPosition());
                }
            });
        }
    }
}
