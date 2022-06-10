package com.hazz.kuangji.mvp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Mill {

    @SerializedName("fil")
    public FilBean fil;
    @SerializedName("cluster")
    public FilBean cluster;
    @SerializedName("fil2")
    public FilBean fil2;
    @SerializedName("fil3")
    public FilBean fil3;
    @SerializedName("bzz")
    public BzzBean bzz;
    @SerializedName("chia")
    public ChiaBean chia;

    public static class FilBean {
        @SerializedName("power")
        public String power;
        @SerializedName("total_revenue")
        public String totalRevenue;
        @SerializedName("yesterday_revenue")
        public String yesterdayRevenue;

        @SerializedName("list")
        public List<ListBean> list;


    }

    public static class BzzBean {
        @SerializedName("power")
        public String power;
        @SerializedName("total_revenue")
        public String totalRevenue;
        @SerializedName("yesterday_revenue")
        public String yesterdayRevenue;
        @SerializedName("list")
        public List<ListBean> list;
    }

    public static class ChiaBean{
        @SerializedName("power")
        public String power;
        @SerializedName("total_revenue")
        public String totalRevenue;
        @SerializedName("yesterday_revenue")
        public String yesterdayRevenue;
        @SerializedName("list")
        public List<ListBean> list;
    }
    /**
     * id : 6675
     * user_id : 172
     * cluster_id : 1
     * product_name : eyeball-BM19 FIL 存储服务器
     * power : 0.10
     * stat : 0  //0=正常；1=到期；2=停产
     * power_seal : 0.00000000
     * all_remain : 540 //周期
     * build_remain : 30 //建设期
     * seal_remain : 1 //封装期
     * gas_amount : 0.50000000 //gas
     * revenue : 0.00000000
     * yesterday : 0.00000000
     * pledge_amount : 1.00000000 //质押
     * miner_type : 1  // 0 老矿机  1 已加速服务器   2  集群
     * miner_type_txt : 已加速服务器
     */
    public static class ListBean {
        @SerializedName("id")
        public String id;
        @SerializedName("user_id")
        public String userId;
        @SerializedName("cluster_id")
        public String clusterId;
        @SerializedName("product_name")
        public String productName;
        @SerializedName("power")
        public String power;
        @SerializedName("stat")
        public String stat;
        @SerializedName("power_seal")
        public String powerSeal;
        @SerializedName("all_remain")
        public String allRemain;
        @SerializedName("build_remain")
        public String buildRemain;
        @SerializedName("seal_remain")
        public String sealRemain;
        @SerializedName("gas_amount")
        public String gasAmount;
        @SerializedName("revenue")
        public String revenue;
        @SerializedName("yesterday")
        public String yesterday;
        @SerializedName("pledge_amount")
        public String pledgeAmount;
        @SerializedName("miner_type")
        public String minerType;
        @SerializedName("miner_type_txt")
        public String minerTypeTxt;
        @SerializedName("line_release")
        public String lineRelease;

    }
}
