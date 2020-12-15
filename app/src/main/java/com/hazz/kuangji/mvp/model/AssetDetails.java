package com.hazz.kuangji.mvp.model;

/**
 * @Created by Zbea
 * @fileName AssetDetails 资产可用详情
 * @date 2020/11/12 11:35
 * @email xiaofeng9212@126.com
 * @describe TODO
 **/
public class AssetDetails {

    public String id;
    public String sum_lockfil;//累积锁仓
    public String start_at;
    public String sum_invite;//累积业绩收益
    public String sum_release;//累积立即释放
    public String sum_line;//累计线性释放
    public String sum_balance;//累积可用

    public String day_lockfil;//每天锁仓
    public String day_total_invite;//当天业绩收益
    public String day_direct_release;//当天立即释放
    public String day_line;//当天线性释放
    public String balance;//当天可用

}
