package com.example.course.SupportClasses.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.course.DBClasses.Provider;
import com.example.course.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class RecyclerAdapterProviders extends RecyclerView.Adapter<RecyclerAdapterProviders.ViewHolder> {

    Context context;
    List<Provider> providers;
    OnProviderItemClick onProviderItemClick;

    public RecyclerAdapterProviders(Context context, List<Provider> providers, OnProviderItemClick onProviderItemClick) {
        this.context = context;
        this.providers = providers;
        this.onProviderItemClick = onProviderItemClick;
    }

    public interface OnProviderItemClick{
        void onClick(int position);
        void onLongClick(int position);
    }
    public void addProvider(Provider provider){
        providers.add(provider);
        notifyDataSetChanged();
    }
    public void addProviderOnPlace(int i, Provider provider){
        providers.add(i,provider);
        notifyDataSetChanged();
    }
    public void deleteProvider(int i){
        providers.remove(i);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(context).inflate(R.layout.item_provider,parent, false);
        return new ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Provider provider = providers.get(position);
        holder.name.setText(provider.name);
        holder.email.setText(provider.eMail);
        holder.telephone.setText(provider.telephone);
    }

    @Override
    public int getItemCount() {
        return providers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
         TextView name;
         TextView telephone;
         TextView email;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_p);
            telephone = itemView.findViewById(R.id.telephone);
            email = itemView.findViewById(R.id.e_mail);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onProviderItemClick.onClick(getAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onProviderItemClick.onLongClick(getAdapterPosition());
                    return true;
                }
            });
        }
    }
}
