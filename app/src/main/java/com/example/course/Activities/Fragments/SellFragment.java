package com.example.course.Activities.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.course.Activities.AddToBucketActivity;
import com.example.course.Activities.AddToSaleActivity;
import com.example.course.DBClasses.AdditionClasses.DetailShort;
import com.example.course.DBClasses.AdditionClasses.SupplyBucket;
import com.example.course.DBClasses.Detail;
import com.example.course.DBClasses.Receipt;
import com.example.course.DBClasses.Sale;
import com.example.course.DBClasses.SupReceipt;
import com.example.course.DBClasses.Supply;
import com.example.course.R;
import com.example.course.SupportClasses.Adapters.AdapterDetailForSale;
import com.example.course.SupportClasses.Adapters.RecyclerAdapterDetails;
import com.example.course.SupportClasses.Adapters.RecyclerAdapterSupplies;
import com.example.course.SupportClasses.App;

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

public class SellFragment extends Fragment implements RecyclerAdapterSupplies.OnBucketItemClick, AdapterDetailForSale.OnDetailItemClick{

    @BindView(R.id.bucket) ImageView bucket;
    @BindView(R.id.search) EditText search;
    @BindView(R.id.list_sells)RecyclerView listSells;
    @BindView(R.id.list_details) RecyclerView listDetails;
    @BindView(R.id.sell) Button sell;
    @BindView(R.id.sells) LinearLayout sells;

    boolean bucketFlag = true;
    LinearLayoutManager layoutManagerDetails;
    LinearLayoutManager layoutManagerSells;
    AdapterDetailForSale adapterDetails;
    RecyclerAdapterSupplies adapterSales;
    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,0);

    SharedPreferences sp;
    int idSell;
    List<Detail> details = App.getInstance().getDatabase().detailDao().detailsInWarehouse("");
    List<SupplyBucket> sellList = App.getInstance().getDatabase().salesDao().sellsInBucket();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sell, container, false);
        ButterKnife.bind(this,view);
        sp = getActivity().getPreferences(Context.MODE_PRIVATE);
        if (sp.getInt("SellId", 0) == 0){
            idSell = (int)App.getInstance().getDatabase().salesDao().insertReceipt(new Receipt(0));
            sp.edit().putInt("SellId", idSell).apply();
        } else {
            idSell = sp.getInt("SellId",0);
        }

        layoutManagerDetails = new LinearLayoutManager(view.getContext());
        listDetails.setLayoutManager(layoutManagerDetails);
        adapterDetails = new AdapterDetailForSale(view.getContext(), details,this);
        listDetails.setAdapter(adapterDetails);

        layoutManagerSells = new LinearLayoutManager(view.getContext());
        listSells.setLayoutManager(layoutManagerSells);
        adapterSales = new RecyclerAdapterSupplies(view.getContext(), sellList,this);
        listSells.setAdapter(adapterSales);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                details = App.getInstance().getDatabase().detailDao().detailsInWarehouse(search.getText().toString());
                adapterDetails = new AdapterDetailForSale(getContext(), details,SellFragment.this);
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
            sells.setLayoutParams(paramsBig);
            sell.setLayoutParams(paramsButton);
            bucketFlag = false;
            search.setEnabled(false);
        } else {
            LinearLayout.LayoutParams paramsBig = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 10);
            sell.setLayoutParams(params);
            sells.setLayoutParams(params);
            listDetails.setLayoutParams(paramsBig);
            bucketFlag = true;
            search.setEnabled(true);
        }
    }

    @OnClick(R.id.sell)
    void setSell(){
        if (sp.getInt("SellId", 0) != 0) {
            for (SupplyBucket supplyBucket: sellList){
                App.getInstance().getDatabase().detailDao().updateDetailMinusWithId(supplyBucket.idDet, supplyBucket.count);
            }
            Receipt receipt = new Receipt(new Date().getTime());
            receipt.id = sp.getInt("SellId", 0);
            App.getInstance().getDatabase().salesDao().updateReceipt(receipt);
            sellList.removeAll(sellList);
            adapterSales.notifyDataSetChanged();
            adapterSales = new RecyclerAdapterSupplies(getContext(), sellList,SellFragment.this);
            listSells.setAdapter(adapterSales);
            sp.edit().clear().apply();
        }
    }





    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            Sale sale = App.getInstance().getDatabase().salesDao().saleWithId(data.getIntExtra("SaleId", 0));
            Uri photo;
            int did = data.getIntExtra("DetailId",0);
            if (data.getStringExtra("Photo").equals("no")) photo = null;
            else photo = Uri.parse(data.getStringExtra("Photo"));
            for (int i = 0; i < details.size(); i++){
                if (details.get(i).id == did){
                    details.get(i).count -= sale.count;
                    adapterDetails.notifyDataSetChanged();
                    break;
                }
            }
            sellList.add(new SupplyBucket(sale.id,data.getIntExtra("DetailId",0),data.getStringExtra("SaleName"),sale.count,photo,sale.cost));
            adapterSales.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(getContext(), AddToSaleActivity.class);
        intent.putExtra("SellId",idSell);
        intent.putExtra("DetailId", details.get(position).id);
        intent.putExtra("Count", details.get(position).count);
        startActivityForResult(intent, 60);
    }

    @Override
    public void onLongClick(int position) {
        if (!bucketFlag){
            App.getInstance().getDatabase().salesDao().deleteSaleWithId(sellList.get(position).id);
            for (int i = 0; i < details.size(); i++){
                if (sellList.get(position).idDet == details.get(i).id){
                    details.get(i).count += sellList.get(position).count;
                    adapterDetails.notifyDataSetChanged();
                    break;
                }
            }
            sellList.remove(position);
            adapterSales.notifyDataSetChanged();
        }
    }
}
