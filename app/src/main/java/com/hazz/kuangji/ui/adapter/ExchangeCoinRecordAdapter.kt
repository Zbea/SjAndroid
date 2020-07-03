package com.hazz.kuangji.ui.adapter


import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hazz.kuangji.R
import com.hazz.kuangji.mvp.model.ExchangeRecord

class ExchangeCoinRecordAdapter(layoutResId: Int, data: List<ExchangeRecord>?) : BaseQuickAdapter<ExchangeRecord, BaseViewHolder>(layoutResId, data) {


    lateinit var onConfirm: (View, Int) -> Unit

    override fun convert(helper: BaseViewHolder, item: ExchangeRecord) {
        helper.run {
            setText(R.id.tv_exchange_type, if (item.type=="UTOF") "USDT 转 FIL" else "FIL 转 USDT")
            setText(R.id.tv_amount1, item.amount1)
            setText(R.id.tv_amount2, item.amount2)
            setText(R.id.tv_order_date, item.create_at)
        }
    }

}
