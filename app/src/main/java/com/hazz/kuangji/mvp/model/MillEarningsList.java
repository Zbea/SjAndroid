package com.hazz.kuangji.mvp.model;

import com.google.gson.annotations.SerializedName;

public class MillEarningsList {


    /**
     * id : 5305527
     * user_id : 172
     * type : 11
     * coin : FIL
     * amount : 0.00501619
     * order_id : 2299
     * brief : static
     * detail : null
     * create_at : 2021-05-30 00:00:27
     */

    @SerializedName("id")
    public String id;
    @SerializedName("user_id")
    public String userId;
    @SerializedName("type")
    public String type;
    @SerializedName("coin")
    public String coin;
    @SerializedName("amount")
    public String amount;
    @SerializedName("order_id")
    public String orderId;
    @SerializedName("brief")
    public String brief;
    @SerializedName("detail")
    public Object detail;
    @SerializedName("create_at")
    public String createAt;
}
