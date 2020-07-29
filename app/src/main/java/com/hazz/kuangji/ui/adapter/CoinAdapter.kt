package com.hazz.kuangji.ui.adapter


import android.content.Intent
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hazz.kuangji.R
import com.hazz.kuangji.mvp.model.Mill
import com.hazz.kuangji.ui.activity.mill.MillRecordActivity
import com.hazz.kuangji.utils.BigDecimalUtil

class CoinAdapter(layoutResId: Int, data: List<Mill.MachineListBean.ListBean>?) : BaseQuickAdapter<Mill.MachineListBean.ListBean, BaseViewHolder>(layoutResId, data) {


    lateinit var onConfirm: (View, Int) -> Unit

    override fun convert(helper: BaseViewHolder, item: Mill.MachineListBean.ListBean) {

        helper.setText(R.id.tv_name, item.product)
        helper.setText(R.id.tv_day, item.remain)
        helper.setText(R.id.tv_number, "矿机编号："+item.miner_number)
        if(item.revenue!=null){
            helper.setText(R.id.tv_leiji, BigDecimalUtil.mul(item.revenue,"1",8)+"FIL")
        }

        helper.setText(R.id.tv_touru, BigDecimalUtil.mul(item.price,"1",8)+"USDT")
        if( item.yesterday!=null){
            helper.setText(R.id.tv_yesterday, item.yesterday+"FIL")
        }


        when (item.state) {
            "1" ->{
                helper.setText(R.id.tv_state, "已运行")
                helper.setTextColor(R.id.tv_state, mContext.resources.getColor(R.color.color_main))
            }
            "0" -> {
                helper.setText(R.id.tv_state, "已停止")
                helper.setTextColor(R.id.tv_state, mContext.resources.getColor(R.color.redF4))
            }
        }
    }
}
