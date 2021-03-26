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
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ui.designapplication.DataClasses.MyData;
import com.ui.designapplication.Models.myModel;
import com.ui.designapplication.Models.newModel;
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
import java.util.UUID;

public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "DetailActivity";
    ActivityDetailBinding binding;
    private RequestQueue mQueue;
    String id;
    String name;
    String Description;
    String decription2;
    String lat, lng;
    String img1, img2, img3, img4, img5, img6;
    FirebaseDatabase firebaseDatabase;
    String uniqueId;
    /*public static final int DRIVER_CAMERA_IMAGE_CODE = 2;*/
   /* private ProgressDialog progressDialog;
    private double latitude = 38.282771;
    private double longtitude = -77.647043;
    private ArrayList<String> mImageUrls = new ArrayList<>();
    String imageEncoded;
    String chkpath = null;
    private String mCurrentPhotoPath;
    ArrayList<Data_Model> arrayList;*/

    public static final int CAMERA_PERMISSION_CODE = 1;
    public static final int CAMERA_IMAGE_CODE = 10;
    public static final int GALLERY_IMAGE_CODE = 100;
    Uri imageData;
    public ArrayList<newModel> myList;
    LinearLayoutManager manager;
    newAdapter adapter;
    FirebaseAuth auth;

   /* int i = 0;
    static final int REQUEST_TAKE_PHOTO = 1;
    private int SELECT_FILE = 2;
    private String userChoosenTask;
    private Uri fileUri; // file url to store image/video*/


    /*String[] afterSplitLat = lat.split(" ");
    String[] afterSplitLng = lng.split(" ");

    double latitude = Double.parseDouble(afterSplitLat[0]);

    double longitude = Double.parseDouble(afterSplitLng[1]);*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        manager = new LinearLayoutManager(this);

        myList = new ArrayList<>();
//        binding.recyclerView.setLayoutManager(manager);

        uniqueId = UUID.randomUUID().toString();

        auth = FirebaseAuth.getInstance();


        binding.pickPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choseImage();
            }
        });

        fetchMyData();





    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    private void choseImage() {
        Intent imageIntent = new Intent();
        imageIntent.setType("image/*");
        imageIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(imageIntent, "Select Picture"), GALLERY_IMAGE_CODE);

        binding.sendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendDataToDb(imageData);

            }
        });

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
                binding.myimage.setImageBitmap(bitmap);

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

    private void SendDataToDb(Uri imageData) {
        final StorageReference reference = FirebaseStorage.getInstance().getReference("userImages/" + System.currentTimeMillis()+"");
        reference.putFile(imageData).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return reference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadedUrl = task.getResult();
                    insertInRealtimeDatabase(downloadedUrl.toString());
                } else {
                    Toast.makeText(DetailActivity.this, "Url not generated", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void insertInRealtimeDatabase(String imageUrl) {
        FirebaseDatabase.getInstance().getReference("images").child(System.currentTimeMillis()+"").child("userImage").setValue(imageUrl)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(DetailActivity.this, "Image uploaded", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DetailActivity.this, "Image not uploaded", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void fetchMyData() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        //Get datasnapshot at your "users" root node
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("images");
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        collectPhoneNumbers((Map<String, Object>) dataSnapshot.getValue());
                        myList.add(new newModel());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });
    }

    private void collectPhoneNumbers(Map<String,Object> users) {

        ArrayList<String> phoneNumbers = new ArrayList<>();

        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : users.entrySet()){

            //Get user map
            Map singleUser = (Map) entry.getValue();
            //Get phone field and append to list
            phoneNumbers.add((String) singleUser.get("userImage"));

        }

        System.out.println(phoneNumbers.toString());
    }
}
