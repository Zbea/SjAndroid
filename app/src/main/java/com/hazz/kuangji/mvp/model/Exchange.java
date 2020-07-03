package com.hazz.kuangji.mvp.model;

import java.io.Serializable;

public class Exchange implements Serializable {

    public String usdtPrice;
    public String usdtNum;
    public String filPrice;
    public String filNum;
    public String usdtrate;
    public String filrate;

    public String wxUrl;//我的微信收款二维码地址
    public String zfbUrl;//我的支付宝收款二维码地址
    public String bankCode;//银行账户
    public String bankName ;//银行开户人
    public String payee ;//
}
