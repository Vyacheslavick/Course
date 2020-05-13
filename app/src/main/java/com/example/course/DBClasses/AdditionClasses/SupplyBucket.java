package com.example.course.DBClasses.AdditionClasses;

import android.net.Uri;

import com.example.course.SupportClasses.UriConverter;

import androidx.room.TypeConverters;

public class SupplyBucket {
    public int id;
    public int idDet;
    public String name;
    public int count;
    @TypeConverters(UriConverter.class)
    public Uri photo;
    public float cost;

    public SupplyBucket(int id,int idDet,String name, int count, Uri photo, float cost) {
        this.id = id;
        this.idDet = idDet;
        this.name = name;
        this.count = count;
        this.photo = photo;
        this.cost = cost;
    }
}
