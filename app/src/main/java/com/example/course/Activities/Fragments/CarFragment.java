package com.example.course.Activities.Fragments;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.course.SupportClasses.Adapters.RecyclerAdapterCar;
import com.example.course.DBClasses.Car;
import com.example.course.R;

import com.example.course.SupportClasses.App;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CarFragment extends Fragment implements RecyclerAdapterCar.OnCarItemClick{

    int flag = 0;
    @BindView(R.id.edit_car) EditText carEdit;
    @BindView(R.id.add_car) Button addCar;
    @BindView(R.id.list_cars) RecyclerView carList;

    LinearLayoutManager layoutManager;
    RecyclerAdapterCar adapter;
    List<Car> cars;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car, container, false);
        ButterKnife.bind(this,view);

        cars = App.getInstance().getDatabase().compatDao().cars();

        layoutManager = new LinearLayoutManager(view.getContext());
        carList.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapterCar(view.getContext(), cars,this);
        carList.setAdapter(adapter);

        return view;
    }

    @OnClick(R.id.add_car)
    void setAddCar(){
        String s = carEdit.getText().toString();
        if (!s.isEmpty()) {
            try {
                int id = (int) App.getInstance().getDatabase().compatDao().insertCar(new Car(0, s));
                cars.add(new Car(id, s));
                adapter.notifyDataSetChanged();
                carEdit.setText("");
            } catch (SQLiteConstraintException e) {
                Toast.makeText(getContext(), "Такая машина уже есть", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onLongCarClick(int position) {
        App.getInstance().getDatabase().compatDao().deleteCar(cars.get(position));
        cars.remove(position);
        adapter.notifyDataSetChanged();
    }
}
