package com.example.cyedemo.Interface;

import com.example.cyedemo.model.IMResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Anik Roy on 2/26/2021.
 */
public interface ApiServices {
    @GET("api/")
    Call<IMResponse> getAllImages(@Query("key") String apiKey, @Query("page") int page, @Query("per_page") int perPage);
}
