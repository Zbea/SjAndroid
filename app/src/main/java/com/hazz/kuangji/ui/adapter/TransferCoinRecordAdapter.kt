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
            if (item.type==1)
            {
                setText(R.id.tv_username, "来自<—"+item.name)
                setText(R.id.tv_amount, "转入数量："+item.amount+item.coin)
                setText(R.id.tv_order_date, "转入时间："+item.create_at)
            }
            else
            {
                setText(R.id.tv_username, "转出—>"+item.name)
                setText(R.id.tv_amount, "转出数量："+item.amount+item.coin)
                setText(R.id.tv_order_date, "转出时间："+item.create_at)
            }
        }
    }

}
