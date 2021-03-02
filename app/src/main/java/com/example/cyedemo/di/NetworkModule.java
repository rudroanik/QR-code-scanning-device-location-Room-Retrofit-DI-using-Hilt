package com.example.cyedemo.di;

import com.example.cyedemo.Interface.ApiServices;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Anik Roy on 2/26/2021.
 */
@InstallIn(ApplicationComponent.class)
@Module
public class NetworkModule {
    @Provides
    public String provideBaseUrl(){

        return "https://pixabay.com/";
    }

    @Provides
    public Converter.Factory provideConverterFactory(){
        return GsonConverterFactory.create();
    }

    @Provides
    public Retrofit provideRetrofit(String baseUrl, Converter.Factory converterFactory){

        return new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(converterFactory).build();
    }

    @Provides
    public ApiServices provideApiInterface(Retrofit retro){
        return retro.create(ApiServices.class);
    }
}
