package com.hazz.kuangji.ui.adapter


import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hazz.kuangji.R
import com.hazz.kuangji.mvp.model.Contract

class ContractListAdapter(layoutResId: Int, data: List<Contract>?) : BaseQuickAdapter<Contract, BaseViewHolder>(layoutResId, data) {


    lateinit var onConfirm: (View, Int) -> Unit

    override fun convert(helper: BaseViewHolder, item: Contract) {
        helper.run {
            setText(R.id.tv_order_number, item.contract_num)
            setText(R.id.tv_order_date, item.create_at)
            setText(R.id.tv_order_type, item.miner_number)
            if (item.is_sign=="1")
            {
                setText(R.id.tv_state, "已签署")
                setTextColor(R.id.tv_state, mContext.resources.getColor(R.color.blue))
            }
            else {
                setText(R.id.tv_state, "未签署")
                setTextColor(R.id.tv_state, mContext.resources.getColor(R.color.redF4))
            }
        }
    }

}
