package com.ui.designapplication.network;

import androidx.annotation.NonNull;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String BASE_URL = "https://godiapi.azurewebsites.net/api/cards/";

    @NonNull
    public static Retrofit getClient() {

        return new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
    }

}
