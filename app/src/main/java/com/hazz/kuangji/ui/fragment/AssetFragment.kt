package com.hazz.kuangji.ui.fragment

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.os.Handler
import androidx.recyclerview.widget.LinearLayoutManager
import com.hazz.kuangji.Constants
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseFragment
import com.hazz.kuangji.events.Index
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Certification
import com.hazz.kuangji.mvp.model.MyAsset
import com.hazz.kuangji.mvp.presenter.CertificationInfoPresenter
import com.hazz.kuangji.mvp.presenter.ZichanPresenter
import com.hazz.kuangji.ui.activity.asset.ChargeActivity
import com.hazz.kuangji.ui.activity.asset.ExtractCoinActivity
import com.hazz.kuangji.ui.activity.asset.IncomingActivity
import com.hazz.kuangji.ui.activity.asset.TransferCoinActivity
import com.hazz.kuangji.ui.activity.mine.MineCertificationActivity
import com.hazz.kuangji.ui.adapter.AssetAdapter
import com.hazz.kuangji.utils.*
import com.hazz.kuangji.widget.RewardItemDeco
import com.scwang.smartrefresh.layout.util.DensityUtil
import kotlinx.android.synthetic.main.fragment_asset.*
import kotlinx.android.synthetic.main.fragment_asset.recycle_view
import kotlinx.android.synthetic.main.fragment_asset.rl_shouyi
import kotlinx.android.synthetic.main.fragment_asset.rl_touzi
import kotlinx.android.synthetic.main.fragment_asset.tv_shouyi
import kotlinx.android.synthetic.main.fragment_asset.tv_touzi
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class AssetFragment : BaseFragment(), IContractView.ZichanView, IContractView.ICertificationInfoView {

    private var mCertification: Certification? = null
    private var mZichanPresenter: ZichanPresenter = ZichanPresenter(this)
    private val mCertificationInfoPresenter = CertificationInfoPresenter(this)
    private var myAsset: MyAsset? = null
    private var isShow = true
    private var mAdapter: AssetAdapter? = null
    private var list: MutableList<MyAsset.AssetsBean>? = mutableListOf()

    override fun getCertification(certification: Certification) {
        mCertification = certification
        if (certification.status == 1) {
            SPUtil.putObj("certification", certification)
        }
    }

    override fun myAsset(msg: MyAsset) {
        sl_refresh?.isRefreshing = false
        myAsset = msg
        tv_copy.text = msg.wallet_address
        if (msg.investment != null) {
            tv_touzi.text = BigDecimalUtil.mul(msg.investment.toString(), "1", 8)
        }

        if (msg.usdt_revenue != null && msg.fcoin_revenue != null) {
            tv_shouyi.text = BigDecimalUtil.mul(msg.usdt_revenue, "1", 8) + "/" + BigDecimalUtil.mul(msg.fcoin_revenue, "1", 8)
        }
        if (msg.usdt_revenue != null && msg.fcoin_revenue == null) {
            tv_shouyi.text = BigDecimalUtil.mul(msg.usdt_revenue, "1", 8) + "/0.00"
        }
        if (msg.usdt_revenue == null && msg.fcoin_revenue != null) {
            tv_shouyi.text = "0.00/" + BigDecimalUtil.mul(msg.fcoin_revenue, "1", 8)
        }

        val assets = msg.assets
        list!!.clear()
        for (coin in assets) {
            if (coin.coin != "BTC" && coin.coin != "ETH") {
                list!!.add(coin)
            }
        }
        mAdapter?.setNewData(list)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_asset
    }


    override fun initView() {
        EventBus.getDefault().register(this)
        mCertification = SPUtil.getObj("certification", Certification::class.java)

        sl_refresh=activity?.findViewById(R.id.sl_refresh_asset)
        sl_refresh?.isRefreshing = true
        sl_refresh?.setColorSchemeResources(R.color.blue)
        sl_refresh?.setOnRefreshListener {
            lazyLoad()
        }

        tv_tibi.setOnClickListener {
            if (myAsset != null) {
                when (mCertification?.status) {
                    0 -> {
                        SToast.showText("实名认证审核中，请稍等")
                    }
                    1 -> {
                        startActivity(Intent(activity, ExtractCoinActivity::class.java).putExtra("amount", myAsset))
                    }
                    else -> {
                        SToast.showText("尚未实名认证，请前往实名认证")
                        Handler().postDelayed(Runnable {
                            val intent = Intent(activity, MineCertificationActivity::class.java)
                            startActivity(intent)
                        }, 500)
                    }
                }
            }
        }
        tv_transfer.setOnClickListener {
            if (myAsset != null) {
                when (mCertification?.status) {
                    0 -> {
                        SToast.showText("实名认证审核中，请稍等")
                    }
                    1 -> {
                        startActivity(Intent(activity, TransferCoinActivity::class.java))
                    }
                    else -> {
                        SToast.showText("尚未实名认证，请前往实名认证")
                        Handler().postDelayed(Runnable {
                            startActivity(Intent(activity, MineCertificationActivity::class.java))
                        }, 500)
                    }
                }
            }
        }

        rl_charge.setOnClickListener {
            startActivity(Intent(activity, ChargeActivity::class.java))
        }
        rl_touzi.setOnClickListener {
            RxBus.get().send(Index())
        }
        rl_shouyi.setOnClickListener {
            startActivity(Intent(activity, IncomingActivity::class.java))
        }
        tv_copy.setOnClickListener {
            val cm = activity!!.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
            val clipData = ClipData.newPlainText("invitation_code", tv_copy.text)

            cm.primaryClip = clipData

            SToast.showText("已成功复制钱包地址")
        }

        recycle_view.layoutManager = LinearLayoutManager(activity)//创建布局管理
        mAdapter = AssetAdapter(R.layout.item_asset, null)
        recycle_view.adapter = mAdapter
        mAdapter?.bindToRecyclerView(recycle_view)
        mAdapter?.setEmptyView(R.layout.fragment_empty)
        val leftRightOffset = DensityUtil.dp2px(15f)
        recycle_view.addItemDecoration(RewardItemDeco(0, 0, 0, leftRightOffset, 0)
        )
    }

    override fun lazyLoad() {
        mZichanPresenter.myAsset()
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
