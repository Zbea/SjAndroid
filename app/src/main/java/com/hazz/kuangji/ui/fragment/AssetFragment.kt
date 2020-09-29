package com.hazz.kuangji.ui.fragment

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
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
import com.hazz.kuangji.ui.activity.asset.ChargeActivity
import com.hazz.kuangji.ui.activity.asset.ExtractCoinActivity
import com.hazz.kuangji.ui.activity.asset.IncomingActivity
import com.hazz.kuangji.ui.activity.asset.TransferCoinActivity
import com.hazz.kuangji.ui.activity.home.MsgListActivity
import com.hazz.kuangji.ui.activity.mill.MillRecordActivity
import com.hazz.kuangji.ui.activity.mine.MineCertificationActivity
import com.hazz.kuangji.ui.adapter.AssetAdapter
import com.hazz.kuangji.utils.*
import com.hazz.kuangji.widget.RewardItemDeco
import com.scwang.smartrefresh.layout.util.DensityUtil
import kotlinx.android.synthetic.main.fragment_asset.*
import kotlinx.android.synthetic.main.fragment_asset.iv_msg
import kotlinx.android.synthetic.main.fragment_asset.recycle_view
import kotlinx.android.synthetic.main.fragment_asset.toolbar
import kotlinx.android.synthetic.main.fragment_asset.tv_share
import kotlinx.android.synthetic.main.fragment_asset.tv_shouyi
import kotlinx.android.synthetic.main.fragment_asset.tv_static
import kotlinx.android.synthetic.main.fragment_asset.tv_touzi
import kotlinx.android.synthetic.main.fragment_asset.tv_yesterday
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*


class AssetFragment : BaseFragment(), IContractView.AssetView, IContractView.ICertificationInfoView,IContractView.ShouyiView {

    private var mCertification: Certification? = null
    private var mAssetPresenter: AssetPresenter = AssetPresenter(this)
    private val mCertificationInfoPresenter = CertificationInfoPresenter(this)
    private var myAsset: MyAsset? = null
    private var mAdapter: AssetAdapter? = null
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
        if (mView==null || tv_copy==null||tv_shouyi==null)return
        myAsset = msg
        tv_copy?.text = msg.wallet_address

        if (msg.investment != null) {
            tv_touzi?.text = BigDecimalUtil.mul(msg.investment.toString(), "1", 8)
        }

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
            if (coin.coin != "BTC" && coin.coin != "ETH") {
                list?.add(coin)
            }
        }
        mAdapter?.setNewData(list)

    }
    override fun inComing(msg: InComing) {
        incoming = msg
        if (mView==null || tv_static==null||tv_share==null|| tv_team==null||tv_yesterday==null)return
        if (msg.invitation != null) {
            tv_static.text = msg.invitation
        }
        if (msg.achievement != null) {
            tv_share.text = msg.achievement
        }
        if (msg.team != null) {
            tv_team.text = msg.team
        }

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

        tv_copy.setOnClickListener {
            val cm = activity?.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
            val clipData = ClipData.newPlainText("invitation_code", tv_copy.text)

            cm.primaryClip = clipData

            SToast.showText("已成功复制钱包地址")
        }

        rl_share.setOnClickListener {
            startActivity(Intent(activity, IncomingActivity::class.java).setFlags(0))
        }
        rl_performance.setOnClickListener {
            startActivity(Intent(activity, IncomingActivity::class.java).setFlags(1))
        }
        rl_team.setOnClickListener {
            startActivity(Intent(activity, IncomingActivity::class.java).setFlags(2))
        }
        rl_yesterday.setOnClickListener {
            startActivity(Intent(activity, MillRecordActivity::class.java))
        }

        if(SPUtil.getBoolean("hide"))
        {
            cb.isChecked = true
            tv_touzi.transformationMethod = PasswordTransformationMethod.getInstance()
            tv_shouyi.transformationMethod = PasswordTransformationMethod.getInstance()
            tv_static.transformationMethod = PasswordTransformationMethod.getInstance()
            tv_share.transformationMethod = PasswordTransformationMethod.getInstance()
            tv_team.transformationMethod = PasswordTransformationMethod.getInstance()
            tv_yesterday.transformationMethod = PasswordTransformationMethod.getInstance()
        }

        cb.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked)
            {
                SPUtil.putBoolean("hide",true)
                tv_touzi.transformationMethod = PasswordTransformationMethod.getInstance()
                tv_shouyi.transformationMethod = PasswordTransformationMethod.getInstance()
                tv_static.transformationMethod = PasswordTransformationMethod.getInstance()
                tv_share.transformationMethod = PasswordTransformationMethod.getInstance()
                tv_team.transformationMethod = PasswordTransformationMethod.getInstance()
                tv_yesterday.transformationMethod = PasswordTransformationMethod.getInstance()
            }
            else
            {
                SPUtil.putBoolean("hide",false)
                tv_touzi.transformationMethod = HideReturnsTransformationMethod.getInstance()
                tv_shouyi.transformationMethod = HideReturnsTransformationMethod.getInstance()
                tv_static.transformationMethod = HideReturnsTransformationMethod.getInstance()
                tv_share.transformationMethod = HideReturnsTransformationMethod.getInstance()
                tv_team.transformationMethod = HideReturnsTransformationMethod.getInstance()
                tv_yesterday.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }
            mAdapter?.notifyDataSetChanged()
        }


        recycle_view.layoutManager = LinearLayoutManager(activity)//创建布局管理
        mAdapter = AssetAdapter(R.layout.item_asset, null)
        recycle_view.adapter = mAdapter
        mAdapter?.bindToRecyclerView(recycle_view)
        mAdapter?.setEmptyView(R.layout.fragment_empty)
        val leftRightOffset = DensityUtil.dp2px(15f)
        recycle_view.addItemDecoration(RewardItemDeco(0, 0, 0, leftRightOffset, 0))

        //定时器(每隔三分钟让下拉刷新可以执行)
        Timer().schedule(object : TimerTask() {
            override fun run() {
                Log.i("sj","开始计时")
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
