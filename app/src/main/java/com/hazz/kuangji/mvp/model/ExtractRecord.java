package com.hazz.kuangji.mvp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ExtractRecord {

        public int pageNum;
        public int pageSize;
        public int total;
        public List<ListBean> list;

        public static class ListBean implements Serializable {

            /**
             * id : 3381
             * user_id : 172
             * amount_all : 140.00000000
             * amount_fee : 10.00000000
             * amount_trans : 130.00000000
             * address :
             * stat : 4
             * stat_txt : 转账成功  0=待审核；1=审核通过；2=审核驳回；3=转账中；4=转账成功；5=转账失败；6=已退回；
             */
            @SerializedName("id")
            public String id;
            @SerializedName("user_id")
            public String userId;
            @SerializedName("amount_all")
            public String amountAll;
            @SerializedName("amount_fee")
            public String amountFee;
            @SerializedName("amount_trans")
            public String amountTrans;
            @SerializedName("address")
            public String address;
            @SerializedName("stat")
            public String stat;
            @SerializedName("stat_txt")
            public String statTxt;
            public String coin;
            public String create_at;

        }

}
