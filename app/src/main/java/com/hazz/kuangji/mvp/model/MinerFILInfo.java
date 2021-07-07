package com.hazz.kuangji.mvp.model;

import com.google.gson.annotations.SerializedName;

/**
 * @Created by Zbea
 * @fileName MinerFILInfo
 * @date 2021/7/2 11:20
 * @email xiaofeng9212@126.com
 * @describe TODO
 **/
public class MinerFILInfo {

    /**
     * id : 22
     * name : eyeball-M19 FIL 存储服务器
     * img :
     * detail :
     * power : 192
     * usdt_price : 900.00000000
     * pledge_price : 0.00000000
     * gas_price : 0.00000000
     * build_term : 30
     * seal_term : 1
     * all_term : 1826
     * create_at : 2021-01-29 15:48:32
     */
    @SerializedName("id")
    public String id;
    @SerializedName("name")
    public String name;
    @SerializedName("img")
    public String img;
    @SerializedName("detail")
    public String detail;
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
    @SerializedName("create_at")
    public String createAt;
}
