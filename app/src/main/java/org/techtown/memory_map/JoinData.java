package org.techtown.memory_map;

import com.google.gson.annotations.SerializedName;

public class JoinData {
    @SerializedName("id")
    private String id;

    @SerializedName("password")
    private String password;

    @SerializedName("nickname")
    private String nickname;

    @SerializedName("email")
    private String email;

    public JoinData(String id, String password, String nickname, String email) {
        this.id=id;
        this.password=password;
        this.nickname=nickname;
        this.email=email;
    }
}
