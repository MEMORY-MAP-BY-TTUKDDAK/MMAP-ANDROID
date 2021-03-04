package org.techtown.memory_map;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MapResponse {
    @SerializedName("status")
    private int status;

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<MarkerData> data;

    public String getMessage() {
        return message;
    }

    public List<MarkerData> getData() {
        return data;
    }
}
