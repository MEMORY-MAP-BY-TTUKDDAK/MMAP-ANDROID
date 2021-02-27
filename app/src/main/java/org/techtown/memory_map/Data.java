package org.techtown.memory_map;

import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("userId")
    private int userId;

    public int getUserId ()
    {
        return userId;
    }

    public void setUserId (int userId)
    {
        this.userId = userId;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [userId = "+userId+"]";
    }
}
