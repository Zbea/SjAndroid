package com.hazz.kuangji.mvp.model;

import com.google.gson.annotations.SerializedName;

public class Agreement {
        /**
         * id : 1
         * title : 测试
         * brief : 测试文章
         * detail : 啊测试文章测试文章测试文章测试文章
         * create_at : 2021-06-07 14:21:48
         */
        @SerializedName("id")
        public String id;
        @SerializedName("title")
        public String title;
        @SerializedName("brief")
        public String brief;
        @SerializedName("detail")
        public String detail;
        @SerializedName("create_at")
        public String createAt;
}
