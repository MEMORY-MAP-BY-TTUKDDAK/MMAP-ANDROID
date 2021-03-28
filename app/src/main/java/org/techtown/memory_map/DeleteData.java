package org.techtown.memory_map;

import com.google.gson.annotations.SerializedName;

public class DeleteData {

    @SerializedName("markerIdx")
    private int markerIdx;

    public DeleteData(int markerIdx) {
        this.markerIdx = markerIdx;
    }

    public int getMarkerIdx() {
        return markerIdx;
    }

    public void setMarkerIdx(int markerIdx) {
        this.markerIdx = markerIdx;
    }
}
