package com.example.cyedemo.paging;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.example.cyedemo.Interface.ApiServices;
import com.example.cyedemo.model.IMResponse;
import com.example.cyedemo.model.Image;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Anik Roy on 2/26/2021.
 */
public class ImageDataSource extends PageKeyedDataSource<Integer, Image> {
    public static final int PAGE_SIZE = 10;
    private static final int FIRST_PAGE = 1;
    private final String API_KEY = "8439361-5e1e53a0e1b58baa26ab398f7";
    private final ApiServices apiServices;

    @Inject
    public ImageDataSource(ApiServices apiServices) {
        this.apiServices = apiServices;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Image> callback) {

        Call<IMResponse> call = apiServices.getAllImages(API_KEY, FIRST_PAGE, PAGE_SIZE);

        call.enqueue(new Callback<IMResponse>() {
            @Override
            public void onResponse(Call<IMResponse> call, Response<IMResponse> response) {
                if (response.body() != null) {
                    callback.onResult(response.body().getImages(), null, FIRST_PAGE + 1);
                }
            }

            @Override
            public void onFailure(Call<IMResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Image> callback) {
        Call<IMResponse> call = apiServices.getAllImages(API_KEY, params.key, PAGE_SIZE);

        call.enqueue(new Callback<IMResponse>() {
            @Override
            public void onResponse(Call<IMResponse> call, Response<IMResponse> response) {
                Integer adjacentKey = (params.key > 1) ? params.key - 1 : null;
                if (response.body() != null) {
                    callback.onResult(response.body().getImages(), adjacentKey);
                }
            }

            @Override
            public void onFailure(Call<IMResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Image> callback) {
        Call<IMResponse> call = apiServices.getAllImages(API_KEY, params.key, PAGE_SIZE);

        call.enqueue(new Callback<IMResponse>() {
            @Override
            public void onResponse(Call<IMResponse> call, Response<IMResponse> response) {
                if (response.body() != null && true) {
                    callback.onResult(response.body().getImages(), params.key + 1);
                }
            }

            @Override
            public void onFailure(Call<IMResponse> call, Throwable t) {

            }
        });
    }
}
