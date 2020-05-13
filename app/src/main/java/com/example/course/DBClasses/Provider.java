package com.example.course.DBClasses;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = "name", unique = true), @Index(value = "telephone", unique = true), @Index(value = "eMail", unique = true)})
public class Provider {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String address;
    public String name;
    public String telephone;
    public String eMail;

    public Provider(int id, String address, String name, String telephone, String eMail) {
        this.id = id;
        this.address = address;
        this.name = name;
        this.telephone = telephone;
        this.eMail = eMail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
