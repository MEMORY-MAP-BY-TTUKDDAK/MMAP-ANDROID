package org.techtown.memory_map;

import com.google.gson.annotations.SerializedName;

public class DeleteResponse {

    @SerializedName("status")
    private int status;

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private boolean data;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
