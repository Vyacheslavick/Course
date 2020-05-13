package com.example.course.SupportClasses.Queries;


import com.example.course.DBClasses.Car;
import com.example.course.DBClasses.Compatibility;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface CompatDao {

    @Insert
    long insertCar(Car car);

    @Delete
    void deleteCar(Car car);

    @Query("Select * from car")
    List<Car> cars();

    @Query("Select * from car where name like :name")
    List<Car> carsLike(String name);

    @Query("Select * from detail where id = :id ")
    List<Car> detailsForCarId(int id);

    @Insert
    long insertCompat(Compatibility compatibility);

    @Delete
    void deleteCompatibility(Compatibility compatibility);

    @Query("Select * from compatibility where idDetail = :id")
    List<Compatibility> getCompatForDetail(int id);



}
