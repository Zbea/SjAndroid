package com.hazz.kuangji.mvp.model.bean;

import java.util.List;

public class TibiRecord {


        /**
         * pageNum : 1
         * pageSize : 10
         * total : 4
         * list : [{"amount":"232.00","coin":"ETH","status":"wait","number":"812910782eeff69de42956db78efa9ba","remark":"{\"fee_rate\":0.05,\"fee_amount\":\"11.6ETH\",\"get_amount\":220.4,\"external_wallet_address\":\"123456789\"}","create_at":"2020-03-10 03:37:57"},{"amount":"232.00","coin":"ETH","status":"wait","number":"4dfb88088e23fc1224d9b80344037fa3","remark":"{\"fee_rate\":0.05,\"fee_amount\":\"11.6ETH\",\"get_amount\":220.4,\"external_wallet_address\":\"123456789\"}","create_at":"2020-03-11 22:06:56"},{"amount":"232.00","coin":"ETH","status":"wait","number":"0aa77d67b61810be985de00af30f933c","remark":"{\"fee_rate\":0.05,\"fee_amount\":\"11.6ETH\",\"get_amount\":220.4,\"external_wallet_address\":\"123456789\"}","create_at":"2020-03-12 16:00:50"},{"amount":"232.00","coin":"ETH","status":"wait","number":"20567285701aacb40cd6768c2565633b","remark":"{\"fee_rate\":0.05,\"fee_amount\":\"11.6ETH\",\"get_amount\":220.4,\"external_wallet_address\":\"123456789\"}","create_at":"2020-03-16 01:46:58"}]
         */

        public int pageNum;
        public int pageSize;
        public String total;
        public List<ListBean> list;

        public static class ListBean {
            /**
             * amount : 232.00
             * coin : ETH
             * status : wait
             * number : 812910782eeff69de42956db78efa9ba
             * remark : {"fee_rate":0.05,"fee_amount":"11.6ETH","get_amount":220.4,"external_wallet_address":"123456789"}
             * create_at : 2020-03-10 03:37:57
             */

            public String amount;
            public String coin;
            public String status;
            public String number;
            public String remark;
            public String create_at;
        }

}
