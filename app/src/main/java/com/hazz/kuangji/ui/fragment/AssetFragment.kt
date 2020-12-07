



package com.hazz.kuangji.ui.fragment

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.RelativeLayout
import com.hazz.kuangji.Constants
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseFragment
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Certification
import com.hazz.kuangji.mvp.model.InComing
import com.hazz.kuangji.mvp.model.MyAsset
import com.hazz.kuangji.mvp.presenter.CertificationInfoPresenter
import com.hazz.kuangji.mvp.presenter.AssetPresenter
import com.hazz.kuangji.mvp.presenter.IncomingPresenter
import com.hazz.kuangji.ui.activity.asset.AssetFilDetailsActivity
import com.hazz.kuangji.ui.activity.asset.IncomingActivity
import com.hazz.kuangji.ui.activity.asset.YesterdayEarningsSourceActivity
import com.hazz.kuangji.ui.activity.home.MsgListActivity
import com.hazz.kuangji.utils.*
import kotlinx.android.synthetic.main.fragment_asset.*
import kotlinx.android.synthetic.main.fragment_asset.iv_msg
import kotlinx.android.synthetic.main.fragment_asset.toolbar
import kotlinx.android.synthetic.main.fragment_asset.tv_shouyi
import kotlinx.android.synthetic.main.fragment_asset.tv_yesterday
import kotlinx.android.synthetic.main.item_asset.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*


class

