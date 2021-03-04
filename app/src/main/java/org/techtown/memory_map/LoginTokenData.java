package org.techtown.memory_map;


import com.google.gson.annotations.SerializedName;

public class LoginTokenData {
    @SerializedName("accessToken")
    private String accessToken;

    @SerializedName("userIdx")
    private String userIdx;

    public String getAccessToken() { return accessToken; }

}
