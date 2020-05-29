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
import com.hazz.kuangji.mvp.contract.LoginContract
import com.hazz.kuangji.mvp.model.Home
import com.hazz.kuangji.mvp.model.bean.Msg
import com.hazz.kuangji.mvp.presenter.HomePresenter
import com.hazz.kuangji.mvp.presenter.MsgPresenter
import com.hazz.kuangji.mvp.presenter.TestPresenter
import com.hazz.kuangji.ui.activity.MsgDescActivity
import com.hazz.kuangji.ui.activity.SignRecordActivity
import com.hazz.kuangji.ui.adapter.HomeAdapter
import com.hazz.kuangji.utils.DisplayManager
import com.hazz.kuangji.utils.DpUtils
import com.hazz.kuangji.widget.GlideImageLoader
import com.hazz.kuangji.widget.RewardItemDeco
import com.hazz.kuangji.widget.TipsDialog
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.fragment_new_home.*

class HomeFragment : BaseFragment(), LoginContract.HomeView, LoginContract.MsgView {

    override fun getMsg(rows: List<Msg>) {

        if (!rows.isNullOrEmpty()) {
            viewsList!!.clear()//记得加这句话，不然可能会产生重影现象
            for (i in rows.indices) {
//设置滚动的单个布局
                val moreView = LayoutInflater.from(activity).inflate(R.layout.item_view_single, null) as RelativeLayout
                //初始化布局的控件
                val tv1 = moreView.findViewById<View>(R.id.tv_title) as TextView
                /**
                 * 设置监听
                 */
                tv1.setOnClickListener { view ->
                    if (rows != null && !rows.isEmpty()) {
                        val articalBean = rows.get(i)
                        val intent = Intent(activity, MsgDescActivity::class.java)
                        intent.putExtra("message", articalBean)
                        startActivity(intent)
                    }
                }

                //进行对控件赋值
                tv1.setText(rows[i].title)
                //添加到循环滚动数组里面去
                viewsList!!.add(moreView)
            }
            marqueeview.setViews(viewsList)
            marqueeview.startFlipping()
        }

    }

    override fun zuyongSucceed(msg: String) {
        tv_qiandao.setBackgroundResource(R.drawable.bg_gray_solid_5dp_coner)
        tv_qiandao.text = "今日已签到"
        tv_qiandao.isClickable = false
        tipsDialog = TipsDialog(activity)
                .setTitle("提示")
                .setContent("签到成功,所有矿机开始运行")
                .sign()
                .setCancleListener { }
                .setConfirmListener {
                    tv_qiandao.setBackgroundResource(R.drawable.bg_gray_solid_5dp_coner)
                    tv_qiandao.text = "今日已签到"
                    tv_qiandao.setTextColor(activity!!.resources.getColor(R.color.hintTextColor))
                    tv_qiandao.isClickable = false
                    tipsDialog!!.dismiss()

                }

        tipsDialog!!.show()
    }


    override fun getHome(msg: Home) {
        val signed = msg.signed
        val carousel = msg.carousel
        if (!carousel.isNullOrEmpty()) {
            adList!!.clear()
            for (a in carousel) {
                adList!!.add(a.url)
            }

            initBanner(adList!!)
        }else{
            adListDefault!!.clear()
            adListDefault!!.add(R.mipmap.banner1)
            initBannerDefault(adListDefault!!)
        }



        if (signed == 1) {//已签到
            tv_qiandao.setBackgroundResource(R.drawable.bg_gray_solid_5dp_coner)
            tv_qiandao.text = "今日已签到"
            tv_qiandao.setTextColor(activity!!.resources.getColor(R.color.hintTextColor))
            tv_qiandao.isClickable = false
        }

        mAdapter!!.setNewData(msg.products)

    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_new_home
    }


    private var adList: MutableList<String>? = mutableListOf()
    private var adListDefault: MutableList<Int>? = mutableListOf()
    private var list: MutableList<Home.ProductsBean>? = mutableListOf()
    private var mAdapter: HomeAdapter? = null
    private var mHomePresenter: HomePresenter = HomePresenter(this)
    private var  mCheckPresenter: TestPresenter = TestPresenter(this)
    private var tipsDialog: TipsDialog? = null
    private var viewsList: MutableList<View>? = mutableListOf()
    private var mCoinPresenter: MsgPresenter = MsgPresenter(this)

    override fun initView() {
        recycle_view.layoutManager = LinearLayoutManager(activity)//创建布局管理
        mAdapter = HomeAdapter(R.layout.item_home, null)
        recycle_view.adapter = mAdapter
        mAdapter!!.bindToRecyclerView(recycle_view)
        mAdapter!!.setEmptyView(R.layout.fragment_empty)
        val leftRightOffset = DensityUtil.dp2px(10f)

        recycle_view.addItemDecoration(
                RewardItemDeco(
                        0,
                        0,
                        0,
                        leftRightOffset,
                        0
                )
        )



        tv_qiandao.setOnClickListener {
            mHomePresenter.sign()

        }

        tv_sign_record.setOnClickListener {
            startActivity(Intent(activity, SignRecordActivity::class.java))

        }

        tv_rule.setOnClickListener {


            tipsDialog = TipsDialog(activity)
                    .setTitle("提示")
                    .setContent("5年收益周期内，每日签到都可获得当日的收益。如期间出现漏签、断签等情况，" +
                            "收益周期会相对应的向后顺延，即保证每位用户都可获得5年的总收益回报。")

                    .rule()


            tipsDialog!!.show()
        }

    }

    override fun lazyLoad() {
        mCoinPresenter.getMsg()
        mHomePresenter.getHome()
//        mCheckPresenter.getTest()
    }

    private fun initBanner(list: List<String>) {

        banner.setImageLoader(GlideImageLoader())
        var layoutParams: ViewGroup.LayoutParams = banner.getLayoutParams()
        layoutParams.height = ((DisplayManager.getScreenWidth()?.minus(DpUtils.dip2px(activity,20f)))?.times(232))?.div(500)!!
        banner.layoutParams=layoutParams;
        banner.setImages(list)
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage)
        //设置自动轮播，默认为true
        banner.isAutoPlay(true)
        //设置轮播时间
        banner.setDelayTime(3000)
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER)
        //banner设置方法全部调用完毕时最后调用
        banner.start()
    }
    private fun initBannerDefault(list: List<Int>) {

        banner.setImageLoader(GlideImageLoader())
        var layoutParams: ViewGroup.LayoutParams = banner.getLayoutParams()
        layoutParams.height = ((DisplayManager.getScreenWidth()?.minus(DpUtils.dip2px(activity,20f)))?.times(232))?.div(500)!!
        banner.layoutParams=layoutParams;
        banner.setImages(list)

        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage)
        //设置自动轮播，默认为true
        banner.isAutoPlay(true)
        //设置轮播时间
        banner.setDelayTime(3000)
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER)
        //banner设置方法全部调用完毕时最后调用
        banner.start()
    }



    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if(!hidden){
            mCoinPresenter.getMsg()
            mHomePresenter.getHome()
        }
    }
}
