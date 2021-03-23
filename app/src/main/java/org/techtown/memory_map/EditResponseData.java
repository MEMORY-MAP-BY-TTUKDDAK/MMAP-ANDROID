package org.techtown.memory_map;

import com.google.gson.annotations.SerializedName;

public class EditResponseData {
    @SerializedName("postIdx")
    private int postIdx;

    @SerializedName("postImg")
    private String postImg;

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

    public EditResponseData(int postIdx, String postImg, String city, String country, String text, double latitude, double longitude, int userIdx, int date, String location) {
        this.postIdx = postIdx;
        this.postImg = postImg;
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
