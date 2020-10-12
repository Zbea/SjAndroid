package com.hazz.kuangji.mvp.model;

import java.util.List;

public class Mill {


        /**
         * total : 2485.00000000
         * yesterday : 910.00000000
         * coin_name : FIL
         * machine_list : {"pageNum":1,"pageSize":10,"total":"12","list":[{"id":"82","user_id":"132","product_id":"1","price":"100.00000000","coin":"USDT","type":"1","remain":"1790","number":null,"state":"1","create_at":"2020-04-05 03:47:55","updated_at":"2020-04-18 15:43:21","mobile":"","contractor":"","address":"","product":"FIL币种算力矿机","outcome_coin":"FIL","revenue":"210.00000000","yesterday":"35.00000000"},{"id":"88","user_id":"132","product_id":"1","price":"100.00000000","coin":"USDT","type":"0","remain":"1790","number":"e99ef52e1bda10bda81b040f9c278643","state":"1","create_at":"2020-04-07 03:42:14","updated_at":"2020-04-18 15:43:21","mobile":"18576794455","contractor":"王尼玛","address":"我住在你的隔壁","product":"FIL币种算力矿机","outcome_coin":"FIL","revenue":"210.00000000","yesterday":"35.00000000"},{"id":"89","user_id":"132","product_id":"1","price":"100.00000000","coin":"USDT","type":"0","remain":"1790","number":"4f01c4c8f25d86e592ec79556d5dd15f","state":"1","create_at":"2020-04-07 03:43:01","updated_at":"2020-04-18 15:43:21","mobile":"18576794455","contractor":"王尼玛","address":"我住在你的隔壁","product":"FIL币种算力矿机","outcome_coin":"FIL","revenue":"210.00000000","yesterday":"70.00000000"},{"id":"91","user_id":"132","product_id":"1","price":"100.00000000","coin":"USDT","type":"0","remain":"1815","number":"20bed46ecf1f1cc12be5bd7e34b1ef1a","state":"1","create_at":"2020-04-14 02:49:00","updated_at":"2020-04-18 15:43:21","mobile":"18576794455","contractor":"王尼玛","address":"我住在你的隔壁","product":"FIL币种算力矿机","outcome_coin":"FIL","revenue":"210.00000000","yesterday":"70.00000000"},{"id":"92","user_id":"132","product_id":"1","price":"100.00000000","coin":"USDT","type":"0","remain":"1821","number":"e689178c5d2e661a814a9914e26e7d48","state":"1","create_at":"2020-04-14 03:22:58","updated_at":"2020-04-18 15:43:21","mobile":"18576794455","contractor":"王尼玛","address":"我住在你的隔壁","product":"FIL币种算力矿机","outcome_coin":"FIL","revenue":"140.00000000","yesterday":"70.00000000"},{"id":"93","user_id":"132","product_id":"1","price":"100.00000000","coin":"USDT","type":"0","remain":"1821","number":"a3edd7023459329be54b5cf7ad584922","state":"1","create_at":"2020-04-14 03:40:39","updated_at":"2020-04-18 15:43:21","mobile":"18576794455","contractor":"王尼玛","address":"我住在你的隔壁","product":"FIL币种算力矿机","outcome_coin":"FIL","revenue":"140.00000000","yesterday":"70.00000000"},{"id":"94","user_id":"132","product_id":"1","price":"400.00000000","coin":"USDT","type":"0","remain":"1821","number":"960eb3635d94ad49a82493f116f6aba6","state":"1","create_at":"2020-04-14 04:05:05","updated_at":"2020-04-18 15:43:21","mobile":"18576794455","contractor":"王尼玛","address":"我住在你的隔壁","product":"FIL币种算力矿机","outcome_coin":"FIL","revenue":"560.00000000","yesterday":"280.00000000"},{"id":"96","user_id":"132","product_id":"1","price":"400.00000000","coin":"USDT","type":"0","remain":"1821","number":"88c78c48f6c4bcec8c322d403b5ba19e","state":"1","create_at":"2020-04-14 04:06:48","updated_at":"2020-04-18 15:43:21","mobile":"18576794455","contractor":"王尼玛","address":"我住在你的隔壁","product":"FIL币种算力矿机","outcome_coin":"FIL","revenue":"560.00000000","yesterday":"280.00000000"},{"id":"97","user_id":"132","product_id":"1","price":"400.00000000","coin":"USDT","type":"0","remain":"1823","number":"84bc6c29e0b13371996bccbb39fd7a08","state":"1","create_at":"2020-04-17 19:06:12","updated_at":"2020-04-18 15:43:21","mobile":"18576794455","contractor":"王尼玛","address":"我住在你的隔壁","product":"FIL币种算力矿机","outcome_coin":"FIL","revenue":"280.00000000","yesterday":null},{"id":"98","user_id":"132","product_id":"1","price":"200.00000000","coin":"USDT","type":"0","remain":"1825","number":"623694be9ff8cd56db93230caa662cc2","state":"1","create_at":"2020-04-18 16:32:52","updated_at":"2020-04-18 16:32:52","mobile":"18775940439","contractor":"哈哈","address":"深圳机场","product":"FIL币种算力矿机","outcome_coin":"FIL","revenue":null,"yesterday":null}]}
         */

        public String total;
        public String yesterday;
        public String coin_name;
        public MachineListBean machine_list;

