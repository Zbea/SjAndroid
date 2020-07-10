package com.hazz.kuangji.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.hazz.kuangji.Constants
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.events.Index
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.presenter.MainPresenter
import com.hazz.kuangji.ui.fragment.*
import com.hazz.kuangji.utils.RxBus
import com.hazz.kuangji.utils.StatusBarUtil
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import kotlinx.android.synthetic.main.activity_main_ruoyu_new.*


class   MainActivity : BaseActivity(), RadioGroup.OnCheckedChangeListener ,IContractView.MainView{

    private var mainPresenter=MainPresenter(this)
    private lateinit var mFragments: ArrayList<Fragment>
    private var mLastSelect = 0

    override fun layoutId(): Int {
        return R.layout.activity_main_ruoyu_new
    }

    override fun initData() {
        StatusBarUtil.darkMode(this,false)
    }

    override fun initView() {
        initFragment()
        mRG.setOnCheckedChangeListener(this)
        RxBus.get().observerOnMain(this,Index::class.java) {
            checkFragment(1)
            mRbMining.isChecked = true
        }

        //腾讯bugly
        Beta.canShowUpgradeActs.add(MainActivity::class.java)
        Beta.upgradeDialogLayoutId = R.layout.dialog_upgrade
        Beta.autoCheckUpgrade = true
        Bugly.init(this, Constants.BUGLY_ID, false)
    }

    override fun start() {
//        mainPresenter.getTest()
    }


    private fun initFragment() {
        mFragments = ArrayList()
        if (mSaveState==null)
        {
            mFragments.add(HomeFragment())
            mFragments.add(MillFragment())
            mFragments.add(AssetFragment())
            mFragments.add(CoinMarketFragment())
            mFragments.add(MineFragment())
        }
        else
        {
            supportFragmentManager.findFragmentByTag(HomeFragment::class.java.simpleName)?.let { mFragments.add(it) }
            supportFragmentManager.findFragmentByTag(MillFragment::class.java.simpleName)?.let { mFragments.add(it) }
            supportFragmentManager.findFragmentByTag(AssetFragment::class.java.simpleName)?.let { mFragments.add(it) }
            supportFragmentManager.findFragmentByTag(CoinMarketFragment::class.java.simpleName)?.let { mFragments.add(it) }
            supportFragmentManager.findFragmentByTag(MineFragment::class.java.simpleName)?.let { mFragments.add(it) }
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.mFrame, mFragments[0], mFragments[0]::class.java.simpleName)
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
            R.id.mRbOtc -> {
                checkFragment(4)

            }


        }
    }

    private fun checkFragment(index: Int) {
        if (index == mLastSelect) {
            return
        }
        val transaction = supportFragmentManager.beginTransaction()
        transaction.hide(mFragments[mLastSelect])
        val fragment = mFragments[index]
        if (!fragment.isAdded) {
            transaction.add(R.id.mFrame, fragment, fragment::class.java.simpleName).show(fragment)
        } else {
            transaction.show(fragment)
        }
        transaction.commitAllowingStateLoss()
        mLastSelect = index

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        for (mFragment in supportFragmentManager.fragments) {
            mFragment.onActivityResult(requestCode, resultCode, data)
        }
    }



}
