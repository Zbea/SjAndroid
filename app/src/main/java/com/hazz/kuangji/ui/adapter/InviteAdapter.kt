package com.hazz.kuangji.ui.adapter


import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hazz.kuangji.R
import com.hazz.kuangji.mvp.model.Invite

class InviteAdapter(layoutResId: Int, data: List<Invite>?) : BaseQuickAdapter<Invite, BaseViewHolder>(layoutResId, data) {


    lateinit var onConfirm: (View, Int) -> Unit

    override fun convert(helper: BaseViewHolder, item: Invite) {

        helper.setText(R.id.user_name, item.userName)
        helper.setText(R.id.invite_time, item.createAt)
    }
}
