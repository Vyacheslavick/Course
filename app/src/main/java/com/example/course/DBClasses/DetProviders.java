package com.example.course.DBClasses;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(entity = Provider.class, parentColumns = "id",childColumns = "idProvider", onDelete = 5, onUpdate = 5),
        @ForeignKey(entity = Detail.class, parentColumns = "id", childColumns = "idDetail", onDelete = 5, onUpdate = 5)})
public class DetProviders {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int idDetail;
    public int idProvider;
    public float cost;
    public long date;

    public DetProviders(int idDetail, int idProvider, float cost, long date) {
        this.idDetail = idDetail;
        this.idProvider = idProvider;
        this.cost = cost;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
