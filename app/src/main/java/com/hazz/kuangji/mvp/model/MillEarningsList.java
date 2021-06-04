package com.hazz.kuangji.mvp.model;

import java.util.List;

public class MillEarningsList {

        /**
         * pageNum : 1
         * pageSize : 10
         * total : 14
         * list : [{"create_at":"2020-04-17 17:47:20","update_at":"2020-04-17 17:47:20","id":"24","user_id":"132","coin_name":"FIL","amount":"140.00000000","category":"static","order_id":"96","create_at_index":"202004","product":"FIL币种算力服务器"},{"create_at":"2020-04-17 17:47:20","update_at":"2020-04-17 17:47:20","id":"23","user_id":"132","coin_name":"FIL","amount":"140.00000000","category":"static","order_id":"94","create_at_index":"202004","product":"FIL币种算力服务器"},{"create_at":"2020-04-17 17:47:20","update_at":"2020-04-17 17:47:20","id":"22","user_id":"132","coin_name":"FIL","amount":"35.00000000","category":"static","order_id":"93","create_at_index":"202004","product":"FIL币种算力服务器"},{"create_at":"2020-04-17 17:47:20","update_at":"2020-04-17 17:47:20","id":"17","user_id":"132","coin_name":"FIL","amount":"35.00000000","category":"static","order_id":"82","create_at_index":"202004","product":"FIL币种算力服务器"},{"create_at":"2020-04-17 17:47:20","update_at":"2020-04-17 17:47:20","id":"18","user_id":"132","coin_name":"FIL","amount":"35.00000000","category":"static","order_id":"88","create_at_index":"202004","product":"FIL币种算力服务器"},{"create_at":"2020-04-17 17:47:20","update_at":"2020-04-17 17:47:20","id":"19","user_id":"132","coin_name":"FIL","amount":"35.00000000","category":"static","order_id":"89","create_at_index":"202004","product":"FIL币种算力服务器"},{"create_at":"2020-04-17 17:47:20","update_at":"2020-04-17 17:47:20","id":"20","user_id":"132","coin_name":"FIL","amount":"35.00000000","category":"static","order_id":"91","create_at_index":"202004","product":"FIL币种算力服务器"},{"create_at":"2020-04-17 17:47:20","update_at":"2020-04-17 17:47:20","id":"21","user_id":"132","coin_name":"FIL","amount":"35.00000000","category":"static","order_id":"92","create_at_index":"202004","product":"FIL币种算力服务器"},{"create_at":"2020-04-17 01:44:34","update_at":"2020-04-17 01:44:34","id":"12","user_id":"132","coin_name":"FIL","amount":"35.00000000","category":"static","order_id":"91","create_at_index":"202004","product":"FIL币种算力服务器"},{"create_at":"2020-04-17 01:44:34","update_at":"2020-04-17 01:44:34","id":"13","user_id":"132","coin_name":"FIL","amount":"35.00000000","category":"static","order_id":"92","create_at_index":"202004","product":"FIL币种算力服务器"}]
         */

        public int pageNum;
        public int pageSize;
        public String total;
        public List<ListBean> list;

        public static class ListBean {
            /**
             * create_at : 2020-04-17 17:47:20
             * update_at : 2020-04-17 17:47:20
             * id : 24
             * user_id : 132
             * coin_name : FIL
             * amount : 140.00000000
             * category : static
             * order_id : 96
             * create_at_index : 202004
             * product : FIL币种算力服务器
             */

            public String create_at;
            public String update_at;
            public String id;
            public String user_id;
            public String coin_name;
            public String amount;
            public String category;
            public String order_id;
            public String create_at_index;
            public String product;
            public String miner_number;
        }

}
