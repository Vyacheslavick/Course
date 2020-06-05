package com.example.course.Activities.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.course.Activities.AddToBucketActivity;
import com.example.course.DBClasses.AdditionClasses.DetailShort;
import com.example.course.DBClasses.AdditionClasses.SupplyBucket;
import com.example.course.DBClasses.Detail;
import com.example.course.DBClasses.SupReceipt;
import com.example.course.DBClasses.Supply;
import com.example.course.R;
import com.example.course.SupportClasses.Adapters.RecyclerAdapterDetails;
import com.example.course.SupportClasses.Adapters.RecyclerAdapterProviders;
import com.example.course.SupportClasses.Adapters.RecyclerAdapterSupplies;
import com.example.course.SupportClasses.App;

import java.util.ArrayList;
import java.util.Date;
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

public class SupplyFragment extends Fragment implements RecyclerAdapterDetails.OnDetailsItemClick, RecyclerAdapterSupplies.OnBucketItemClick {


    @BindView(R.id.bucket) ImageView bucket;
    @BindView(R.id.filters) ImageView filters;
    @BindView(R.id.do_supply) Button doSupply;
    @BindView(R.id.supplies) LinearLayout supplies;
    @BindView(R.id.list_supplies) RecyclerView listSupplies;
    @BindView(R.id.list_details) RecyclerView listDetails;
    @BindView(R.id.search) EditText search;

    LinearLayoutManager layoutManagerDetails;
    LinearLayoutManager layoutManagerSupplies;
    RecyclerAdapterDetails adapterDetails;
    RecyclerAdapterSupplies adapterSupplies;

    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,0);
    boolean bucketFlag = true;

    List<DetailShort> details = App.getInstance().getDatabase().detailDao().getFullShortDetails("");
    List<SupplyBucket> supplyList = App.getInstance().getDatabase().supplyDao().suppliesInBucket();

    SharedPreferences sp;
    int idSup;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_supply, container, false);
        ButterKnife.bind(this, view);


        sp = getActivity().getPreferences(Context.MODE_PRIVATE);
        SupReceipt sr = App.getInstance().getDatabase().supplyDao().getLastRec();
        if (sr == null) sr = new SupReceipt(0);
        if (sr.date != 0 || sr.id == 0 ){
            idSup = (int)App.getInstance().getDatabase().supplyDao().insertSupReceipt(new SupReceipt(0));
            sp.edit().putInt("SupplyId", idSup).apply();
        } else {
            sp.edit().putInt("SupplyId", sr.id).apply();
            idSup = sp.getInt("SupplyId",0);
        }
        layoutManagerDetails = new LinearLayoutManager(view.getContext());
        listDetails.setLayoutManager(layoutManagerDetails);
        adapterDetails = new RecyclerAdapterDetails(view.getContext(), details,this);
        listDetails.setAdapter(adapterDetails);

        layoutManagerSupplies = new LinearLayoutManager(view.getContext());
        listSupplies.setLayoutManager(layoutManagerSupplies);
        adapterSupplies = new RecyclerAdapterSupplies(view.getContext(), supplyList,this);
        listSupplies.setAdapter(adapterSupplies);


        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                details = App.getInstance().getDatabase().detailDao().getFullShortDetails(search.getText().toString());
                adapterDetails = new RecyclerAdapterDetails(getContext(), details,SupplyFragment.this);
                listDetails.setAdapter(adapterDetails);
                adapterDetails.notifyDataSetChanged();
            }
        });

        return view;
    }

    @OnClick(R.id.bucket)
    void setBucket(){
        if (bucketFlag){
            LinearLayout.LayoutParams paramsBig = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 9);
            LinearLayout.LayoutParams paramsButton = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
            listDetails.setLayoutParams(params);
            supplies.setLayoutParams(paramsBig);
            doSupply.setLayoutParams(paramsButton);
            bucketFlag = false;
            search.setEnabled(false);
        } else {
            LinearLayout.LayoutParams paramsBig = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 10);
            doSupply.setLayoutParams(params);
            supplies.setLayoutParams(params);
            listDetails.setLayoutParams(paramsBig);
            bucketFlag = true;
            search.setEnabled(true);
        }
    }


    @OnClick(R.id.do_supply)
    void setDoSupply(){
        if (sp.getInt("SupplyId", 0) != 0) {
            for (SupplyBucket supplyBucket: supplyList){
                App.getInstance().getDatabase().detailDao().updateDetailWithId(supplyBucket.idDet, supplyBucket.count);
            }
            SupReceipt supReceipt = new SupReceipt(new Date().getTime());
            supReceipt.id = sp.getInt("SupplyId", 0);
            App.getInstance().getDatabase().supplyDao().updateSupReceipt(supReceipt);
            supplyList.removeAll(supplyList);
            adapterSupplies.notifyDataSetChanged();
            adapterSupplies = new RecyclerAdapterSupplies(getContext(), supplyList,SupplyFragment.this);
            listSupplies.setAdapter(adapterSupplies);
            sp.edit().clear().apply();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            Supply supply = App.getInstance().getDatabase().supplyDao().supplyWithId(data.getIntExtra("SupplyId", 0));
            Uri photo;
            if (data.getStringExtra("Photo").equals("no")) photo = null;
            else photo = Uri.parse(data.getStringExtra("Photo"));
            supplyList.add(new SupplyBucket(supply.idSup,data.getIntExtra("DetailId",0),data.getStringExtra("SupplyName"),supply.count,photo,data.getFloatExtra("SupplyCost",0)));
            adapterSupplies.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(int position) {
        if (bucketFlag){
            Intent intent = new Intent(getContext(), AddToBucketActivity.class);
            intent.putExtra("SupplyId",idSup);
            intent.putExtra("DetailId", details.get(position).id);
            startActivityForResult(intent, 60);

        }
    }

    @Override
    public void onLongClick(int position) {
        if (!bucketFlag){
            App.getInstance().getDatabase().supplyDao().deleteSupplyWithId(supplyList.get(position).id);
            supplyList.remove(position);
            adapterSupplies.notifyDataSetChanged();
        }
    }
}
