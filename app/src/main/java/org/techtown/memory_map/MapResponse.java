package org.techtown.memory_map;

import com.google.gson.annotations.SerializedName;

public class MapResponse {
    @SerializedName("status")
    private int status;

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    public class Data{
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

}
