package com.hazz.kuangji.ui.adapter


import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hazz.kuangji.R
import com.hazz.kuangji.mvp.model.bean.Node

class NodeSecondAdapter(layoutResId: Int, data: List<Node.InviteUsersBean.ChildrenBean>?) : BaseQuickAdapter<Node.InviteUsersBean.ChildrenBean, BaseViewHolder>(layoutResId, data) {


    lateinit var onConfirm: (View, Int) -> Unit

    override fun convert(helper: BaseViewHolder, item: Node.InviteUsersBean.ChildrenBean) {

        if(4+helper.position<10){
            helper.setText(R.id.tv_id, "0"+(4+helper.position).toString())
        }else{
            helper.setText(R.id.tv_id, (4+helper.position).toString())
        }

        helper.setText(R.id.tv_name, item.username)
        helper.setText(R.id.tv_amount, item.self_purchase+"USDT")


    }
}
