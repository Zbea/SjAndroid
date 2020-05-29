package com.hazz.kuangji.ui.adapter


import android.content.Intent
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hazz.kuangji.R
import com.hazz.kuangji.mvp.model.bean.Msg
import com.hazz.kuangji.ui.activity.MsgDescActivity

class MsgAdapter(layoutResId: Int, data: List<Msg>?) : BaseQuickAdapter<Msg, BaseViewHolder>(layoutResId, data) {


    lateinit var onConfirm: (View, Int) -> Unit

    override fun convert(helper: BaseViewHolder, item: Msg) {

        helper.setText(R.id.mTvNoticeTitle, item.title)
        helper.setText(R.id.mTvTime, item.start_at)


        helper.itemView.setOnClickListener {

            mContext.startActivity(Intent(mContext, MsgDescActivity::class.java).putExtra("message", item))
        }
    }
}
