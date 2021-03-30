package org.techtown.memory_map;

import com.google.gson.annotations.SerializedName;

public class NameData {
    @SerializedName("name")
    private String name;

    public NameData(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
