package com.hazz.kuangji.mvp.model;

import java.util.List;

public class ChargeRecord {



        /**
         * pageNum : 1
         * pageSize : 10
         * total : 1
         * list : [{"amount":"123456.00000000","coin":"BTC","create_at":"2020-03-19 03:41:50","number":"4dfb88088e23fc1224d9b80344037fa3"}]
         */

        public int pageNum;
        public int pageSize;
        public String total;
        public List<ListBean> list;

        public static class ListBean {
            /**
             * amount : 123456.00000000
             * coin : BTC
             * create_at : 2020-03-19 03:41:50
             * number : 4dfb88088e23fc1224d9b80344037fa3
             */

            public String amount;
            public String coin;
            public String create_at;
            public String number;
        }

}
