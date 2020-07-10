package com.hazz.kuangji.ui.activity.mill

import android.graphics.Color
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.TimePickerView
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Mill
import com.hazz.kuangji.mvp.model.Mingxi
import com.hazz.kuangji.mvp.presenter.MillPresenter
import com.hazz.kuangji.ui.adapter.MillRecordAdapter
import com.hazz.kuangji.utils.ToolBarCustom
import com.hazz.kuangji.widget.RewardItemDeco
import com.scwang.smartrefresh.layout.util.DensityUtil
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.invitefriends_record.mToolBar
import java.text.SimpleDateFormat
import java.util.*


class MillRecordActivity : BaseActivity(), IContractView.kuangjiView {
    private var number=""

    override fun getKuangji(msg: Mill) {

    }

    override fun getMingxi(msg: Mingxi) {
        for (item in msg.list)
        {
            item.miner_number=number
        }
        mAdapter?.setNewData(msg.list)

    }


    override fun layoutId(): Int {
        return R.layout.activity_list
    }

    override fun initData() {

    }


    private var mAdapter: MillRecordAdapter? = null
    private var year=0
    private var month=0
    private var day=0

    private var mMillPresenter: MillPresenter = MillPresenter(this)
    private var pvTime: TimePickerView? = null
    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setLeftIcon(R.mipmap.back_white)
                .setTitle("收益明细")
                .setToolBarBgRescource(R.drawable.bg_blue_gradient)
                .setTitleColor(resources.getColor(R.color.color_white))
                .setRightOneIcon(R.mipmap.bt_pick_time)
                .setRightOneIconIsShow(true)
                .setOnLeftIconClickListener {finish() }
                .setOnRightIconClickListener {
                    showTime()
                }

        number=intent.getStringExtra("number")

        rc_list.layoutManager = LinearLayoutManager(this)//创建布局管理
        mAdapter = MillRecordAdapter(R.layout.item_mingxi, null)
        rc_list.adapter = mAdapter
        mAdapter!!.bindToRecyclerView(rc_list)
        mAdapter!!.setEmptyView(R.layout.fragment_empty)
        val leftRightOffset = DensityUtil.dp2px(10f)
        rc_list.addItemDecoration(
                RewardItemDeco(
                        0,
                        0,
                        0,
                        leftRightOffset,
                        0
                )
        )

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)+1
        day = calendar.get(Calendar.DAY_OF_MONTH)
        var time= "$year-$month-$day"
        mMillPresenter.mingxi(time,time)
    }

    private fun showTime() {
        val selectedDate = Calendar.getInstance()
        val startDate = Calendar.getInstance()
        startDate.set(2013, 0, 23)
        val endDate = Calendar.getInstance()
        endDate.set(2029, 11, 28)
        //时间选择器

        pvTime = TimePickerBuilder(this, OnTimeSelectListener { start, end ->

            mMillPresenter.mingxi(start,end)

        }).setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setSubCalSize(20)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
                //                        .setTitleText("请选择时间")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTextColorCenter(Color.BLACK)//设置选中项的颜色
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                .setCancelColor(Color.BLUE)//取消按钮文字颜色
                //                        .setTitleBgColor(0xFF666666)//标题背景颜色 Night mode
                //                        .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
                //                        .setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR) + 20)//默认是1900-2100年
                //                        .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate, endDate)//起始终止年月日设定
                //                        .setLabel("年","月","日","时","分","秒")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                //                        .isDialog(true)//是否显示为对话框样式
                .build()
        pvTime!!.setDate(Calendar.getInstance())//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime!!.show()

    }

    private fun getTimes(date: Date): String {//可根据需要自行截取数据显示
        val format = SimpleDateFormat("yyyy-MM-dd")
        return format.format(date)
    }

    override fun start() {


    }


}
