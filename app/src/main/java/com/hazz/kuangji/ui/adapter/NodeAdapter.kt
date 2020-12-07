package com.hazz.kuangji.ui.adapter


import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hazz.kuangji.Constants
import com.hazz.kuangji.R
import com.hazz.kuangji.mvp.model.Node

class NodeAdapter(layoutResId: Int, data: List<Node.InviteUsersBean>?) : BaseQuickAdapter<Node.InviteUsersBean, BaseViewHolder>(layoutResId, data) {


    lateinit var onConfirm: (View, Int) -> Unit

    override fun convert(helper: BaseViewHolder, item: Node.InviteUsersBean) {

        helper.setText(R.id.tv_name, item.username)
        helper.setText(R.id.tv_team, if (item.team == "0.00000000")"0" else item.team)
        helper.setText(R.id.tv_team_t, if (item.team_t == "0.00000000")"0" else item.team_t)
        helper.setText(R.id.tv_direct, if (item.direct_purchase == "0.00000000")"0" else item.direct_purchase)
        helper.setText(R.id.tv_direct_t, if (item.direct_t == "0.00000000")"0" else item.direct_t)
        helper.setText(R.id.tv_person_t, if (item.self_purchase_t == "0.00000000")"0" else item.self_purchase_t)
        helper.setText(R.id.tv_num, "团队："+item.children.size+"人")

        Glide.with(mContext).load( Constants.URL_INVITE+item.profile_img)
                .apply(RequestOptions.bitmapTransform(CircleCrop()).error(R.mipmap.icon_home_mine))
                .into(helper.getView(R.id.iv_header))
    }
}
