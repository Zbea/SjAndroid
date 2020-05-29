package com.hazz.kuangji.mvp.model.bean;

import java.util.List;

public class Friends {

        /**
         * number : 18
         * invitation_code : 451291
         * invite_users : [{"id":"2","username":"090927","create_at":"2020-03-08 00:54:17"},{"id":"3","username":"276210","create_at":"2020-03-08 01:03:27"},{"id":"7","username":"713201","create_at":"2020-03-13 16:27:22"},{"id":"8","username":"789401","create_at":"2020-03-14 15:37:29"},{"id":"1","username":"test","create_at":"2020-03-14 15:42:50"},{"id":"18","username":"764366","create_at":"2020-03-14 15:44:32"},{"id":"19","username":"461763","create_at":"2020-03-14 15:45:58"},{"id":"20","username":"360277","create_at":"2020-03-14 15:46:17"},{"id":"21","username":"257908","create_at":"2020-03-14 15:46:56"},{"id":"22","username":"582914","create_at":"2020-03-14 15:47:21"},{"id":"23","username":"514139","create_at":"2020-03-14 15:49:59"},{"id":"24","username":"583964","create_at":"2020-03-14 17:09:29"},{"id":"25","username":"034825","create_at":"2020-03-14 17:16:02"},{"id":"26","username":"test12345t6","create_at":"2020-03-14 17:27:58"},{"id":"27","username":"mo","create_at":"2020-03-14 18:04:39"},{"id":"29","username":"xk","create_at":"2020-03-15 03:43:22"},{"id":"32","username":"test12345t6123234","create_at":"2020-03-15 03:45:57"},{"id":"33","username":"xiekai","create_at":"2020-03-15 03:46:26"}]
         */

        public String number;
        public String invitation_code;
        public List<InviteUsersBean> invite_users;

        public static class InviteUsersBean {
            /**
             * id : 2
             * username : 090927
             * create_at : 2020-03-08 00:54:17
             */

            public String id;
            public String username;
            public String create_at;
        }

}
