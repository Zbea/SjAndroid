package com.hazz.kuangji.ui.adapter

import android.content.Intent
import android.view.View
import android.widget.Button
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hazz.kuangji.R
import com.hazz.kuangji.mvp.model.ClusterOrder
import com.hazz.kuangji.ui.activity.mill.ClusterEarningsDetailsActivity
import com.hazz.kuangji.ui.activity.mill.MillEarningsDetailsActivity
import com.hazz.kuangji.utils.BigDecimalUtil


class MillClusterAdapter(layoutResId: Int, data: List<ClusterOrder.ClusterList.ClusterOrderBean>?) : BaseQuickAdapter<ClusterOrder.ClusterList.ClusterOrderBean, BaseViewHolder>(layoutResId, data) {


    lateinit var onConfirm: (View, Int) -> Unit


    override fun convert(helper: BaseViewHolder, item: ClusterOrder.ClusterList.ClusterOrderBean) {
        helper.setText(R.id.tv_name, item.product_name)
        helper.setText(R.id.tv_day, item.remain)
        helper.setText(R.id.tv_amount, item.buy_storage+"T")
        helper.setText(R.id.tv_cluster_date, item.buy_date+"天")
        if(item.revenue!=null){
            helper.setText(R.id.tv_leiji, item.revenue+"FIL")
        }

        if( item.yesterday!=null){
            helper.setText(R.id.tv_yesterday, item.yesterday+"FIL")
        }

        helper.setText(R.id.tv_state, item.state_txt)
        if (item.state_txt=="建设中")
        {
            helper.setVisible(R.id.ll_bottom,false)
            helper.setVisible(R.id.iv_state,false)
            helper.setTextColor(R.id.tv_state, mContext.resources.getColor(R.color.color_yellow))
        }
        else
        {
            helper.setVisible(R.id.ll_bottom,true)
            helper.setVisible(R.id.iv_state,true)
            helper.setTextColor(R.id.tv_state, mContext.resources.getColor(R.color.color_main))
        }

        helper.getView<Button>(R.id.btn_earning).setOnClickListener {
            mContext.startActivity(Intent(mContext, ClusterEarningsDetailsActivity::class.java).putExtra("orderId",item.id))
        }

    }
}
