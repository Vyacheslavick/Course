package com.example.course.SupportClasses;

import android.net.Uri;

import androidx.room.TypeConverter;

public class UriConverter {

    @TypeConverter
    public Uri fromString(String s) {
        if (s == null) return null;
        else return Uri.parse(s);
    }

    @TypeConverter
    public String toString(Uri uri) {
        if (uri == null) return null;
        else return uri.toString();
    }
}
