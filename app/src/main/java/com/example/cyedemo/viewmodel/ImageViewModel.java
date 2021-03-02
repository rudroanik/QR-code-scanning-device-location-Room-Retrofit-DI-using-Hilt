package com.example.cyedemo.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.example.cyedemo.Interface.ApiServices;
import com.example.cyedemo.model.Image;
import com.example.cyedemo.paging.ImageDataSource;
import com.example.cyedemo.paging.ImageDataSourceFactory;

/**
 * Created by Anik Roy on 2/26/2021.
 */
public class ImageViewModel extends AndroidViewModel {
    public LiveData<PageKeyedDataSource<Integer, Image>> dataSourceLiveData;
    ApiServices apiServices;

    @ViewModelInject
    public ImageViewModel(@NonNull Application application, ApiServices apiServices) {
        super(application);
        this.apiServices = apiServices;
    }

    public LiveData<PagedList<Image>> getPagedListLiveData() {
        ImageDataSourceFactory factory = new ImageDataSourceFactory(apiServices);
        dataSourceLiveData = factory.getItemLiveDataSource();
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(ImageDataSource.PAGE_SIZE).build();
        return (new LivePagedListBuilder(factory, pagedListConfig)).build();
    }
}
