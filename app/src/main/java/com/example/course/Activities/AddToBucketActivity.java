package com.example.course.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.course.DBClasses.AdditionClasses.DetailShort;
import com.example.course.DBClasses.AdditionClasses.ProviderForDetail;
import com.example.course.DBClasses.Detail;
import com.example.course.DBClasses.Provider;
import com.example.course.DBClasses.Supply;
import com.example.course.R;
import com.example.course.SupportClasses.App;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AddToBucketActivity extends AppCompatActivity {

    ImageView photo;
    TextView name;
    TextView price;
    EditText count;
    Spinner providersSpinner;
    Button addToBucket;
    List<ProviderForDetail> provider;
    int idDetprov;
    int idDet ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_bucket);
        photo = findViewById(R.id.detail_photo);
        name = findViewById(R.id.name);
        price = findViewById(R.id.price);
        count = findViewById(R.id.count);
        providersSpinner = findViewById(R.id.providers);
        addToBucket = findViewById(R.id.add_to_bucket);

        idDet = getIntent().getIntExtra("DetailId", 0);

        provider = App.getInstance().getDatabase().providerDao().pricesForDetail(idDet);
        idDetprov = provider.get(0).id;

        final DetailShort detail = App.getInstance().getDatabase().detailDao().getShortDetailWithId(getIntent().getIntExtra("DetailId", 0));
        name.setText(detail.name);
        if (detail.photo != null) Picasso.get().load(detail.photo).centerCrop().resize(600, 600).into(photo);

        List<String> providers = new ArrayList<>();
        for (ProviderForDetail p : provider){
            providers.add(p.name);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, providers);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        providersSpinner.setAdapter(adapter);


        providersSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (ProviderForDetail p : provider){
                    if (p.name.equals(providersSpinner.getItemAtPosition(position))){
                        price.setText(p.cost + " грн");
                        idDetprov = p.id;
                        detail.cost = p.cost;
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        addToBucket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!count.getText().toString().equals("")){
                    Supply supply = new Supply(idDetprov, Integer.parseInt(count.getText().toString()),getIntent().getIntExtra("SupplyId", 0));
                    int sup = (int) App.getInstance().getDatabase().supplyDao().insertSupply(supply);
                    Intent intent = new Intent();
                    intent.putExtra("SupplyId", sup);
                    intent.putExtra("SupplyName", detail.name);
                    intent.putExtra("SupplyCost", detail.cost);
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
