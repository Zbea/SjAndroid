package com.hazz.kuangji.ui.adapter


import android.util.Log
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hazz.kuangji.R
import com.hazz.kuangji.mvp.model.bean.ExchangeRecordBean
import com.hazz.kuangji.mvp.model.bean.Friends

class ExchangeRecordAdapter(layoutResId: Int, data: List<ExchangeRecordBean>?) : BaseQuickAdapter<ExchangeRecordBean, BaseViewHolder>(layoutResId, data) {


    lateinit var onConfirm: (View, Int) -> Unit

    override fun convert(helper: BaseViewHolder, item: ExchangeRecordBean) {
        helper.run {
            setText(R.id.tv_order_number, item.orderNumber)
            setText(R.id.tv_order_date, item.date)
            setText(R.id.tv_order_number_name, if (item.type==0)"买入数量：" else "卖出数量：")
            setText(R.id.tv_order_number_num, item.num.toString())
            setText(R.id.tv_order_money, "￥"+item.money)
            setText(R.id.tv_state, item.stateName)

            when(item.state)
            {
                0 -> setTextColor(R.id.tv_state, mContext.resources.getColor(R.color.redF4) )
                1 -> setTextColor(R.id.tv_state, mContext.resources.getColor(R.color.color_yellow) )
                else -> setTextColor(R.id.tv_state, mContext.resources.getColor(R.color.blue) )
            }

        }
    }

}
