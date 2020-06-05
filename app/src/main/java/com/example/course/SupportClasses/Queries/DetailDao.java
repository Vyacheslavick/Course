package com.example.course.SupportClasses.Queries;

import com.example.course.DBClasses.Detail;
import com.example.course.DBClasses.AdditionClasses.DetailShort;
import com.example.course.DBClasses.Sale;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface DetailDao {

    @Insert
    long insertDetail(Detail detail);

    @Delete
    void deleteDetail(Detail detail);

    @Update
    void updateDetail(Detail detail);

    @Query("Update detail set count = count + :count where id = :id")
    void updateDetailWithId(int id, int count);

    @Query("Update detail set count = count - :count where id = :id")
    void updateDetailMinusWithId(int id, int count);

    @Query("Delete from detail where id = :id")
    void  deleteDetailWithId(int id);

    @Query("Select * from detail ")
    List<Detail> details();

    @Query("Select * from detail where id = :id")
    Detail detailWithId(int id);

    @Query("Select * from detail where count > 0 and (name like '%' || :name || '%' or vendor like '%' || :name || '%' or type like '%' || :name || '%') ")
    List<Detail> detailsInWarehouse(String name);




    @Query("Select id , name, vendor, 0 as cost, material, type, photo " +
            "from detail where name like '%' || :search || '%' or vendor like '%' || :search || '%' or type like '%' || :search || '%' ")
    List<DetailShort> detailShortLike(String search);


    @Query("Select dp.cost " +
            "from detail d join detproviders dp on (d.id = dp.idDetail)" +
            "where d.id = :id group by dp.idProvider having max(date)")
    float getShortDetailsPrice(int id);

    @Query("Select id,  name ,  vendor, 0 as cost,  material,  type,  photo " +
            "from detail ")
    List<DetailShort> getShortDetails();

    @Query("Select d.id as id, d.name as name , d.vendor as vendor, dp.cost as cost, d.material as material, d.type as type, d.photo as photo " +
            "from detail d join detproviders dp on (d.id = dp.idDetail) where d.name like '%' || :name || '%' or d.vendor like '%' || :name || '%' or d.type like '%' || :name || '%' " +
            " group by d.id having max(date)")
    List<DetailShort> getFullShortDetails(String name);

    @Query("Select d.id as id, d.name as name , d.vendor as vendor, 0 as cost, d.material as material, d.type as type, d.photo as photo " +
            "from detail d  where d.id = :id ")
    DetailShort getShortDetailWithId(int id);



}
