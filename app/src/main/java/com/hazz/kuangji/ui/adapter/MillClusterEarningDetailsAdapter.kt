package com.hazz.kuangji.ui.adapter


import android.view.View
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hazz.kuangji.R
import com.hazz.kuangji.mvp.model.ClusterEarningsDetails
import com.hazz.kuangji.mvp.model.MillEarningsDetails
import com.hazz.kuangji.utils.BigDecimalUtil
import com.hazz.kuangji.utils.SPUtil

class MillClusterEarningDetailsAdapter (layoutResId: Int, data: List<ClusterEarningsDetails.ClusterEarningsDetailsBean>?) : BaseQuickAdapter<ClusterEarningsDetails.ClusterEarningsDetailsBean, BaseViewHolder>(layoutResId, data) {


    lateinit var onConfirm: (View, Int) -> Unit

    override fun convert(helper: BaseViewHolder, item: ClusterEarningsDetails.ClusterEarningsDetailsBean) {

        var position=helper.adapterPosition

        var fil=BigDecimalUtil.mul(item.return_fil,"1",8)

        helper.setText(R.id.tv_date,item.start_at)
        helper.setText(R.id.tv_sealed,item.seal_amount)
        helper.setText(R.id.tv_sealed_sum,item.sealed_storage)
        helper.setText(R.id.tv_fil,fil)
        helper.setText(R.id.tv_t,item.fil_amount)
        helper.setText(R.id.tv_25,item.line25)
        helper.setText(R.id.tv_75,item.line75)
        helper.setText(R.id.tv_usable,item.usable)


        if (BigDecimalUtil.isOdd(position))
        {
            helper.getView<LinearLayout>(R.id.ll_bg).setBackgroundColor(mContext.resources.getColor(if (!SPUtil.getBoolean("skin")) R.color.color_gray_bg else R.color.color_gray_bg_night))
        }
        else{
            helper.getView<LinearLayout>(R.id.ll_bg).setBackgroundColor(mContext.resources.getColor(if (!SPUtil.getBoolean("skin")) R.color.color_bg else R.color.color_bg_night))
        }
    }
}