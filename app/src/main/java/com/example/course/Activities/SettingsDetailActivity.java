package com.example.course.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.course.DBClasses.AdditionClasses.ProviderName;
import com.example.course.DBClasses.AdditionClasses.ProvidersPrice;
import com.example.course.SupportClasses.Adapters.RecyclerAdapterCar;
import com.example.course.DBClasses.Car;
import com.example.course.DBClasses.Compatibility;
import com.example.course.DBClasses.DetProviders;
import com.example.course.R;
import com.example.course.SupportClasses.Adapters.RecyclerAdapterPrices;
import com.example.course.SupportClasses.App;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class SettingsDetailActivity extends AppCompatActivity implements RecyclerAdapterPrices.OnPriceItemClick, RecyclerAdapterCar.OnCarItemClick{

    LinearLayoutManager layoutManager;
    LinearLayoutManager layoutManager2;
    @BindView(R.id.providers_with_price_list) RecyclerView list;
    @BindView(R.id.list_cars) RecyclerView carList;
    RecyclerAdapterPrices adapterPrices;
    RecyclerAdapterCar adapterCar;
    List<ProvidersPrice> prices;
    List<ProviderName> providerNames;
    List<Car> cars;
    List<Compatibility> compatibilities;
    List<Car> compatCars;
    @BindView(R.id.providers) Spinner providersSpinner;
    @BindView(R.id.cars) Spinner carsSpinner;

    @BindView(R.id.price) EditText price;
    @BindView(R.id.save) Button save;
    @BindView(R.id.add_car) Button add;
    int detId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_detail);
        ButterKnife.bind(this);

        detId = getIntent().getIntExtra("Id", 0);

        List<String> names = new ArrayList<>();
        prices = App.getInstance().getDatabase().providerDao().getPriceForDetail(detId);
        providerNames = App.getInstance().getDatabase().providerDao().getProvidersNames();
        for (ProviderName providerName : providerNames) {
            names.add(providerName.name);
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, names);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        providersSpinner.setAdapter(adapter);

        layoutManager = new LinearLayoutManager(SettingsDetailActivity.this);
        list.setLayoutManager(layoutManager);
        adapterPrices = new RecyclerAdapterPrices(SettingsDetailActivity.this, prices, this);
        list.setAdapter(adapterPrices);


        List<String> carNames = new ArrayList<>();
        cars = App.getInstance().getDatabase().compatDao().cars();
        for (Car car: cars){
            carNames.add(car.getName());
        }

        final ArrayAdapter<String> adapterCarSpinner = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, carNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carsSpinner.setAdapter(adapterCarSpinner);

        compatibilities = App.getInstance().getDatabase().compatDao().getCompatForDetail(detId);
        compatCars = new ArrayList<>();
        for (Compatibility compatibility: compatibilities){
            for (int i = 0; i < cars.size(); i++){
                if (compatibility.idCar == cars.get(i).getId()) {
                    compatCars.add(cars.get(i));
                }
            }
        }

        layoutManager2 = new LinearLayoutManager(SettingsDetailActivity.this);
        carList.setLayoutManager(layoutManager2);
        adapterCar = new RecyclerAdapterCar(this, compatCars, this);
        carList.setAdapter(adapterCar);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!price.getText().toString().isEmpty()) {
                    int provId = providerNames.get(providersSpinner.getSelectedItemPosition()).id;
                    DetProviders detProviders = new DetProviders(detId, provId, Float.parseFloat(price.getText().toString()), new Date().getTime());
                    String name = providerNames.get(providersSpinner.getSelectedItemPosition()).name;
                    App.getInstance().getDatabase().providerDao().insertDetProvider(detProviders);


                    for (int i = prices.size() - 1; i >= 0; i--) {
                        if (prices.get(i).name.equals(name)) prices.remove(i);
                        adapterPrices.notifyDataSetChanged();
                    }
                    prices.add(new ProvidersPrice(provId, name, Float.parseFloat(price.getText().toString()), 0));
                    adapterPrices.notifyDataSetChanged();
                    price.setText("");
                } else
                    Toast.makeText(SettingsDetailActivity.this, "Для начала введите стоимость", Toast.LENGTH_LONG).show();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (cars.size()>0) {
                        int carId = cars.get(carsSpinner.getSelectedItemPosition()).getId();
                        int idComp = (int) App.getInstance().getDatabase().compatDao().insertCompat(new Compatibility(0, carId, detId));
                        compatibilities.add(new Compatibility(idComp, cars.get(carsSpinner.getSelectedItemPosition()).getId(), detId));
                        compatCars.add(new Car(carId, cars.get(carsSpinner.getSelectedItemPosition()).getName()));
                        adapterCar.notifyDataSetChanged();
                    }
                } catch (SQLiteConstraintException e){
                    Toast.makeText(SettingsDetailActivity.this, "Данная машина уже совместима", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onLongClick(int position) {
        App.getInstance().getDatabase().providerDao().deleteFromAssortment(prices.get(position).id, detId);
        prices.remove(position);
        adapterPrices.notifyDataSetChanged();
    }

    @Override
    public void onLongCarClick(int position) {
        int carId = compatCars.get(position).getId();
        for (int i = 0; i < compatibilities.size(); i++){
            if (carId == compatibilities.get(i).idCar) {
                App.getInstance().getDatabase().compatDao().deleteCompatibility(compatibilities.get(i));
                compatCars.remove(position);
                adapterCar.notifyDataSetChanged();
                break;
            }
        }

    }
}
