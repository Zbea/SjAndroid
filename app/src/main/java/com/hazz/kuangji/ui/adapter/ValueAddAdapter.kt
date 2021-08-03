package com.hazz.kuangji.ui.adapter

import android.view.View
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hazz.kuangji.R
import com.hazz.kuangji.mvp.model.ValueAdd
import com.hazz.kuangji.utils.BigDecimalUtil

class ValueAddAdapter(layoutResId: Int, data: List<ValueAdd.ListBean>?) : BaseQuickAdapter<ValueAdd.ListBean, BaseViewHolder>(layoutResId, data) {


    lateinit var onConfirm: (View, Int) -> Unit

    override fun convert(helper: BaseViewHolder, item: ValueAdd.ListBean) {
        helper.setText(R.id.tv_title, item.productName)
        helper.setText(R.id.tv_rate, BigDecimalUtil.mul("100",item.rate,0)+"%")
        helper.setText(R.id.tv_amount, item.price)
        helper.setText(R.id.tv_date, item.createAt)
        helper.setText(R.id.tv_day, item.remain)
        helper.setText(R.id.tv_earnings, item.currentProfit+" FIL")
        helper.setText(R.id.tv_state, if(item.status=="0")"进行中" else "已完成,待提取")
        var isOpen=false
        helper.getView<LinearLayout>(R.id.ll_click).setOnClickListener {
            if (isOpen)
            {
                isOpen=false
                helper.setVisible(R.id.ll_content,false)
                helper.setImageResource(R.id.iv_arrow,R.mipmap.icon_arrow_down)
            }
            else{
                isOpen=true
                helper.setVisible(R.id.ll_content,true)
                helper.setImageResource(R.id.iv_arrow,R.mipmap.icon_arrow_up)
            }
        }
        helper.addOnClickListener(R.id.btn_commit)
    }
}