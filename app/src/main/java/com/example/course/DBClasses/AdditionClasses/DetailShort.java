package com.example.course.DBClasses.AdditionClasses;

import android.net.Uri;

import com.example.course.SupportClasses.UriConverter;

import androidx.room.TypeConverters;

public class DetailShort {

    public int id;
    public String name;
    public String vendor;
    public float cost;
    public String material;
    public String type;
    @TypeConverters(UriConverter.class)
    public Uri photo;

    public DetailShort(int id, String name, String vendor, float cost, String material, String type, Uri photo) {
        this.id = id;
        this.name = name;
        this.vendor = vendor;
        this.cost = cost;
        this.material = material;
        this.type = type;
        this.photo = photo;
    }
}
