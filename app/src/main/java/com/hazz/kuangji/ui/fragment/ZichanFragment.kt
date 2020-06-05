package com.hazz.kuangji.ui.fragment

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseFragment
import com.hazz.kuangji.events.Index
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.bean.MyAsset
import com.hazz.kuangji.mvp.presenter.ZichanPresenter
import com.hazz.kuangji.ui.activity.ChargeActivity
import com.hazz.kuangji.ui.activity.IncomingActivity
import com.hazz.kuangji.ui.activity.TibiActivity
import com.hazz.kuangji.ui.adapter.ZichanAdapter
import com.hazz.kuangji.utils.BigDecimalUtil
import com.hazz.kuangji.utils.RxBus
import com.hazz.kuangji.utils.SToast
import com.hazz.kuangji.widget.RewardItemDeco
import com.scwang.smartrefresh.layout.util.DensityUtil
import kotlinx.android.synthetic.main.fragment_zichan.*
import kotlinx.android.synthetic.main.fragment_zichan.recycle_view


class ZichanFragment : BaseFragment(), IContractView.ZichanView {


    @SuppressLint("SetTextI18n")
    override fun myAsset(msg: MyAsset) {
        myAsset=msg
        tv_copy.text=msg.wallet_address
        if(msg.investment!=null){
            tv_touzi.text=BigDecimalUtil.mul(msg.investment.toString(),"1",5)
        }

        if(msg.usdt_revenue!=null&&msg.fcoin_revenue!=null){
            tv_shouyi.text=BigDecimalUtil.mul(msg.usdt_revenue,"1",2)+"/"+BigDecimalUtil.mul(msg.fcoin_revenue,"1",2)
        }
        if(msg.usdt_revenue!=null&&msg.fcoin_revenue==null){
            tv_shouyi.text=BigDecimalUtil.mul(msg.usdt_revenue,"1",2)+"/0.00"
        }
        if(msg.usdt_revenue==null&&msg.fcoin_revenue!=null){
            tv_shouyi.text="0.00/"+BigDecimalUtil.mul(msg.fcoin_revenue,"1",2)
        }


        val assets = msg.assets
        list!!.clear()
        for(coin in assets){
            if(coin.coin!="BTC" && coin.coin!="ETH"){
                list!!.add(coin)
            }
        }

        mAdapter!!.setNewData(list)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_zichan
    }


   private var mZichanPresenter:ZichanPresenter= ZichanPresenter(this)
    private var myAsset:MyAsset?=null
    private var isShow=true
    private var mAdapter: ZichanAdapter?=null
    private var list: MutableList<MyAsset.AssetsBean>? = mutableListOf()
    @SuppressLint("SetTextI18n")
    override fun initView() {
        tv_tibi.setOnClickListener {
            if(myAsset!=null){
                startActivity(Intent(activity,TibiActivity::class.java).putExtra("amount",myAsset))
            }


        }
        rl_charge.setOnClickListener {
            startActivity(Intent(activity,ChargeActivity::class.java))

        }
        rl_touzi.setOnClickListener {
           RxBus.get().send(Index())
        }
        rl_shouyi.setOnClickListener {
            startActivity(Intent(activity,IncomingActivity::class.java))
        }
        tv_copy.setOnClickListener {
            val cm = activity!!.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
            val clipData = ClipData.newPlainText("invitation_code", tv_copy.text)

            cm.primaryClip = clipData

            SToast.showText("已成功复制钱包地址")
        }

        recycle_view.layoutManager = LinearLayoutManager(activity)//创建布局管理
        mAdapter = ZichanAdapter(R.layout.item_zichan, null)
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




    }

    override fun lazyLoad() {

    }

    override fun onResume() {
        super.onResume()
        mZichanPresenter.myAsset()
    }


}
