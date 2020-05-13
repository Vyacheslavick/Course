package com.example.course.SupportClasses.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.course.DBClasses.AdditionClasses.DetailShort;
import com.example.course.R;
import com.example.course.SupportClasses.App;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapterDetails extends RecyclerView.Adapter<RecyclerAdapterDetails.ViewHolder> {

    public Context context;
    public List<DetailShort> details;
    public OnDetailsItemClick onDetailsItemClick;

    public interface OnDetailsItemClick{
        void onClick(int position);
        void onLongClick(int position);
    }

    public RecyclerAdapterDetails(Context context, List<DetailShort> details, OnDetailsItemClick onDetailsItemClick) {
        this.context = context;
        this.details = details;
        this.onDetailsItemClick = onDetailsItemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(context).inflate(R.layout.item_detail, parent, false);
        return new ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DetailShort detail = details.get(position);
        if (detail.photo != null) Picasso.get().load(detail.photo).centerCrop().resize(600, 600).into(holder.photo);
        holder.name.setText(detail.name.concat(" ").concat(detail.vendor));
        holder.material.setText(detail.material);
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String s = decimalFormat.format(detail.cost) + " грн";
        holder.price.setText(s);
        holder.type.setText(detail.type);
    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView photo;
        TextView name;
        TextView price;
        TextView material;
        TextView type;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.detail_photo);
            name = itemView.findViewById(R.id.detail_name);
            price = itemView.findViewById(R.id.detail_price);
            material = itemView.findViewById(R.id.detail_material);
            type = itemView.findViewById(R.id.detail_type);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDetailsItemClick.onClick(getAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onDetailsItemClick.onLongClick(getAdapterPosition());
                    return true;
                }
            });
        }
    }
}
