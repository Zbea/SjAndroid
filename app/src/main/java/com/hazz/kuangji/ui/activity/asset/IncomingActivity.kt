package com.hazz.kuangji.ui.activity.asset

import android.graphics.Color
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.InComing
import com.hazz.kuangji.mvp.presenter.IncomingPresenter
import com.hazz.kuangji.ui.adapter.IncomingAdapter
import com.hazz.kuangji.utils.BigDecimalUtil
import com.hazz.kuangji.utils.SPUtil
import com.hazz.kuangji.utils.StatusBarUtil
import com.hazz.kuangji.utils.ToolBarCustom
import com.hazz.kuangji.widget.RewardItemDeco
import com.scwang.smartrefresh.layout.util.DensityUtil
import kotlinx.android.synthetic.main.activity_incoming.*


class IncomingActivity : BaseActivity(), TabLayout.OnTabSelectedListener, IContractView.ShouyiView {


    private val titles: Array<String> = arrayOf("分享收益", "业绩收益", "团队收益")

    private var adapter: IncomingAdapter? = null
    private var incoming: InComing? = null
    private var mIncomingPresenter: IncomingPresenter = IncomingPresenter(this)
    private var position=0

    override fun inComing(msg: InComing) {

        incoming = msg
        if (msg.fcoin_revenue != null&&msg.usdt_revenue != null) {
            tv_total.text =BigDecimalUtil.mul( msg.usdt_revenue,"1",8)+" USDT"
            tv_total_fil.text=BigDecimalUtil.mul( msg.fcoin_revenue,"1",8)+" FIL"
        }
        if (msg.fcoin_revenue != null&&msg.usdt_revenue == null) {
            tv_total.text ="0.00/"+BigDecimalUtil.mul( msg.fcoin_revenue,"1",8)
            tv_total_fil.visibility= View.GONE
        }

        if (msg.fcoin_revenue == null&&msg.usdt_revenue != null) {
            tv_total.text = BigDecimalUtil.mul( msg.usdt_revenue,"1",8)+"/0.00"
            tv_total_fil.visibility= View.GONE
        }

        if (msg.invitation != null) {
            tv_static.text = msg.invitation
        }
        if (msg.achievement != null) {
            tv_share.text = msg.achievement
        }
        if (msg.team != null) {
            tv_yeji.text = msg.team
        }

        if (msg.yesterday_usdt != null&& msg.yesterday_fcoin!=null) {
            tv_yesterday.text = BigDecimalUtil.mul( msg.yesterday_usdt,"1",4) + "/" + BigDecimalUtil.mul( msg.yesterday_fcoin,"1",8)
        }

        if (msg.yesterday_usdt != null&& msg.yesterday_fcoin==null) {
            tv_yesterday.text = BigDecimalUtil.mul( msg.yesterday_usdt,"1",4) + "/" +"0.00"
        }
        if (msg.yesterday_usdt == null&& msg.yesterday_fcoin!=null) {
            tv_yesterday.text ="0.00" + "/" + BigDecimalUtil.mul( msg.yesterday_fcoin,"1",8)
        }

        when (position) {
            0 -> adapter!!.setNewData(incoming!!.invitation_list)
            1 -> adapter!!.setNewData(incoming!!.achievement_list)
            2 -> adapter!!.setNewData(incoming!!.team_list)
        }
    }


    override fun onTabReselected(p0: TabLayout.Tab?) {

    }

    override fun onTabUnselected(p0: TabLayout.Tab?) {
    }

    override fun onTabSelected(p0: TabLayout.Tab?) {
        if (incoming != null) {
            when (p0!!.position) {
                0 -> adapter!!.setNewData(incoming!!.invitation_list)
                1 -> adapter!!.setNewData(incoming!!.achievement_list)
                2 -> adapter!!.setNewData(incoming!!.team_list)
            }
        }


    }


    override fun layoutId(): Int {
        return R.layout.activity_incoming
    }

    override fun initData() {
    }

    override fun initView() {

        ToolBarCustom.newBuilder(toolbar as Toolbar)
                .setOnLeftIconClickListener { finish() }

        position=intent.flags

        recycle_view.layoutManager = LinearLayoutManager(this)//创建布局管理
        adapter = IncomingAdapter(R.layout.item_incoming, null)
        recycle_view.adapter = adapter
        adapter?.bindToRecyclerView(recycle_view)
        adapter?.setEmptyView(R.layout.fragment_empty)
        val leftRightOffset = DensityUtil.dp2px(10f)

        recycle_view.addItemDecoration(RewardItemDeco(0, 0, 0, leftRightOffset, 0))
        if (SPUtil.getBoolean("skin"))
            invitation_tab_layout.setTabTextColors(Color.parseColor("#ffffff"),Color.parseColor("#00767E"))
        invitation_tab_layout.addOnTabSelectedListener(this)
        for (a in titles) {
            invitation_tab_layout.addTab(invitation_tab_layout.newTab().setText(a))
        }
        invitation_tab_layout.getTabAt(position)?.select()
    }

    override fun start() {
    }

    override fun onResume() {
        super.onResume()
        mIncomingPresenter.shouyi(true)
    }

}
