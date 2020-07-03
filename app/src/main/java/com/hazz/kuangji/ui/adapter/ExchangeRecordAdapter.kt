package com.hazz.kuangji.ui.adapter


import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hazz.kuangji.R
import com.hazz.kuangji.mvp.model.ExchangeRecord

class ExchangeRecordAdapter(layoutResId: Int, data: List<ExchangeRecord>?) : BaseQuickAdapter<ExchangeRecord, BaseViewHolder>(layoutResId, data) {


    lateinit var onConfirm: (View, Int) -> Unit

    override fun convert(helper: BaseViewHolder, item: ExchangeRecord) {
        helper.run {
            setText(R.id.tv_order_number, item.order_code)
            setText(R.id.tv_order_date, item.create_at)
            setText(R.id.tv_order_number_name, if (item.transaction_type == "1") "买入数量：" else "卖出数量：")
            setText(R.id.tv_order_number_num, item.num+item.typcoin)
            setText(R.id.tv_order_money, "￥" + item.money)

            if (item.is_del==1)
            {
                setText(R.id.tv_state, "已取消")
                setTextColor(R.id.tv_state, mContext.resources.getColor(R.color.color_666666))
            }
            else
            {
                when (item.status) {
                    "0" -> {
                        if (item.transaction_type == "2") {
                            setText(R.id.tv_state, "处理中")
                            setTextColor(R.id.tv_state, mContext.resources.getColor(R.color.color_yellow))
                        } else {
                            if (item.is_pay == "0") {
                                setText(R.id.tv_state, "待付款")
                                setTextColor(R.id.tv_state, mContext.resources.getColor(R.color.redF4))
                            } else {
                                setText(R.id.tv_state, "处理中")
                                setTextColor(R.id.tv_state, mContext.resources.getColor(R.color.color_yellow))
                            }
                        }

                    }
                    else -> {
                        setText(R.id.tv_state, "已完成")
                        setTextColor(R.id.tv_state, mContext.resources.getColor(R.color.blue))
                    }
                }
            }
        }
    }

}
