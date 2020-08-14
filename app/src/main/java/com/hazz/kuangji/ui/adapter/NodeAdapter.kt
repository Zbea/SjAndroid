package com.hazz.kuangji.ui.adapter


import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hazz.kuangji.R
import com.hazz.kuangji.mvp.model.Node

class NodeAdapter(layoutResId: Int, data: List<Node.InviteUsersBean>?) : BaseQuickAdapter<Node.InviteUsersBean, BaseViewHolder>(layoutResId, data) {


    lateinit var onConfirm: (View, Int) -> Unit

    override fun convert(helper: BaseViewHolder, item: Node.InviteUsersBean) {

        helper.setText(R.id.tv_name, item.username)
        helper.setText(R.id.tv_person, if (item.self_purchase == "0.00000000")"0" else item.self_purchase)
        helper.setText(R.id.tv_team, if (item.direct_purchase == "0.00000000")"0" else item.direct_purchase)
        helper.setText(R.id.tv_num, "团队："+item.children.size+"人")
    }
}
