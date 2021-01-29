package com.hazz.kuangji.ui.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hazz.kuangji.R
import com.hazz.kuangji.mvp.model.AssetClusterEarningsDetails

class AssetClusterAdapter(layoutResId: Int, data: List<AssetClusterEarningsDetails.AssetClusterBean>?) : BaseQuickAdapter<AssetClusterEarningsDetails.AssetClusterBean, BaseViewHolder>(layoutResId, data) {


    lateinit var onConfirm: (View, Int) -> Unit

    override fun convert(helper: BaseViewHolder, item: AssetClusterEarningsDetails.AssetClusterBean) {

        helper.setText(R.id.tv_date, item.create_at)
        helper.setText(R.id.tv_fil,  "+"+item.amount+"FIL")

    }

}
