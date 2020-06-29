package com.hazz.kuangji.ui.adapter


import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hazz.kuangji.R
import com.hazz.kuangji.mvp.model.bean.Mingxi
import com.hazz.kuangji.utils.BigDecimalUtil

class MIngxiRecordAdapter(layoutResId: Int, data: List<Mingxi.ListBean>?) : BaseQuickAdapter<Mingxi.ListBean, BaseViewHolder>(layoutResId, data) {


    lateinit var onConfirm: (View, Int) -> Unit

    override fun convert(helper: BaseViewHolder, item: Mingxi.ListBean) {

        helper.setText(R.id.tv_name, item.product)
        helper.setText(R.id.tv_time, item.create_at)
        helper.setText(R.id.tv_amount, "+"+BigDecimalUtil.mul(item.amount,"1",2)+" FIL")

    }
}
