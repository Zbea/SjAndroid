package com.hazz.kuangji.mvp.model;

import java.util.List;

/**
 * @Created by Zbea
 * @fileName ClusterOrder
 * @date 2020/12/9 17:45
 * @email xiaofeng9212@126.com
 * @describe TODO
 **/
public class ClusterOrder {
    public String total;
    public String yesterday;
    public ClusterList cluster_list;

    public static class ClusterList{

        public String total;
        public String pageSize;
        public String pageNum;

        public List<ClusterOrderBean> list;

        public static class ClusterOrderBean{
            public String id;
            public String state;
            public String create_at;
            public String build_remain;
            public String product_name;
            public String state_txt;
            public String buy_date;
            public String amount;
            public String storage;
            public String buy_storage;
            public String remain;
            public String revenue;
            public String yesterday;
        }

    }


}
