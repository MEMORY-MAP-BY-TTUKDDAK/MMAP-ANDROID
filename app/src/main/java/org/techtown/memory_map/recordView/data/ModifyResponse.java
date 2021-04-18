package org.techtown.memory_map;

import com.google.gson.annotations.SerializedName;

public class ModifyResponse {
    @SerializedName("status")
    private int status;

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private ModifyResponseData data;

    public ModifyResponse(int status, boolean success, String message, ModifyResponseData data){
        this.status = status;
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public ModifyResponseData getData() {
        return data;
    }
}
