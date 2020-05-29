package com.hazz.kuangji.ui.adapter


import android.content.Intent
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hazz.kuangji.R
import com.hazz.kuangji.mvp.model.bean.Touzi
import com.hazz.kuangji.ui.activity.TouziConfirmActivity
import com.hazz.kuangji.utils.BigDecimalUtil

class TouziAdapter(layoutResId: Int, data: List<Touzi.ProductsBean>?) : BaseQuickAdapter<Touzi.ProductsBean, BaseViewHolder>(layoutResId, data) {


    lateinit var onConfirm: (View, Int) -> Unit

    override fun convert(helper: BaseViewHolder, item: Touzi.ProductsBean) {

    helper.setText(R.id.tv_name, item.name)
    helper.setText(R.id.tv_start, BigDecimalUtil.mul(item.price.toString(),"1",2)+"CNY")
    helper.setText(R.id.tv_incoming, BigDecimalUtil.mul(item.price.toString(),item.out,2)+"CNY")
    helper.setText(R.id.tv_amount, BigDecimalUtil.mul(item.price.toString(),item.rate,2)+"CNY")


    helper.itemView.setOnClickListener {

        mContext.startActivity(Intent(mContext,TouziConfirmActivity::class.java).putExtra("touzi",item))
    }
}
}
