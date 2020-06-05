package com.example.course.Activities;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.course.DBClasses.Detail;
import com.example.course.R;
import com.example.course.SupportClasses.App;
import com.squareup.picasso.Picasso;

public class ReturnActivity extends AppCompatActivity {

    @BindView(R.id.detail_photo) ImageView photo;
    @BindView(R.id.detail_name) TextView name;
    @BindView(R.id.detail_vendor) TextView vendor;
    @BindView(R.id.detail_material) TextView material;
    @BindView(R.id.detail_type) TextView type;
    @BindView(R.id.detail_warranty) TextView warranty;
    @BindView(R.id.detail_count) TextView count;
    @BindView(R.id.detail_note) TextView note;
    @BindView(R.id.return_count) EditText returnCount;

    @OnClick(R.id.history)
    void setHistory(){

    }

    @OnClick(R.id.return_det)
    void setReturn(){
        if (!returnCount.getText().toString().isEmpty()) {
            int dc = Integer.parseInt(returnCount.getText().toString());
            if (dc <= detail.count) {
                App.getInstance().getDatabase().detailDao().updateDetailMinusWithId(detail.id, dc);
                Intent intent = new Intent();
                intent.putExtra("Count", dc);
                setResult(RESULT_OK, intent);
                onBackPressed();
            }
        } else Toast.makeText(this, "Введите кол-во на возврат", Toast.LENGTH_LONG).show();
    }

    Detail detail;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return);
        ButterKnife.bind(this);
        sp = getPreferences(Context.MODE_PRIVATE);
        detail = App.getInstance().getDatabase().detailDao().detailWithId(getIntent().getIntExtra("DetId", 0));
        if (detail.photo != null) Picasso.get().load(detail.photo).centerCrop().resize(600, 600).into(photo);
        name.setText(detail.name);
        vendor.setText(detail.vendor);
        material.setText(detail.material);
        type.setText(detail.type);
        warranty.setText("Гарантия " + detail.warranty);
        count.setText(detail.count + "шт на складе");
        if (!detail.note.isEmpty()) note.setText(detail.note);
    }
}
