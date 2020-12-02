package com.hazz.kuangji.ui.adapter


import android.view.View
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hazz.kuangji.R
import com.hazz.kuangji.mvp.model.AssetDetails
import com.hazz.kuangji.mvp.model.MillEarningsDetails
import com.hazz.kuangji.utils.BigDecimalUtil
import com.hazz.kuangji.utils.SPUtil

class AssetDetailsAdapter (layoutResId: Int, data: List<AssetDetails>?) : BaseQuickAdapter<AssetDetails, BaseViewHolder>(layoutResId, data) {


    lateinit var onConfirm: (View, Int) -> Unit

    override fun convert(helper: BaseViewHolder, item: AssetDetails) {

        var position=helper.adapterPosition

        helper.setText(R.id.tv_date,item.start_at)
        helper.setText(R.id.tv_25,item.day_direct_release)
        helper.setText(R.id.tv_75,item.day_line)
        helper.setText(R.id.tv_lock,item.day_lockfil)
        helper.setText(R.id.tv_usable,item.balance)
        helper.setText(R.id.tv_achviment,item.day_total_invite)


        if (BigDecimalUtil.isOdd(position))
        {
            helper.getView<LinearLayout>(R.id.ll_bg).setBackgroundColor(mContext.resources.getColor(if (!SPUtil.getBoolean("skin")) R.color.color_gray_bg else R.color.color_gray_bg_night))
        }
        else{
            helper.getView<LinearLayout>(R.id.ll_bg).setBackgroundColor(mContext.resources.getColor(if (!SPUtil.getBoolean("skin")) R.color.color_bg else R.color.color_bg_night))
        }
    }
}
