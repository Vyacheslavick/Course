package com.example.course.DBClasses.AdditionClasses;

import android.net.Uri;

import com.example.course.SupportClasses.UriConverter;

import androidx.room.TypeConverters;

public class SaleFull {
    public int id;
    public int idDetail;
    public String detailName;
    public String vendor;
    public int count;
    public float cost;
    @TypeConverters({UriConverter.class})
    public Uri image;

    public SaleFull(int id,  int idDetail, String detailName, String vendor, int count, float cost, Uri image) {
        this.id = id;
        this.idDetail = idDetail;
        this.detailName = detailName;
        this.vendor = vendor;
        this.count = count;
        this.cost = cost;
        this.image = image;
    }
}
