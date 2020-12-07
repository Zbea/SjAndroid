package com.hazz.kuangji.ui.activity.investment

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.hazz.kuangji.Constants
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Investment
import com.hazz.kuangji.mvp.model.InvestmentProduct
import com.hazz.kuangji.mvp.presenter.InvestmentPresenter
import com.hazz.kuangji.ui.activity.mine.MineExchangePwdActivity
import com.hazz.kuangji.ui.adapter.InvestmentAdapter
import com.hazz.kuangji.ui.adapter.InvestmentCompletedAdapter
import com.hazz.kuangji.utils.BigDecimalUtil
import com.hazz.kuangji.utils.SToast
import com.hazz.kuangji.utils.ToolBarCustom
import com.hazz.kuangji.utils.Utils
import com.hazz.kuangji.widget.CommonDialog
import com.hazz.kuangji.widget.RewardItemDeco
import com.hazz.kuangji.widget.SafeCheckDialog
import com.scwang.smartrefresh.layout.util.DensityUtil
import kotlinx.android.synthetic.main.activity_exchange_buy.*
import kotlinx.android.synthetic.main.activity_exchange_buy.mToolBar
import kotlinx.android.synthetic.main.activity_exchange_sale.*
import kotlinx.android.synthetic.main.activity_investment.*
import kotlinx.android.synthetic.main.activity_investment.rc_list
import kotlinx.android.synthetic.main.activity_list.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @Created by Zbea
 * @fileName InvestmentActivity
 * @date 2020/11/23 16:49
 * @email xiaofeng9212@126.com
 * @describe TODO
 **/
class InvestmentActivity : BaseActivity(), IContractView.IInvestmentView {

    private val mInvestmentPresenter = InvestmentPresenter(this)
    private var mAdapter: InvestmentAdapter? = null
    private var mAdapterCompleted: InvestmentCompletedAdapter? = null
    private var pageNuming = 1
    private var pageNumed = "1"
    private var pageSize="20"
    private var item: Investment? = null
    private var mPasswordDialog: SafeCheckDialog? = null
    private var pos=0
    private var id:String?=null
    private var isOut=true//状态是正常还是已完成
    private var isBuy=false //是否继续购买
    private var mInvestmentProduct = InvestmentProduct()

    override fun getLists(item: Investment) {
        this.item = item
        tv_fil.text = if (item.amount.isNullOrEmpty()) "0 FIL" else item.amount+"FIL"
        mAdapter?.setNewData(item.list)
//        if (item.list.size<20)
//        {
//            mAdapter?.loadMoreEnd()
//        }
//        else{
//            mAdapter?.loadMoreComplete()
//        }
    }

    override fun getListsCompleted(item: Investment) {
        mAdapterCompleted?.setNewData(item.list)
    }

    override fun onSuccess() {
        mAdapter?.remove(pos)
        if (isBuy)
        {
            isBuy=false
            startActivity(Intent(this, InvestmentProductListActivity::class.java))
        }
        else
        {
            SToast.showText(if (isOut) "退出服务成功，将返回您的本金" else "服务已结束，本金、利息取出成功")
        }

    }

    override fun layoutId(): Int {
        return R.layout.activity_investment
    }

    override fun initView() {
        ToolBarCustom.newBuilder(toolbar as Toolbar)
                
                .setOnLeftIconClickListener { finish() }

        EventBus.getDefault().register(this)

        rg_investment.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.rb_left) {
                ll_going.visibility = View.VISIBLE
                ll_gone.visibility = View.GONE
            }
            if (checkedId == R.id.rb_right) {
                ll_going.visibility = View.GONE
                ll_gone.visibility = View.VISIBLE
            }
        }

        tv_add.setOnClickListener {
            startActivity(Intent(this, InvestmentProductListActivity::class.java))
        }

    }

    override fun initData() {

        rc_list.layoutManager = LinearLayoutManager(this)//创建布局管理
        mAdapter = InvestmentAdapter(R.layout.item_investment_order, null)
        rc_list.adapter = mAdapter
        mAdapter?.bindToRecyclerView(rc_list)
        mAdapter?.setEmptyView(R.layout.template_investment_empty)
        val leftRightOffset = DensityUtil.dp2px(15f)
        rc_list.addItemDecoration(RewardItemDeco(0, 0, 0, leftRightOffset, 0))
        mAdapter?.setOnItemChildClickListener { adapter, view, position ->
            val mitem = mAdapter?.getItem(position)
            id=mitem?.id
            pos=position
            if (view.id == R.id.tv_buy)//继续生息
            {
                isBuy=true
                mInvestmentProduct.id = mitem?.product_id
                mInvestmentProduct.name = mitem?.product_name
                mInvestmentProduct.round = mitem?.round
                mInvestmentProduct.rate = mitem?.rate
                onSetPassword()
            }
            if (view.id == R.id.tv_close) {
                if (mitem?.status == "0") {
                    CommonDialog(this)
                            .setContent("增值服务活动正在进行中，未结束退出您将获取不到您的收益！").builder()
                            .setDialogClickListener(object : CommonDialog.DialogClickListener {
                                override fun cancel() {
                                }

                                override fun ok() {
                                    isOut=true
                                    onSetPassword()
                                }
                            })

                }
                if (mitem?.status == "2") {
                    isOut=false
                    onSetPassword()
                }
            }

        }
//
//        mAdapter?.setOnLoadMoreListener {
//            pageNuming++
//            mInvestmentPresenter.getLists(pageNuming.toString())
//        }


        rc_listed.layoutManager = LinearLayoutManager(this)//创建布局管理
        mAdapterCompleted = InvestmentCompletedAdapter(R.layout.item_investment_order_completed, null)
        rc_listed.adapter = mAdapterCompleted
        mAdapterCompleted?.bindToRecyclerView(rc_listed)
        rc_listed.addItemDecoration(RewardItemDeco(0, 0, 0, leftRightOffset, 0))

    }

    override fun start() {
        mInvestmentPresenter.getLists(pageNuming.toString())
        mInvestmentPresenter.getListsCompleted(pageNumed)
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: String) {
        if (event == Constants.CODE_INVESTMENT_BUY) {
            pageNuming = 1
            mInvestmentPresenter.getLists(pageNuming.toString())
        }
    }

    //输入交易密码
    private fun onSetPassword() {
        if (mPasswordDialog == null) {
            mPasswordDialog = SafeCheckDialog(this)
                    .setCancelListener { }
                    .setForgetListener {
                        startActivity(Intent(this, MineExchangePwdActivity::class.java))
                    }
                    .setConfirmListener { _, password ->
                        mInvestmentPresenter.outInvestment(id!!, Utils.encryptMD5(password))

                    }.setCancelListener {
                    }
        }
        mPasswordDialog?.show()
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


}