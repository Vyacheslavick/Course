package com.example.course.DBClasses.AdditionClasses;

public class ProvidersPrice {

    public int id;
    public String name;
    public float price;
    long date;

    public ProvidersPrice(int id, String name, float price, long date) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.date = date;
    }
}
