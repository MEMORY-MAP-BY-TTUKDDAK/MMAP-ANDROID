package org.techtown.memory_map;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("status")
    private int status;

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    public class Data{
        @SerializedName("userId")
        private String userId;
    }

    public int getStatus() { return status; }

    public String getMessage() { return message; }

}