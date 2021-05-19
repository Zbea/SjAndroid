package com.hazz.kuangji

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.SkinAppCompatDelegateImpl
import com.hazz.kuangji.ui.activity.MainActivity
import com.hazz.kuangji.utils.*
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.BuildConfig
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import skin.support.SkinCompatManager
import skin.support.app.SkinAppCompatViewInflater
import skin.support.app.SkinCardViewInflater
import skin.support.constraint.app.SkinConstraintViewInflater
import skin.support.design.app.SkinMaterialViewInflater
import kotlin.properties.Delegates


class MyApplication : Application(){


    companion object {

        private val TAG = "MyApplication"

        var context: Context by Delegates.notNull()
            private set


    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        initConfig()
        DisplayManager.init(this)
        DensityUtils.setDensity(this)
        registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks)

        //腾讯bugly
        Beta.canShowUpgradeActs.add(MainActivity::class.java)
        Beta.upgradeDialogLayoutId = R.layout.dialog_upgrade
        Beta.autoCheckUpgrade = true
        Beta.upgradeCheckPeriod = 20 * 1000
        Beta.initDelay = 4 * 1000
        Beta.enableNotification = true
        Bugly.init(this, Constants.BUGLY_ID, false)

        SPUtil.init(this)
        SToast.initToast(this)

        SkinCompatManager.withoutActivity(this)
                .addInflater(SkinAppCompatViewInflater()) // 基础控件换肤初始化
                .addInflater(SkinMaterialViewInflater()) // material design 控件换肤初始化[可选]
                .addInflater(SkinConstraintViewInflater()) // ConstraintLayout 控件换肤初始化[可选]
                .addInflater(SkinCardViewInflater()) // CardView v7 控件换肤初始化[可选]
                .setSkinStatusBarColorEnable(true) // 关闭状态栏换肤，默认打开[可选]
                .setSkinWindowBackgroundEnable(false) // 关闭windowBackground换肤，默认打开[可选]
                .loadSkin()

    }


    /**
     * 初始化配置
     */
    private fun initConfig() {

        val formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // 隐藏线程信息 默认：显示
                .methodCount(0)         // 决定打印多少行（每一行代表一个方法）默认：2
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
                .tag("hao_zz")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build()
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }


    private val mActivityLifecycleCallbacks = object : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            Log.d(TAG, "onCreated: " + activity.componentName.className)
            ActivityManager.getInstance().addActivity(activity)
           DensityUtils.setDefault(activity)
        }

        override fun onActivityStarted(activity: Activity) {
            Log.d(TAG, "onStart: " + activity.componentName.className)
        }

        override fun onActivityResumed(activity: Activity) {

        }

        override fun onActivityPaused(activity: Activity) {

        }

        override fun onActivityStopped(activity: Activity) {

        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

        }

        override fun onActivityDestroyed(activity: Activity) {
            Log.d(TAG, "onDestroy: " + activity.componentName.className)
            ActivityManager.getInstance().finishActivity(activity)

        }
    }


}
