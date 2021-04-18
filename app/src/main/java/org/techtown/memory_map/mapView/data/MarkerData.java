package org.techtown.memory_map;

import com.google.gson.annotations.SerializedName;

public class MarkerData {
    @SerializedName("markerIdx")
    private int markerIdx;
    @SerializedName("lattitude")
    private double latitude;
    @SerializedName("longtitude")
    private double longitude;
    @SerializedName("city")
    private String city;
    @SerializedName("country")
    private String country;
    @SerializedName("date")
    private int date;

    public double getLatitude(){
        return latitude;
    }
    public double getLongitude(){
        return longitude;
    }
    public int getDate() { return date;}
    public String getCity() { return city;}
    public String getCountry() { return country;}
}
