package com.hazz.kuangji.ui.adapter


import android.view.View
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hazz.kuangji.R
import com.hazz.kuangji.mvp.model.MillEarningsDetails
import com.hazz.kuangji.utils.BigDecimalUtil
import com.hazz.kuangji.utils.SPUtil

/**
 * 加速服务器收益适配器
 */
class MillEarningAccelerateAdapter (layoutResId: Int, data: List<MillEarningsDetails>?) : BaseQuickAdapter<MillEarningsDetails, BaseViewHolder>(layoutResId, data) {


    lateinit var onConfirm: (View, Int) -> Unit

    override fun convert(helper: BaseViewHolder, item: MillEarningsDetails) {

        var position=helper.adapterPosition

        helper.setText(R.id.tv_date,item.start_at)
        helper.setText(R.id.tv_seal_day,item.seal_day)
        helper.setText(R.id.tv_sealed,item.sealed)
        helper.setText(R.id.tv_gas,item.gas)
        helper.setText(R.id.tv_25,item.line25)
        helper.setText(R.id.tv_75,item.release)
        helper.setText(R.id.tv_fil_t,item.fil_amount)
        helper.setText(R.id.tv_fil,item.return_fil)

        if (BigDecimalUtil.isOdd(position))
        {
            helper.getView<LinearLayout>(R.id.ll_bg).setBackgroundColor(mContext.resources.getColor(if (!SPUtil.getBoolean("skin")) R.color.color_gray_bg else R.color.color_gray_bg_night))
        }
        else{
            helper.getView<LinearLayout>(R.id.ll_bg).setBackgroundColor(mContext.resources.getColor(if (!SPUtil.getBoolean("skin")) R.color.color_bg else R.color.color_bg_night))
        }
    }
}
