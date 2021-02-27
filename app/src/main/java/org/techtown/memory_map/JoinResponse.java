package org.techtown.memory_map;

import com.google.gson.annotations.SerializedName;

public class JoinResponse {

    @SerializedName("status")
    private int status;

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;
/*
    @SerializedName("data")
    private Data data;*/

    public class Data{
        @SerializedName("userId")
        private int userId;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

}




