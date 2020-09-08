package com.hazz.kuangji.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.hazz.kuangji.Constants
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Certification
import com.hazz.kuangji.mvp.presenter.CertificationInfoPresenter
import com.hazz.kuangji.mvp.presenter.MainPresenter
import com.hazz.kuangji.ui.fragment.*
import com.hazz.kuangji.utils.RxBus
import com.hazz.kuangji.utils.SPUtil
import com.hazz.kuangji.utils.StatusBarUtil
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import kotlinx.android.synthetic.main.activity_main_ruoyu_new.*
import kotlinx.android.synthetic.main.fragment_new_home.*
import java.util.logging.Logger


class   MainActivity : BaseActivity(), RadioGroup.OnCheckedChangeListener ,IContractView.MainView, IContractView.ICertificationInfoView{

    private var mainPresenter=MainPresenter(this)
    private val mCertificationInfoPresenter = CertificationInfoPresenter(this)
    private lateinit var mFragments: ArrayList<Fragment>
    private var mLastSelect = 0

    override fun getCertification(certification: Certification) {
        if (certification.status == 1) {
            SPUtil.putObj("certification", certification)
        }
    }

    override fun layoutId(): Int {
        return R.layout.activity_main_ruoyu_new
    }

    override fun initData() {
//        mCertificationInfoPresenter.getCertification()
        //腾讯bugly
        Beta.canShowUpgradeActs.add(MainActivity::class.java)
        Beta.upgradeDialogLayoutId = R.layout.dialog_upgrade
        Beta.autoCheckUpgrade = true
        Beta.upgradeCheckPeriod = 60 * 1000
        Bugly.init(this, Constants.BUGLY_ID, false)

    }

    override fun initView() {
        initFragment()
        mRG.setOnCheckedChangeListener(this)

        supportFragmentManager.beginTransaction()
                .replace(R.id.fl_left, MineFragment(), MineFragment()::class.java.simpleName)
                .commitAllowingStateLoss()
        dl_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }

    override fun start() {
//        mainPresenter.getTest()
    }


    private fun initFragment() {
        mFragments = ArrayList()
        mFragments.add(HomeFragment())
        mFragments.add(MillFragment())
        mFragments.add(AssetFragment())
        mFragments.add(CoinMarketFragment())
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_content, mFragments[0], mFragments[0]::class.java.simpleName)
            .commitAllowingStateLoss()

        mRbMall.isChecked = true
        mLastSelect = 0
    }



    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when (checkedId) {

            R.id.mRbMall -> {
                checkFragment(0)
            }
            R.id.mRbMining -> {
                checkFragment(1)
            }
            R.id.mRbHangqing -> {
                checkFragment(3)
            }
            R.id.mRbShopCar -> {
                checkFragment(2)
            }

        }
    }

    fun checkMill()
    {
        checkFragment(1)
        mRbMining.isChecked=true
    }

    fun openMine()
    {
        dl_layout.openDrawer(Gravity.LEFT)
    }


    private fun checkFragment(index: Int) {
        if (index == mLastSelect) {
            return
        }
        if (mFragments.size>index)
        {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.hide(mFragments[mLastSelect])
            val fragment = mFragments[index]
            if (!fragment.isAdded) {
                transaction.add(R.id.fl_content, fragment, fragment::class.java.simpleName).show(fragment)
            } else {
                transaction.show(fragment)
            }
            transaction.commitAllowingStateLoss()
            mLastSelect = index
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        for (mFragment in supportFragmentManager.fragments) {
            mFragment.onActivityResult(requestCode, resultCode, data)
        }
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            if (dl_layout.isDrawerOpen(GravityCompat.START)){
                dl_layout.closeDrawers()
                return false
            }
        }
        return super.onKeyDown(keyCode, event)
    }

}
