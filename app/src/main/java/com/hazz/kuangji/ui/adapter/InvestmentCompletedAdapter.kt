package com.hazz.kuangji.ui.adapter

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hazz.kuangji.R
import com.hazz.kuangji.mvp.model.Investment
import com.hazz.kuangji.utils.BigDecimalUtil

class InvestmentCompletedAdapter(layoutResId: Int, data: List<Investment.ListBean>?) : BaseQuickAdapter<Investment.ListBean, BaseViewHolder>(layoutResId, data) {


    lateinit var onConfirm: (View, Int) -> Unit

    override fun convert(helper: BaseViewHolder, item: Investment.ListBean) {

        helper.setText(R.id.tv_title, item.product_name+"(${item.round}天)")
        helper.setText(R.id.tv_fee, BigDecimalUtil.mul(item.rate,"100")+"%")
        helper.setText(R.id.tv_money, item.price+"FIL")
        helper.setText(R.id.tv_date, item.create_at)
        helper.setText(R.id.tv_incoming, item.current_profit+"FIL")
        helper.getView<LinearLayout>(R.id.ll_top).setOnClickListener {
            item.isCheck = !item.isCheck
            helper.setVisible(R.id.ll_content,item.isCheck)
            helper.getView<ImageView>(R.id.iv_arrow).setImageResource(if(item.isCheck) R.mipmap.icon_arrow_up else R.mipmap.icon_arrow_down)
        }
    }

}
