package com.hazz.kuangji.ui.adapter

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hazz.kuangji.R
import com.hazz.kuangji.mvp.model.Investment
import com.hazz.kuangji.utils.BigDecimalUtil

class InvestmentAdapter(layoutResId: Int, data: List<Investment.ListBean>?) : BaseQuickAdapter<Investment.ListBean, BaseViewHolder>(layoutResId, data) {


    lateinit var onConfirm: (View, Int) -> Unit

    override fun convert(helper: BaseViewHolder, item: Investment.ListBean) {

        helper.setText(R.id.tv_title, item.product_name+"(${item.round}天)")
        helper.setText(R.id.tv_fee, BigDecimalUtil.mul(item.rate,"100")+"%")
        helper.setText(R.id.tv_money, item.price+"FIL")
        helper.setText(R.id.tv_date, item.create_at)
        helper.setText(R.id.tv_day, item.remain+"天")
        helper.setText(R.id.tv_incoming, item.current_profit+"FIL")
        helper.getView<LinearLayout>(R.id.ll_top).setOnClickListener {
            item.isCheck = !item.isCheck
            helper.setVisible(R.id.ll_content,item.isCheck)
            helper.getView<ImageView>(R.id.iv_arrow).setImageResource(if(item.isCheck) R.mipmap.icon_arrow_up else R.mipmap.icon_arrow_down)
        }

        var tvStatus = helper.getView<TextView>(R.id.tv_status)
        var tvClose = helper.getView<TextView>(R.id.tv_close)
        var tvBuy = helper.getView<TextView>(R.id.tv_buy)

        helper.addOnClickListener(R.id.tv_buy)
        helper.addOnClickListener(R.id.tv_close)

        if (item.status=="0")
        {
            tvStatus.text="进行中"
            tvClose.text="退出"
        }
        else if (item.status=="1")
        {
            tvStatus.text="提前提款审核中"
            tvClose.visibility=View.GONE
        }
        else if (item.status=="2")
        {
            tvStatus.text="未提款"
            tvBuy.visibility=View.VISIBLE
            tvClose.text="提出"
        }
        else if (item.status=="3")
        {
            tvStatus.text="已完成"
            tvClose.visibility=View.GONE
        }

    }

}
