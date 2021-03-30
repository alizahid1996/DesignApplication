package com.ui.designapplication.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.annotation.NonNull;

public class ServerResponse {

    @SerializedName("nearbyCards")
    private List<NearbyCards> nearbyCards;

    public List<NearbyCards> getNearbyCards() {
        return nearbyCards;
    }

    public void setNearbyCards(List<NearbyCards> nearbyCards) {
        this.nearbyCards = nearbyCards;
    }

    @NonNull
    @Override
    public String toString() {
        return "ServerResponse{" +
                "nearbyCards=" + nearbyCards +
                "}";
    }
}