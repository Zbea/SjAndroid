package com.hazz.kuangji.mvp.model;

import com.google.gson.annotations.SerializedName;

/**
 * @Created by Zbea
 * @fileName AccelerateInfo
 * @date 2021/4/20 16:19
 * @email xiaofeng9212@126.com
 * @describe TODO
 **/
public class AccelerateInfo {
    /**
     * id : 209  //矿机id
     * name : 20号矿机
     * img : null
     * detail : <span style="white-space:normal;">老用户升级加速</span>
     * usdt_price : 0.00000000 //单T USDT
     * pledge_price : 10.00000000  //单T 质押
     * gas_price : 5.10000000 //单TGAS
     * build_term : 0  //建设期
     * seal_term : 1 //封装期
     * all_term : 540 //周期
     * max_boost_t : 2.85714286 //最大可加速t数
     * order_id :
     */
    @SerializedName("id")
    public String id;
    @SerializedName("name")
    public String name;
    @SerializedName("img")
    public Object img;
    @SerializedName("detail")
    public String detail;
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
    @SerializedName("max_boost_t")
    public String maxBoostT;
    @SerializedName("order_id")
    public String orderId;
}
