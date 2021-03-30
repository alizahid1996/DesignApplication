package com.ui.designapplication.Models;

import com.google.gson.annotations.SerializedName;

public class RequestModel {

    private String radius;

    private String lat;

    @SerializedName("long")
    private String longitude;

    public RequestModel(String radius, String lat, String longitude) {

        this.lat = lat;
        this.radius = radius;
        this.longitude = longitude;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
