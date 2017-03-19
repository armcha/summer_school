package com.luseen.yandexsummerschool.data.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {

    private static Api instance = new Api();

    public static Api getInstance() {
        return instance;
    }

    private ApiInterface apiService;

    private Gson getGson() {
        return new GsonBuilder().create();
    }

    private OkHttpClient getHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS);

        return builder.build();
    }

    private Api() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.DEV_URL)
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .client(getHttpClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        apiService = retrofit.create(ApiInterface.class);
    }

    public ApiInterface getApiService() {
        return apiService;
    }
}