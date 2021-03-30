package com.ui.designapplication.network;

import androidx.annotation.NonNull;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String BASE_URL = "https://godiapi.azurewebsites.net/api/";
    private static Retrofit retrofit = null;

    @NonNull
        public static Retrofit getClient() {
            if (retrofit == null) {
                retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create()).build();
            }
            return retrofit;

        }
    }
