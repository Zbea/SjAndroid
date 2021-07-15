package com.hazz.kuangji.mvp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @Created by Zbea
 * @fileName ValueAdd
 * @date 2021/7/15 11:23
 * @email xiaofeng9212@126.com
 * @describe 增值服务
 **/
public class ValueAdd {


    /**
     * amount : 5.29559294
     * list : [{"id":"1161","price":"5.29559294","remain":"12","create_at":"2021-01-29 15:59:36","status":"0","product_id":"3","current_profit":"0.24712767","profit_time":"2021-07-15 00:00:03","product_name":"增值服务，稳定收入","term":"180","rate":"0.05000000"}]
     */

    @SerializedName("amount")
    public String amount;
    /**
     * id : 1161
     * price : 5.29559294
     * remain : 12
     * create_at : 2021-01-29 15:59:36
     * status : 0
     * product_id : 3
     * current_profit : 0.24712767
     * profit_time : 2021-07-15 00:00:03
     * product_name : 增值服务，稳定收入
     * term : 180
     * rate : 0.05000000
     */

    @SerializedName("list")
    public List<ListBean> list;

    public static class ListBean {
        @SerializedName("id")
        public String id;
        @SerializedName("price")
        public String price;
        @SerializedName("remain")
        public String remain;
        @SerializedName("create_at")
        public String createAt;
        @SerializedName("status")
        public String status;
        @SerializedName("product_id")
        public String productId;
        @SerializedName("current_profit")
        public String currentProfit;
        @SerializedName("profit_time")
        public String profitTime;
        @SerializedName("product_name")
        public String productName;
        @SerializedName("term")
        public String term;
        @SerializedName("rate")
        public String rate;
    }
}
