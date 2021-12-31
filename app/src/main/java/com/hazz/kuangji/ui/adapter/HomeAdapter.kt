package com.hazz.kuangji.ui.adapter


import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hazz.kuangji.Constants
import com.hazz.kuangji.R
import com.hazz.kuangji.mvp.model.Home

class HomeAdapter(layoutResId: Int, data: List<Home.ProductBean.FilBean>?) : BaseQuickAdapter<Home.ProductBean.FilBean, BaseViewHolder>(layoutResId, data) {


    lateinit var onConfirm: (View, Int) -> Unit

    override fun convert(helper: BaseViewHolder, item: Home.ProductBean.FilBean) {

        helper.run {
            setText(R.id.tv_name, item.name)
            setText(R.id.tv_type, "类型：FIL存储服务器")
            setText(R.id.tv_time, "周期："+item.allTerm+"天")

            Glide.with(mContext).load(Constants.URL_BASE+item.img).apply(
                RequestOptions.bitmapTransform(RoundedCorners(20))).into(getView(R.id.iv))
        }
    }

}
