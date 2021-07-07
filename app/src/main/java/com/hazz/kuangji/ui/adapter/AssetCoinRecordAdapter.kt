package com.hazz.kuangji.ui.adapter


import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hazz.kuangji.R
import com.hazz.kuangji.mvp.model.AssetCoin
import com.hazz.kuangji.mvp.model.ExtractRecord

class AssetCoinRecordAdapter(layoutResId: Int, data: List<AssetCoin>?) : BaseQuickAdapter<AssetCoin, BaseViewHolder>(layoutResId, data) {

    lateinit var onConfirm: (View, Int) -> Unit

    override fun convert(helper: BaseViewHolder, item: AssetCoin) {
        helper.setText(R.id.tv_type, item.typeTxt)
        helper.setText(R.id.tv_time, item.createAt)
        helper.setText(R.id.tv_amount, item.amount+" "+item.coin)
        helper.setText(R.id.tv_state, "已完成")

    }
}
