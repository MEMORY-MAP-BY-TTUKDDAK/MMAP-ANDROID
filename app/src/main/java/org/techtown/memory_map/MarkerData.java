package org.techtown.memory_map;

import com.google.gson.annotations.SerializedName;

public class MarkerData {
    @SerializedName("markerIdx")
    private int markerIdx;
    @SerializedName("lattitude")
    private double latitude;
    @SerializedName("longtitude")
    private double longitude;

    public double getLatitude(){
        return latitude;
    }
    public double getLongitude(){
        return longitude;
    }
}
