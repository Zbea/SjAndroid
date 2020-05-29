package com.hazz.kuangji.mvp.model.bean;

import java.io.Serializable;
import java.util.List;

public class Touzi {

        /**
         * level : 土星池
         * current_invest : 20000.00000000
         * profile_img : data/profile_img/profile.png
         * invest : 1506332.43046040
         * remain : 16930.12130489
         * revenue : 3012664.8609208
         * products : [{"id":"1","name":"金星池","price":"2000.00000000","coin":"USDT","out":"2.00000000","rate":"0.03000000","created_at":"2020-03-09 18:46:55","updated_at":"2020-03-21 03:59:33","status":"1","target_coin":"RVC","coins":6395.7829829582,"daily_return":191.87348948875,"total_return":12791.565965916},{"id":"2","name":"木星池","price":"4000.00000000","coin":"USDT","out":"2.00000000","rate":"0.03000000","created_at":"2020-03-09 18:46:55","updated_at":"2020-03-21 03:59:33","status":"1","target_coin":"RVC","coins":12795.783230506,"daily_return":383.87349691517,"total_return":25591.566461011},{"id":"3","name":"水星池","price":"6000.00000000","coin":"USDT","out":"2.00000000","rate":"0.03000000","created_at":"2020-03-09 18:46:55","updated_at":"2020-03-21 03:59:33","status":"1","target_coin":"RVC","coins":19193.674845759,"daily_return":575.81024537276,"total_return":38387.349691517},{"id":"4","name":"火星池","price":"8000.00000000","coin":"USDT","out":"2.00000000","rate":"0.03000000","created_at":"2020-03-09 18:46:55","updated_at":"2020-03-21 03:59:33","status":"1","target_coin":"RVC","coins":25591.566461011,"daily_return":767.74699383034,"total_return":51183.132922023},{"id":"5","name":"土星池","price":"10000.00000000","coin":"USDT","out":"2.00000000","rate":"0.03000000","created_at":"2020-03-09 18:46:55","updated_at":"2020-03-21 03:59:33","status":"1","target_coin":"RVC","coins":31989.458076264,"daily_return":959.68374228793,"total_return":63978.916152529}]
         * assets : [{"coin":"USDT","balance":"480.00000000"},{"coin":"ETH","balance":"2.95035949"},{"coin":"RVC","balance":"107616.21083283"}]
         */

        public String level;
        public String current_invest;
        public String profile_img;
        public String invest;
        public String remain;
        public double revenue;
        public List<ProductsBean> products;
        public List<AssetsBean> assets;

        public static class ProductsBean implements Serializable {
            /**
             * id : 1
             * name : 金星池
             * price : 2000.00000000
             * coin : USDT
             * out : 2.00000000
             * rate : 0.03000000
             * created_at : 2020-03-09 18:46:55
             * updated_at : 2020-03-21 03:59:33
             * status : 1
             * target_coin : RVC
             * coins : 6395.7829829582
             * daily_return : 191.87348948875
             * total_return : 12791.565965916
             */

            public String id;
            public String name;
            public String price;
            public String coin;
            public String out;
            public String rate;
            public String created_at;
            public String updated_at;
            public String status;
            public String target_coin;
            public double coins;
            public double daily_return;
            public double total_return;
        }

        public static class AssetsBean {
            /**
             * coin : USDT
             * balance : 480.00000000
             */

            public String coin;
            public String balance;
        }

}
