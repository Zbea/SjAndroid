package com.hazz.kuangji.mvp.model;

import com.google.gson.annotations.SerializedName;

/**
 * @Created by Zbea
 * @fileName ExtractRule
 * @date 2021/6/30 14:23
 * @email xiaofeng9212@126.com
 * @describe TODO
 **/
public class ExtractRule {

    public RuleBean ERC;
    public RuleBean TRC;
    public RuleBean FIL;
    public RuleBean BZZ;
    public RuleBean CHIA;
    public RuleBean FIL2;
    public RuleBean FIL3;

    public static class RuleBean {

        @SerializedName("coin")
        public String coin;
        @SerializedName("amount_min")
        public String amountMin;
        @SerializedName("amount_max")
        public String amountMax;
        @SerializedName("fee_min")
        public String feeMin;
        @SerializedName("fee_rate")
        public String feeRate;
    }
}
