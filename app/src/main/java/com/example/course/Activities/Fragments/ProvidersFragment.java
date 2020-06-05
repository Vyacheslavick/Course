package com.example.course.Activities.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.example.course.Activities.AddProviderActivity;
import com.example.course.DBClasses.Provider;
import com.example.course.R;
import com.example.course.SupportClasses.App;
import com.example.course.SupportClasses.Adapters.RecyclerAdapterProviders;


import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


import static android.app.Activity.RESULT_OK;

public class ProvidersFragment extends Fragment implements RecyclerAdapterProviders.OnProviderItemClick {

    @BindView(R.id.list_provider) RecyclerView list;

    LinearLayoutManager layoutManager;
    RecyclerAdapterProviders adapter;
    List<Provider> providerList;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_providers,container,false);
        ButterKnife.bind(this, view);

        providerList = App.getInstance().getDatabase().providerDao().providers();
        layoutManager = new LinearLayoutManager(view.getContext());
        list.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapterProviders(view.getContext(), providerList,this);
        list.setAdapter(adapter);

        return view;
    }

    @OnClick(R.id.add_provider)
    void setAddProvider(){
        Intent intent = new Intent(getContext(), AddProviderActivity.class);
        startActivityForResult(intent, 98);
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(getContext(), AddProviderActivity.class);
        intent.putExtra("ProviderId", providerList.get(position).id);
        intent.putExtra("ListId", position);
        startActivityForResult(intent, 100);
    }

    @Override
    public void onLongClick(int position) {
        App.getInstance().getDatabase().providerDao().deleteProvider(providerList.get(position));
        providerList.remove(position);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Provider provider = new Provider(data.getIntExtra("ProviderId", 0), data.getStringExtra("ProviderAddress"),
                    data.getStringExtra("ProviderName"),
                    data.getStringExtra("ProviderTelephone"),
                    data.getStringExtra("ProviderEmail"));
            if (requestCode == 98) {
                providerList.add(provider);
                adapter.notifyDataSetChanged();
            } else {
                adapter.deleteProvider((data.getIntExtra("ListId", 0)));
                adapter.addProviderOnPlace(data.getIntExtra("ListId", 0),provider);
            }
        }
    }

}
