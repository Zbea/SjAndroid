package com.hazz.kuangji.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
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
import kotlinx.android.synthetic.main.fragment_asset.tv_share
import kotlinx.android.synthetic.main.fragment_asset.tv_shouyi
import kotlinx.android.synthetic.main.fragment_asset.tv_static
import kotlinx.android.synthetic.main.fragment_asset.tv_yeji
import kotlinx.android.synthetic.main.fragment_asset.tv_yesterday
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*


class AssetFragment : BaseFragment(), IContractView.AssetView, IContractView.ShouyiView {

    private var mAssetPresenter: AssetPresenter = AssetPresenter(this)
    private var myAsset: MyAsset? = null
    private var list: MutableList<MyAsset.AssetsBean>? = mutableListOf()
    private var incoming: InComing? = null
    private var mIncomingPresenter: IncomingPresenter = IncomingPresenter(this)


    override fun myAsset(msg: MyAsset) {
        if (mView==null ||msg==null)return
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
                tv_fil_balance?.text=coin.balance
                tv_fil_frozen?.text=coin.frozen
                tv_fil_locked?.text=coin.locked
                tv_fil_pledge?.text=coin.pledge
                tv_fil_balance_25?.text=coin.line25
                tv_fil_balance_75?.text=coin.line75
                tv_fil_balance_achievement?.text=coin.achievement
                tv_fil_balance_team?.text=coin.team
            }
            if (coin.coin == "USDT" ) {
                tv_usdt_balance?.text=coin.balance
                tv_usdt_frozen?.text=coin.frozen
            }
        }

    }
    override fun inComing(msg: InComing) {
        incoming = msg
        if (mView==null || tv_yesterday==null)return

        tv_static?.text = msg.invitation
        tv_share?.text = msg.achievement
        tv_yeji?.text = msg.team
        tv_yesterday?.text = BigDecimalUtil.mul( msg.yesterday_usdt,"1",8) + "/" + BigDecimalUtil.mul( msg.yesterday_fcoin,"1",8)


    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_asset
    }


    @SuppressLint("WrongConstant")
    override fun initView() {

        var layoutParams: RelativeLayout.LayoutParams= toolbar.layoutParams as RelativeLayout.LayoutParams
        layoutParams.topMargin= activity?.let { DensityUtils.getStatusBarHeight(it) }!!
        toolbar.layoutParams=layoutParams

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

//        ll_yesterday.setOnClickListener {
//            startActivity(Intent(activity, YesterdayEarningsSourceActivity::class.java))
//        }

        ll_fil.setOnClickListener {
            startActivity(Intent(activity, AssetFilDetailsActivity::class.java))
        }


    }

    override fun lazyLoad() {
        mAssetPresenter.myAsset(false)
        mIncomingPresenter.shouyi(false)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            mAssetPresenter.myAsset(false)
        }
    }




}
