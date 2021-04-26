package com.hazz.kuangji.ui.adapter

import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hazz.kuangji.R
import com.hazz.kuangji.mvp.model.Mill
import com.hazz.kuangji.ui.activity.mill.MillAccelerateActivity
import com.hazz.kuangji.ui.activity.mill.MillEarningsAccelerateDetailsActivity
import com.hazz.kuangji.ui.activity.mill.MillEarningsDetailsActivity
import com.hazz.kuangji.ui.activity.mine.ContractDetailsActivity
import com.hazz.kuangji.ui.activity.mine.ContractManagerActivity
import com.hazz.kuangji.utils.BigDecimalUtil

/**
 * 加速矿机适配器
 */
class MillAccelerateAdapter(layoutResId: Int, data: List<Mill.MachineListBean.ListBean>?) : BaseQuickAdapter<Mill.MachineListBean.ListBean, BaseViewHolder>(layoutResId, data) {

    lateinit var onConfirm: (View, Int) -> Unit

    override fun convert(helper: BaseViewHolder, item: Mill.MachineListBean.ListBean) {
        helper.setText(R.id.tv_name, item.product)
        helper.setText(R.id.tv_number, "服务器编号："+item.miner_number)
        helper.setText(R.id.tv_money, item.buy_storage)
        if(item.revenue!=null){
            helper.setText(R.id.tv_total_fil, item.revenue)
        }
        if( item.yesterday!=null){
            helper.setText(R.id.tv_yesterday_fil, item.yesterday)
        }
        helper.setText(R.id.tv_day, item.remain)
        helper.setText(R.id.tv_pledge, item.pledge_amount)
        helper.setText(R.id.tv_gas, item.gas_amount)
        helper.setText(R.id.tv_gas_expend, item.gas_consume)
        helper.setText(R.id.tv_construction_day, item.build_remain)
        helper.setText(R.id.tv_package_day, item.seal_remain)
        helper.setVisible(R.id.btn_contract, item.hide_contract=="0")

        helper.getView<Button>(R.id.btn_contract).setOnClickListener {
            mContext.startActivity(Intent(mContext, ContractDetailsActivity::class.java).putExtra("contract_code",item.id)
                    .putExtra("contract_sign",item.is_sign).putExtra("miner_type", "1"))
        }
        helper.getView<Button>(R.id.btn_earning).setOnClickListener {
            mContext.startActivity(Intent(mContext, MillEarningsAccelerateDetailsActivity::class.java).putExtra("orderId",item.id))
        }

    }
}
