package org.techtown.memory_map;

import com.google.gson.annotations.SerializedName;

public class EditData {
    @SerializedName("img")
    private String img;

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

    @SerializedName("location")
    private String location;

    public EditData(String img, String city, String country, String text, double latitude, double longitude, int userIdx, int date, String location) {
        this.img = img;
        this.city = city;
        this.country = country;
        this.text = text;
        this.latitude = latitude;
        this.longitude = longitude;
        this.userIdx = userIdx;
        this.date = date;
        this.location = location;
    }
}
