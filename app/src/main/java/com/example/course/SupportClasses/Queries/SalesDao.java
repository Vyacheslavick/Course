package com.example.course.SupportClasses.Queries;

import com.example.course.DBClasses.AdditionClasses.FullReceipt;
import com.example.course.DBClasses.AdditionClasses.SupplyBucket;
import com.example.course.DBClasses.Receipt;
import com.example.course.DBClasses.Sale;
import com.example.course.DBClasses.AdditionClasses.SaleFull;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface SalesDao {

    @Insert
    long insertReceipt(Receipt receipt);

    @Insert
    long insertSales(Sale sales);

    @Update
    void updateReceipt(Receipt... receipts);

    @Update
    void updateSales(Sale... sales);

    @Delete
    void deleteReceipt(Receipt... receipts);

    @Delete
    void deleteSale(Sale... sales);

    @Query("Delete from sale where id = :id")
    void deleteSaleWithId(int id);

    @Query("Select r.id as id, r.date as date, sum(s.cost*s.count) as sum from Receipt r join Sale s on (r.id = s.idReceipt) " +
            "where date > 0 group by r.date order by date desc")
    List<FullReceipt> receipts();

    @Query("Select * from Sale where idReceipt = :id")
    List<Sale> sales(int id);

    @Query("Select s.id as id, d.id as idDetail, d.name as detailName, s.cost as cost, d.vendor as vendor, s.count as count , d.photo as image " +
            "from detail d join sale s on (d.id = s.idDetail) where s.idReceipt = :idReceipt")
    List<SaleFull> viewSale(int idReceipt);

    @Query("Select d.id as idDet, s.id as id, d.name as name, s.count as count, d.photo as photo, s.cost as cost " +
            "from receipt r join sale s on (r.id = s.idReceipt) join detail d on (s.idDetail = d.id) " +
            "where r.date = 0" )
    List<SupplyBucket> sellsInBucket();

    @Query("Select * from Sale where id = :id")
    Sale saleWithId(int id);
}
