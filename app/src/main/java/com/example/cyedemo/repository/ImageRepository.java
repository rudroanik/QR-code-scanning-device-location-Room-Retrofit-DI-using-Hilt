package com.example.cyedemo.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.cyedemo.Interface.ApiServices;
import com.example.cyedemo.model.IMResponse;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageRepository {

    ApiServices apiServices;
    MutableLiveData<IMResponse> imageList;
    public static final int PAGE_SIZE = 10;
    private static final int FIRST_PAGE = 1;
    private final String API_KEY = "8439361-5e1e53a0e1b58baa26ab398f7";

    @Inject
    public ImageRepository(ApiServices apiServices, MutableLiveData<IMResponse> imageList) {
        this.apiServices = apiServices;
        this.imageList = imageList;
    }

    public MutableLiveData<IMResponse> getPosts() {
        Call<IMResponse> call = apiServices.getAllImages(API_KEY, FIRST_PAGE, PAGE_SIZE);
        call.enqueue(new Callback<IMResponse>() {
            @Override
            public void onResponse(Call<IMResponse> call, Response<IMResponse> response) {
                if (response.isSuccessful()) {
                    imageList.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<IMResponse> call, Throwable t) {

            }
        });

        return imageList;
    }
}
