package com.example.course.SupportClasses;

import com.example.course.DBClasses.Car;

import com.example.course.DBClasses.Compatibility;
import com.example.course.DBClasses.DetProviders;
import com.example.course.DBClasses.Detail;

import com.example.course.DBClasses.Provider;
import com.example.course.DBClasses.Receipt;
import com.example.course.DBClasses.Sale;
import com.example.course.DBClasses.SupReceipt;
import com.example.course.DBClasses.Supply;
import com.example.course.SupportClasses.Queries.CompatDao;
import com.example.course.SupportClasses.Queries.DetailDao;
import com.example.course.SupportClasses.Queries.ProviderDao;
import com.example.course.SupportClasses.Queries.SalesDao;
import com.example.course.SupportClasses.Queries.SupplyDao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Car.class, Compatibility.class, Detail.class, DetProviders.class,
        Provider.class, Receipt.class, Sale.class, Supply.class, SupReceipt.class}, version = 8, exportSchema = false)
public abstract class FirmDatabase extends RoomDatabase {
    public abstract CompatDao compatDao();
    public abstract DetailDao detailDao();
    public abstract ProviderDao providerDao();
    public abstract SalesDao salesDao();
    public abstract SupplyDao supplyDao();
}
