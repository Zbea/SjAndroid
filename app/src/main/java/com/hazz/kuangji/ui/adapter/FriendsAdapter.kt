package com.hazz.kuangji.ui.adapter


import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hazz.kuangji.R
import com.hazz.kuangji.mvp.model.Friends

class FriendsAdapter(layoutResId: Int, data: List<Friends.InviteUsersBean>?) : BaseQuickAdapter<Friends.InviteUsersBean, BaseViewHolder>(layoutResId, data) {


    lateinit var onConfirm: (View, Int) -> Unit

    override fun convert(helper: BaseViewHolder, item: Friends.InviteUsersBean) {

        helper.setText(R.id.user_name, item.username)
        helper.setText(R.id.invite_time, "于"+item.create_at+"注册")
    }
}
