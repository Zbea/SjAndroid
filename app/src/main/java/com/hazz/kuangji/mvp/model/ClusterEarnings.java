package com.hazz.kuangji.mvp.model;

import java.util.List;

/**
 * @Created by Zbea
 * @fileName ClusterEarnings
 * @date 2020/12/10 16:08
 * @email xiaofeng9212@126.com
 * @describe TODO
 **/
public class ClusterEarnings {

    public String total;
    public String pageSize;
    public String pageNum;
    public List<ClusterEarningsBean> list;

    public static class ClusterEarningsBean{

        public String id;
        public String user_id;
        public String coin_name;
        public String amount;
        public String category;
        public String order_id;
        public String product;
        public String cluster_number;
        public String fil_form;
        public String order_amount;
        public String product_amount;
        public String date_at;
        public String create_at;
    }
}