AssetFragment : BaseFragment(), IContractView.AssetView, IContractView.ICertificationInfoView,IContractView.ShouyiView {

    private var mCertification: Certification? = null
    private var mAssetPresenter: AssetPresenter = AssetPresenter(this)
    private val mCertificationInfoPresenter = CertificationInfoPresenter(this)
    private var myAsset: MyAsset? = null
    private var list: MutableList<MyAsset.AssetsBean>? = mutableListOf()
    private var incoming: InComing? = null
    private var mIncomingPresenter: IncomingPresenter = IncomingPresenter(this)

    override fun getCertification(certification: Certification) {
        sl_refresh?.isRefreshing = false
        mCertification = certification
        if (certification.status == 1) {
            SPUtil.putObj("certification", certification)
        }
    }

    override fun myAsset(msg: MyAsset) {
        if (mView==null ||tv_shouyi==null)return
        myAsset = msg

        if (msg.usdt_revenue != null && msg.fcoin_revenue != null) {
            tv_shouyi?.text = BigDecimalUtil.mul(msg.usdt_revenue, "1", 8) + "/" + BigDecimalUtil.mul(msg.fcoin_revenue, "1", 8)
        }
        if (msg.usdt_revenue != null && msg.fcoin_revenue == null) {
            tv_shouyi?.text = BigDecimalUtil.mul(msg.usdt_revenue, "1", 8) + "/0.00"
        }
        if (msg.usdt_revenue == null && msg.fcoin_revenue != null) {
            tv_shouyi?.text = "0.00/" + BigDecimalUtil.mul(msg.fcoin_revenue, "1", 8)
        }

        val assets = msg.assets
        list?.clear()
        for (coin in assets) {
            if (coin.coin == "FCOIN" ) {
                tv_fil_balance.text=coin.balance
                tv_fil_frozen.text=coin.frozen
                tv_fil_locked.text=coin.locked
                tv_fil_pledge.text=coin.pledge
                tv_fil_balance_25.text=coin.line25
                tv_fil_balance_75.text=coin.line75
                tv_fil_balance_achievement.text=coin.achievement
                tv_fil_balance_team.text=coin.team
            }
            if (coin.coin == "USDT" ) {
                tv_usdt_balance.text=coin.balance
                tv_usdt_frozen.text=coin.frozen
            }
        }

    }
    override fun inComing(msg: InComing) {
        incoming = msg
        if (mView==null || tv_yesterday==null)return


        if (msg.yesterday_usdt != null&& msg.yesterday_fcoin!=null) {
            tv_yesterday.text = BigDecimalUtil.mul( msg.yesterday_usdt,"1",8) + "/" + BigDecimalUtil.mul( msg.yesterday_fcoin,"1",8)
        }

        if (msg.yesterday_usdt != null&& msg.yesterday_fcoin==null) {
            tv_yesterday.text = BigDecimalUtil.mul( msg.yesterday_usdt,"1",8) + "/" +"0.00"
        }
        if (msg.yesterday_usdt == null&& msg.yesterday_fcoin!=null) {
            tv_yesterday.text ="0.00" + "/" + BigDecimalUtil.mul( msg.yesterday_fcoin,"1",8)
        }
    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_asset
    }


    @SuppressLint("WrongConstant")
    override fun initView() {

        var layoutParams: RelativeLayout.LayoutParams= toolbar.layoutParams as RelativeLayout.LayoutParams
        layoutParams.topMargin= activity?.let { DensityUtils.getStatusBarHeight(it) }!!
        toolbar.layoutParams=layoutParams

        EventBus.getDefault().register(this)
        mCertification = SPUtil.getObj("certification", Certification::class.java)

        sl_refresh=activity?.findViewById(R.id.sl_refresh_asset)
        sl_refresh?.isRefreshing = true
        sl_refresh?.setColorSchemeResources(R.color.color_main)
        sl_refresh?.setOnRefreshListener {
            lazyLoad()
        }

        iv_msg.setOnClickListener {
            startActivity(Intent(activity, MsgListActivity::class.java))
        }
        rl_earnings.setOnClickListener {
            startActivity(Intent(activity, IncomingActivity::class.java))
        }

        rl_yesterday.setOnClickListener {
            startActivity(Intent(activity, YesterdayEarningsSourceActivity::class.java))
        }

        rl_fil_balance.setOnClickListener {
            startActivity(Intent(activity, AssetFilDetailsActivity::class.java))
        }


        if(SPUtil.getBoolean("hide"))
        {
            cb.isChecked = true
            tv_shouyi.transformationMethod = PasswordTransformationMethod.getInstance()

            tv_fil_balance.transformationMethod = PasswordTransformationMethod.getInstance()
            tv_fil_frozen.transformationMethod = PasswordTransformationMethod.getInstance()
            tv_fil_locked.transformationMethod = PasswordTransformationMethod.getInstance()
            tv_fil_pledge.transformationMethod = PasswordTransformationMethod.getInstance()
            tv_fil_balance_25.transformationMethod = PasswordTransformationMethod.getInstance()
            tv_fil_balance_75.transformationMethod = PasswordTransformationMethod.getInstance()
            tv_fil_balance_achievement.transformationMethod = PasswordTransformationMethod.getInstance()
            tv_fil_balance_team.transformationMethod = PasswordTransformationMethod.getInstance()
            tv_usdt_balance.transformationMethod = PasswordTransformationMethod.getInstance()
            tv_usdt_frozen.transformationMethod = PasswordTransformationMethod.getInstance()
        }

        cb.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked)
            {
                SPUtil.putBoolean("hide",true)
                tv_shouyi.transformationMethod = PasswordTransformationMethod.getInstance()
                tv_yesterday.transformationMethod = PasswordTransformationMethod.getInstance()
                tv_fil_balance.transformationMethod = PasswordTransformationMethod.getInstance()
                tv_fil_frozen.transformationMethod = PasswordTransformationMethod.getInstance()
                tv_fil_locked.transformationMethod = PasswordTransformationMethod.getInstance()
                tv_fil_pledge.transformationMethod = PasswordTransformationMethod.getInstance()
                tv_fil_balance_25.transformationMethod = PasswordTransformationMethod.getInstance()
                tv_fil_balance_75.transformationMethod = PasswordTransformationMethod.getInstance()
                tv_fil_balance_achievement.transformationMethod = PasswordTransformationMethod.getInstance()
                tv_fil_balance_team.transformationMethod = PasswordTransformationMethod.getInstance()
                tv_usdt_balance.transformationMethod = PasswordTransformationMethod.getInstance()
                tv_usdt_frozen.transformationMethod = PasswordTransformationMethod.getInstance()
            }
            else
            {
                SPUtil.putBoolean("hide",false)
                tv_shouyi.transformationMethod = HideReturnsTransformationMethod.getInstance()
                tv_yesterday.transformationMethod = HideReturnsTransformationMethod.getInstance()
                tv_fil_balance.transformationMethod = HideReturnsTransformationMethod.getInstance()
                tv_fil_frozen.transformationMethod = HideReturnsTransformationMethod.getInstance()
                tv_fil_locked.transformationMethod = HideReturnsTransformationMethod.getInstance()
                tv_fil_pledge.transformationMethod = HideReturnsTransformationMethod.getInstance()
                tv_fil_balance_25.transformationMethod = HideReturnsTransformationMethod.getInstance()
                tv_fil_balance_75.transformationMethod = HideReturnsTransformationMethod.getInstance()
                tv_fil_balance_achievement.transformationMethod = HideReturnsTransformationMethod.getInstance()
                tv_fil_balance_team.transformationMethod = HideReturnsTransformationMethod.getInstance()
                tv_usdt_balance.transformationMethod = HideReturnsTransformationMethod.getInstance()
                tv_usdt_frozen.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }
        }


        //定时器(每隔三分钟让下拉刷新可以执行)
        Timer().schedule(object : TimerTask() {
            override fun run() {
                sl_refresh?.isEnabled=true
            }
        } , 0, 3*60*1000)
    }

    override fun lazyLoad() {
        sl_refresh?.isEnabled=false
        mAssetPresenter.myAsset(false)
        mIncomingPresenter.shouyi(false)
        mCertificationInfoPresenter.getCertification()


    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: String) {
        if (event == Constants.CODE_CERTIFICATION_BROAD) {
            mCertificationInfoPresenter.getCertification()
        }
        if (event == Constants.CODE_INVESTMENT_BUY) {
            mAssetPresenter.myAsset(false)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            if (mCertification?.status==0)
            {
                mCertificationInfoPresenter.getCertification()
            }
        }
    }




}
