package com.hazz.kuangji.ui.activity

import android.content.Intent
import android.os.Handler
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import com.hazz.kuangji.Constants
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Account
import com.hazz.kuangji.mvp.model.Certification
import com.hazz.kuangji.mvp.model.UploadModel
import com.hazz.kuangji.mvp.presenter.CertificationInfoPresenter
import com.hazz.kuangji.mvp.presenter.MyAccountPresenter
import com.hazz.kuangji.net.BaseView
import com.hazz.kuangji.net.IBaseView
import com.hazz.kuangji.ui.activity.mine.MineCertificationActivity
import com.hazz.kuangji.ui.fragment.AssetFragment
import com.hazz.kuangji.ui.fragment.HomeFragment
import com.hazz.kuangji.ui.fragment.MillFragment
import com.hazz.kuangji.ui.fragment.MyFragment
import com.hazz.kuangji.utils.SPUtil
import com.hazz.kuangji.utils.SToast
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*
import kotlin.collections.ArrayList

class  MainActivity : BaseActivity(), RadioGroup.OnCheckedChangeListener,IContractView.IAccountView,IContractView.ICertificationInfoView  {
    private var myAccountPresenter=MyAccountPresenter(this)
    private val mCertificationInfoPresenter = CertificationInfoPresenter(this)
    private lateinit var mFragments: ArrayList<Fragment>
    private var mLastSelect = 0
    var mCertification:Certification?=null


    override fun getCertification(certification: Certification) {
        mCertification = certification
        if (certification.stat == "1") {
            SPUtil.putObj("certification", certification)
        }
    }

    override fun getAccount(msg: Account) {
        SPUtil.putString("user_register_url",msg.userRegisterUrl )
        SPUtil.putString("mobile",msg.mobile)
        SPUtil.putString("username",msg.userName)
        SPUtil.putString("image",msg.img)
        SPUtil.putString("invitation_code",msg.inviteCode)
    }
    override fun setHeader(msg: UploadModel) {
        TODO("Not yet implemented")
    }


    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
    }

    override fun initView() {
        EventBus.getDefault().register(this)
        mCertification= SPUtil.getObj("certification", Certification::class.java)
        initFragment()
        mRG.setOnCheckedChangeListener(this)
    }

    override fun start() {
        myAccountPresenter.getAccount()
        mCertificationInfoPresenter.getCertification()
    }

    private fun initFragment() {
        mFragments = ArrayList()
        mFragments.add(HomeFragment())
        mFragments.add(MillFragment())
        mFragments.add(AssetFragment())
        mFragments.add(MyFragment())
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_content, mFragments[0], mFragments[0]::class.java.simpleName)
            .commitAllowingStateLoss()

        mRbHome.isChecked = true
        mLastSelect = 0
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when (checkedId) {

            R.id.mRbHome -> {
                checkFragment(0)
            }
            R.id.mRbMining -> {
                checkFragment(1)
            }
            R.id.mRbAsset -> {
                checkFragment(2)
            }
            R.id.mRbMy -> {
                checkFragment(3)
            }

        }
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


    /**
     * 判断是否已经实名认证
     */
    fun isCertificated():Boolean
    {
        mCertification= SPUtil.getObj("certification", Certification::class.java)
        when (mCertification?.stat) {
            "0"-> {
                SToast.showText("实名认证审核中，请稍等")
                return false
            }
            "1"-> {
                return true
            }
            else -> {
                SToast.showText("尚未实名认证，请先前往实名认证")
                Handler().postDelayed(Runnable {
                    startActivity(Intent(this, MineCertificationActivity::class.java))
                }, 500)
                return false
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: String) {
        if (event == Constants.CODE_CERTIFICATION_BROAD) {
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    if (mCertification?.stat!="1")
                    {
                        mCertificationInfoPresenter.getCertification()
                    }
                }
            } , 0, 3*60*1000)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        for (mFragment in supportFragmentManager.fragments) {
            mFragment.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}
