package com.hazz.kuangji.mvp.model;

import java.util.List;

public class EarningsSource {



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
            public String product_id;
            public String fil_from;
            public String storage;
            public String order_amount;
            public String product_amoun;
            public String t_num;
        }

}
