package com.example.course.Activities;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.course.DBClasses.Detail;
import com.example.course.DBClasses.Sale;
import com.example.course.DBClasses.Supply;
import com.example.course.R;
import com.example.course.SupportClasses.App;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class AddToSaleActivity extends AppCompatActivity {

    @BindView(R.id.detail_photo) ImageView photo;
    @BindView(R.id.name) TextView name;
    @BindView(R.id.avg_price) TextView avgPrice;
    @BindView(R.id.price) EditText price;
    @BindView(R.id.count) EditText count;
    @BindView(R.id.add_to_bucket) Button addToBucket;

    int idDetail;
    int idSell;
    Detail detail;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_sale);
        ButterKnife.bind(this);
        sp = getPreferences(Context.MODE_PRIVATE);
        idDetail = getIntent().getIntExtra("DetailId", 0);
        detail = App.getInstance().getDatabase().detailDao().detailWithId(idDetail);
        detail.count = getIntent().getIntExtra("Count", 0);
        if (detail.photo != null) Picasso.get().load(detail.photo).centerCrop().resize(600, 600).into(photo);
        idSell = getIntent().getIntExtra("SellId", 0);
        List<Float> avgList = App.getInstance().getDatabase().providerDao().averagePriceForDetail(idDetail);
        float avg = 0;
        for (int i = 0; i < avgList.size(); i++) avg += avgList.get(i);
        avg = avg / avgList.size();
        DecimalFormat format = new DecimalFormat("0.00");
        avgPrice.setText("Средняя закупочная: "+ format.format(avg) + "грн");
        name.setText(detail.name);
        addToBucket.setOnClickListener(v -> {
            if (!count.getText().toString().equals("") && !count.getText().toString().equals("")){
                int dc;
                if (Integer.parseInt(count.getText().toString()) <= detail.count){
                    dc = Integer.parseInt(count.getText().toString());
                    Sale sale = new Sale(idSell, idDetail, dc, Float.parseFloat(price.getText().toString()));
                    int saleId = (int) App.getInstance().getDatabase().salesDao().insertSales(sale);
                    Intent intent = new Intent();
                    intent.putExtra("SaleId", saleId);
                    intent.putExtra("SaleName", detail.name);
                    intent.putExtra("DetailId", detail.id);
                    if (detail.photo == null) intent.putExtra("Photo","no");
                    else intent.putExtra("Photo", detail.photo.toString());
                    setResult(RESULT_OK,intent);
                    onBackPressed();
                }
            }
        });
    }
}
