package org.techtown.memory_map;

import com.google.gson.annotations.SerializedName;

public class Record {

    @SerializedName("city")
    private String city;

    @SerializedName("country")
    private String country;

    @SerializedName("text")
    private String text;

    @SerializedName("img")
    private String img;

    @SerializedName("user_userIdx")
    private int user_userIdx;

    @SerializedName("date")
    private int date;

    public Record(String city, String country, String text, String img, int user_userIdx, int date) {
        this.city = city;
        this.country = country;
        this.text = text;
        this.img = img;
        this.user_userIdx = user_userIdx;
        this.date = date;
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
}


