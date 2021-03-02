package com.example.cyedemo.repository;

import android.os.AsyncTask;

import com.example.cyedemo.database.QrCodeDao;
import com.example.cyedemo.database.table.QrCodeData;

import javax.inject.Inject;

/**
 * Created by Anik Roy on 2/25/2021.
 */
public class QrCodeRepository {

    private final QrCodeDao qrCodeDao;

    @Inject
    public QrCodeRepository(QrCodeDao qrCodeDao) {
        this.qrCodeDao = qrCodeDao;

    }

    public void insertData(QrCodeData qrCodeData) {
        new insertAsyncTask(qrCodeDao).execute(qrCodeData);
    }

    private static class insertAsyncTask extends AsyncTask<QrCodeData, Void, Void> {

        private final QrCodeDao mAsyncTaskDao;

        insertAsyncTask(QrCodeDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final QrCodeData... params) {
            mAsyncTaskDao.insertData(params[0]);
            return null;
        }
    }

}
