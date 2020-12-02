package com.hazz.kuangji.ui.adapter

import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hazz.kuangji.R
import com.hazz.kuangji.mvp.model.Investment
import com.hazz.kuangji.mvp.model.InvestmentProduct
import com.hazz.kuangji.ui.activity.investment.InvestmentProductBuyActivity
import com.hazz.kuangji.utils.BigDecimalUtil

class InvestmentProductAdapter(layoutResId: Int, data: List<InvestmentProduct>?) : BaseQuickAdapter<InvestmentProduct, BaseViewHolder>(layoutResId, data) {


    lateinit var onConfirm: (View, Int) -> Unit

    override fun convert(helper: BaseViewHolder, item: InvestmentProduct) {

        helper.setText(R.id.tv_title, item.name+"(${item.round}天)")
        helper.setText(R.id.tv_fee, BigDecimalUtil.mul(item.rate,"100"))

        helper.getView<TextView>(R.id.tv_buy).setOnClickListener {
            mContext.startActivity(Intent(mContext,InvestmentProductBuyActivity::class.java).putExtra("investment",item))
        }



    }

}
