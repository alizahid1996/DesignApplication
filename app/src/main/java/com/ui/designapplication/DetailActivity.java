package com.ui.designapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.ui.designapplication.ui.RecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "DetailActivity";
    ActivityDetailBinding binding;
    private RequestQueue mQueue;
    Uri imageData;

    public static final int CAMERA_PERMISSION_CODE = 1;
    public static final int CAMERA_IMAGE_CODE = 10;
    public static final int GALLERY_IMAGE_CODE = 100;

    public static final int DRIVER_CAMERA_IMAGE_CODE = 2;
    private ProgressDialog progressDialog;
    private double latitude = 38.282771;
    private double longtitude = -77.647043;
    private ArrayList<String> mImageUrls = new ArrayList<>();
    ArrayList<Data_Model> arrayList;
    private int SELECT_FILE = 2;

    String id="";
    String name="";

    String Description="";
    String decription2="";
    String address="";
    String city="";
    String streetAddress="";
    String state="";
    String zipcode="";

    String lattitude="";
    String longitude="";
    String imageUrl="";
    String reviews="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            id = bundle.getString("id");
            name = bundle.getString("name");
            Description = bundle.getString("Description");
            decription2 = bundle.getString("decription2");
            city = bundle.getString("city");
            streetAddress = bundle.getString("streetAddress");
            state = bundle.getString("state");
            zipcode = bundle.getString("zipcode");
            lattitude= bundle.getString("latitude");
            longitude = bundle.getString("longitude");
            imageUrl = bundle.getString("imageUrl");
            reviews = bundle.getString("reviews");

        }

      address = streetAddress+""+city+""+state+""+zipcode;
         binding.tv2.setText(name);
        binding.tv3.setText(address);


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

        ShowDialog(DetailActivity.this);

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
                Toast.makeText(DetailActivity.this, "Camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_IMAGE_CODE);
            } else {
                Toast.makeText(DetailActivity.this, "Camera permission denied", Toast.LENGTH_LONG).show();
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


