package com.hazz.kuangji.mvp.model;

import java.io.Serializable;
import java.util.List;

public class MyAsset implements Serializable {


        /**
         * wallet_address : 0x61e337e8a91900d57bc6801e3d0cf909cbe52356
         * config : [{"create_at":"2020-04-05 02:45:30","update_at":"2020-04-05 02:45:30","id":"1","name":"提币最低","value":"20","desc":""},{"create_at":"2020-04-05 02:45:30","update_at":"2020-04-05 02:45:30","id":"2","name":"提币最高","value":"10000","desc":""},{"create_at":"2020-04-05 02:45:30","update_at":"2020-04-05 02:45:30","id":"3","name":"转账手续费","value":"0.5","desc":"百分比"}]
         * usdt_to_cny_rate : 7.1768
         * fcoin_revenue : 735.00000000
         * usdt_revenue : 10033.00000000
         * investment : 1400.00000000
         * assets : [{"balance":"8601.00000000","coin":"USDT","frozen":"22.00"},{"balance":"771.00000000","coin":"FIL","frozen":"21.00"}]
         */

        public String wallet_address;
        public String usdt_to_cny_rate;
        public String fcoin_revenue;
        public String usdt_revenue;
        public String investment;
        public List<ConfigBean> config;
        public List<AssetsBean> assets;

        public static class ConfigBean implements Serializable{
            /**
             * create_at : 2020-04-05 02:45:30
             * update_at : 2020-04-05 02:45:30
             * id : 1
             * name : 提币最低
             * value : 20
             * desc :
             */

            public String create_at;
            public String update_at;
            public String id;
            public String name;
            public String value;
            public String desc;
        }

        public static class AssetsBean implements Serializable{
            /**
             * balance : 8601.00000000
             * coin : USDT
             * frozen : 22.00
             */

            public String balance;
            public String coin;
            public String frozen;
            public String locked;
            public String pledge;
            public String team;
            public String achievement;
            public String max_withdraw;
            public String line25;
            public String line75;
        }

}
