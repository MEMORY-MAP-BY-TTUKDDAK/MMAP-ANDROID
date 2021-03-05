package org.techtown.memory_map;


import com.google.gson.annotations.SerializedName;

public class LoginTokenData {
    @SerializedName("accessToken")
    private String accessToken;

    @SerializedName("userIdx")
    private int userIdx;

    public String getAccessToken() { return accessToken; }
    public int getUserIdx() {return userIdx;}
}
