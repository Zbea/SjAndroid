package com.hazz.kuangji.ui.activity.mine

import android.content.Intent
import androidx.appcompat.widget.Toolbar
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.model.UserInfo
import com.hazz.kuangji.ui.fragment.MineCertificationOneFragment
import com.hazz.kuangji.utils.SPUtil
import com.hazz.kuangji.utils.ToolBarCustom
import kotlinx.android.synthetic.main.activity_charge.*

class MineCertificationActivity : BaseActivity() {

    override fun layoutId(): Int {
        return R.layout.activity_mine_certification
    }

    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setLeftIcon(R.mipmap.icon_back)
                .setTitle("实名认证")
                .setTitleColor(resources.getColor(R.color.color_white))
                .setToolBarBgRescource(R.drawable.bg_main_gradient)
                .setOnLeftIconClickListener { finish() }
    }

    override fun initData() {

        var userInfo = SPUtil.getObj("user", UserInfo::class.java)

        supportFragmentManager.beginTransaction()
                .replace(R.id.fl_content, MineCertificationOneFragment().newInstance())
                .commitAllowingStateLoss()

    }

    override fun start() {
    }


    /**
     * 解决Fragment中的onActivityResult()方法无响应问题。
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        /**
         * 1.使用getSupportFragmentManager().getFragments()获取到当前Activity中添加的Fragment集合
         * 2.遍历Fragment集合，手动调用在当前Activity中的Fragment中的onActivityResult()方法。
         */
        for (mFragment in supportFragmentManager.fragments) {
            mFragment.onActivityResult(requestCode, resultCode, data)
        }
    }


}