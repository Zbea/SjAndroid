package com.hazz.kuangji.mvp.model;

import java.io.Serializable;
import java.util.List;

/**
 * @Created by Zbea
 * @fileName ClusterInfo
 * @date 2020/12/9 15:17
 * @email xiaofeng9212@126.com
 * @describe 集群信息
 **/
public class Cluster {

    public List<ClusterInfo> products;

    public static class ClusterInfo implements Serializable
    {
        public String id;
        public String name;
        public String price;
        public String desc;
        public String coin;
        public String round;
        public String status;
        public String outcome_coin;
        public String build_duration;
        public String pic;
        public String rate;
        public String created_at;
        public String updated_at;
        public String storage;
    }

}
