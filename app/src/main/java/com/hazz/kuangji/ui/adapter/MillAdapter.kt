package com.hazz.kuangji.ui.adapter

import android.content.Intent
import android.view.View
import android.widget.Button
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hazz.kuangji.R
import com.hazz.kuangji.mvp.model.Mill
import com.hazz.kuangji.ui.activity.mill.MillEarningsDetailsActivity
import com.hazz.kuangji.ui.activity.mine.ContractDetailsActivity
import com.hazz.kuangji.utils.BigDecimalUtil

class MillAdapter(layoutResId: Int, data: List<Mill.MachineListBean.ListBean>?) : BaseQuickAdapter<Mill.MachineListBean.ListBean, BaseViewHolder>(layoutResId, data) {


    lateinit var onConfirm: (View, Int) -> Unit


    override fun convert(helper: BaseViewHolder, item: Mill.MachineListBean.ListBean) {
        helper.setText(R.id.tv_name, item.product)
        helper.setText(R.id.tv_day, item.remain)
        helper.setText(R.id.tv_number, "服务器ID："+item.id+" / 服务器编号："+item.miner_number)
        helper.setText(R.id.tv_mill_num, item.buy_storage+"T")
        if(item.revenue!=null){
            helper.setText(R.id.tv_leiji, BigDecimalUtil.mul(item.revenue,"1",8)+"FIL")
        }


        helper.setText(R.id.tv_touru, item.buy_storage+"T")
        if( item.yesterday!=null){
            helper.setText(R.id.tv_yesterday, item.yesterday+"FIL")
        }

        if (item.storage=="192")
        {
            helper.setText(R.id.iv_total, "整机")
            helper.setTextColor(R.id.iv_total, mContext.resources.getColor(R.color.color_yellow))
        }else {
            helper.setText(R.id.iv_total, item.storage+"T")
            helper.setTextColor(R.id.iv_total, mContext.resources.getColor(R.color.color_666666))
        }

        when (item.state) {
            "1" ->{
                helper.setText(R.id.tv_state, "已运行")
                helper.setTextColor(R.id.tv_state, mContext.resources.getColor(R.color.color_main))
            }
            "0" -> {
                helper.setText(R.id.tv_state, "已停止")
                helper.setTextColor(R.id.tv_state, mContext.resources.getColor(R.color.redF4))
            }
        }

        helper.setVisible(R.id.btn_contract, item.hide_contract=="0")

        helper.getView<Button>(R.id.btn_contract).setOnClickListener {
            if (item.hide_contract=="0")
            {
                mContext.startActivity(Intent(mContext, ContractDetailsActivity::class.java).putExtra("contract_code",item.id)
                        .putExtra("contract_sign",item.is_sign))
            }
        }
        helper.getView<Button>(R.id.btn_earning).setOnClickListener {
            mContext.startActivity(Intent(mContext, MillEarningsDetailsActivity::class.java).putExtra("orderId",item.id))
        }

    }
}
