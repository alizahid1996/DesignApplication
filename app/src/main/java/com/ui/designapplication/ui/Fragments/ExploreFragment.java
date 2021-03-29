package com.ui.designapplication.ui.Fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.ConnectionCallbacks;
import com.google.android.gms.common.api.internal.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.ui.designapplication.Adapter.ParksAdapter;
import com.ui.designapplication.Models.NearbyCards;
import com.ui.designapplication.Models.ServerResponse;
import com.ui.designapplication.R;
import com.ui.designapplication.databinding.FragmentExploreBinding;
import com.ui.designapplication.network.ApiClient;
import com.ui.designapplication.network.ApiInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

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
    String lat ;
    String lng ;
    String p;
    LocationManager locationManager;
    private ApiInterface apiInterface;
    private static final int REQUEST_Location = 1;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double currentLatitude;
    private double currentLongitude;

    private ParksAdapter adapter;

    private List<NearbyCards> models;

    FragmentExploreBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentExploreBinding.inflate(getLayoutInflater(), container, false);

        models = new ArrayList<>();

        apiInterface = ApiClient.getClient()
                .create(ApiInterface.class);


        //Add Permission
        ActivityCompat.requestPermissions(getActivity(),new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_Location);



        getServerResponse();

        ShowDialog(getActivity());
        return binding.getRoot();
    }

    private void getServerResponse() {

        String request = "{\n" +
                "\"lat\":\"37.412687780\",\n" +
                "\"long\" : \"-77.64786873015784\",\n" +
                "\"radius\":\"20\"\n" +
                "}";

        /*String request = "{\n" +
                "\"lat\":\"37.412687780\",\n" +
                "\"long\" : \"-77.64786873015784\",\n" +
                "\"radius\":\"20\"\n" +
                "}";*/

        try {

            JSONObject jsonObject = new JSONObject(request);

            Call<ServerResponse> call = apiInterface.getResponse(jsonObject);

            call.enqueue(new Callback<ServerResponse>() {
                @Override
                public void onResponse(@NonNull Call<ServerResponse> call,
                                       @NonNull Response<ServerResponse> response) {

                    if (response.isSuccessful() &&
                            response.code() == 200 &&
                            response.body() != null) {

                        ServerResponse serverResponse = response.body();
                        models.addAll(serverResponse.getNearbyCards());

                        // Do any thing with Response

                        /*Toast.makeText(getActivity(),

                                serverResponse.toString(), Toast.LENGTH_LONG).show();

                        Toast.makeText(getActivity(), serverResponse
                                .getNearbyCards().toString(), Toast.LENGTH_LONG).show();*/

                    }
                    DismissDialog();
                    adapter = new ParksAdapter(models, requireActivity());
                    binding.recyclerView.setAdapter(adapter);
                }

                @Override
                public void onFailure(@NonNull Call<ServerResponse> call, @NonNull Throwable t) {

                    Log.d(TAG, "Request Failure -> " + t.getMessage());

                    Toast.makeText(getActivity(),
                            t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        } catch (JSONException exception) {
            DismissDialog();
            exception.printStackTrace();

        }
    }

    @Override
    public void onStart() {
        super.onStart();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(linearLayoutManager);

    }






    /*private void DataFetching() {
        String url = "https://godiapi.azurewebsites.net/api/cards/getLandingPage";
        StringRequest complainFetchRequest = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                    try {
                        JSONObject jObj = new JSONObject(response);
                        JSONArray jsonArray = jObj.getJSONArray("nearbyCards");
                        if (jsonArray.length() == 0) {
                            Toast.makeText(getActivity(), "No Data", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Data Showing", Toast.LENGTH_SHORT).show();
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
                        }

                        DismissDialog();

                        adapter = new ParksAdapter(models, requireActivity());
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
                        Toast.makeText(getActivity(), "Null" + error.getMessage(), Toast.LENGTH_SHORT).show();
                        DismissDialog();
                    }


                })

        {

            @Override
            protected Map<String, String> getParams() {
            // Posting parameters to login url
            Map<String, String> params = new HashMap<>();
            params.put("lat","37.431572");
            params.put("long", "-78.656891");
            params.put("radius", Integer.toString(40));
            Log.i("sending ", params.toString());
            return params;
        }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=utf-8");
                return params;
            }

        };

        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        queue.add(complainFetchRequest);

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