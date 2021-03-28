package org.techtown.memory_map;

import com.google.gson.annotations.SerializedName;

public class Record {

    @SerializedName("markerIdx")
    private int markerIdx;

    @SerializedName("postImg")
    private String img;

    @SerializedName("city")
    private String city;

    @SerializedName("country")
    private String country;

    @SerializedName("text")
    private String text;

    @SerializedName("date")
    private int date;

    @SerializedName("user_userIdx")
    private int user_userIdx;

    @SerializedName("location")
    private String location;

    @SerializedName("name")
    private String name;

    public Record(int markerIdx, String img, String city, String country, String text, int date, int user_userIdx, String location, String name) {
        this.markerIdx = markerIdx;
        this.img = img;
        this.city = city;
        this.country = country;
        this.text = text;
        this.user_userIdx = user_userIdx;
        this.date = date;
        this.location = location;
        this.name = name;
    }

    public int getMarkerIdx() {
        return markerIdx;
    }

    public void setMarkerIdx(int markerIdx) {
        this.markerIdx = markerIdx;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImg() {
        return img;
    }

    public int getDate() {  return date;  }

    public int getUser_userIdx() {
        return user_userIdx;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setUser_userIdx(int user_userIdx) {
        this.user_userIdx = user_userIdx;
    }

    public String getLocation() {
        return location;
    }
}


