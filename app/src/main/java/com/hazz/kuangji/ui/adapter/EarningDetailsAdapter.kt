package com.hazz.kuangji.ui.adapter


import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hazz.kuangji.R
import com.hazz.kuangji.mvp.model.MillEarningsDetails
import com.hazz.kuangji.utils.BigDecimalUtil
import java.math.RoundingMode

class EarningDetailsAdapter (layoutResId: Int, data: List<MillEarningsDetails>?) : BaseQuickAdapter<MillEarningsDetails, BaseViewHolder>(layoutResId, data) {


    lateinit var onConfirm: (View, Int) -> Unit

    override fun convert(helper: BaseViewHolder, item: MillEarningsDetails) {

        var position=helper.adapterPosition

        var fil=BigDecimalUtil.mul(item.return_fil,"1",8)

        var f25=BigDecimalUtil.mul(item.return_fil,"0.25",8)

        var f75=BigDecimalUtil.mul(item.return_fil,"0.75",8)

        var day=BigDecimalUtil.sub("180",item.remain,8)//运行天数

        var dayRelease=BigDecimalUtil.div(f75,"180",8)//每天释放额度

        var release=BigDecimalUtil.mul(dayRelease,day,8)//运行多少天释放

        var lock=BigDecimalUtil.mul(dayRelease,item.remain,8)//运行多少天质押

        var usable=BigDecimalUtil.add(f25,release,8,RoundingMode.UP)//每天可用


        helper.setText(R.id.tv_date,item.start_at)
        helper.setText(R.id.tv_fil,fil)
        helper.setText(R.id.tv_25,f25)
        helper.setText(R.id.tv_75,lock)
        helper.setText(R.id.tv_release,release)
        helper.setText(R.id.tv_usable,usable)
        if (BigDecimalUtil.isOdd(position))
        {
            helper.getView<LinearLayout>(R.id.ll_bg).setBackgroundColor(mContext.resources.getColor(R.color.color_gray_bg))
        }
        else{
            helper.getView<LinearLayout>(R.id.ll_bg).setBackgroundColor(mContext.resources.getColor(R.color.color_bg))
        }
    }
}
