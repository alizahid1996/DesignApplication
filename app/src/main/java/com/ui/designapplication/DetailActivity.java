package com.ui.designapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ui.designapplication.DataClasses.MyData;
import com.ui.designapplication.databinding.ActivityDetailBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DetailActivity extends AppCompatActivity {

    ActivityDetailBinding binding;
    private RequestQueue mQueue;
    String id;
    String name;
    String Description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.backButton.setOnClickListener(v -> {
            finish();
        });


        Toast.makeText(this, "" + name, Toast.LENGTH_SHORT).show();

         DataFetching();
        //NewDataFetching();
    }


    private void DataFetching() {
        String url = "https://godiapi.azurewebsites.net/api/cards/getParkById/600764a194361a4b14bf2a35";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Fetching data from API and storing into string
                        try {
                            id = response.getString("id");
                            name = response.getString("name");
                            Description = response.getString("description");
                            //str_death = response.getString("deceased");
                            Handler delayToshowProgress = new Handler();
                            delayToshowProgress.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    binding.tv2.setText(name);
                                    binding.Description.setText(Description);
                                   /* binding.affected.setText(NumberFormat.getInstance().format(Integer.parseInt(str_confirmed)));
                                    int_active_new = Integer.parseInt(str_confirmed) - (Integer.parseInt(str_recovered) + Integer.parseInt(str_death));
                                    binding.active.setText("+" + NumberFormat.getInstance().format(int_active_new));

                                    binding.recovered.setText(NumberFormat.getInstance().format(Integer.parseInt(str_recovered)));

                                    binding.death.setText(NumberFormat.getInstance().format(Integer.parseInt(str_death)));

                                    binding.trackingPiechart.invalidate();
                                    setData(Float.parseFloat(str_confirmed),Float.parseFloat(str_recovered),Float.parseFloat(str_death));
*/

                                }
                            }, 1000);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetailActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

}


