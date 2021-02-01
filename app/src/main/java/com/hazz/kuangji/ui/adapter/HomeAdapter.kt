package com.hazz.kuangji.ui.adapter


import android.content.Intent
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hazz.kuangji.Constants
import com.hazz.kuangji.R
import com.hazz.kuangji.mvp.model.Home
import com.hazz.kuangji.ui.activity.home.HomeRentActivity
import com.hazz.kuangji.utils.BigDecimalUtil
import com.hazz.kuangji.utils.GlideEngine

class HomeAdapter(layoutResId: Int, data: List<Home.ProductsBean>?) : BaseQuickAdapter<Home.ProductsBean, BaseViewHolder>(layoutResId, data) {


    lateinit var onConfirm: (View, Int) -> Unit

    override fun convert(helper: BaseViewHolder, item: Home.ProductsBean) {

        helper.run {
            setText(R.id.tv_name, item.name)
            setText(R.id.tv_type, "服务器类型："+item.type)
            setText(R.id.tv_time, "合约周期："+item.round+"天")
            setText(R.id.tv_suanli, "服务器算力："+item.power)
            setText(R.id.tv_amount, "价格："+BigDecimalUtil.mul(item.price,"1",8)+"USDT")
            GlideEngine.createGlideEngine().loadImage(mContext, Constants.URL_INVITE+item.pic,getView(R.id.iv))
            getView<TextView>(R.id.tv_zu).setOnClickListener {
                mContext.startActivity(Intent(mContext, HomeRentActivity::class.java).putExtra("produce",item))

            }
        }
    }

}
