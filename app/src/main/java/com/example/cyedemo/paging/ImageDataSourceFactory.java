package com.example.cyedemo.paging;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.example.cyedemo.Interface.ApiServices;
import com.example.cyedemo.model.Image;

import javax.inject.Inject;

/**
 * Created by Anik Roy on 2/26/2021.
 */
public class ImageDataSourceFactory extends DataSource.Factory<Integer, Image> {
    private final MutableLiveData<PageKeyedDataSource<Integer, Image>> itemLiveDataSource = new MutableLiveData<>();
    private final ApiServices apiServices;

    @Inject
    public ImageDataSourceFactory(ApiServices apiServices) {
        this.apiServices = apiServices;
    }

    @Override
    public DataSource<Integer, Image> create() {
        ImageDataSource imageDataSource = new ImageDataSource(apiServices);
        itemLiveDataSource.postValue(imageDataSource);
        return imageDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, Image>> getItemLiveDataSource() {
        return itemLiveDataSource;
    }
}
