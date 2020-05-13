package com.example.course.SupportClasses;

import android.app.Application;

import androidx.room.Room;

public class App extends Application {
    public static App instance;

    private FirmDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, FirmDatabase.class, "database").fallbackToDestructiveMigration().allowMainThreadQueries()
                .build();
    }

    public static App getInstance() {
        return instance;
    }

    public FirmDatabase getDatabase() {
        return database;
    }
}
