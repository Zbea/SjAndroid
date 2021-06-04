package com.hazz.kuangji.mvp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Home implements Serializable {

    /**
     * signed : 0
     * products : [{"id":"1","name":"垃圾玩意1a","price":"10.00000000","desc":"这个是个真的服务器，不是资金盘，租用成功后第二天开始产币","coin":"USDT","round":"11","power":"aaab","status":"1","outcome_coin":"FIL","rate":"0.10000000","created_at":"2020-04-05 01:32:34","updated_at":"2020-04-20 16:40:45","user_balance":"10710.00000000"},{"id":"2","name":"ETH币种算力服务器","price":"100.00000000","desc":"这个是一个测试产品","coin":"FIL","round":"1825","power":"1T","status":"1","outcome_coin":"ETH","rate":"0.90000000","created_at":"2020-04-05 02:04:16","updated_at":"2020-04-18 22:22:25","user_balance":"62.13333332"},{"id":"6","name":"算力服务器123","price":"0.00000000","desc":"暂无描述","coin":"USDT","round":"2","power":"0.03000000","status":"1","outcome_coin":"FIL","rate":"1.00000000","created_at":"2020-04-18 21:08:34","updated_at":"2020-04-18 22:22:49","user_balance":"10710.00000000"},{"id":"11","name":"aaaaaaaa","price":"1000.00000000","desc":"暂无描述","coin":"USDT","round":"1825","power":"dddddd","status":"1","outcome_coin":"FIL","rate":"0.10000000","created_at":"2020-04-18 22:45:44","updated_at":"2020-04-18 22:45:44","user_balance":"10710.00000000"}]
     * carousel : [{"id":"2","url":"fffffddaaaaaaaaaaaa","title":"daaaaaaaaaaaa","content":"asdfasdfaaaaaaaaaaaaaaaaaa","state":"1","created_at":"2020-04-28 16:29:12","updated_at":"2020-04-28 17:19:09"},{"id":"3","url":"fffffddasdf","title":"dfdddasdf","content":"asdfasdfasdfsadf","state":"1","created_at":"2020-04-28 16:29:30","updated_at":"2020-04-28 16:29:30"}]
     */

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
        /**
         * id : 1
         * name : 垃圾玩意1a
         * price : 10.00000000
         * desc : 这个是个真的服务器，不是资金盘，租用成功后第二天开始产币
         * coin : USDT
         * round : 11
         * power : aaab
         * status : 1
         * outcome_coin : FIL
         * rate : 0.10000000
         * created_at : 2020-04-05 01:32:34
         * updated_at : 2020-04-20 16:40:45
         * user_balance : 10710.00000000
         */

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
