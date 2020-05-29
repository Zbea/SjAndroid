package com.hazz.kuangji.mvp.model.bean;

import java.io.Serializable;
import java.util.List;

public class Node implements Serializable{


        /**
         * team : 4000000.00000000
         * direct : 41000.00000000
         * invite_users : [{"id":"134","username":"mo","level":"0","self_purchase":"0.00000000","direct_purchase":"0.00000000","children":[{"id":"139","username":"mj","level":"0","self_purchase":"0.00000000"}]}]
         */

        public String team;
        public String direct;
        public List<InviteUsersBean> invite_users;

        public static class InviteUsersBean implements Serializable {
            /**
             * id : 134
             * username : mo
             * level : 0
             * self_purchase : 0.00000000
             * direct_purchase : 0.00000000
             *
             * children : [{"id":"139","username":"mj","level":"0","self_purchase":"0.00000000"}]
             */

            public String id;
            public String username;
            public String level;
            public String self_purchase;
            public String direct_purchase;
            public List<ChildrenBean> children;

            public static class ChildrenBean implements Serializable{
                /**
                 * id : 139
                 * username : mj
                 * level : 0
                 * self_purchase : 0.00000000
                 */

                public String id;
                public String username;
                public String level;
                public String self_purchase;
            }
        }

}
