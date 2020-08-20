package com.hazz.kuangji.ui.fragment

import android.content.Intent
import android.text.SpannableString
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseFragment
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Contract
import com.hazz.kuangji.mvp.model.Home
import com.hazz.kuangji.mvp.model.Msg
import com.hazz.kuangji.mvp.presenter.HomePresenter
import com.hazz.kuangji.mvp.presenter.MsgPresenter
import com.hazz.kuangji.ui.activity.home.*
import com.hazz.kuangji.ui.adapter.HomeAdapter
import com.hazz.kuangji.utils.DisplayManager
import com.hazz.kuangji.utils.DpUtils
import com.hazz.kuangji.widget.GlideImageLoader
import com.hazz.kuangji.widget.RewardItemDeco
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.fragment_new_home.*
import java.util.*

class HomeFragment : BaseFragment(), IContractView.HomeView, IContractView.MsgView {

    private var adList: MutableList<String>? = mutableListOf()
    private var mAdapter: HomeAdapter? = null
    private var mHomePresenter: HomePresenter = HomePresenter(this)
    private var mCoinPresenter: MsgPresenter = MsgPresenter(this)


    override fun getMsg(rows: List<Msg>) {
        if (mView==null||activity==null)return
        if (!rows.isNullOrEmpty()) {
            var listInfo=ArrayList<CharSequence>()
            for (s in rows)
            {
                listInfo.add(SpannableString(s.title))
            }
            marqueeView.startWithList(listInfo)
            marqueeView.setOnItemClickListener { position, textView ->
                val item = rows[position]
                val intent = Intent(activity, MsgDescActivity::class.java)
                intent.putExtra("message", item)
                startActivity(intent)
            }
        }
    }

    override fun getHome(msg: Home) {
        if (mView==null||activity==null)return
        sl_refresh?.isRefreshing=false

        val carousel = msg.carousel
        if (!carousel.isNullOrEmpty()) {
            adList?.clear()
            for (a in carousel) {
                adList?.add(a.url)
            }
            initBanner(adList!!)
        }
        mAdapter?.setNewData(msg.products)
    }

    override fun zuyongSucceed(contract: Contract) {
    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_new_home
    }


    override fun initView() {
        sl_refresh=activity?.findViewById(R.id.sl_refresh)
        sl_refresh?.isRefreshing=true
        sl_refresh?.setColorSchemeResources(R.color.blue)
        sl_refresh?.setOnRefreshListener {
            lazyLoad()
        }

        recycle_view.layoutManager = LinearLayoutManager(activity)//创建布局管理
        mAdapter = HomeAdapter(R.layout.item_home, null)
        recycle_view.adapter = mAdapter
        mAdapter!!.bindToRecyclerView(recycle_view)
        mAdapter!!.setEmptyView(R.layout.fragment_empty)
        val leftRightOffset = DensityUtil.dp2px(15f)

        recycle_view.addItemDecoration(RewardItemDeco(0, 0, 0, leftRightOffset, 0))

        ll_charge.setOnClickListener {
            startActivity(Intent(activity, ExchangeBuyActivity::class.java))
        }

        ll_sale.setOnClickListener {
            startActivity(Intent(activity, ExchangeSaleActivity::class.java))
        }

        ll_exchange.setOnClickListener {
            startActivity(Intent(activity, ExchangeCoinActivity::class.java))
        }

        tv_more.setOnClickListener {
            startActivity(Intent(activity, ContractSigningActivity::class.java))
        }

    }

    override fun lazyLoad() {
        mCoinPresenter.getMsg()
        mHomePresenter.getHome()
    }

    private fun initBanner(list: List<String>) {
        banner?.run {
            setImageLoader(GlideImageLoader())
            var layoutParams1: ViewGroup.LayoutParams = banner.layoutParams
            layoutParams1.height = ((DisplayManager.getScreenWidth()?.minus(DpUtils.dip2px(activity, 20f)))?.times(232))?.div(500)!!
            layoutParams = layoutParams1;
            setImages(list)
            //设置banner动画效果
            setBannerAnimation(Transformer.DepthPage)
            //设置自动轮播，默认为true
            isAutoPlay(true)
            //设置轮播时间
            setDelayTime(3000)
            //设置指示器位置（当banner模式中有指示器时）
            setIndicatorGravity(BannerConfig.CENTER)
            //banner设置方法全部调用完毕时最后调用
            start()
        }
    }



}
