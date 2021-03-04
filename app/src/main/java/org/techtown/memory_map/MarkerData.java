package org.techtown.memory_map;

import com.google.gson.annotations.SerializedName;

public class MarkerData {
    @SerializedName("lattitudde")
    private int latitude;
    @SerializedName("longtitude")
    private int longitude;

    public int getLatitude(){
        return latitude;
    }
    public int getLongitude(){
        return longitude;
    }
}
