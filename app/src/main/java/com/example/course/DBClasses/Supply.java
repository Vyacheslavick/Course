package com.example.course.DBClasses;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(entity = DetProviders.class, parentColumns = "id",childColumns = "detProvider", onDelete = 5, onUpdate = 5)})
public class Supply {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int detProvider;
    public int idSup;
    public int count;

    public Supply(int detProvider, int count, int idSup) {
        this.detProvider = detProvider;
        this.count = count;
        this.idSup = idSup;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
