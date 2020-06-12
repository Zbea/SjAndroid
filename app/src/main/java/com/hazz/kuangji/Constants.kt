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

        val BUNDLE_VIDEO_DATA = "video_data"
        val BUNDLE_CATEGORY_DATA = "category_data"

        //腾讯 Bugly APP id
        val BUGLY_APPID = "176aad0d9e"


        //sp 存储的文件名
        val FILE_WATCH_HISTORY_NAME = "watch_history_file"   //观看记录

        val FILE_COLLECTION_NAME = "collection_file"    //收藏视屏缓存的文件名

        const val URL_TEST = "http://192.168.1.13:5000/"
        const val URL_NEW_BASE = "http://192.168.1.116:5000/"
        const val URL_BASE = "http://192.168.1.116/api/"
        const val URL_INVITE = "http://192.168.1.116/"
//  const val URL_BASE = "http://app.sjyminer.com/api/"
//    const val URL_NEW_BASE = "http://app.sjyminer.com:5000/"
// const val URL_INVITE = "http://app.sjyminer.com/"

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