        public static class MachineListBean {
            /**
             * pageNum : 1
             * pageSize : 10
             * total : 12
             * list : [{"id":"82","user_id":"132","product_id":"1","price":"100.00000000","coin":"USDT","type":"1","remain":"1790","number":null,"state":"1","create_at":"2020-04-05 03:47:55","updated_at":"2020-04-18 15:43:21","mobile":"","contractor":"","address":"","product":"FIL币种算力矿机","outcome_coin":"FIL","revenue":"210.00000000","yesterday":"35.00000000"},{"id":"88","user_id":"132","product_id":"1","price":"100.00000000","coin":"USDT","type":"0","remain":"1790","number":"e99ef52e1bda10bda81b040f9c278643","state":"1","create_at":"2020-04-07 03:42:14","updated_at":"2020-04-18 15:43:21","mobile":"18576794455","contractor":"王尼玛","address":"我住在你的隔壁","product":"FIL币种算力矿机","outcome_coin":"FIL","revenue":"210.00000000","yesterday":"35.00000000"},{"id":"89","user_id":"132","product_id":"1","price":"100.00000000","coin":"USDT","type":"0","remain":"1790","number":"4f01c4c8f25d86e592ec79556d5dd15f","state":"1","create_at":"2020-04-07 03:43:01","updated_at":"2020-04-18 15:43:21","mobile":"18576794455","contractor":"王尼玛","address":"我住在你的隔壁","product":"FIL币种算力矿机","outcome_coin":"FIL","revenue":"210.00000000","yesterday":"70.00000000"},{"id":"91","user_id":"132","product_id":"1","price":"100.00000000","coin":"USDT","type":"0","remain":"1815","number":"20bed46ecf1f1cc12be5bd7e34b1ef1a","state":"1","create_at":"2020-04-14 02:49:00","updated_at":"2020-04-18 15:43:21","mobile":"18576794455","contractor":"王尼玛","address":"我住在你的隔壁","product":"FIL币种算力矿机","outcome_coin":"FIL","revenue":"210.00000000","yesterday":"70.00000000"},{"id":"92","user_id":"132","product_id":"1","price":"100.00000000","coin":"USDT","type":"0","remain":"1821","number":"e689178c5d2e661a814a9914e26e7d48","state":"1","create_at":"2020-04-14 03:22:58","updated_at":"2020-04-18 15:43:21","mobile":"18576794455","contractor":"王尼玛","address":"我住在你的隔壁","product":"FIL币种算力矿机","outcome_coin":"FIL","revenue":"140.00000000","yesterday":"70.00000000"},{"id":"93","user_id":"132","product_id":"1","price":"100.00000000","coin":"USDT","type":"0","remain":"1821","number":"a3edd7023459329be54b5cf7ad584922","state":"1","create_at":"2020-04-14 03:40:39","updated_at":"2020-04-18 15:43:21","mobile":"18576794455","contractor":"王尼玛","address":"我住在你的隔壁","product":"FIL币种算力矿机","outcome_coin":"FIL","revenue":"140.00000000","yesterday":"70.00000000"},{"id":"94","user_id":"132","product_id":"1","price":"400.00000000","coin":"USDT","type":"0","remain":"1821","number":"960eb3635d94ad49a82493f116f6aba6","state":"1","create_at":"2020-04-14 04:05:05","updated_at":"2020-04-18 15:43:21","mobile":"18576794455","contractor":"王尼玛","address":"我住在你的隔壁","product":"FIL币种算力矿机","outcome_coin":"FIL","revenue":"560.00000000","yesterday":"280.00000000"},{"id":"96","user_id":"132","product_id":"1","price":"400.00000000","coin":"USDT","type":"0","remain":"1821","number":"88c78c48f6c4bcec8c322d403b5ba19e","state":"1","create_at":"2020-04-14 04:06:48","updated_at":"2020-04-18 15:43:21","mobile":"18576794455","contractor":"王尼玛","address":"我住在你的隔壁","product":"FIL币种算力矿机","outcome_coin":"FIL","revenue":"560.00000000","yesterday":"280.00000000"},{"id":"97","user_id":"132","product_id":"1","price":"400.00000000","coin":"USDT","type":"0","remain":"1823","number":"84bc6c29e0b13371996bccbb39fd7a08","state":"1","create_at":"2020-04-17 19:06:12","updated_at":"2020-04-18 15:43:21","mobile":"18576794455","contractor":"王尼玛","address":"我住在你的隔壁","product":"FIL币种算力矿机","outcome_coin":"FIL","revenue":"280.00000000","yesterday":null},{"id":"98","user_id":"132","product_id":"1","price":"200.00000000","coin":"USDT","type":"0","remain":"1825","number":"623694be9ff8cd56db93230caa662cc2","state":"1","create_at":"2020-04-18 16:32:52","updated_at":"2020-04-18 16:32:52","mobile":"18775940439","contractor":"哈哈","address":"深圳机场","product":"FIL币种算力矿机","outcome_coin":"FIL","revenue":null,"yesterday":null}]
             */

            public int pageNum;
            public int pageSize;
            public String total;
            public List<ListBean> list;

            public static class ListBean {
                /**
                 * id : 82
                 * user_id : 132
                 * product_id : 1
                 * price : 100.00000000
                 * coin : USDT
                 * type : 1
                 * remain : 1790
                 * number : null
                 * state : 1
                 * create_at : 2020-04-05 03:47:55
                 * updated_at : 2020-04-18 15:43:21
                 * mobile :
                 * contractor :
                 * address :
                 * product : FIL币种算力矿机
                 * outcome_coin : FIL
                 * revenue : 210.00000000
                 * yesterday : 35.00000000
                 */

                public String id;
                public String user_id;
                public String product_id;
                public String price;
                public String product_price;
                public String coin;
                public String type;
                public String remain;
                public Object number;
                public String state;
                public String create_at;
                public String updated_at;
                public String mobile;
                public String contractor;
                public String address;
                public String product;
                public String outcome_coin;
                public String revenue;
                public String yesterday;
                public String miner_number;
                public String storage;
                public String buy_storage;
            }

    }
}
