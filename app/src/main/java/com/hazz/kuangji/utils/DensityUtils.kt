package com.hazz.kuangji.utils


import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks
import android.content.Context
import android.content.res.Configuration
import android.util.DisplayMetrics

/**
 * 通过修改系统参数来适配android设备 ,屏幕适配方案
 */

object DensityUtils {

    private var appDensity: Float = 0.toFloat()
    private var appScaledDensity: Float = 0.toFloat()
    private var appDisplayMetrics: DisplayMetrics? = null
    private var barHeight: Int = 0

    fun setDensity(application: Application) {
        //获取application的DisplayMetrics
        appDisplayMetrics = application.resources.displayMetrics
        //获取状态栏高度
        barHeight = getStatusBarHeight(application)

        if (appDensity == 0f) {
            //初始化的时候赋值
            appDensity = appDisplayMetrics!!.density
            appScaledDensity = appDisplayMetrics!!.scaledDensity

            //添加字体变化的监听
            application.registerComponentCallbacks(object : ComponentCallbacks {
                override fun onConfigurationChanged(newConfig: Configuration?) {
                    //字体改变后,将appScaledDensity重新赋值
                    if (newConfig != null && newConfig.fontScale > 0) {
                        appScaledDensity = application.resources.displayMetrics.scaledDensity
                    }
                }

                override fun onLowMemory() {}
            })
        }
    }


    /**
     * 获取状态栏高度
     *
     * @param context context
     * @return 状态栏高度
     */
    public fun getStatusBarHeight(context: Context): Int {
        // 获得状态栏高度
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        return context.resources.getDimensionPixelSize(resourceId)
    }


    //此方法在BaseActivity中做初始化(如果不封装BaseActivity的话,直接用下面那个方法就好)
    fun setDefault(activity: Activity) {
        setAppOrientation(activity, "width")
    }

    //此方法用于在某一个Activity里面更改适配的方向
    fun setOrientation(activity: Activity, orientation: String) {
        setAppOrientation(activity, orientation)
    }

    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * targetDensity
     * targetScaledDensity
     * targetDensityDpi
     * 这三个参数是统一修改过后的值
     *
     *
     * orientation:方向值,传入width或height
     */
    private fun setAppOrientation(activity: Activity?, orientation: String) {

        val targetDensity: Float

        if (orientation == "height") {
            targetDensity = (appDisplayMetrics!!.heightPixels - barHeight) / 667f
        } else {
            targetDensity = appDisplayMetrics!!.widthPixels / 375f
        }

        val targetScaledDensity = targetDensity * (appScaledDensity / appDensity)
        val targetDensityDpi = (160 * targetDensity).toInt()

        /**
         *
         * 最后在这里将修改过后的值赋给系统参数
         *
         * 只修改Activity的density值
         */
        val activityDisplayMetrics = activity!!.resources.displayMetrics
        activityDisplayMetrics.density = targetDensity
        activityDisplayMetrics.scaledDensity = targetScaledDensity
        activityDisplayMetrics.densityDpi = targetDensityDpi
    }
}