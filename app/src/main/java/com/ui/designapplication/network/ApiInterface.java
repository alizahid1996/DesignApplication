package com.ui.designapplication.network;

import com.ui.designapplication.Models.ServerResponse;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {


    @POST("getLandingPage")
    Call<ServerResponse> getResponse(@Body JSONObject jsonObject);


}