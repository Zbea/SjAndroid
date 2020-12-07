package com.hazz.kuangji.ui.adapter


import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hazz.kuangji.Constants
import com.hazz.kuangji.R
import com.hazz.kuangji.mvp.model.Node
import com.hazz.kuangji.utils.GlideEngine

class NodeTwoAdapter(layoutResId: Int, data: List<Node.InviteUsersBean.ChildrenBean>?) : BaseQuickAdapter<Node.InviteUsersBean.ChildrenBean, BaseViewHolder>(layoutResId, data) {


    lateinit var onConfirm: (View, Int) -> Unit

    override fun convert(helper: BaseViewHolder, item: Node.InviteUsersBean.ChildrenBean) {

        helper.setText(R.id.tv_name, item.username)
        helper.setText(R.id.tv_person_t, if (item.self_purchase == "0.00000000")"0" else item.self_purchase_t)
        GlideEngine.createGlideEngine().loadImage(mContext, Constants.URL_INVITE+item.profile_img,helper.getView(R.id.iv_header))
    }
}
