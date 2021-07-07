package com.hazz.kuangji.mvp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Home implements Serializable {


    @SerializedName("product")
    public ProductBean product;
    @SerializedName("banner")
    public List<BannerBean> banner;

    public static class ProductBean implements Serializable{
        /**
         * id : 211
         * name : 矿机名称
         * img :
         * power : 算力存储 T
         * usdt_price : 400.00000000
         * pledge_price : 10.00000000
         * gas_price : 5.00000000
         * build_term : 建设期
         * seal_term : 封装期
         * all_term : 运行周期
         */
        @SerializedName("fil")
        public List<FilBean> fil;
        @SerializedName("chia")
        public List<FilBean> chia;
        @SerializedName("bzz")
        public List<FilBean> bzz;

        public static class FilBean {
            @SerializedName("id")
            public String id;
            @SerializedName("name")
            public String name;
            @SerializedName("img")
            public String img;
            @SerializedName("power")
            public String power;
            @SerializedName("usdt_price")
            public String usdtPrice;
            @SerializedName("pledge_price")
            public String pledgePrice;
            @SerializedName("gas_price")
            public String gasPrice;
            @SerializedName("build_term")
            public String buildTerm;
            @SerializedName("seal_term")
            public String sealTerm;
            @SerializedName("all_term")
            public String allTerm;
        }
    }

    public static class BannerBean {
        @SerializedName("img")
        public String img;
        @SerializedName("name")
        public String name;
        @SerializedName("detail")
        public String detail;
        @SerializedName("id")
        public String id;
    }
}
