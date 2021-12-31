package com.hazz.kuangji.ui.adapter


import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hazz.kuangji.R
import com.hazz.kuangji.mvp.model.ExtractRecord

class ExtractCoinRecordAdapter(layoutResId: Int, data: List<ExtractRecord.ListBean>?) : BaseQuickAdapter<ExtractRecord.ListBean, BaseViewHolder>(layoutResId, data) {


    lateinit var onConfirm: (View, Int) -> Unit

    override fun convert(helper: BaseViewHolder, item: ExtractRecord.ListBean) {

        helper.setText(R.id.tv_time, item.create_at)
        helper.setText(R.id.tv_amount, "-"+item.amountAll+" "+item.coin)
        helper.setText(R.id.tv_state, item.statTxt)
        if (item.stat=="0"){
            helper.setTextColor(R.id.tv_state, mContext.resources.getColor(R.color.color_main))
        }
        else{
            helper.setTextColor(R.id.tv_state, mContext.resources.getColor(R.color.color_333333))
        }

    }
}
