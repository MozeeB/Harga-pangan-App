package com.integra.hargapangan.network;

import com.integra.hargapangan.GlobalActivtiy;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {

    private static Retrofit setInitRetrofit() {
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();
        return new Retrofit.Builder()
                .baseUrl(GlobalActivtiy.DATA)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public static ApiService getInitRetrofit() {
        return setInitRetrofit().create(ApiService.class);
    }

    //detail

    private static Retrofit setInitRetrofit2() {
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();
        return new Retrofit.Builder()
                .baseUrl(GlobalActivtiy.URLDETAIL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    //update

    public static ApiService getInitRetrofit2() {
        return setInitRetrofit2().create(ApiService.class);
    }


    //detail

    private static Retrofit setInitRetrofit3() {
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();
        return new Retrofit.Builder()
                .baseUrl(GlobalActivtiy.URLUPDATE)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public static ApiService getInitRetrofit3() {
        return setInitRetrofit3().create(ApiService.class);
    }

}

