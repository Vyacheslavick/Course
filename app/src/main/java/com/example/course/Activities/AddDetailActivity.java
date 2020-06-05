package com.example.course.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Index;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteConstraintException;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.course.DBClasses.AdditionClasses.ProviderName;
import com.example.course.DBClasses.DetProviders;
import com.example.course.DBClasses.Detail;
import com.example.course.R;
import com.example.course.SupportClasses.App;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.text.InputType.TYPE_CLASS_NUMBER;
import static android.text.InputType.TYPE_CLASS_TEXT;
import static android.text.InputType.TYPE_NULL;

public class AddDetailActivity extends AppCompatActivity {

    @BindView(R.id.detail_photo) ImageView photo;
    @BindView(R.id.detail_name) EditText name;
    @BindView(R.id.detail_vendor) EditText vendor;
    @BindView(R.id.detail_material) EditText material;
    @BindView(R.id.detail_type) EditText type;
    @BindView(R.id.detail_warranty) EditText warranty;
    @BindView(R.id.add_photo) Button addPhoto;
    @BindView(R.id.price) EditText price;
    @BindView(R.id.detail_providers) Spinner providers;
    @BindView(R.id.detail_note) EditText note;
    @BindView(R.id.detail_save) Button save;
    @BindView(R.id.addition_settings) Button settings;
    List<ProviderName> providerNames;
    List<String> names;
    Uri picture;
    Detail detail;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_detail);
        ButterKnife.bind(this);

        sp = getPreferences(Context.MODE_PRIVATE);
        sp.edit().clear().apply();
        App.getInstance().getDatabase().supplyDao().deleteBucket();

        if (getIntent().getIntExtra("requestCode", 0) == 80) settings.setEnabled(false);
        else {
            detail = App.getInstance().getDatabase().detailDao().detailWithId(getIntent().getIntExtra("id", 0));
            picture = detail.photo;

            Picasso.get().load(picture).centerCrop().resize(600, 600).into(photo);
            name.setText(detail.name);
            vendor.setText(detail.vendor);
            material.setText(detail.material);
            type.setText(detail.type);
            warranty.setText(detail.warranty + "");
            if (getIntent().getFloatExtra("price", 0) > 0) {
                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                String s = decimalFormat.format(getIntent().getFloatExtra("price", 0)) + "";
                price.setText(s);
            }
            note.setText(detail.note);
        }

        names = new ArrayList<>();
        providerNames = App.getInstance().getDatabase().providerDao().getProvidersNames();
        names.add("Все поставщики");
        names.add("Ни один поставщик");
        for (ProviderName providerName : providerNames) {
            names.add(providerName.name);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, names);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        providers.setAdapter(adapter);

    }

    @OnClick(R.id.addition_settings)
    void setSettings(){
        Intent intent = new Intent(AddDetailActivity.this, SettingsDetailActivity.class);
        intent.putExtra("Id", detail.id);
        startActivity(intent);
    }

    @OnClick(R.id.add_photo)
    void setAddPhoto(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        startActivityForResult(intent, 50);
    }

    @OnClick(R.id.detail_save)
    void setSave(){
        int id;
        if (!fromEdit(name).isEmpty()) {
            if (!fromEdit(vendor).isEmpty()) {
                if (!fromEdit(material).isEmpty()) {
                    if (!fromEdit(type).isEmpty()) {
                        if (providers.getSelectedItemPosition() != 1) {
                            if (!fromEdit(price).isEmpty()) {
                                try {

                                    short warrantyDetail;
                                    try {
                                        warrantyDetail = Short.parseShort(fromEdit(warranty));
                                    }catch (NumberFormatException e){
                                        warrantyDetail = 0;
                                    }
                                    Detail detail = new Detail(fromEdit(name), fromEdit(vendor), fromEdit(material), 0, warrantyDetail, fromEdit(type), fromEdit(note), picture);

                                    if (getIntent().getIntExtra("requestCode", 0) != 80) {
                                        id = getIntent().getIntExtra("id", 0);
                                        detail.id = id;
                                        App.getInstance().getDatabase().detailDao().updateDetail(detail);
                                    } else {
                                        id = (int) App.getInstance().getDatabase().detailDao().insertDetail(detail);
                                    }

                                    float cost = Float.parseFloat(fromEdit(price).replace(',', '.'));
                                    long time = new Date().getTime();
                                    if (providers.getSelectedItemPosition() == 0) {
                                        for (ProviderName providerName : providerNames) {
                                            DetProviders detProviders = new DetProviders(id, providerName.id, cost, time);
                                            App.getInstance().getDatabase().providerDao().insertDetProvider(detProviders);
                                        }
                                    } else {
                                        DetProviders detProviders = new DetProviders(id, providerNames.get(providers.getSelectedItemPosition() - 2).id, cost, time);
                                        App.getInstance().getDatabase().providerDao().insertDetProvider(detProviders);
                                    }
                                    Intent intent = new Intent();
                                    intent.putExtra("DetailId", id);
                                    setResult(RESULT_OK, intent);

                                    onBackPressed();
                                } catch (SQLiteConstraintException e) {
//                                            Toast.makeText(AddDetailActivity.this, "Такая деталь уже существует", Toast.LENGTH_LONG).show();
                                }

                            } else
                                Toast.makeText(AddDetailActivity.this, "Введите цену детали, либо уберите поставщиков", Toast.LENGTH_LONG).show();
                        } else {

                            try {
                                Detail detail = new Detail(fromEdit(name), fromEdit(vendor), fromEdit(material), 0, Short.parseShort(fromEdit(warranty)), fromEdit(type), fromEdit(note), picture);
                                if (getIntent().getIntExtra("requestCode", 0) != 80) {
                                    id = getIntent().getIntExtra("id", 0);
                                    detail.id = id;
                                    App.getInstance().getDatabase().detailDao().updateDetail(detail);
                                } else {
                                    id = (int) App.getInstance().getDatabase().detailDao().insertDetail(detail);
                                }
                                Intent intent = new Intent();
                                intent.putExtra("DetailId", id);
                                setResult(RESULT_OK, intent);
                                onBackPressed();
                            } catch (SQLiteConstraintException e) {
                                Toast.makeText(AddDetailActivity.this, "Такая деталь уже существует", Toast.LENGTH_LONG).show();
                            }
                        }
                    } else
                        Toast.makeText(AddDetailActivity.this, "Введите тип/раздел детали", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(AddDetailActivity.this, "Введите материал детали", Toast.LENGTH_LONG).show();
            } else
                Toast.makeText(AddDetailActivity.this, "Введите артикул детали", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(AddDetailActivity.this, "Введите название детали", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 50) {
                picture = data.getData();
                Picasso.get().load(picture).resize(600, 600).centerCrop().into(photo);
            }
        }
    }

    public String fromEdit(EditText editText) {
        return editText.getText().toString();
    }
}
