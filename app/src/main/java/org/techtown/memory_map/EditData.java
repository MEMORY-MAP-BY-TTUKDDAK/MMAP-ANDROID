package org.techtown.memory_map;

import com.google.gson.annotations.SerializedName;

public class EditData {
    //@SerializedName("postImg")
    //private String img;

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

    public EditData(String city, String country, String text, double latitude, double longitude, int userIdx, int date, String location) {
        //this.img = img;
        this.city = city;
        this.country = country;
        this.text = text;
        this.latitude = latitude;
        this.longitude = longitude;
        this.userIdx = userIdx;
        this.date = date;
        this.location = location;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getText() {
        return text;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getUserIdx() {
        return userIdx;
    }

    public int getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }
}
