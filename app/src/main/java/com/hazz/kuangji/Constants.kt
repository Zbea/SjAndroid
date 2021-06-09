package com.hazz.kuangji

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
        const val CODE=1024
        const val SALE_MIN=10 //最少卖多少个
        const val BUY_MIN=100 //最少买多少个
        const val REAT_MIN=20 //最少租用服务器

        const val CODE_EXCHANGE_SALE="10001"
        const val CODE_BUY_BROAD="10002"
        const val CODE_CERTIFICATION_BROAD="10003"
        const val CODE_SKIN_BROAD="10004"
        const val CODE_INVESTMENT_BUY="10005"
        const val CODE_CLUSTER_EXTRACT="10006"

        const val BUGLY_ID="9e1bc2b87d"

//        const val URL_NEW_BASE = "http://192.168.1.116:5000/"
//        const val URL_BASE = "http://192.168.1.116/api/"
//        const val URL_INVITE = "http://192.168.1.116/"

        const val URL_BASE = "http://app.sjyminer.com/api/"
        const val URL_NEW_BASE = "http://app.sjyminer.com:5000/"
        const val URL_INVITE = "http://app.sjyminer.com/"

        const val URL_DOWNLOAD = "$URL_INVITE#/about"
    }


}


