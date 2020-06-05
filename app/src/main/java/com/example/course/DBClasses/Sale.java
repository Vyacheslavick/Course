package com.example.course.DBClasses;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(entity = Receipt.class, parentColumns = "id",childColumns = "idReceipt", onDelete = 5, onUpdate = 5),
        @ForeignKey(entity = Detail.class, parentColumns = "id",childColumns = "idDetail", onDelete = 5, onUpdate = 5)})
public class Sale {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int idReceipt;
    public int idDetail;
    public int count;
    public float cost;

    public Sale( int idReceipt, int idDetail, int count, float cost) {
        this.idReceipt = idReceipt;
        this.idDetail = idDetail;
        this.count = count;
        this.cost = cost;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
