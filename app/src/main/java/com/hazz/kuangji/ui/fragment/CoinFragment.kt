package com.hazz.kuangji.ui.fragment


import android.widget.LinearLayout
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseFragment
import com.hazz.kuangji.utils.DensityUtils
import kotlinx.android.synthetic.main.fragment_coin.*

class CoinFragment : BaseFragment() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_coin
    }

    override fun initView() {
        web_view.loadUrl("https://m.huobi.me/zh-cn/market/")
        var layoutParams: LinearLayout.LayoutParams= web_view.layoutParams as LinearLayout.LayoutParams
        layoutParams.topMargin= activity?.let { DensityUtils.getStatusBarHeight(it) }!!
        web_view.layoutParams=layoutParams

    }

    override fun lazyLoad() {

    }



}
