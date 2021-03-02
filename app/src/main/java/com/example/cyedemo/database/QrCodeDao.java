package com.example.cyedemo.database;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.cyedemo.database.table.QrCodeData;

/**
 * Created by Anik Roy on 2/25/2021.
 */
@Dao
public interface QrCodeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertData(QrCodeData qrCodeData);

    @Query("SELECT * FROM qrCode_scan")
    DataSource.Factory<Integer, QrCodeData> getAllPaginatedData();

}
