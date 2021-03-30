package com.ui.designapplication.network;

import com.ui.designapplication.Models.RequestModel;
import com.ui.designapplication.Models.ServerResponse;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {

    @Headers("Content-Type: application/json")
    @POST("cards/getLandingPage")
    Call<ServerResponse> getResponse(@Body RequestModel jsonObject);

}