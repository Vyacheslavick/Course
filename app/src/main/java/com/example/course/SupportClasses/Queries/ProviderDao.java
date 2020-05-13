package com.example.course.SupportClasses.Queries;

import android.widget.ListView;

import com.example.course.DBClasses.AdditionClasses.ProviderForDetail;
import com.example.course.DBClasses.AdditionClasses.ProvidersPrice;
import com.example.course.DBClasses.DetProviders;
import com.example.course.DBClasses.Provider;
import com.example.course.DBClasses.AdditionClasses.ProviderName;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


@Dao
public interface ProviderDao {

    @Delete
    void deleteProvider(Provider provider);

    @Update
    void updateProvider(Provider provider);

    @Insert
    long insertProvider(Provider provider);

    @Insert
    long insertDetProvider(DetProviders detProviders);

    @Delete
    void deleteDetProvider(DetProviders detProviders);

    @Query("Delete from detproviders where idProvider = :idProvider and idDetail = :idDetail")
    void deleteFromAssortment(int idProvider, int idDetail);

    @Query("Select * from provider")
    List<Provider> providers();

    @Query("Select * from provider p where p.id = :id")
    Provider providerWithId(int id);


    @Query("Select name, id from provider order by name asc")
    List<ProviderName> getProvidersNames();

    @Query("Select p.id as id, p.name as name, d.cost as price, max(d.date) as date " +
            " from provider p join detproviders d on (p.id = d.idProvider)" +
            " where d.idDetail = :id group by 1")
    List<ProvidersPrice> getPriceForDetail(int id);

    @Query("Select dp.cost as cost, dp.idProvider as idProvider, p.name as name, dp.id as id " +
            "from provider p join detproviders dp on (p.id = dp.idProvider) join detail d on (dp.idDetail = d.id)" +
            "where d.id = :id group by idProvider having max(dp.date)")
    List<ProviderForDetail> pricesForDetail(int id);

}
