package com.example.course.DBClasses;


import android.net.Uri;

import com.example.course.SupportClasses.UriConverter;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(indices = {@Index(value = {"name", "vendor"}, unique = true),
        @Index(value = {"name", "material"}, unique = true)})
public class Detail {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String vendor;
    public String material;
    public int count;
    public short warranty;
    @ColumnInfo(index = true) public String type;
    public String note;
    @TypeConverters({UriConverter.class}) public Uri photo;

    public Detail(String name, String vendor, String material, int count, short warranty, String type, String note,  Uri photo) {
        this.name = name;
        this.vendor = vendor;
        this.material = material;
        this.count = count;
        this.warranty = warranty;
        this.type = type;
        this.note = note;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
