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
import java.io.File;
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
    String id;
    String name;
    String Description;
    String decription2;
    String lat, lng;
    public static final int DRIVER_CAMERA_IMAGE_CODE = 2;
    private ProgressDialog progressDialog;
    private double latitude = 38.282771;
    private double longtitude = -77.647043;
    private ArrayList<String> mImageUrls = new ArrayList<>();
    String imageEncoded;
    String chkpath = null;
    private String mCurrentPhotoPath;
    ArrayList<Data_Model> arrayList;
    MyAdapter m;
    int i = 0;
    static final int REQUEST_TAKE_PHOTO = 1;
    private int SELECT_FILE = 2;
    private String userChoosenTask;
    private Uri fileUri; // file url to store image/video


    /*String[] afterSplitLat = lat.split(" ");
    String[] afterSplitLng = lng.split(" ");

    double latitude = Double.parseDouble(afterSplitLat[0]);

    double longitude = Double.parseDouble(afterSplitLng[1]);*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        arrayList = new ArrayList<>();
        m = new MyAdapter();
        Log.d("oncreate", "set adapter");
        binding.recyclerView.setAdapter(m);
        ViewGroup.LayoutParams params = binding.recyclerView.getLayoutParams();
        params.height = 200;
        binding.recyclerView.setLayoutParams(params);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.recyclerView.setLayoutManager(linearLayoutManager);


        //Back Button
        binding.backButton.setOnClickListener(v -> {
            finish();
        });

        binding.pickPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();

            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);

        ShowDialog(DetailActivity.this);
        DataFetching();

    }

    public void takepicture() {
        Log.d("Cameraclick", "takepicture");

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;


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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO) {

                // Save Image To Gallery
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                File f = new File(mCurrentPhotoPath);
                Uri contentUri = Uri.fromFile(f);
                mediaScanIntent.setData(contentUri);
                this.sendBroadcast(mediaScanIntent);
                String clickpath = mCurrentPhotoPath;
                Data_Model data_model = new Data_Model();
                data_model.setImage(clickpath);
                arrayList.add(data_model);
                binding.recyclerView.setAdapter(m);
                m.notifyDataSetChanged();



            } else if (requestCode == SELECT_FILE) {
                Log.d("gallery", "checkpointd");
                onSelectFromGalleryResult(data);
            }
        }

    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.Myviewholder> {

        @Override
        public Myviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = getLayoutInflater().inflate(R.layout.item, parent, false);
            Myviewholder myviewholder = new Myviewholder(v);
            Log.d("myactivty ", "oncreateViewHolder");

            return myviewholder;
        }

        @Override
        public void onBindViewHolder(Myviewholder holder, final int position) {
            final Data_Model m = arrayList.get(position);
            Log.d(" myactivty", "onBindviewholder" + position);
            //holder.imageView.setVisibility(View.VISIBLE);
            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();
            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 20;
            final Bitmap bitmap = BitmapFactory.decodeFile(m.getImage(), options);
            holder.imageView.setImageBitmap(bitmap);
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), ImageDisplay.class);
                    String chkpath = m.getImage();
                    intent.putExtra("path", chkpath);
                    Log.d("intent", "new activity");
                    startActivity(intent);
                }
            });
            holder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    final int pst = position;
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    arrayList.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, arrayList.size());
                                    // Yes button clicked
                                    Toast.makeText(DetailActivity.this, "Yes Clicked",
                                            Toast.LENGTH_LONG).show();
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    // No button clicked
                                    // do nothing
                                    Toast.makeText(DetailActivity.this, "No Clicked",
                                            Toast.LENGTH_LONG).show();
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
                    builder.setMessage("Are you sure want to Delete ?")
                            .setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();

                    return true;
                }
            });


        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class Myviewholder extends RecyclerView.ViewHolder {
            public ImageView imageView;

            public Myviewholder(View itemView) {
                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.image);
            }
        }
    }

    private void onSelectFromGalleryResult(Intent data) {

        Uri selectedImageUri = data.getData();
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(selectedImageUri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(column_index);
        Data_Model data_model = new Data_Model();
        data_model.setImage(path);
        arrayList.add(data_model);
        m.notifyDataSetChanged();
        // Toast.makeText(this, "" + path, Toast.LENGTH_LONG).show();
    }

    private void selectImage() {

        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = true;

                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        takepicture();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();


                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        Log.d("gallery", "checkpointA");
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
        Log.d("gallery", "checkpointB");
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_TAKE_PHOTO) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(DetailActivity.this, "Camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, DRIVER_CAMERA_IMAGE_CODE);
            } else {
                Toast.makeText(DetailActivity.this, "Camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
// Capture Image From Camera End

    }


}


