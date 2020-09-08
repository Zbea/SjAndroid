package com.hazz.kuangji.mvp.model;

import java.io.Serializable;
import java.util.List;

public class Home implements Serializable {

        /**
         * signed : 0
         * products : [{"id":"1","name":"垃圾玩意1a","price":"10.00000000","desc":"这个是个真的矿机，不是资金盘，租用成功后第二天开始产币","coin":"USDT","round":"11","power":"aaab","status":"1","outcome_coin":"FIL","rate":"0.10000000","created_at":"2020-04-05 01:32:34","updated_at":"2020-04-20 16:40:45","user_balance":"10710.00000000"},{"id":"2","name":"ETH币种算力矿机","price":"100.00000000","desc":"这个是一个测试产品","coin":"FIL","round":"1825","power":"1T","status":"1","outcome_coin":"ETH","rate":"0.90000000","created_at":"2020-04-05 02:04:16","updated_at":"2020-04-18 22:22:25","user_balance":"62.13333332"},{"id":"6","name":"算力矿机123","price":"0.00000000","desc":"暂无描述","coin":"USDT","round":"2","power":"0.03000000","status":"1","outcome_coin":"FIL","rate":"1.00000000","created_at":"2020-04-18 21:08:34","updated_at":"2020-04-18 22:22:49","user_balance":"10710.00000000"},{"id":"11","name":"aaaaaaaa","price":"1000.00000000","desc":"暂无描述","coin":"USDT","round":"1825","power":"dddddd","status":"1","outcome_coin":"FIL","rate":"0.10000000","created_at":"2020-04-18 22:45:44","updated_at":"2020-04-18 22:45:44","user_balance":"10710.00000000"}]
         * carousel : [{"id":"2","url":"fffffddaaaaaaaaaaaa","title":"daaaaaaaaaaaa","content":"asdfasdfaaaaaaaaaaaaaaaaaa","state":"1","created_at":"2020-04-28 16:29:12","updated_at":"2020-04-28 17:19:09"},{"id":"3","url":"fffffddasdf","title":"dfdddasdf","content":"asdfasdfasdfsadf","state":"1","created_at":"2020-04-28 16:29:30","updated_at":"2020-04-28 16:29:30"}]
         */

        public int signed;
        public List<ProductsBean> products;
        public List<CarouselBean> carousel;

        public static class ProductsBean implements Serializable{
            /**
             * id : 1
             * name : 垃圾玩意1a
             * price : 10.00000000
             * desc : 这个是个真的矿机，不是资金盘，租用成功后第二天开始产币
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

}
