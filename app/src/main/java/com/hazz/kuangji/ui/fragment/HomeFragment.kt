package com.hazz.kuangji.ui.fragment

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseFragment
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Home
import com.hazz.kuangji.mvp.model.bean.Msg
import com.hazz.kuangji.mvp.presenter.HomePresenter
import com.hazz.kuangji.mvp.presenter.MsgPresenter
import com.hazz.kuangji.ui.activity.home.ExchangeBuyActivity
import com.hazz.kuangji.ui.activity.home.ExchangeCoinActivity
import com.hazz.kuangji.ui.activity.home.ExchangeSaleActivity
import com.hazz.kuangji.ui.activity.home.MsgDescActivity
import com.hazz.kuangji.ui.adapter.HomeAdapter
import com.hazz.kuangji.utils.DisplayManager
import com.hazz.kuangji.utils.DpUtils
import com.hazz.kuangji.widget.GlideImageLoader
import com.hazz.kuangji.widget.RewardItemDeco
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.fragment_new_home.*
import kotlinx.android.synthetic.main.fragment_new_home.sl_refresh

class HomeFragment : BaseFragment(), IContractView.HomeView, IContractView.MsgView {

    private var adList: MutableList<String>? = mutableListOf()
    private var adListDefault: MutableList<Int>? = mutableListOf()
    private var mAdapter: HomeAdapter? = null
    private var mHomePresenter: HomePresenter = HomePresenter(this)
    private var viewsList: MutableList<View>? = mutableListOf()
    private var mCoinPresenter: MsgPresenter = MsgPresenter(this)


    override fun getMsg(rows: List<Msg>) {
        if (activity == null) return
        if (!rows.isNullOrEmpty()) {
            viewsList!!.clear()//记得加这句话，不然可能会产生重影现象
            for (i in rows.indices) { //设置滚动的单个布局
                val moreView = LayoutInflater.from(activity).inflate(R.layout.item_view_single, null) as RelativeLayout
                //初始化布局的控件
                val tv1 = moreView.findViewById<View>(R.id.tv_title) as TextView
                /**
                 * 设置监听
                 */
                tv1.setOnClickListener {
                    if (rows != null && rows.isNotEmpty()) {
                        val articalBean = rows[i]
                        val intent = Intent(activity, MsgDescActivity::class.java)
                        intent.putExtra("message", articalBean)
                        startActivity(intent)
                    }
                }

                //进行对控件赋值
                tv1.text = rows[i].title
                //添加到循环滚动数组里面去
                viewsList!!.add(moreView)
            }
            marqueeview.setViews(viewsList)
            marqueeview.setFlipInterval(3000)
            marqueeview.startFlipping()
        }
    }

    override fun getHome(msg: Home) {

        sl_refresh.isRefreshing=false

        val carousel = msg.carousel
        if (!carousel.isNullOrEmpty()) {
            adList!!.clear()
            for (a in carousel) {
                adList!!.add(a.url)
            }
            initBanner(adList!!)
        } else {
            adListDefault!!.clear()
            adListDefault!!.add(R.mipmap.banner1)
            initBannerDefault(adListDefault!!)
        }
        mAdapter!!.setNewData(msg.products)
    }

    override fun zuyongSucceed(msg: String) {
    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_new_home
    }


    override fun initView() {

        sl_refresh.isRefreshing=true
        sl_refresh.setColorSchemeResources(R.color.blue)
        sl_refresh.setOnRefreshListener {
            lazyLoad()
        }

        recycle_view.layoutManager = LinearLayoutManager(activity)//创建布局管理
        mAdapter = HomeAdapter(R.layout.item_home, null)
        recycle_view.adapter = mAdapter
        mAdapter!!.bindToRecyclerView(recycle_view)
        mAdapter!!.setEmptyView(R.layout.fragment_empty)
        val leftRightOffset = DensityUtil.dp2px(15f)

        recycle_view.addItemDecoration(
                RewardItemDeco(
                        0,
                        0,
                        0,
                        leftRightOffset,
                        0
                )
        )



        ll_charge.setOnClickListener {
            startActivity(Intent(activity, ExchangeBuyActivity::class.java))
        }

        ll_sale.setOnClickListener {
            startActivity(Intent(activity, ExchangeSaleActivity::class.java))
        }

        ll_exchange.setOnClickListener {
            startActivity(Intent(activity, ExchangeCoinActivity::class.java))
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

    private fun initBannerDefault(list: List<Int>) {
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

    override fun fail(msg: String) {
        super.fail(msg)
        sl_refresh.isRefreshing=false
    }

}
