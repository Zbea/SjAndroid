package com.hazz.kuangji.mvp.model.bean;

import java.util.List;

public class TouziRecord {


        /**
         * pageNum : 1
         * pageSize : 10
         * total : 7
         * list : [{"id":"56","user_id":"1","coin_id":"1","coin_name":"RVC","amount":"2000.00000000","coins":"6278.41847742","type":"0","rate":"0.03","daily_return":"188.35255432","remain":"12368.48440051","create_at":"2020-03-21 04:09:46","expire_at":"2020-05-27 01:09:46","number":"48dbd3f93fc064460406a00696cfc693","state":"1","updated_at":"2020-03-21 18:49:26","product":"金星池"},{"id":"52","user_id":"1","coin_id":"1","coin_name":"RVC","amount":"4000.00000000","coins":"2.00000000","type":"0","rate":"0.03","daily_return":"0.06000000","remain":"1.94000000","create_at":"2020-03-20 00:32:29","expire_at":"2020-03-20 00:32:29","number":"689072dd78c1fd383c9139787d02cc10","state":"1","updated_at":"2020-03-21 18:49:26","product":"木星池"},{"id":"51","user_id":"1","coin_id":"1","coin_name":"RVC","amount":"4000.00000000","coins":"2.00000000","type":"0","rate":"0.03","daily_return":"0.06000000","remain":"1.94000000","create_at":"2020-03-20 00:32:13","expire_at":"2020-03-20 00:32:13","number":"fc22ba79802507472a96c15d2a48ee8b","state":"1","updated_at":"2020-03-21 18:49:26","product":"木星池"},{"id":"34","user_id":"1","coin_id":"1","coin_name":"ETH","amount":"4000.00000000","coins":"17.02127660","type":"0","rate":"0.03","daily_return":"0.51063830","remain":"31.99999999","create_at":"2020-03-18 16:07:28","expire_at":"2020-05-24 13:07:28","number":"c2f63dcd29921fb83b796ad358e63c7d","state":"1","updated_at":"2020-03-21 18:49:26","product":"木星池"},{"id":"33","user_id":"1","coin_id":"1","coin_name":"ETH","amount":"2000.00000000","coins":"17.21466690","type":"0","rate":"0.03","daily_return":"0.51644001","remain":"32.36357375","create_at":"2020-03-17 22:53:23","expire_at":"2020-05-23 19:53:23","number":"45251546828d67809cf77fc623193e97","state":"1","updated_at":"2020-03-21 18:49:26","product":"金星池"},{"id":"8","user_id":"1","coin_id":"1","coin_name":"ETH","amount":"2000.00000000","coins":"15.63599406","type":"0","rate":"0.03","daily_return":"0.46907982","remain":"20.01407244","create_at":"2020-03-14 23:47:02","expire_at":"2020-05-20 20:47:02","number":"2510a6b6862f2f489333cdf98657b261","state":"1","updated_at":"2020-03-21 18:49:26","product":"金星池"},{"id":"3","user_id":"1","coin_id":"1","coin_name":"ETH","amount":"2000.00000000","coins":"1500000.14004542","type":"0","rate":"0.03","daily_return":"0.45420136","remain":"4473.37925820","create_at":"2020-03-14 16:02:43","expire_at":"2020-05-20 13:02:43","number":"2ab12c9e55189bebce4de89f4e7b8cef","state":"1","updated_at":"2020-03-21 18:49:26","product":"金星池"}]
         */

        public int pageNum;
        public int pageSize;
        public String total;
        public List<ListBean> list;

        public static class ListBean {
            /**
             * id : 56
             * user_id : 1
             * coin_id : 1
             * coin_name : RVC
             * amount : 2000.00000000
             * coins : 6278.41847742
             * type : 0
             * rate : 0.03
             * daily_return : 188.35255432
             * remain : 12368.48440051
             * create_at : 2020-03-21 04:09:46
             * expire_at : 2020-05-27 01:09:46
             * number : 48dbd3f93fc064460406a00696cfc693
             * state : 1
             * updated_at : 2020-03-21 18:49:26
             * product : 金星池
             */

            public String id;
            public String user_id;
            public String coin_id;
            public String coin_name;
            public String amount;
            public String coins;
            public String type;
            public String rate;
            public String daily_return;
            public String remain;
            public String create_at;
            public String expire_at;
            public String number;
            public String state;
            public String updated_at;
            public String product;

    }
}
