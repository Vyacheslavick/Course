package com.example.course.SupportClasses.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.course.DBClasses.Car;
import com.example.course.DBClasses.Compatibility;
import com.example.course.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapterCar extends RecyclerView.Adapter<RecyclerAdapterCar.ViewHolder> {

    public Context context;
    public List<Car> carList;
    public List<Compatibility> compatibilities;
    public OnCarItemClick onCarItemClick;

    public interface OnCarItemClick{
        void onLongCarClick(int position);
    }

    public RecyclerAdapterCar(Context context, List<Car> carList, OnCarItemClick onCarItemClick) {
        this.context = context;
        this.carList = carList;
        this.onCarItemClick = onCarItemClick;
    }

    @NonNull
    @Override
    public RecyclerAdapterCar.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(context).inflate(R.layout.item_provider_price, parent, false);
        return new ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterCar.ViewHolder holder, int position) {
        holder.name.setText(carList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.price);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onCarItemClick.onLongCarClick(getAdapterPosition());
                    return true;
                }
            });
        }
    }
}
