package com.hazz.kuangji.mvp.model;

import java.io.Serializable;

/**
 * @Created by Zbea
 * @fileName Config
 * @date 2020/12/14 14:00
 * @email xiaofeng9212@126.com
 * @describe TODO
 **/
public class Config  implements Serializable {

    public int androidVersion;
    public ConfigBean config;

    public static class ConfigBean{

        public String quotation_url;

    }

}
