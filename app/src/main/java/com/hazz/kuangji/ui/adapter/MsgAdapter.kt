package com.hazz.kuangji.ui.adapter


import android.content.Intent
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hazz.kuangji.R
import com.hazz.kuangji.mvp.model.Msg
import com.hazz.kuangji.ui.activity.home.MsgDescActivity

class MsgAdapter(layoutResId: Int, data: List<Msg.MsgBean>?) : BaseQuickAdapter<Msg.MsgBean, BaseViewHolder>(layoutResId, data) {


    lateinit var onConfirm: (View, Int) -> Unit

    override fun convert(helper: BaseViewHolder, item: Msg.MsgBean) {

        helper.setText(R.id.mTvNoticeTitle, item.title)
        helper.setText(R.id.mTvTime, item.createAt)

        helper.itemView.setOnClickListener {
            mContext.startActivity(Intent(mContext, MsgDescActivity::class.java).putExtra("message", item))
        }
    }
}
