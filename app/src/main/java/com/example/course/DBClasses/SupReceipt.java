package com.example.course.DBClasses;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SupReceipt {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public long date;

    public SupReceipt(long date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
