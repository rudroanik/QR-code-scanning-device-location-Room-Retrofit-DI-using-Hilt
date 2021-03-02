package com.example.cyedemo.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.cyedemo.database.table.QrCodeData;

/**
 * Created by Anik Roy on 2/25/2021.
 */
@Database(entities = {QrCodeData.class}, version = 2, exportSchema = false)
public abstract class CyeDatabase extends RoomDatabase {

    public abstract QrCodeDao qrCodeDao();

}
