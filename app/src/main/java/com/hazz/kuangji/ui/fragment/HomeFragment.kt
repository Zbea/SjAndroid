package com.hazz.kuangji.ui.fragment

import android.content.Intent
import android.text.SpannableString
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidkun.xtablayout.XTabLayout
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.hazz.kuangji.Constants
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseFragment
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Certification
import com.hazz.kuangji.mvp.model.Home
import com.hazz.kuangji.mvp.model.Msg
import com.hazz.kuangji.mvp.presenter.HomePresenter
import com.hazz.kuangji.mvp.presenter.MsgPresenter
import com.hazz.kuangji.ui.activity.MainActivity
import com.hazz.kuangji.ui.activity.home.ChargeActivity
import com.hazz.kuangji.ui.activity.home.ExtractCoinActivity
import com.hazz.kuangji.ui.activity.home.*
import com.hazz.kuangji.ui.activity.mine.InviteActivity
import com.hazz.kuangji.ui.activity.mine.MineCertificatedActivity
import com.hazz.kuangji.ui.activity.mine.MineCertificationActivity
import com.hazz.kuangji.ui.adapter.HomeAdapter
import com.hazz.kuangji.utils.*
import com.hazz.kuangji.widget.RewardItemDeco
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.youth.banner.Banner
import com.youth.banner.adapter.BannerAdapter
import com.youth.banner.config.IndicatorConfig
import com.youth.banner.util.BannerUtils
import kotlinx.android.synthetic.main.activity_mill_accelerate_rent.*
import kotlinx.android.synthetic.main.fragment_mine.*
import kotlinx.android.synthetic.main.fragment_new_home.*
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : BaseFragment(), IContractView.HomeView, IContractView.MsgView{

    private var mAdapter: HomeAdapter? = null
    private var mHomePresenter: HomePresenter = HomePresenter(this)
    private var mCoinPresenter: MsgPresenter = MsgPresenter(this)
    private var home:Home?=null
    private var keyLists=ArrayList<String>()
    private var allLists=ArrayList<Home.ProductBean.FilBean>()


    //消息列表
    override fun getMsg(msg: Msg) {
        if (mView==null||activity==null)return
        var listInfo=ArrayList<CharSequence>()
        for (s in msg?.list)
        {
            listInfo.add(SpannableString(s.title))
        }
        marqueeView.startWithList(listInfo)
        marqueeView.setOnItemClickListener { position, _ ->
            val item = msg?.list[position]
            val intent = Intent(activity, MsgDescActivity::class.java)
            intent.putExtra("message", item)
            startActivity(intent)
        }
    }

    override fun getMsgDetails(msg: Msg.MsgBean) {
        TODO("Not yet implemented")
    }

    //服务器列表
    override fun getHome(msg: Home) {
        if (mView==null||activity==null)return
        sl_refresh?.isRefreshing=false
        home=msg

        initTab()
        initBanner()
        if (msg.product?.fil!=null)
            allLists.addAll(msg.product?.fil!!)
        if (msg.product?.chia!=null)
            allLists.addAll(msg.product?.chia!!)
        if (msg.product?.bzz!=null)
            allLists.addAll(msg.product?.bzz!!)
        mAdapter?.setNewData(msg.product?.fil)

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_new_home
    }


    override fun initView() {

        var layoutParams: RelativeLayout.LayoutParams= toolbar.layoutParams as RelativeLayout.LayoutParams
        layoutParams.topMargin= activity?.let { DensityUtils.getStatusBarHeight(it) }!!
        toolbar.layoutParams=layoutParams

        sl_refresh=activity?.findViewById(R.id.sl_refresh)
        sl_refresh?.isRefreshing=true
        sl_refresh?.setColorSchemeResources(R.color.color_main)
        sl_refresh?.setOnRefreshListener {
            lazyLoad()
        }

        recycle_view.layoutManager = LinearLayoutManager(activity)//创建布局管理
        mAdapter = HomeAdapter(R.layout.item_home, null)
        recycle_view.adapter = mAdapter
        mAdapter?.bindToRecyclerView(recycle_view)
        mAdapter?.setOnItemClickListener { _, _, position ->
            startActivity(Intent(context, HomeRentActivity::class.java).putExtra("produceId", home?.product?.fil?.get(position)?.id))
        }
        val leftRightOffset = DensityUtil.dp2px(10f)
        recycle_view.addItemDecoration(RewardItemDeco(0, 0, 0, leftRightOffset, 0))

        iv_msg.setOnClickListener {
            startActivity(Intent(activity, MsgListActivity::class.java))
        }

        layout_certification.setOnClickListener {
            var mCertification= SPUtil.getObj("certification", Certification::class.java)
            if (mCertification?.stat=="1"||mCertification?.stat=="0")
            {
                var intent = Intent(activity, MineCertificatedActivity::class.java)
                intent.putExtra("certification", mCertification)
                startActivity(intent)
            }
            else
            {
                startActivity(Intent(activity, MineCertificationActivity::class.java))
            }
        }

        layout_invite.setOnClickListener {
//            startActivity(Intent(activity, InviteActivity::class.java))
            SToast.showText("抱歉，暫不支持")
        }

    }

    override fun lazyLoad() {
        tab.removeAllTabs()
        keyLists.clear()
        allLists.clear()
        mCoinPresenter.getMsg()
        mHomePresenter.getHome()
    }


    private fun initTab()
    {
        if (home?.product==null)return
        //遍历索引
        var json= JSONObject(Gson().toJson(home?.product).toString())
        var it=json.keys()
        while (it.hasNext())
        {
            keyLists.add(it.next().toString())
        }
        keyLists.reverse()//倒序
        for (i in keyLists) {
            tab.addTab(tab.newTab().setText(i))
        }

        if (home?.product?.fil?.size==0){
            ll_content.visibility=View.GONE
        }
        else{
            ll_content.visibility=View.VISIBLE
        }

        tab.addOnTabSelectedListener(object : XTabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: XTabLayout.Tab?) {
                when(tab?.text.toString()){
                    "fil"->{
                        if (home?.product?.fil!=null)
                            mAdapter?.setNewData(home?.product?.fil)
                    }
                    "chia"->{
                        if (home?.product?.chia!=null)
                            mAdapter?.setNewData(home?.product?.chia)
                    }
                    "bzz"->{
                        if (home?.product?.bzz!=null)
                            mAdapter?.setNewData(home?.product?.bzz)
                    }
                    else->{
                        mAdapter?.setNewData(allLists)
                    }
                }

            }
            override fun onTabUnselected(tab: XTabLayout.Tab?) {

            }
            override fun onTabReselected(tab: XTabLayout.Tab?) {

            }

        })
    }

    private fun initBanner() {

        var banner =mBanner as Banner<Home.BannerBean, ImageAdapter>
        banner?.run {
            addBannerLifecycleObserver(this@HomeFragment)//添加生命周期观察者
//            setAdapter(object : BannerImageAdapter<Home.BannerBean>(list) {
//                override fun onBindView(holder: BannerImageHolder?, data: Home.BannerBean, position: Int, size: Int) {
//                    if (holder != null) {
//                        Glide.with(context).load(Constants.URL_INVITE+data.img).apply(
//                            RequestOptions().transform(RoundedCorners(30))).into(holder.imageView)
//                    }
//                }
//            })
            setAdapter(home?.banner?.let { ImageAdapter(it) })
            setBannerGalleryEffect(0,5)
            setIndicatorMargins(IndicatorConfig.Margins(30))
            setIndicator(rIndicator,false)
            start()
        }
    }

    private class ImageAdapter(imageUrls: List<Home.BannerBean>) : BannerAdapter<Home.BannerBean, ImageAdapter.ImageHolder>(imageUrls) {

        override fun onCreateHolder(parent: ViewGroup?, viewType: Int): ImageHolder {
            val imageView = ImageView(parent!!.context)
            val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            imageView.layoutParams = params
            imageView.scaleType = ImageView.ScaleType.FIT_XY
            //通过裁剪实现圆角
            BannerUtils.setBannerRound(imageView, 30f)
            return ImageHolder(imageView)
        }

        override fun onBindView(holder: ImageHolder, data: Home.BannerBean, position: Int, size: Int) {
            Glide.with(holder.itemView)
                .load(Constants.URL_BASE+data.img)
                .into(holder.imageView)
        }


        class ImageHolder(view: View) : RecyclerView.ViewHolder(view) {
            var imageView: ImageView = view as ImageView
        }

    }


}
