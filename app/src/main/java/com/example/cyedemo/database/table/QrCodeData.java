package com.example.cyedemo.database.table;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Anik Roy on 2/25/2021.
 */
@Entity(tableName = "qrCode_scan")
public class QrCodeData {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    private String data;

    public QrCodeData(String data) {
        this.data = data;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getData() {
        return data;
    }
}
