

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
import com.hazz.kuangji.mvp.model.ClusterEarningsDetails
import com.hazz.kuangji.mvp.model.MillEarningsDetails
import com.hazz.kuangji.mvp.presenter.MillEarningsDetailsPresenter
import com.hazz.kuangji.ui.adapter.MillClusterEarningDetailsAdapter
import com.hazz.kuangji.utils.BigDecimalUtil
import com.hazz.kuangji.utils.ToolBarCustom
import kotlinx.android.synthetic.main.activity_mill_cluster_earnings_details.*
import java.math.RoundingMode
import java.util.*


/**
 * @Created by Zbea
 * @fileName MillEarningsDetailsActivity
 * @date 2020/11/6 14:31
 * @email xiaofeng9212@126.com
 * @describe 集群收益详细
 **/
class ClusterEarningsDetailsActivity : BaseActivity(), IContractView.EarningsDetailsView{

    private val presenter=MillEarningsDetailsPresenter(this)
    private var mAdapter: MillClusterEarningDetailsAdapter? = null
    private var id=""
    private var pvTime: TimePickerView? = null

    override fun getDetails(msg: List<MillEarningsDetails>) {
    }

    override fun getClusterDetails(msg: ClusterEarningsDetails) {

        var totalSealed="0"
        var totalSealedSum="0"
        var totalT="0"
        var totalFil="0"
        var total25="0"
        var total75="0"
        var totalUsable="0"

        for (item in msg.list)
        {
            item.usable=BigDecimalUtil.add(item.line25,item.line75,8, RoundingMode.UP)

            totalSealed=BigDecimalUtil.add(totalSealed,item.seal_amount,8, RoundingMode.UP)
            totalSealedSum=BigDecimalUtil.add(totalSealedSum,item.sealed_storage,8, RoundingMode.UP)
            totalT=BigDecimalUtil.add(totalT,item.fil_amount,8, RoundingMode.UP)
            totalFil=BigDecimalUtil.add(totalFil,item.return_fil,8, RoundingMode.UP)
            total25=BigDecimalUtil.add(total25,item.line25,8, RoundingMode.UP)
            total75=BigDecimalUtil.add(total75,item.line75,8, RoundingMode.UP)
            totalUsable=BigDecimalUtil.add(totalUsable,item.usable,8, RoundingMode.UP)

        }
        tv_total_sealed.text=totalSealed
        tv_total_sealed_sum.text=totalSealedSum
        tv_total_t.text=totalT
        tv_total_fil.text=totalFil
        tv_total_25.text=total25
        tv_total_75.text=total75
        tv_total_usable.text=totalUsable
        mAdapter?.setNewData(msg.list)
    }

    override fun layoutId(): Int {
        return R.layout.activity_mill_cluster_earnings_details
    }

    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setTitle("收益明细")
                .setOnLeftIconClickListener {finish() }
                .setRightOneIcon(R.mipmap.icon_mill_pick_time)
                .setRightOneIconIsShow(true)
                .setOnRightIconClickListener {
                    showTime()
                }

        id=intent.getStringExtra("orderId")

        rc_list.layoutManager = LinearLayoutManager(this)//创建布局管理
        mAdapter = MillClusterEarningDetailsAdapter(R.layout.item_cluster_earnings_details, null)
        rc_list.adapter = mAdapter
        mAdapter!!.bindToRecyclerView(rc_list)

    }

    override fun initData() {
        presenter.getClusterLists(id)
    }
    override fun start() {
    }

    private fun showTime() {
        val startDate = Calendar.getInstance()
        startDate.set(2013, 0, 23)
        val endDate = Calendar.getInstance()
        endDate.set(2029, 11, 28)
        //时间选择器
        pvTime = TimePickerBuilder(this, OnTimeSelectListener { start, end ->
            presenter.getClusterLists(id,start,end)

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
