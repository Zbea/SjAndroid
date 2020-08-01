package com.hazz.kuangji.ui.fragment


import android.content.Intent
import android.view.KeyEvent
import android.widget.LinearLayout
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseFragment
import com.hazz.kuangji.ui.activity.home.MsgListActivity
import com.hazz.kuangji.utils.DensityUtils
import kotlinx.android.synthetic.main.fragment_coin_market.*
import kotlinx.android.synthetic.main.fragment_coin_market.iv_msg

class CoinMarketFragment : BaseFragment() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_coin_market
    }

    override fun initView() {

        var layoutParams: LinearLayout.LayoutParams= toolbar.layoutParams as LinearLayout.LayoutParams
        layoutParams.topMargin= activity?.let { DensityUtils.getStatusBarHeight(it) }!!
        toolbar.layoutParams=layoutParams

        iv_msg.setOnClickListener {
            startActivity(Intent(activity, MsgListActivity::class.java))
        }

        web_view.loadUrl("https://m.huobi.me/zh-cn/market/")


        web_view.setOnKeyListener { v, keyCode, event ->

                           if ((keyCode == KeyEvent.KEYCODE_BACK) && web_view.canGoBack()) {
                                web_view.goBack()
                               return@setOnKeyListener true;
                           }
                          return@setOnKeyListener false;
        }

    }

    override fun lazyLoad() {

    }




}
