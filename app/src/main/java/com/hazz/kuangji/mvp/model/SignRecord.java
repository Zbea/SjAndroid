package com.hazz.kuangji.mvp.model;

import java.util.List;

public class SignRecord {


        /**
         * pageNum : 1
         * pageSize : 500
         * total : 2
         * list : [{"id":"3","user_id":"133","create_at":"2020-04-29 20:28:03","update_at":"2020-04-29 20:28:03"},{"id":"1","user_id":"133","create_at":"2020-04-28 01:18:41","update_at":"2020-04-28 01:18:41"}]
         */

        public int pageNum;
        public int pageSize;
        public String total;
        public List<ListBean> list;

        public static class ListBean {
            /**
             * id : 3
             * user_id : 133
             * create_at : 2020-04-29 20:28:03
             * update_at : 2020-04-29 20:28:03
             */

            public String id;
            public String user_id;
            public String create_at;
            public String update_at;
        }

}
