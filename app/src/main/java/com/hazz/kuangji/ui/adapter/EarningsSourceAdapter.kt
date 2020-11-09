package com.hazz.kuangji.ui.adapter


import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hazz.kuangji.R
import com.hazz.kuangji.mvp.model.EarningsSource

class EarningsSourceAdapter (layoutResId: Int, data: List<EarningsSource.ListBean>?) : BaseQuickAdapter<EarningsSource.ListBean, BaseViewHolder>(layoutResId, data) {


    lateinit var onConfirm: (View, Int) -> Unit

    override fun convert(helper: BaseViewHolder, item: EarningsSource.ListBean) {

        helper.setText(R.id.tv_name, item.fil_from)
        helper.setText(R.id.tv_amount, "+"+item.amount+" FIL")

    }
}
