package com.hazz.kuangji.base

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hazz.kuangji.net.BaseView
import com.hazz.kuangji.net.ExceptionHandle
import com.hazz.kuangji.ui.activity.LoginActivity
import com.hazz.kuangji.utils.ActivityManager
import com.hazz.kuangji.utils.SPUtil
import com.hazz.kuangji.utils.SToast
import com.hazz.kuangji.utils.StatusBarUtil
import com.hazz.kuangji.utils.StatusBarUtil.Companion.darkMode
import com.hazz.kuangji.widget.ProgressDialog
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions


abstract class BaseActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks, BaseView {
    /**
     * 多种状态的 View 的切换
     */

    var permissionsnew: RxPermissions? = null
    var mDialog: ProgressDialog? = null

    override fun moveTaskToBack(nonRoot: Boolean): Boolean {
        return super.moveTaskToBack(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(layoutId())
        darkMode(this)
        mDialog = ProgressDialog(this)
        initView()
        initData()
        start()
        initListener()

        permissionsnew = RxPermissions(this)

    }

    private fun initListener() {

    }



    /**
     *  加载布局
     */
    abstract fun layoutId(): Int

    /**
     * 初始化数据
     */
    abstract fun initData()

    /**
     * 初始化 View
     */
    abstract fun initView()

    /**
     * 开始请求
     */
    abstract fun start()





    /**
     * 打卡软键盘
     */
    fun openKeyBord(mEditText: EditText, mContext: Context) {
        val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN)
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    /**
     * 关闭软键盘
     */
    fun closeKeyBord(mEditText: EditText, mContext: Context) {
        val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(mEditText.windowToken, 0)
    }


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
            Toast.makeText(this, "已拒绝权限" + sb + "并不再询问", Toast.LENGTH_SHORT).show()
            AppSettingsDialog.Builder(this)
                    .setRationale("此功能需要" + sb + "权限，否则无法正常使用，是否打开设置")
                    .setPositiveButton("好")
                    .setNegativeButton("不行")
                    .build()
                    .show()
        }
    }

    fun showMissingPermissionDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("提示")
        builder.setMessage("当前应用缺少必要权限。请点击\"设置\"-\"权限\"-打开所需权限。")
        // 拒绝, 退出应用
        builder.setNegativeButton("取消") { dialog, which ->

        }
        builder.setPositiveButton("确定") { dialog, which -> startAppSettings() }

        builder.setCancelable(false)
        builder.show()
    }

    /**
     * 启动应用的设置
     */
    fun startAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse("package:" + "com.hazz.kuangji")
        startActivity(intent)
    }

    override fun addSubscription(d: Disposable) {

    }

    override fun login() {

        SToast.showText("token已失效,请重新登陆")
        SPUtil.putString("token", "")
        SPUtil.removeObj("certification")

        Handler().postDelayed(Runnable {
            startActivity(Intent(this, LoginActivity::class.java))
            ActivityManager.getInstance().finishOthers(LoginActivity::class.java)
        }, 500)


    }

    override fun hideLoading() {
        mDialog!!.dismiss()
    }

    override fun Loading() {
    }

    override fun fail(msg: String) {
        SToast.showTextLong(msg)
    }

    override fun onStartRequest() {
        mDialog!!.show()
    }

    override fun onFailer(responeThrowable: ExceptionHandle.ResponeThrowable?) {

    }

    override fun onComplete() {

    }

    override fun onPause() {
        super.onPause()
        mDialog!!.dismiss()
    }

}


