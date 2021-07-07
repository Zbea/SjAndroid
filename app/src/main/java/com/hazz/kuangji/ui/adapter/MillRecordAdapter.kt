package com.hazz.kuangji.ui.adapter


import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hazz.kuangji.R
import com.hazz.kuangji.mvp.model.MillEarningsList

class MillRecordAdapter (layoutResId: Int, data: List<MillEarningsList>?) : BaseQuickAdapter<MillEarningsList, BaseViewHolder>(layoutResId, data) {


    lateinit var onConfirm: (View, Int) -> Unit

    override fun convert(helper: BaseViewHolder, item: MillEarningsList) {

        helper.setText(R.id.tv_date,item.createAt)
        helper.setText(R.id.tv_num,"+"+item.amount+item.coin)

    }
}
