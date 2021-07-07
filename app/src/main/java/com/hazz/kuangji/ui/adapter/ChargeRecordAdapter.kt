package com.hazz.kuangji.ui.adapter


import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hazz.kuangji.R
import com.hazz.kuangji.mvp.model.ChargeRecord

class ChargeRecordAdapter(layoutResId: Int, data: List<ChargeRecord.ListBean>?) : BaseQuickAdapter<ChargeRecord.ListBean, BaseViewHolder>(layoutResId, data) {


    lateinit var onConfirm: (View, Int) -> Unit

    override fun convert(helper: BaseViewHolder, item: ChargeRecord.ListBean) {

        helper.setText(R.id.tv_type,"转入" )
        helper.setText(R.id.tv_time, item.createAt)
        helper.setText(R.id.tv_amount, "+"+item.amount+item.coin)

    }
}
