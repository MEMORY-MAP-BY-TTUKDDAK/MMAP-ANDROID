package org.techtown.memory_map;

import com.google.gson.annotations.SerializedName;

public class EditResponse {
    @SerializedName("status")
    private int status;

    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private EditResponseData data;

    public int getStatus() { return status; }

}
