package com.ui.designapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ui.designapplication.databinding.ActivityDetail1Binding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class DetailActivity1 extends AppCompatActivity implements OnMapReadyCallback {

    private RequestQueue mQueue;
    ActivityDetail1Binding binding;
    Uri imageData;
    String id;
    String name;
    String Description;
    String address;
    String city;
    String streetAddress;
    String state;
    String zipcode;
    public static final int CAMERA_PERMISSION_CODE = 1;
    public static final int CAMERA_IMAGE_CODE = 10;
    public static final int GALLERY_IMAGE_CODE = 100;
    String decription2;
    String lat, lng;
    public static final int DRIVER_CAMERA_IMAGE_CODE = 2;
    private ProgressDialog progressDialog;
    private double latitude = 37.45042;
    private double longtitude = -77.63554;
    private ArrayList<String> mImageUrls = new ArrayList<>();
    String imageEncoded;
    String chkpath = null;
    private String mCurrentPhotoPath;
    ArrayList<Data_Model> arrayList;
    int i = 0;
    static final int REQUEST_TAKE_PHOTO = 1;
    private int SELECT_FILE = 2;
    private String userChoosenTask;
    private Uri fileUri; // file url to store image/video

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetail1Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        //Back Button
        binding.backButton.setOnClickListener(v -> {
            finish();
        });

        binding.pickPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choseImage();

            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);
        DataFetching();

        ShowDialog(DetailActivity1.this);
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
        String url = "https://godiapi.azurewebsites.net/api/cards/getParkById/600f29a49e36c5f0bf6d0565";
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
                            streetAddress = response.getString("streetAddress");
                            city = response.getString("city");
                            state = response.getString("state");
                            zipcode = response.getString("zipCode");
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
                                    address = streetAddress +","+city+","+state+","+zipcode;
                                    binding.tv3.setText(address);
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
                Toast.makeText(DetailActivity1.this, error.toString(), Toast.LENGTH_LONG).show();
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

    private void choseImage() {
        Intent imageIntent = new Intent();
        imageIntent.setType("image/*");
        imageIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(imageIntent, "Select Picture"), GALLERY_IMAGE_CODE);
    }
    //On Activity Result
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        if (requestCode == GALLERY_IMAGE_CODE && resultCode == RESULT_OK) {
            imageData = imageReturnedIntent.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageData);
                final byte[] inputData = getImageBytes(bitmap);
                binding.image.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //On Request Permission Result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(DetailActivity1.this, "Camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_IMAGE_CODE);
            } else {
                Toast.makeText(DetailActivity1.this, "Camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    public byte[] getImageBytes(Bitmap inImage) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] imageBytes = outputStream.toByteArray();
        return imageBytes;
    }
}