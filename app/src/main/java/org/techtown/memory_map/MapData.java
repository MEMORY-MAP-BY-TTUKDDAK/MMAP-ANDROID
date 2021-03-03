package org.techtown.memory_map;

import com.google.gson.annotations.SerializedName;

public class MapData {
    @SerializedName("id")
    private String id;

    @SerializedName("password")
    private String pw;

    public MapData(String id, String pw) {
        this.id = id;
        this.pw = pw;
    }

}
