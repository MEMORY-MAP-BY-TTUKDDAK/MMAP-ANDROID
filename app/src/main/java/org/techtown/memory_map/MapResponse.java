package org.techtown.memory_map;

import com.google.gson.annotations.SerializedName;

public class MapResponse {
    @SerializedName("status")
    private int status;

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private MarkerData data;

    public String getMessage() {
        return message;
    }

    public MarkerData getData() {
        return data;
    }
}
