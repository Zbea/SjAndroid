package com.hazz.kuangji.ui.adapter


import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class FenhongAdapter(layoutResId: Int, data: List<String>?) : BaseQuickAdapter<String, BaseViewHolder>(layoutResId, data) {


    lateinit var onConfirm: (View, Int) -> Unit

    override fun convert(helper: BaseViewHolder, item: String) {

//        helper.setText(R.id.tv1, item.id)
//        helper.setText(R.id.tv2, item.create_at)
//        helper.setText(R.id.tv3, item.amount)
//
//        helper.itemView.setOnClickListener {
//
//            mContext.startActivity(Intent(mContext, MsgDescActivity::class.java).putExtra("message", item))
//        }
    }
}
