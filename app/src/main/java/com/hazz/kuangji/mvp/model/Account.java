package com.hazz.kuangji.mvp.model;

import com.google.gson.annotations.SerializedName;

public class Account {

        @SerializedName("user_register_url")
        public String userRegisterUrl;
        @SerializedName("android_download_url")
        public String androidDownloadUrl;
        @SerializedName("ios_download_url")
        public String iosDownloadUrl;
        @SerializedName("android_version")
        public String androidVersion;
        @SerializedName("ios_version")
        public String iosVersion;
        @SerializedName("user_name")
        public String userName;
        @SerializedName("mobile")
        public String mobile;
        @SerializedName("invite_code")
        public String inviteCode;
        @SerializedName("img")
        public String img;
        public String exist_deposit;
}
