package com.hazz.kuangji.mvp.model;

import java.io.Serializable;
import java.util.List;

/**
 * @Created by Zbea
 * @fileName AssetCluster
 * @date 2021/1/27 10:35
 * @email xiaofeng9212@126.com
 * @describe TODO
 **/
public class AssetCluster implements Serializable {

    /*
    "total_storage": "总算力",
        "seal_storage": "有效封装算力",
        "seal_add": "算力增量",
        "release_fil": "总释放量",
        "day_t_fil": "一天单T产币量",
        "day_coinage_fil": "一天总产币",
        "unrelease_fil": "未释放",
        "pledge_fil": "质押币"
     */

    public String total_storage;
    public String seal_storage;
    public String seal_add;
    public String release_fil;
    public String day_t_fil;
    public String day_coinage_fil;
    public String unrelease_fil;
    public String pledge_fil;
    public String round;

    public List<ConfigBean> config;

    public static class ConfigBean implements Serializable {
        /**
         * create_at : 2020-04-05 02:45:30
         * update_at : 2020-04-05 02:45:30
         * id : 1
         * name : 提币最低
         * value : 20
         * desc :
         */

        public String create_at;
        public String update_at;
        public String id;
        public String name;
        public String value;
        public String desc;
    }

}
