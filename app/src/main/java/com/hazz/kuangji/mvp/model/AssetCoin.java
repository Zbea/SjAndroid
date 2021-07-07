package com.hazz.kuangji.mvp.model;

import com.google.gson.annotations.SerializedName;

/**
 * @Created by Zbea
 * @fileName AssetCoin
 * @date 2021/7/2 16:06
 * @email xiaofeng9212@126.com
 * @describe TODO
 **/
public class AssetCoin {

    /**
     * coin : FIL
     * create_at : 2021-06-15 15:34:19
     * amount : -30.00000000
     * type : 10
     * type_txt : 转入
     */

    @SerializedName("coin")
    public String coin;
    @SerializedName("create_at")
    public String createAt;
    @SerializedName("amount")
    public String amount;
    @SerializedName("type")
    public String type;
    @SerializedName("type_txt")
    public String typeTxt;
}
