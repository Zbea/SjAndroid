package com.hazz.kuangji.mvp.model;

import com.google.gson.annotations.SerializedName;

public class Invite {
    /**
     * user_name : 军
     * img : /uploads/data/profile.png
     * mobile : 139****9999
     * create_at : 2020-05-08 15:48:38
     */
    @SerializedName("user_name")
    public String userName;
    @SerializedName("img")
    public String img;
    @SerializedName("mobile")
    public String mobile;
    @SerializedName("create_at")
    public String createAt;
}
