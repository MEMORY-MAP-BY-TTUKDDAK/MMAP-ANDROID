package org.techtown.memory_map;

import com.google.gson.annotations.SerializedName;

public class EditData {
    @SerializedName("city")
    private String city;

    @SerializedName("country")
    private String country;

    @SerializedName("text")
    private String text;

    @SerializedName("lattitude")
    private double latitude;

    @SerializedName("longtitude")
    private double longitude;

    @SerializedName("userIdx")
    private int userIdx;

    @SerializedName("date")
    private int date;

    public EditData(String city, String country, String text, double latitude, double longitude, int userIdx, int date) {
        this.city = city;
        this.country = country;
        this.text = text;
        this.latitude = latitude;
        this.longitude = longitude;
        this.userIdx = userIdx;
        this.date = date;
    }
}
