package com.hazz.kuangji.base

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.hazz.kuangji.net.BaseView
import com.hazz.kuangji.net.ExceptionHandle
import com.hazz.kuangji.ui.activity.LoginActivity
import com.hazz.kuangji.utils.ActivityManager
import com.hazz.kuangji.utils.SPUtil
import com.hazz.kuangji.utils.SToast
import com.hazz.kuangji.widget.ProgressDialog
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions


abstract class BaseFragment : Fragment(), EasyPermissions.PermissionCallbacks, BaseView {

    /**
     * 视图是否加载完毕
     */
    private var isViewPrepare = false
    /**
     * 数据是否加载过了
     */
    private var hasLoadData = false
    /**
     * 多种状态的 View 的切换
     */
    var mView:View?=null


    var mDialog: ProgressDialog? = null
    var sl_refresh: SwipeRefreshLayout?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView=inflater.inflate(getLayoutId(), container,false)
        return mView
    }


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            lazyLoadDataIfPrepared()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewPrepare = true
        initView()
        mDialog = ProgressDialog(activity)
        lazyLoadDataIfPrepared()
        //多种状态切换的view 重试点击事件

    }

    private fun lazyLoadDataIfPrepared() {
        if (userVisibleHint && isViewPrepare && !hasLoadData) {
            lazyLoad()
            hasLoadData = true
        }
    }



    /**
     * 加载布局
     */
    @LayoutRes
    abstract fun getLayoutId(): Int

    /**
     * 初始化 ViewI
     */
    abstract fun initView()

    /**
     * 懒加载
     */
    abstract fun lazyLoad()

    override fun onDestroy() {
        super.onDestroy()

    }


    /**
     * 重写要申请权限的Activity或者Fragment的onRequestPermissionsResult()方法，
     * 在里面调用EasyPermissions.onRequestPermissionsResult()，实现回调。
     *
     * @param requestCode  权限请求的识别码
     * @param permissions  申请的权限
     * @param grantResults 授权结果
     */
    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    /**
     * 当权限被成功申请的时候执行回调
     *
     * @param requestCode 权限请求的识别码
     * @param perms       申请的权限的名字
     */
    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        Log.i("EasyPermissions", "获取成功的权限$perms")
    }

    /**
     * 当权限申请失败的时候执行的回调
     *
     * @param requestCode 权限请求的识别码
     * @param perms       申请的权限的名字
     */
    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        //处理权限名字字符串
        val sb = StringBuffer()
        for (str in perms) {
            sb.append(str)
            sb.append("\n")
        }
        sb.replace(sb.length - 2, sb.length, "")
        //用户点击拒绝并不在询问时候调用
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            Toast.makeText(activity, "已拒绝权限" + sb + "并不再询问", Toast.LENGTH_SHORT).show()
            AppSettingsDialog.Builder(this)
                    .setRationale("此功能需要" + sb + "权限，否则无法正常使用，是否打开设置")
                    .setPositiveButton("好")
                    .setNegativeButton("不行")
                    .build()
                    .show()
        }
    }

    override fun addSubscription(d: Disposable) {

    }

    override fun login() {
        if (mView==null||activity==null)return
        SToast.showText("连接超时,请重新登陆")
        SPUtil.putString("token", "")
        SPUtil.removeObj("certification")
        Handler().postDelayed(Runnable {
            startActivity(Intent(activity, LoginActivity::class.java))
            ActivityManager.getInstance().finishOthers(LoginActivity::class.java)
        }, 500)
    }

    override fun hideLoading() {
        if (mView==null||activity==null)return
        mDialog?.dismiss()
        sl_refresh?.isRefreshing=false
    }

    override fun Loading() {
    }

    override fun fail(msg: String) {
        if (mView==null||activity==null)return
        SToast.showText( msg)
        sl_refresh?.isRefreshing=false
    }

    override fun onStartRequest() {
        mDialog?.show()
    }

    override fun onFailer(responeThrowable: ExceptionHandle.ResponeThrowable?) {
        sl_refresh?.isRefreshing=false
    }

    override fun onComplete() {
        sl_refresh?.isRefreshing=false
    }
}
