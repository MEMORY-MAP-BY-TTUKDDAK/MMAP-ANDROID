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

    public Record(String city, String country, String text, String img, int user_userIdx) {
        this.city = city;
        this.country = country;
        this.text = text;
        this.img = img;
        this.user_userIdx = user_userIdx;
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

    public void setImg(String img) {
        this.img = img;
    }

    public int getUser_userIdx() {
        return user_userIdx;
    }

    public void setUser_userIdx(int user_userIdx) {
        this.user_userIdx = user_userIdx;
    }

    /*
    int _id;
    String address;
    String locationX;
    String locationY;
    String contents;
    String picture;
    String createDateStr;

    public Record(int _id, String address, String locationX, String locationY, String contents, String picture, String createDateStr) {
        this._id = _id;
        this.address = address;
        this.locationX = locationX;
        this.locationY = locationY;
        this.contents = contents;
        this.picture = picture;
        this.createDateStr = createDateStr;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocationX() {
        return locationX;
    }

    public void setLocationX(String locationX) {
        this.locationX = locationX;
    }

    public String getLocationY() {
        return locationY;
    }

    public void setLocationY(String locationY) {
        this.locationY = locationY;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }
     */
}


