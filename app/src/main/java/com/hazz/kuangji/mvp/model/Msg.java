package com.hazz.kuangji.mvp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Msg implements Serializable {

        /**
         * total : 1
         * pageNum : 1
         * pageSize : 20
         * list : [{"id":"1","title":"测试","brief":"测试文章","create_at":"2021-06-07 14:21:48"}]
         */
        @SerializedName("total")
        public String total;
        @SerializedName("pageNum")
        public int pageNum;
        @SerializedName("pageSize")
        public int pageSize;
        @SerializedName("list")
        public List<MsgBean> list;

        public static class MsgBean implements Serializable{
                @SerializedName("id")
                public String id;
                @SerializedName("title")
                public String title;
                @SerializedName("brief")
                public String brief;
                @SerializedName("create_at")
                public String createAt;
                public String detail;
        }
}
