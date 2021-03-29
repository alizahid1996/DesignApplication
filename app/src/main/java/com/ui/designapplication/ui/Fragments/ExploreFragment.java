package com.ui.designapplication.ui.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ui.designapplication.Adapter.ParksAdapter;
import com.ui.designapplication.Models.myModel;
import com.ui.designapplication.R;
import com.ui.designapplication.databinding.FragmentExploreBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ExploreFragment extends Fragment {

    String id;
    String name;

    String Description;
    String decription2;
    String address;
    String city;
    String streetAddress2;
    String streetAddress;
    String state;
    String zipcode;
    ProgressDialog progressDialog;
    String type, phoneNumber, hoursOfOperation, website, email, longitude, latitude, stayLimit;
    String acres, tags, tagCount, contentImage, file, fileString, lastUpdated, origin, approved;
    String status, likeCount, imageUrl, reviews, photos, weatherForecast;
    double lat = 37.431572;
    double lng = -78.656891;
    String p;

    private ParksAdapter adapter;

    private ArrayList<myModel> models;

    FragmentExploreBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentExploreBinding.inflate(getLayoutInflater(), container, false);
       /*binding.ShenandoahNationalPark.setOnClickListener(v -> {
           Intent intent = new Intent(getActivity(), DetailActivity.class);
           startActivity(intent);
       });

        binding.edith.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), DetailActivity1.class);
            startActivity(intent);
        });
        binding.purcellParkCardview.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), DetailActivity2.class);
            startActivity(intent);
        });
        binding.hillandleParkCardView.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), DetailActivity3.class);
            startActivity(intent);
        });*/
        models = new ArrayList<>();
        DataFetching();
        //DataFeching();
        //DataFeching2();
        //DataFeching3();
        ShowDialog(getActivity());
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(linearLayoutManager);

    }


    private void DataFetching() {
        String url = "https://godiapi.azurewebsites.net/api/cards/getLandingPage";
        StringRequest complainFetchRequest = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONArray jsonArray = jObj.getJSONArray("nearbyCards");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject o = jsonArray.getJSONObject(i);

                        DismissDialog();
                        id = o.getString("id");
                        name = o.getString(("name"));
                        Description = o.getString("description");
                        streetAddress = o.getString("streetAddress");
                        streetAddress2 = o.getString("streetAddress2");
                        state = o.getString("state");
                        city = o.getString("city");
                        zipcode = o.getString("zipCode");
                        decription2 = o.getString("directions");
                        type = o.getString("type");
                        phoneNumber = o.getString(("phoneNumber"));
                        hoursOfOperation = o.getString("hoursOfOperation");
                        website = o.getString("website");
                        email = o.getString("email");
                        latitude = o.getString("lat");
                        longitude = o.getString("long");
                        stayLimit = o.getString("stayLimit");
                        acres = o.getString("acres");
                        tags = o.getString("tags");
                        tagCount = o.getString("tagCount");
                        contentImage = o.getString("contentImage");
                        file = o.getString("file");
                        fileString = o.getString("fileString");
                        lastUpdated = o.getString("lastUpdated");
                        origin = o.getString("origin");
                        approved = o.getString("approved");
                        status = o.getString("status");
                        likeCount = o.getString("likeCount");
                        imageUrl = o.getString("imageUrl");
                        reviews = o.getString("reviews");
                        photos = o.getString("photos");
                        weatherForecast = o.getString("weatherForecast");


                        models.add(new myModel(id, name, Description, streetAddress, streetAddress2, state, city, zipcode, decription2, type, phoneNumber, hoursOfOperation, website, email, latitude, longitude, stayLimit, acres, tags, tagCount, contentImage, file, fileString, lastUpdated, origin, approved, status, likeCount, imageUrl, weatherForecast, reviews, photos));
                    }

                    DismissDialog();

                    adapter = new ParksAdapter(models, getActivity());
                    binding.recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    DismissDialog();
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();

                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        DismissDialog();
                    }

                }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put("lat", ("37.431572"));
                params.put("long", ("-78.656891"));
                params.put("radius", ("40"));
                return params;
            }

        };

        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        queue.add(complainFetchRequest);

    }

    /*private void DataFeching(){
        String url = "https://godiapi.azurewebsites.net/api/cards/getParkById/600f29a49e36c5f0bf6d0565";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
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
                            name2 = response.getString("name");
                            streetAddress = response.getString("streetAddress");
                            city = response.getString("city");
                            state = response.getString("state");
                            zipcode = response.getString("zipCode");


                            Handler delayToshowProgress = new Handler();
                            delayToshowProgress.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    binding.riverCity.setText(name2);
                                    address = streetAddress +","+city+","+state+","+zipcode;
                                    binding.address.setText(address);

                                }
                            }, 1);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(jsonObjectRequest);

    }*/

    /*private void DataFeching2(){
        String url = "https://godiapi.azurewebsites.net/api/cards/getParkById/600f2a8ad36805fcc9248a63";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
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
                            name3 = response.getString("name");
                            streetAddress1 = response.getString("streetAddress");
                            city1 = response.getString("city");
                            state1 = response.getString("state");


                            Handler delayToshowProgress = new Handler();
                            delayToshowProgress.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    binding.purcellpark.setText(name3);
                                    address2 = streetAddress1 +","+city1+","+state1;
                                    binding.purcelladdress.setText(address2);

                                }
                            }, 1);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(jsonObjectRequest);

    }*/

    /*private void DataFeching3(){
        String url = "https://godiapi.azurewebsites.net/api/cards/getParkById/600f2a8fd36805fcc9248ab1";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
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
                            name4 = response.getString("name");
                            streetAddress2 = response.getString("streetAddress");
                            city2 = response.getString("city");
                            state2 = response.getString("state");


                            Handler delayToshowProgress = new Handler();
                            delayToshowProgress.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    binding.hillandlePark.setText(name4);
                                    address3 = streetAddress2 +","+city2+","+state2;
                                    binding.hillandleParkAddress.setText(address3);

                                }
                            }, 1);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(jsonObjectRequest);

    }*/

    public void ShowDialog(Context context) {
        //setting up progress dialog
        progressDialog = new ProgressDialog(context);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    public void DismissDialog() {
        progressDialog.dismiss();
    }
}