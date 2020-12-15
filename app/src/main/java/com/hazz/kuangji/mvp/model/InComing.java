package com.hazz.kuangji.mvp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InComing {

    /*
     * fcoin_revenue : 735.00000000
     * usdt_revenue : 10033.00000000
     * yesterday_usdt : null
     * yesterday_fcoin : 70.00000000
     * static : 735.00000000
     * invitation : 11.00000000
     * achievement : null
     * static_list : [{"amount":"35.00000000","create_at":"2020-04-17 01:44:34","coin_name":"FIL"},{"amount":"35.00000000","create_at":"2020-04-17 01:44:34","coin_name":"FIL"},{"amount":"35.00000000","create_at":"2020-04-17 01:44:34","coin_name":"FIL"},{"amount":"35.00000000","create_at":"2020-04-17 01:44:34","coin_name":"FIL"},{"amount":"140.00000000","create_at":"2020-04-17 01:44:34","coin_name":"FIL"},{"amount":"140.00000000","create_at":"2020-04-17 01:44:34","coin_name":"FIL"},{"amount":"35.00000000","create_at":"2020-04-16 01:44:34","coin_name":"FIL"},{"amount":"35.00000000","create_at":"2020-04-16 01:44:34","coin_name":"FIL"},{"amount":"35.00000000","create_at":"2020-04-14 03:16:00","coin_name":"FIL"},{"amount":"35.00000000","create_at":"2020-04-14 03:16:00","coin_name":"FIL"},{"amount":"35.00000000","create_at":"2020-04-14 03:16:00","coin_name":"FIL"},{"amount":"35.00000000","create_at":"2020-04-14 03:15:23","coin_name":"FIL"},{"amount":"35.00000000","create_at":"2020-04-14 03:15:23","coin_name":"FIL"},{"amount":"35.00000000","create_at":"2020-04-14 03:15:23","coin_name":"FIL"},{"amount":"35.00000000","create_at":"2020-04-14 03:15:23","coin_name":"FIL"}]
     * invitation_list : [{"amount":"1.00000000","create_at":"2020-04-14 03:22:58","coin":"USDT"},{"amount":"10.00000000","create_at":"2020-04-05 03:34:11","coin":"ETH"}]
     */

    public String fcoin_revenue;
    public String usdt_revenue;
    public String yesterday_usdt;
    public String yesterday_fcoin;
    @SerializedName("static")
    public String staticX;
    public String invitation;
    public String achievement;
    public String team;
    public List<StaticListBean> achievement_list;
    public List<StaticListBean> invitation_list;
    public List<StaticListBean> team_list;
    public static class StaticListBean {
        /**
         * amount : 35.00000000
         * create_at : 2020-04-17 01:44:34
         * coin_name : FIL
         */

        public String amount;
        public String create_at;
        public String coin;
    }

    public String sealed_total;
    public String sealed_add;
    public String cluster_total;
    public String cluster_yesterday;

}
