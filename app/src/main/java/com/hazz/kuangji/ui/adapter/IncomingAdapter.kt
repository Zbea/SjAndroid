package com.hazz.kuangji.ui.adapter


import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hazz.kuangji.R
import com.hazz.kuangji.mvp.model.InComing

class IncomingAdapter(layoutResId: Int, data: List<InComing.StaticListBean>?) : BaseQuickAdapter<InComing.StaticListBean, BaseViewHolder>(layoutResId, data) {


    lateinit var onConfirm: (View, Int) -> Unit

    override fun convert(helper: BaseViewHolder, item: InComing.StaticListBean) {


        helper.setText(R.id.tv_time, item.create_at)

        helper.setText(R.id.tv_amount, "+"+item.amount+item.coin)


    }
}
