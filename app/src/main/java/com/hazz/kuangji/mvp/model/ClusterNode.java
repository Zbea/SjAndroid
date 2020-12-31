package com.hazz.kuangji.mvp.model;

import java.io.Serializable;
import java.util.List;

public class ClusterNode implements Serializable {




    public String team;
    public String invite;
    public String direct_t;
    public String self_t;
    public List<ClusterUsersBean> cluster_users;

    public static class ClusterUsersBean implements Serializable {


        public String id;
        public String username;
        public String self_t;
        public String father_id;
        public String user_id;
        public String profile_img;
        public String direct_t;
        public List<ChildrenBean> children;

        public static class ChildrenBean implements Serializable {

            public String id;
            public String username;
            public String self_t;
            public String father_id;
            public String user_id;
            public String profile_img;
        }
    }

}
