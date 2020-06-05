package com.hazz.kuangji.ui.adapter


import android.util.Log
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hazz.kuangji.R
import com.hazz.kuangji.mvp.model.bean.ExchangeRecordBean
import com.hazz.kuangji.mvp.model.bean.Friends

class ExchangeCoinRecordAdapter(layoutResId: Int, data: List<ExchangeRecordBean>?) : BaseQuickAdapter<ExchangeRecordBean, BaseViewHolder>(layoutResId, data) {


    lateinit var onConfirm: (View, Int) -> Unit

    override fun convert(helper: BaseViewHolder, item: ExchangeRecordBean) {
        helper.run {
            setText(R.id.tv_order_number, item.orderNumber)
            setText(R.id.tv_order_date, item.date)
        }
    }

}
