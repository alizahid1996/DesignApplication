package com.ui.designapplication;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ui.designapplication.DataClasses.MyData;
import com.ui.designapplication.Models.myModel;
import com.ui.designapplication.databinding.ActivityDetailBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    ActivityDetailBinding binding;
    private RequestQueue mQueue;
    String id;
    String name;
    String Description;
    String decription2;
    String lat, lng;
    private ProgressDialog progressDialog;
    private double latitude = 37.349642;
    private double longtitude = -121.938987;
    Uri uri;
    Bitmap bitmap;
    public myAdpater adpater;
    private ArrayList<imageModel> list;


    /*String[] afterSplitLat = lat.split(" ");
    String[] afterSplitLng = lng.split(" ");

    double latitude = Double.parseDouble(afterSplitLat[0]);

    double longitude = Double.parseDouble(afterSplitLng[1]);*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        list = new ArrayList<>();

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));

        binding.pickPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(i, 100);
            }
        });

        //Back Button
        binding.backButton.setOnClickListener(v -> {
            finish();
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);

        ShowDialog(DetailActivity.this);
        DataFetching();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng sydney = new LatLng(latitude, longtitude);
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng))));
        googleMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("My Marker"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
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
                            decription2 = response.getString("directions");
                            lng = response.getString("long");
                            lat = response.getString("lat");
                            Log.d("name", "lat" + lat);

                            DismissDialog();
                            JSONArray arr = response.getJSONArray("weatherForecast");
                            for (int i = 0; i < arr.length(); i++) {
                                JSONObject jobj = arr.getJSONObject(i);
                            }
                            Handler delayToshowProgress = new Handler();
                            delayToshowProgress.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    binding.tv2.setText(name);
                                    binding.Description.setText(Description);
                                    binding.descriptionDetail.setText(decription2);
                                    DismissDialog();
                                }
                            }, 1);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            // When an image is picked
            if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
                // get the image from data
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                list = new ArrayList<>();
                if (data.getData() != null) {
                    Uri mImageUri = data.getData();

                    // Get the cursor
                    Cursor cursor = getContentResolver().query(mImageUri, filePathColumn, null, null, null);
                    // Move to first row
                    assert cursor != null;
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String imageEncoded = cursor.getString(columnIndex);
                    cursor.close();

                    /*Bitmap mBitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    mBitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), bitmapOptions);
                    file = savebitmap(mBitmap);*/

                    ArrayList<Uri> mArrayUri = new ArrayList<>();
                    mArrayUri.add(mImageUri);
                    adpater = new myAdpater(DetailActivity.this, list);
                    binding.recyclerView.setAdapter(adpater);
                    adpater.notifyDataSetChanged();
                } else {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        ArrayList<Uri> mArrayUri = new ArrayList<>();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {
                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            mArrayUri.add(uri);

                            // Get the cursor
                            Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
                            // Move to first row
                            assert cursor != null;
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            String imageEncoded  = cursor.getString(columnIndex);
                            cursor.close();


                            /*Bitmap mMultipleBitmap;
                            BitmapFactory.Options bo = new BitmapFactory.Options();
                            mMultipleBitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), bo);
                            file = savebitmap(mMultipleBitmap);*/

                            adpater = new myAdpater(DetailActivity.this, list);
                            binding.recyclerView.setAdapter(adpater);
                            adpater.notifyDataSetChanged();
                        }
                        Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                    }
                }
            } else {
                Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

/*    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 || resultCode == RESULT_OK ||
                data != null || data.getData() != null ){
            uri = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                list.add(new imageModel(bitmap));
                adpater = new myAdpater(DetailActivity.this, list);
                binding.recyclerView.setAdapter(adpater);
                //binding.image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }*/


}


