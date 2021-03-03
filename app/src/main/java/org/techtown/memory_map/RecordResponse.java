package org.techtown.memory_map;

import com.google.gson.annotations.SerializedName;

public class RecordResponse {
    @SerializedName("status")
    private int status;

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    public class Data{
        @SerializedName("userIdx")
        private int userIdx;

        @SerializedName("city")
        private String city;

        @SerializedName("country")
        private String country;

        @SerializedName("text")
        private String text;

        @SerializedName("img")
        private String img;
    }

    public int getStatus() { return status; }

    public String getMessage() { return message; }
}
