package com.example.cyedemo.di;

import android.content.Context;

import androidx.room.Room;

import com.example.cyedemo.database.CyeDatabase;
import com.example.cyedemo.database.QrCodeDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;

/**
 * Created by Anik Roy on 2/26/2021.
 */
@InstallIn(ApplicationComponent.class)
@Module
public class DatabaseModule {

    @Singleton
    @Provides
    public CyeDatabase getDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context.getApplicationContext(), CyeDatabase.class, "cye_database")
                            .fallbackToDestructiveMigration().build();
    }

    @Provides
    public QrCodeDao getDao(CyeDatabase cyeDatabase){
       return cyeDatabase.qrCodeDao();
    }
}
