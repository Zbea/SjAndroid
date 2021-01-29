package com.hazz.kuangji.ui.adapter


import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hazz.kuangji.R
import com.hazz.kuangji.mvp.model.MillEarningsList

class MillRecordAdapter (layoutResId: Int, data: List<MillEarningsList.ListBean>?) : BaseQuickAdapter<MillEarningsList.ListBean, BaseViewHolder>(layoutResId, data) {


    lateinit var onConfirm: (View, Int) -> Unit

    override fun convert(helper: BaseViewHolder, item: MillEarningsList.ListBean) {

        helper.setText(R.id.tv_name, item.product)
        helper.setText(R.id.tv_time, item.create_at)
        helper.setText(R.id.tv_id, "服务器ID："+item.order_id)
        helper.setText(R.id.tv_number, "服务器编号："+item.miner_number)
        helper.setText(R.id.tv_amount, "+"+item.amount+" FIL")

    }
}
