package com.example.cyedemo.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.cyedemo.database.QrCodeDao;
import com.example.cyedemo.database.table.QrCodeData;
import com.example.cyedemo.repository.QrCodeRepository;

/**
 * Created by Anik Roy on 2/25/2021.
 */
public class QrCodeViewModel extends AndroidViewModel {

    private final QrCodeRepository qrCodeRepository;
    Application application;
    public final LiveData<PagedList<QrCodeData>> pagedListLiveData;
    QrCodeDao qrCodeDao;

    @ViewModelInject
    public QrCodeViewModel(@NonNull Application application, QrCodeRepository qrCodeRepository, QrCodeDao qrCodeDao) {
        super(application);
        this.application = application;
        this.qrCodeRepository = qrCodeRepository;
        this.qrCodeDao = qrCodeDao;

        int PAGE_SIZE = 5;
        pagedListLiveData = new LivePagedListBuilder<>(qrCodeDao.getAllPaginatedData(), PAGE_SIZE).build();
    }

    public void insertData(QrCodeData qrCodeData) {
        qrCodeRepository.insertData(qrCodeData);
    }

}
