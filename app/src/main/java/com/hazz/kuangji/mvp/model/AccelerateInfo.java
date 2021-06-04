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
     * id : 2
     * name : 加速服务器2
     * desc : 测试
     * old_product_id : 20
     * storage : 200
     * usdt_price : 300.00000000
     * gas_price : 5.00000000
     * pledge_price : 10.00000000
     * term : 540
     * build_term : 30
     * seal_term : 120
     * contract_id : 1
     * status : 1
     * rent_type : 0
     * created_at : 2021-04-08 12:53:05
     * updated_at : 2021-04-20 16:16:36
     * miner_number :
     * pic : /uploads/miner_pic/miner-01.png
     */

    @SerializedName("id")
    public String id;
    @SerializedName("name")
    public String name;
    @SerializedName("desc")
    public String desc;
    @SerializedName("old_product_id")
    public String oldProductId;
    @SerializedName("storage")
    public String storage;
    @SerializedName("usdt_price")
    public String usdtPrice;
    @SerializedName("gas_price")
    public String gasPrice;
    @SerializedName("pledge_price")
    public String pledgePrice;
    @SerializedName("term")
    public String term;
    @SerializedName("build_term")
    public String buildTerm;
    @SerializedName("seal_term")
    public String sealTerm;
    @SerializedName("contract_id")
    public String contractId;
    @SerializedName("status")
    public String status;
    @SerializedName("rent_type")
    public String rentType;
    @SerializedName("created_at")
    public String createdAt;
    @SerializedName("updated_at")
    public String updatedAt;
    @SerializedName("miner_number")
    public String minerNumber;
    @SerializedName("less_boost")
    public String lessBoost;
    public String pic;

}
