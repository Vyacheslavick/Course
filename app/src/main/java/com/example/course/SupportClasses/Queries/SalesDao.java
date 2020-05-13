package com.example.course.SupportClasses.Queries;

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
    void insertReceipt(Receipt receipt);

    @Insert
    void insertSales(Sale... sales);

    @Update
    void updateReceipt(Receipt... receipts);

    @Update
    void updateSales(Sale... sales);

    @Delete
    void deleteReceipt(Receipt... receipts);

    @Delete
    void deleteSale(Sale... sales);

    @Query("Select * from Receipt")
    List<Receipt> receipts();

    @Query("Select * from Sale where idReceipt = :id")
    List<Sale> sales(int id);

    @Query("Select d.name as detailName, s.cost as cost, d.material as material, d.type as type, d.vendor as vendor, s.count as count, p.name as provName , d.photo as image " +
            "from detail d join detproviders dp on (d.id = dp.idDetail) join Provider p on (dp.idProvider = p.id) join sale s on (dp.id = s.detProvider) where s.idReceipt = :idReceipt")
    List<SaleFull> viewSale(int idReceipt);
}
