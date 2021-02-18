package org.techtown.memory_map;

import com.google.gson.annotations.SerializedName;

public class JoinResponse {
    @SerializedName("status")
    private int status;

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private String accessToken;
    private int userIdx;

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return status;
    }
}
