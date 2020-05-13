package com.example.course.DBClasses;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = @Index(value = {"idCar","idDetail"}, unique = true), foreignKeys = {@ForeignKey(entity = Car.class, parentColumns = "id", childColumns = "idCar", onDelete = 5, onUpdate = 5),
        @ForeignKey(entity = Detail.class, parentColumns = "id", childColumns = "idDetail", onDelete = 5, onUpdate = 5)})
public class Compatibility {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public int idCar;
    public int idDetail;

    public Compatibility(long id, int idCar, int idDetail) {
        this.id = id;
        this.idCar = idCar;
        this.idDetail = idDetail;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
