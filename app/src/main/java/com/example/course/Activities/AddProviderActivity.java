package com.example.course.Activities;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.course.DBClasses.Provider;
import com.example.course.R;
import com.example.course.SupportClasses.App;

import java.util.List;

public class AddProviderActivity extends AppCompatActivity {

     @BindView(R.id.name_provider) TextView name;
     @BindView(R.id.address_provider) TextView address;
     @BindView(R.id.telephone_provider) TextView telephone;
     @BindView(R.id.e_mail_provider) TextView email;
     @BindView(R.id.name_edit_provider) EditText nameEdit;
     @BindView(R.id.address_edit_provider) EditText addressEdit;
     @BindView(R.id.telephone_edit_provider) EditText telephoneEdit;
     @BindView(R.id.e_mail_edit_provider) EditText emailEdit;
     @BindView(R.id.save_changes) Button saveChanges;

     int providerId;
     Provider providerIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_provider);
        ButterKnife.bind(this);

        providerId = getIntent().getIntExtra("ProviderId", -1);

        if (providerId >-1) {
            providerIntent = App.getInstance().getDatabase().providerDao().providerWithId(providerId);
            nameEdit.setText(providerIntent.name);
            emailEdit.setText(providerIntent.eMail);
            telephoneEdit.setText(providerIntent.telephone);
            addressEdit.setText(providerIntent.address);


        }
    }

    @OnClick(R.id.save_changes)
    void setSaveChanges(){
        if (!nameEdit.getText().toString().isEmpty()) {
            if (!addressEdit.getText().toString().isEmpty()) {
                if (!telephoneEdit.getText().toString().isEmpty()) {

                    Intent intent = new Intent();
                    Provider provider = new Provider(0, addressEdit.getText().toString(), nameEdit.getText().toString(),
                            telephoneEdit.getText().toString(), emailEdit.getText().toString());

                    intent.putExtra("ProviderAddress", provider.address);
                    intent.putExtra("ProviderName", provider.name);
                    intent.putExtra("ProviderTelephone", provider.telephone);
                    intent.putExtra("ProviderEmail", provider.eMail);

                    if (providerId > -1) {
                        try {
                            provider.setId(providerId);
                            App.getInstance().getDatabase().providerDao().updateProvider(provider);
                            intent.putExtra("ProviderId", provider.getId());
                            intent.putExtra("ListId", getIntent().getIntExtra("ListId", 0));
                            setResult(RESULT_OK, intent);
                            onBackPressed();
                        } catch (SQLiteConstraintException e) {
                            Toast.makeText(AddProviderActivity.this, "Поставщик с такими данными уже существует", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        try {
                            int id = (int) App.getInstance().getDatabase().providerDao().insertProvider(provider);
                            intent.putExtra("ProviderId", id);
                            setResult(RESULT_OK, intent);
                            onBackPressed();
                        } catch (SQLiteConstraintException e) {
                            Toast.makeText(AddProviderActivity.this, "Поставщик с такими данными уже существует", Toast.LENGTH_LONG).show();
                        }


                    }

                } else telephone.setText("Телефон - обязательное поле");
            } else address.setText("Адресс - обязательное поле");
        } else name.setText("Название - обязательное поле");
    }

}
