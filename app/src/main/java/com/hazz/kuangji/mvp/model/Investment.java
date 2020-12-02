package com.hazz.kuangji.mvp.model;

import java.util.List;

/**
 * @Created by Zbea
 * @fileName Investment
 * @date 2020/11/24 15:21
 * @email xiaofeng9212@126.com
 * @describe TODO
 **/
public class Investment {

    public int total;
    public int pageSize;
    public int pageNum;
    public String amount;
    public List<ListBean> list;

    public static class ListBean {

        public String create_at;
        public String id;
        public String price;
        public String remain;
        public String status;
        public String current_profit;
        public String profit_time;
        public String product_name;
        public String product_id;
        public String round;
        public String rate;
        public boolean isCheck;
    }


}
