package com.hazz.kuangji.mvp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChargeRecord {
    /**
     * total : 23
     * pageSize : 30
     * pageNum : 1
     * list : [{"coin":"USDT","create_at":"2020-05-23 06:49:12","amount":"100.00000000"}]
     */

    @SerializedName("total")
    public String total;
    @SerializedName("pageSize")
    public int pageSize;
    @SerializedName("pageNum")
    public int pageNum;
    /**
     * coin : USDT
     * create_at : 2020-05-23 06:49:12
     * amount : 100.00000000
     */

    @SerializedName("list")
    public List<ListBean> list;

    public static class ListBean {
        @SerializedName("coin")
        public String coin;
        @SerializedName("create_at")
        public String createAt;
        @SerializedName("amount")
        public String amount;
    }
}
