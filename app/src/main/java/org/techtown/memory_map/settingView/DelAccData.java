package org.techtown.memory_map;

import com.google.gson.annotations.SerializedName;

public class DelAccData {
    @SerializedName("userIdx")
    private int userIdx;

    public DelAccData(int userIdx) {
        this.userIdx = userIdx;
    }

    public int getUserIdx() {
        return userIdx;
    }

    public void setUserIdx(int userIdx) {
        this.userIdx = userIdx;
    }
}
