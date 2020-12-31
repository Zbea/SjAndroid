package com.hazz.kuangji.ui.adapter


import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hazz.kuangji.Constants
import com.hazz.kuangji.R
import com.hazz.kuangji.mvp.model.ClusterNode
import com.hazz.kuangji.mvp.model.Node

class ClusterNodeAdapter(layoutResId: Int, data: List<ClusterNode.ClusterUsersBean>?) : BaseQuickAdapter<ClusterNode.ClusterUsersBean, BaseViewHolder>(layoutResId, data) {


    lateinit var onConfirm: (View, Int) -> Unit

    override fun convert(helper: BaseViewHolder, item: ClusterNode.ClusterUsersBean) {

        helper.setText(R.id.tv_name, item.username)
        helper.setText(R.id.tv_direct, item.direct_t)
        helper.setText(R.id.tv_myself, item.self_t)

        Glide.with(mContext).load( Constants.URL_INVITE+item.profile_img)
                .apply(RequestOptions.bitmapTransform(CircleCrop()).error(R.mipmap.icon_home_mine))
                .into(helper.getView(R.id.iv_header))
    }
}
