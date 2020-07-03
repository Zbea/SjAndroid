package com.hazz.kuangji.ui.adapter


import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hazz.kuangji.R
import com.hazz.kuangji.mvp.model.TransferCoin

class TransferCoinRecordAdapter(layoutResId: Int, data: List<TransferCoin>?) : BaseQuickAdapter<TransferCoin, BaseViewHolder>(layoutResId, data) {


    lateinit var onConfirm: (View, Int) -> Unit

    override fun convert(helper: BaseViewHolder, item: TransferCoin) {
        helper.run {
            setText(R.id.tv_username, "转入到："+item.name)
            setText(R.id.tv_amount1, item.amount+item.coin)
            setText(R.id.tv_order_date, item.create_at)
        }
    }

}
