package com.ui.designapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ui.designapplication.databinding.ActivityDemoBinding;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DemoActivity extends AppCompatActivity {
    ActivityDemoBinding binding;

    Uri imageData;
    public static final int CAMERA_PERMISSION_CODE = 1;
    public static final int CAMERA_IMAGE_CODE = 10;
    public static final int GALLERY_IMAGE_CODE = 100;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDemoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();

        //Pick Image from Gallery
        binding.pickPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choseImage();
            }
        });

        binding.sendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendDataToDb(imageData);
            }
        });
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
                Toast.makeText(DemoActivity.this, "Camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_IMAGE_CODE);
            } else {
                Toast.makeText(DemoActivity.this, "Camera permission denied", Toast.LENGTH_LONG).show();
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
        final StorageReference reference = FirebaseStorage.getInstance().getReference("userImages/" + System.currentTimeMillis() + "");
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
                    Toast.makeText(DemoActivity.this, "Url not generated", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void insertInRealtimeDatabase(String imageUrl) {
        FirebaseDatabase.getInstance().getReference("images").child(System.currentTimeMillis() +"").setValue(imageUrl)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(DemoActivity.this, "Image uploaded", Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(DemoActivity.this, "Image not uploaded", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}