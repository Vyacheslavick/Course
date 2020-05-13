package com.example.course.DBClasses.AdditionClasses;

import android.net.Uri;

import com.example.course.SupportClasses.UriConverter;

import androidx.room.TypeConverters;

public class SaleFull {
    public String detailName;
    public String material;
    public String type;
    public String vendor;
    public int count;
    public float cost;
    public String provName;
    @TypeConverters({UriConverter.class})
    public Uri image;

    public SaleFull(String detailName, String material, String type, String vendor, int count, float cost, String provName, Uri image) {
        this.detailName = detailName;
        this.material = material;
        this.type = type;
        this.vendor = vendor;
        this.count = count;
        this.cost = cost;
        this.provName = provName;
        this.image = image;
    }
}
