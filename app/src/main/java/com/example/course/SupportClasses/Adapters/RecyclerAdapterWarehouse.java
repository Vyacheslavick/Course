package com.example.course.SupportClasses.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.course.DBClasses.Detail;
import com.example.course.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapterWarehouse extends RecyclerView.Adapter<RecyclerAdapterWarehouse.ViewHolder> {

    Context context;
    List<Detail> details;


    public interface OnDetailItemClick{
        void onClick();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(context).inflate(R.layout.item_detail, parent, false);
        return new ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

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
        }
    }
}
