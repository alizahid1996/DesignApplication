package com.ui.designapplication;

import android.graphics.Bitmap;
import android.net.Uri;

public class imageModel {
    private Uri imageUri;

    public imageModel(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public imageModel() {
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }
}
