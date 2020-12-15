package com.hazz.kuangji.mvp.model;

import java.util.List;

/**
 * @Created by Zbea
 * @fileName ClusterEarningsDetails
 * @date 2020/12/11 14:36
 * @email xiaofeng9212@126.com
 * @describe 资产收益详情
 **/
public class AssetClusterEarningsDetails {


    public String total;
    public String pageSize;
    public String pageNum;

    public List<AssetClusterBean> list;

    public static class AssetClusterBean{

        //集群
        /**
         * "create_at": "2020-12-13",
         *                 "remain": "180",
         *                 "day_return_fil": "当天总收益",
         *                 "day_seal_amount": "当天的总增长的算力",
         *                 "fil_amount": "当天单T产币",
         *                 "sum_return_fil": "当天总产币",
         *                 "sum_seal_amount": "13之前的总算力",
         *                 "line25": "3.86100000",
         *                 "line75": "0.00000000"
         */
        public String create_at;
        public String remain;
        public String day_return_fil;
        public String day_seal_amount;
        public String fil_amount;
        public String sum_return_fil;
        public String sum_seal_amount;
        public String line25;
        public String line75;
        public String usable;
    }


}
