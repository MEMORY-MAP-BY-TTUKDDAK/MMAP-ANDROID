package org.techtown.memory_map;

import com.google.gson.annotations.SerializedName;

public class JoinData {
    @SerializedName("id")
    private String id;

    @SerializedName("password")
    private String password;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    public JoinData(String id, String password, String name, String email) {
        this.id=id;
        this.password=password;
        this.name=name;
        this.email=email;
    }
}
