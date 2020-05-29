package com.hazz.kuangji.ui.adapter


import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hazz.kuangji.R
import com.hazz.kuangji.mvp.model.bean.MyAsset
import com.hazz.kuangji.utils.BigDecimalUtil

class ZichanAdapter(layoutResId: Int, data: List<MyAsset.AssetsBean>?) : BaseQuickAdapter<MyAsset.AssetsBean, BaseViewHolder>(layoutResId, data) {


    lateinit var onConfirm: (View, Int) -> Unit

    override fun convert(helper: BaseViewHolder, item: MyAsset.AssetsBean) {

        helper.setText(R.id.tv_coin, item.coin)
        if(item.coin=="FCOIN"){
            helper.setText(R.id.tv_coin, "FIL")
        }
        helper.setText(R.id.tv1, BigDecimalUtil.mul(item.balance, "1", 5))
        helper.setText(R.id.tv2, item.frozen)


//        helper.getView<TextView>(R.id.tv_zu).setOnClickListener {
//            mContext.startActivity(Intent(mContext, ZujieActivity::class.java))
//
//        }
    }

}
