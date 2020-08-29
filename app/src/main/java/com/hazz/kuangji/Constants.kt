package com.hazz.kuangji

import android.os.Environment

//  ┏┓　　　┏┓
//┏┛┻━━━┛┻┓
//┃　　　　　　　┃
//┃　　　━　　　┃
//┃　┳┛　┗┳　┃
//┃　　　　　　　┃
//┃　　　┻　　　┃
//┃　　　　　　　┃
//┗━┓　　　┏━┛
//    ┃　　　┃   神兽保佑
//    ┃　　　┃   代码无BUG！
//    ┃　　　┗━━━┓
//    ┃　　　　　　　┣┓
//    ┃　　　　　　　┏┛
//    ┗┓┓┏━┳┓┏┛
//      ┃┫┫　┃┫┫
//      ┗┻┛　┗┻┛
/**
 * Created by xk on 2017/11/27.
 * desc: 常量
 */
class Constants private constructor() {

    companion object {

        const val SALE_MIN=10 //最少卖多少个
        const val BUY_MIN=100 //最少买多少个

        const val CODE_EXCHANGE_SALE="10001"
        const val CODE_CERTIFICATION_BROAD="10003"
        const val CODE_SIGN_BROAD="10004"

        const val BUGLY_ID="9e1bc2b87d"

        const val URL_TEST = "http://192.168.1.13:5000/"
 
//        const val URL_NEW_BASE = "http://192.168.1.116:5000/"
//        const val URL_BASE = "http://192.168.1.116/api/"
//        const val URL_INVITE = "http://192.168.1.116/"
//        const val URL_BASE = "http://app.sjyminer.com/api/"
//        const val URL_NEW_BASE = "http://app.sjyminer.com:5000/"
//        const val URL_INVITE = "http://app.sjyminer.com/"
        const val URL_BASE = "http://123.57.245.50/api/"
        const val URL_NEW_BASE = "http://123.57.245.50:5000/"
        const val URL_INVITE = "http://123.57.245.50/"
        const val URL_DOWNLOAD = "$URL_INVITE#/about"
    }


}


enum class LanguageType(var value: String) {
    /**
     * 中文 简体
     */
    LG_SIMPLIFIED_CHINESE("zh_CN"),

    /**
     * 中文 繁体
     */
    LG_TRADITIONAL_CHINESE("zh_TW"),

    /**
     * 英语
     */
    LG_ENGLISH("en_US")
}