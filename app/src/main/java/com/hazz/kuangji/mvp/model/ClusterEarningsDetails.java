package com.hazz.kuangji.mvp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @Created by Zbea
 * @fileName ClusterEarningsDetails
 * @date 2020/12/11 14:36
 * @email xiaofeng9212@126.com
 * @describe 收益详情
 **/
public class ClusterEarningsDetails {


    public String total;
    public String pageSize;
    public String pageNum;

    public List<ClusterEarningsDetailsBean> list;

    public static class ClusterEarningsDetailsBean{

        /**
         * "order_id": "订单id",
         *   "start_at": "时间",
         *    "sealed_storage": "当前集群总的算力T数",
         *    "seal_amount": "当天的算力增长T数",
         *    "sum_seal_amount": "累计昨日的算力数量",
         *    "remain": "剩余释放天数(180)",
         *    "fil_amount": "单T产币量",
         *    "return_fil": "当天产币",
         *    "sum_return_fil": "累计产币",
         *     "line25": "25%的直接释放",
         *     "line75": "75%的线性释放"
         */

        public String order_id;
        public String start_at;
        public String sealed_storage;
        public String seal_amount;
        public String sum_seal_amount;
        public String remain;
        public String fil_amount;
        public String return_fil;
        public String sum_return_fil;
        public String line25;
        public String line75;
        public String usable;
    }


}
