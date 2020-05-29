package com.hazz.kuangji.ui.adapter


import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hazz.kuangji.R
import com.hazz.kuangji.mvp.model.bean.ChargeRecord
import com.hazz.kuangji.mvp.model.bean.SignRecord

class SignRecordAdapter(layoutResId: Int, data: List<SignRecord.ListBean>?) : BaseQuickAdapter<SignRecord.ListBean, BaseViewHolder>(layoutResId, data) {


    lateinit var onConfirm: (View, Int) -> Unit

    override fun convert(helper: BaseViewHolder, item: SignRecord.ListBean) {

       // helper.setText(R.id.tv_name, item.coin)
        helper.setText(R.id.tv_time, item.create_at)
        helper.setText(R.id.tv_amount, "签到"+(data.size-helper.position)+"天")

//
//        helper.itemView.setOnClickListener {
//
//            mContext.startActivity(Intent(mContext, MsgDescActivity::class.java).putExtra("message", item))
//        }
    }
}
