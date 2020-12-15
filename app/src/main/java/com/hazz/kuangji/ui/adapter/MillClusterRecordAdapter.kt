package com.hazz.kuangji.ui.adapter


import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hazz.kuangji.R
import com.hazz.kuangji.mvp.model.ClusterEarnings
import com.hazz.kuangji.mvp.model.MillEarningsList

class MillClusterRecordAdapter (layoutResId: Int, data: List<ClusterEarnings.ClusterEarningsBean>?) : BaseQuickAdapter<ClusterEarnings.ClusterEarningsBean, BaseViewHolder>(layoutResId, data) {


    lateinit var onConfirm: (View, Int) -> Unit

    override fun convert(helper: BaseViewHolder, item: ClusterEarnings.ClusterEarningsBean) {

        helper.setText(R.id.tv_name, item.product)
        helper.setText(R.id.tv_time, item.create_at)
        helper.setText(R.id.tv_amount, "+"+item.amount+" FIL")

    }
}
