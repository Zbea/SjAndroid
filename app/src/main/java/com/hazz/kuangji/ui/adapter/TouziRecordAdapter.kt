package com.hazz.kuangji.ui.adapter


import android.graphics.Color
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hazz.kuangji.R
import com.hazz.kuangji.mvp.model.TouziRecord

class TouziRecordAdapter(layoutResId: Int, data: List<TouziRecord.ListBean>?) : BaseQuickAdapter<TouziRecord.ListBean, BaseViewHolder>(layoutResId, data) {


    lateinit var onConfirm: (View, Int) -> Unit

    override fun convert(helper: BaseViewHolder, item: TouziRecord.ListBean) {

        helper.setText(R.id.tv_name, item.product)
        helper.setText(R.id.tv_amount, item.amount)
        helper.setText(R.id.tv_time, item.create_at)
        when (item.state) {
            "1" -> {
                helper.setText(R.id.tv_state, "分红中")
                helper.setTextColor(R.id.tv_state, mContext.resources.getColor(R.color.redF4))
            }
            else -> {
                helper.setText(R.id.tv_state, "投资结束")
                helper.setTextColor(R.id.tv_state, Color.parseColor("#F9FBFF"))
            }
        }


    }
}
