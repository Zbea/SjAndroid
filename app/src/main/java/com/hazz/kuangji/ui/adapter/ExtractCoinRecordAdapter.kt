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
        when(item.stat){
            "4"->{
                helper.setText(R.id.tv_state, "已完成")
                helper.setTextColor(R.id.tv_state, mContext.resources.getColor(R.color.color_333333))
            }
            "0"->{
                helper.setText(R.id.tv_state, "待审核")
                helper.setTextColor(R.id.tv_state, mContext.resources.getColor(R.color.color_yellow))
            }
            else->{
                helper.setText(R.id.tv_state, "已取消")
                helper.setTextColor(R.id.tv_state,mContext.resources.getColor(R.color.color_999999))
            }
        }

    }
}
