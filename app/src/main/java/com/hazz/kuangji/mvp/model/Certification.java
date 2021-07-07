package com.hazz.kuangji.mvp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Certification implements Serializable {

    /**
     * user_id : 172
     * real_name : 真实姓名
     * id_num : 身份证号
     * img_front : 身份证正面
     * img_back : 身份证反面
     * img_hold : 手持身份证
     * stat : 1  -1 未进行实名  0 待审核 1 审核通过 2 审核不通过
     */
    @SerializedName("user_id")
    public String userId;
    @SerializedName("real_name")
    public String realName;
    @SerializedName("id_num")
    public String idNum;
    @SerializedName("img_front")
    public String imgFront;
    @SerializedName("img_back")
    public String imgBack;
    @SerializedName("img_hold")
    public String imgHold;
    @SerializedName("stat")
    public String stat;
    public String reason;
    public String address;
    public String email;
    public String code;
}
