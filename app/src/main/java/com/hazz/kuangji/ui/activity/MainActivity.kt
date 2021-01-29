package com.hazz.kuangji.ui.activity

import android.content.Intent
import android.view.Gravity
import android.view.KeyEvent
import android.widget.RadioGroup
import android.widget.RelativeLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.hazz.kuangji.Constants
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Certification
import com.hazz.kuangji.mvp.model.Config
import com.hazz.kuangji.mvp.presenter.CertificationInfoPresenter
import com.hazz.kuangji.mvp.presenter.ConfigPresenter
import com.hazz.kuangji.ui.fragment.*
import com.hazz.kuangji.utils.DensityUtils
import com.hazz.kuangji.utils.SPUtil
import com.hazz.kuangji.utils.StatusBarUtil
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import kotlinx.android.synthetic.main.activity_main_ruoyu_new.*
import kotlinx.android.synthetic.main.activity_main_ruoyu_new.iv_mine


class   MainActivity : BaseActivity(), RadioGroup.OnCheckedChangeListener ,IContractView.IConfigView, IContractView.ICertificationInfoView{

    private var mConfigPresenter=ConfigPresenter(this)
    private val mCertificationInfoPresenter = CertificationInfoPresenter(this)
    private lateinit var mFragments: ArrayList<Fragment>
    private var mLastSelect = 0

    override fun getCertification(certification: Certification) {
        if (certification.status == 1) {
            SPUtil.putObj("certification", certification)
        }
    }
 
    override fun getConfig(item: Config) {
        SPUtil.putObj("Config",item)
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
        Beta.upgradeCheckPeriod = 20 * 1000
        Bugly.init(this, Constants.BUGLY_ID, false)

    }

    override fun initView() {
        initFragment()
        mRG.setOnCheckedChangeListener(this)

        supportFragmentManager.beginTransaction()
                .replace(R.id.fl_left, MineFragment(), MineFragment()::class.java.simpleName)
                .commitAllowingStateLoss()
        dl_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)

        var layoutParams: RelativeLayout.LayoutParams= ll_header.layoutParams as RelativeLayout.LayoutParams
        layoutParams.topMargin= DensityUtils.getStatusBarHeight(this)
        ll_header.layoutParams=layoutParams

        setImage()

        iv_mine.setOnClickListener {
            openMine()
        }
    }

    override fun start() {
        mConfigPresenter.getConfig()
    }


    private fun initFragment() {
        mFragments = ArrayList()
        mFragments.add(HomeFragment())
        mFragments.add(MillFragment())
        mFragments.add(AssetFragment())
        mFragments.add(ClusterFragment())
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

    private fun openMine()
    {
        dl_layout.openDrawer(Gravity.LEFT)
    }

    /**
     * 设置头像
     */
    fun setImage()
    {
        val requestOptions= RequestOptions.bitmapTransform(CircleCrop()).error(R.mipmap.icon_home_mine)
        Glide.with(this).load(Constants.URL_INVITE+SPUtil.getString("image")).apply(requestOptions).into(iv_mine)
    }

    fun setStateMode(s:Boolean)
    {
        StatusBarUtil.darkMode(this,s)
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
