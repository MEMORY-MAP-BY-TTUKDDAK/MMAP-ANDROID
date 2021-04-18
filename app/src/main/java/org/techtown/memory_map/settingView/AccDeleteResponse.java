package org.techtown.memory_map;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AccDeleteResponse {
    @SerializedName("status")
    private int status;

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private AccDelResponseData data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AccDelResponseData getData() {
        return data;
    }

    public void setData(AccDelResponseData data) {
        this.data = data;
    }
}
