package com.hazz.kuangji.ui.activity.asset

import android.graphics.Color
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.TimePickerView
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.AssetDetails
import com.hazz.kuangji.mvp.model.MillEarningsDetails
import com.hazz.kuangji.mvp.presenter.AssetDetailsPresenter
import com.hazz.kuangji.mvp.presenter.MillEarningsDetailsPresenter
import com.hazz.kuangji.ui.adapter.AssetDetailsAdapter
import com.hazz.kuangji.ui.adapter.EarningDetailsAdapter
import com.hazz.kuangji.utils.BigDecimalUtil
import com.hazz.kuangji.utils.ToolBarCustom
import kotlinx.android.synthetic.main.activity_asset_fil_details.*
import kotlinx.android.synthetic.main.activity_mill_earnings_details.*
import kotlinx.android.synthetic.main.activity_mill_earnings_details.mToolBar
import kotlinx.android.synthetic.main.activity_mill_earnings_details.rc_list
import kotlinx.android.synthetic.main.activity_mill_earnings_details.tv_total_25
import kotlinx.android.synthetic.main.activity_mill_earnings_details.tv_total_75
import kotlinx.android.synthetic.main.activity_mill_earnings_details.tv_total_usable
import java.math.RoundingMode
import java.util.*


/**
 * @Created by Zbea
 * @fileName MillEarningsDetailsActivity
 * @date 2020/11/12 14:31
 * @email xiaofeng9212@126.com
 * @describe 矿机收益详细
 **/
class AssetFilDetailsActivity : BaseActivity(), IContractView.AssetFilDetailsView{

    private val presenter=AssetDetailsPresenter(this)
    private var mAdapter: AssetDetailsAdapter? = null

    override fun getDetails(msg: List<AssetDetails>) {
        var totalT="0" //业绩收益
        var total25="0" //立即释放
        var totalLock="0" //锁仓
        var totalRelease="0" //线性释放
        var totalUsable="0" //可用
        for (item in msg)
        {

            totalT=BigDecimalUtil.add(totalT,item.day_total_invite,8, RoundingMode.UP)
            total25=BigDecimalUtil.add(total25,item.day_direct_release,8, RoundingMode.UP)
            totalLock=BigDecimalUtil.add(totalLock,item.day_lockfil,8, RoundingMode.UP)
            totalRelease=BigDecimalUtil.add(totalRelease,item.day_line,8, RoundingMode.UP)
            totalUsable=BigDecimalUtil.add(totalUsable,item.balance,8, RoundingMode.UP)

        }
        tv_total_25.text=total25
        tv_total_75.text=totalRelease
        tv_total_lock.text=totalLock
        tv_total_usable.text=totalUsable
        tv_total_achviment.text=totalT

        mAdapter?.setNewData(msg)

    }

    override fun layoutId(): Int {
        return R.layout.activity_asset_fil_details
    }

    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setTitle("可用明细")
                .setOnLeftIconClickListener {finish() }
                .setRightOneIcon(R.mipmap.icon_mill_pick_time)
                .setRightOneIconIsShow(true)
                .setOnRightIconClickListener {
                    showTime()
                }


        rc_list.layoutManager = LinearLayoutManager(this)//创建布局管理
        mAdapter = AssetDetailsAdapter(R.layout.item_asset_fil_details, null)
        rc_list.adapter = mAdapter
        mAdapter!!.bindToRecyclerView(rc_list)
        mAdapter!!.setEmptyView(R.layout.fragment_empty)

    }

    override fun initData() {
        presenter.getLists()
    }
    override fun start() {
    }

    private fun showTime() {
        val startDate = Calendar.getInstance()
        startDate.set(2013, 0, 23)
        val endDate = Calendar.getInstance()
        endDate.set(2029, 11, 28)
        //时间选择器
        var pvTime = TimePickerBuilder(this, OnTimeSelectListener { start, end ->
            presenter.getLists(start,end)

        }).setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setSubCalSize(16)//滚轮文字大小
                .setTitleSize(16)//标题文字大小
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTextColorCenter(Color.BLACK)//设置选中项的颜色
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.GREEN)//确定按钮文字颜色
                .setCancelColor(Color.GREEN)//取消按钮文字颜色
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .build()
        pvTime!!.setDate(Calendar.getInstance())//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime!!.show()

    }


}
