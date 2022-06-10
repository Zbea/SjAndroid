package com.hazz.kuangji.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseFragment
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Asset
import com.hazz.kuangji.mvp.model.AssetCoin
import com.hazz.kuangji.mvp.presenter.AssetPresenter
import com.hazz.kuangji.ui.activity.MainActivity
import com.hazz.kuangji.ui.activity.asset.AssetCoinRecordActivity
import com.hazz.kuangji.ui.activity.home.ChargeActivity
import com.hazz.kuangji.ui.activity.home.ExtractCoinActivity
import com.hazz.kuangji.ui.activity.mill.MillEargingsListActivity
import com.hazz.kuangji.utils.DensityUtils
import com.hazz.kuangji.utils.SToast
import kotlinx.android.synthetic.main.fragment_asset.*


class AssetFragment : BaseFragment(), IContractView.IAssetView {

    private var mAssetPresenter: AssetPresenter = AssetPresenter(this)

    override fun myAsset(items: List<Asset>) {

        for (coin in items) {
            if (coin.coin == "FIL") {
                tv_fil_balance?.text = coin?.amount
                tv_fil_frozen?.text = coin?.frozen
            }
            if (coin.coin == "USDT") {
                tv_usdt_balance?.text = coin?.amount
                tv_usdt_frozen?.text = coin?.frozen
            }
            if (coin.coin == "FIL2") {
                tv_fil2_balance?.text = coin?.amount
                tv_fil2_frozen?.text = coin?.frozen
                ll_fil2.visibility= View.VISIBLE
            }
            if (coin.coin == "FIL3") {
                tv_fil3_balance?.text = coin?.amount
                tv_fil3_frozen?.text = coin?.frozen
                ll_fil3.visibility= View.VISIBLE
            }
            if (coin.coin == "BZZ") {
                tv_bzz_balance?.text = coin?.amount
                tv_bzz_frozen?.text = coin?.frozen
                ll_bzz.visibility= View.VISIBLE
            }
            if (coin.coin == "CHIA") {
                tv_chia_balance?.text = coin?.amount
                tv_chia_frozen?.text = coin?.frozen
                ll_chia.visibility= View.VISIBLE
            }
        }

    }
    override fun getAssetCoinList(items: List<AssetCoin>) {
        TODO("Not yet implemented")
    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_asset
    }


    @SuppressLint("WrongConstant")
    override fun initView() {

        var layoutParams: LinearLayout.LayoutParams =
            tv_top.layoutParams as LinearLayout.LayoutParams
        layoutParams.topMargin = activity?.let { DensityUtils.getStatusBarHeight(it) }!!
        tv_top.layoutParams = layoutParams

        sl_refresh = activity?.findViewById(R.id.sl_refresh_asset)
        sl_refresh?.isRefreshing = true
        sl_refresh?.setColorSchemeResources(R.color.color_main)
        sl_refresh?.setOnRefreshListener {
            lazyLoad()
        }

        ll_fil.setOnClickListener {
            startActivity(Intent(context, AssetCoinRecordActivity::class.java).putExtra("coin","FIL"))
        }
        ll_fil2.setOnClickListener {
            startActivity(Intent(context, AssetCoinRecordActivity::class.java).putExtra("coin","FIL2"))
        }
        ll_fil3.setOnClickListener {
            startActivity(Intent(context, AssetCoinRecordActivity::class.java).putExtra("coin","FIL3"))
        }
        ll_bzz.setOnClickListener {
            startActivity(Intent(context, AssetCoinRecordActivity::class.java).putExtra("coin","BZZ"))
        }
        ll_chia.setOnClickListener {
            startActivity(Intent(context, AssetCoinRecordActivity::class.java).putExtra("coin","CHIA"))
        }
        ll_usdt.setOnClickListener {
            startActivity(Intent(context, AssetCoinRecordActivity::class.java).putExtra("coin","USDT"))
        }
        ll_recharge.setOnClickListener {
//            startActivity(Intent(activity, ChargeActivity::class.java))
            SToast.showText("抱歉，暫不支持")
        }
        ll_extract.setOnClickListener {
            if ((activity as MainActivity).isCertificated())
                startActivity(Intent(activity, ExtractCoinActivity::class.java))
        }

    }

    override fun lazyLoad() {
        mAssetPresenter.myAsset(false)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            mAssetPresenter.myAsset(false)
        }
    }


}
