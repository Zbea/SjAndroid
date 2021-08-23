package com.hazz.kuangji.ui.adapter

import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hazz.kuangji.R
import com.hazz.kuangji.mvp.model.Mill
import com.hazz.kuangji.ui.activity.mill.MillAccelerateActivity
import com.hazz.kuangji.ui.activity.mill.MillEargingsListActivity
import com.hazz.kuangji.utils.BigDecimalUtil
import java.math.RoundingMode

/**
 * 加速服务器适配器
 */
class MillFILAdapter(layoutResId: Int, data: List<Mill.ListBean>?) : BaseQuickAdapter<Mill.ListBean, BaseViewHolder>(layoutResId, data) {

    lateinit var onConfirm: (View, Int) -> Unit

    override fun convert(helper: BaseViewHolder, item: Mill.ListBean) {
        helper.setText(R.id.tv_name, item.productName)
        helper.setText(R.id.tv_power, item.power)
        helper.setText(R.id.tv_power_title, if (item.minerType=="0") "数量（T）" else "封装总量（T）")
        helper.setText(R.id.tv_seal, BigDecimalUtil.mul(item.powerSeal,"1",2) )
        helper.setText(R.id.tv_total_fil, item.revenue)
        helper.setText(R.id.tv_yesterday_fil, item.yesterday)
        helper.setText(R.id.tv_day, item.allRemain)
        helper.setText(R.id.tv_pledge, item.pledgeAmount)
        helper.setText(R.id.tv_gas, item.gasAmount)
        helper.setText(R.id.tv_construction_day, item.buildRemain)
        helper.setText(R.id.tv_package_day, item.sealRemain)
        helper.setText(R.id.tv_line_release, item.lineRelease)

        helper.getView<TextView>(R.id.btn_earning).setOnClickListener {
            mContext.startActivity(Intent(mContext, MillEargingsListActivity::class.java).putExtra("orderId",item.id))
        }
        helper.setVisible(R.id.ll_seal,item.minerType!="0")
        helper.setVisible(R.id.iv_accelerate,item.stat=="0"&&item.minerType=="0")//倒期隐藏加速包

        helper.getView<ImageView>(R.id.iv_accelerate).setOnClickListener {
            mContext.startActivity(Intent(mContext, MillAccelerateActivity::class.java).putExtra("orderId",item.id))
        }

    }
}
