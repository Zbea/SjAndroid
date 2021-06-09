package com.hazz.kuangji.mvp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Home implements Serializable {

    public int signed;
    public List<ProductsBean> products;
    public List<CarouselBean> carousel;
    public ClusterBean clusters;
    public List<BoostBean> boost;

    public static class BoostBean implements Serializable{
        /**
         * id : 3
         * name : 加速服务器3
         * desc : 测试
         * old_product_id : 0
         * storage : 600
         * usdt_price : 600.00000000
         * gas_price : 5.00000000
         * pledge_price : 15.00000000
         * term : 540
         * build_term : 20
         * seal_term : 100
         * contract_id : 1
         * status : 1
         * rent_type : 1
         * created_at : 2021-04-08 12:54:50
         * updated_at : 2021-04-15 14:50:01
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
        @SerializedName("pic")
        public String pic;

    }

    public static class ProductsBean implements Serializable {

        public String id;
        public String name;
        public String price;
        public String desc;
        public String coin;
        public String round;
        public String power;
        public String status;
        public String outcome_coin;
        public String rent_type;
        public String pic;
        public String rate;
        public String created_at;
        public String updated_at;
        public String user_balance;
        public String storage;
        public String type;
    }

    public static class CarouselBean {
        /**
         * id : 2
         * url : fffffddaaaaaaaaaaaa
         * title : daaaaaaaaaaaa
         * content : asdfasdfaaaaaaaaaaaaaaaaaa
         * state : 1
         * created_at : 2020-04-28 16:29:12
         * updated_at : 2020-04-28 17:19:09
         */

        public String id;
        public String url;
        public String title;
        public String content;
        public String state;
        public String created_at;
        public String updated_at;
    }

    public static class ClusterBean {

        public String name;
        public String pic;
        public String type;
        public String round;
    }

}
