package com.example.course.Activities.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;

import com.example.course.Activities.AddDetailActivity;
import com.example.course.DBClasses.AdditionClasses.DetailShort;
import com.example.course.R;
import com.example.course.SupportClasses.App;
import com.example.course.SupportClasses.Adapters.RecyclerAdapterDetails;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.app.Activity.RESULT_OK;


public class DetailFragment extends Fragment implements RecyclerAdapterDetails.OnDetailsItemClick{

    EditText search;
    Button button;
    LinearLayoutManager layoutManager;
    RecyclerAdapterDetails adapter;
    List<DetailShort> details;
    RecyclerView list;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        search = view.findViewById(R.id.search);
        button = view.findViewById(R.id.add_detail);

        list = view.findViewById(R.id.list_details);
        details = App.getInstance().getDatabase().detailDao().getShortDetails();
        for (int i = 0; i < details.size(); i++){
            details.get(i).cost = App.getInstance().getDatabase().detailDao().getShortDetailsPrice(details.get(i).id);
        }

        layoutManager = new LinearLayoutManager(view.getContext());
        list.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapterDetails(view.getContext(), details,this);
        list.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddDetailActivity.class);
                intent.putExtra("requestCode", 80);
                startActivityForResult(intent, 80);
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                details = App.getInstance().getDatabase().detailDao().detailShortLike("%"+search.getText().toString()+"%");
                for (int i = 0; i < details.size(); i++){
                    details.get(i).cost = App.getInstance().getDatabase().detailDao().getShortDetailsPrice(details.get(i).id);
                }
                adapter = new RecyclerAdapterDetails(getContext(), details,DetailFragment.this);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            int id = data.getIntExtra("DetailId",0);
            if (requestCode != 80) {
                for (int i = 0; i < details.size(); i++)
                    if (details.get(i).id == id) {
                        details.remove(i);
                        details.add(i, App.getInstance().getDatabase().detailDao().getShortDetailWithId(id));
                        details.get(i).cost = App.getInstance().getDatabase().detailDao().getShortDetailsPrice(details.get(i).id);
                    }
            } else {
                details.add(App.getInstance().getDatabase().detailDao().getShortDetailWithId(id));
                details.get(details.size()-1).cost = App.getInstance().getDatabase().detailDao().getShortDetailsPrice(details.get(details.size()-1).id);
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(getContext(), AddDetailActivity.class);
        intent.putExtra("id", details.get(position).id);
        intent.putExtra("price", details.get(position).cost);
        startActivityForResult(intent, 90);
    }

    @Override
    public void onLongClick(int position) {
        App.getInstance().getDatabase().detailDao().deleteDetailWithId(details.get(position).id);
        details.remove(position);
        adapter.notifyDataSetChanged();
    }
}
