package com.hazz.kuangji.ui.adapter


import android.graphics.Color
import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hazz.kuangji.R
import com.hazz.kuangji.mvp.model.bean.TibiRecord

class ExtractCoinRecordAdapter(layoutResId: Int, data: List<TibiRecord.ListBean>?) : BaseQuickAdapter<TibiRecord.ListBean, BaseViewHolder>(layoutResId, data) {


    lateinit var onConfirm: (View, Int) -> Unit

    override fun convert(helper: BaseViewHolder, item: TibiRecord.ListBean) {

        helper.setText(R.id.tv_name, item.coin)
        helper.setText(R.id.tv_time, item.create_at)
        helper.setText(R.id.tv_amount, item.amount)
        when(item.status){
            "pass"->{
                helper.setText(R.id.tv_state, "已完成")
                helper.setTextColor(R.id.tv_state, Color.parseColor("#B3BED9"))
            }
            "wait"->{
                helper.setText(R.id.tv_state, "待审核")
                helper.setTextColor(R.id.tv_state, mContext.resources.getColor(R.color.blue))
            }
            "fail"->{
                helper.setText(R.id.tv_state, "审核失败")
                helper.setTextColor(R.id.tv_state,mContext.resources.getColor(R.color.redF4))
            }
        }
        if(item.coin=="USDT"){
            helper.getView<ImageView>(R.id.iv).setImageResource(R.mipmap.usdt)
        }else{
            helper.getView<ImageView>(R.id.iv).setImageResource(R.mipmap.eth)
        }

    }
}
