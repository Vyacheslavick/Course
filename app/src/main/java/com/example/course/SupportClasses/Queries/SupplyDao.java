package com.example.course.SupportClasses.Queries;

import com.example.course.DBClasses.AdditionClasses.SuppCount;
import com.example.course.DBClasses.AdditionClasses.SupplyBucket;
import com.example.course.DBClasses.SupReceipt;
import com.example.course.DBClasses.Supply;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface SupplyDao {

    @Insert
    long insertSupply(Supply supply);

    @Update
    void updateSupply(Supply supplies);

    @Delete
    void deleteSupply(Supply... supplies);

    @Query("Delete from supply where id = :id")
    void deleteSupplyWithId(int id);

    @Query("Select COUNT(s.id) as count, s.idSup as idSup, sr.date as date from Supply s join SupReceipt sr on (s.idSup = sr.id) group by s.idSup order by s.idSup desc ")
    List<SuppCount> supplies();


    @Query("Select * from supply where id = :id")
    Supply supplyWithId(int id);

    @Query("Select d.id as idDet, s.id as id, d.name as name, s.count as count, d.photo as photo, dp.cost as cost " +
            "from supreceipt sr join supply s on (sr.id = s.idSup) join detproviders dp on (s.detProvider = dp.id) join detail d on (dp.idDetail = idDetail) " +
            "where sr.date = 0" )
    List<SupplyBucket> suppliesInBucket();

    @Insert
    long insertSupReceipt(SupReceipt supReceipt);

    @Update
    void updateSupReceipt(SupReceipt supReceipt);

    @Query("Delete from supreceipt where date = 0")
    void deleteBucket();

}
