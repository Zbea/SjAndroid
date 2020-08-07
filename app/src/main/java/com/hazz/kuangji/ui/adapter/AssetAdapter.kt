package com.hazz.kuangji.ui.adapter

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hazz.kuangji.R
import com.hazz.kuangji.mvp.model.MyAsset
import com.hazz.kuangji.utils.BigDecimalUtil
import com.hazz.kuangji.utils.SPUtil

class AssetAdapter(layoutResId: Int, data: List<MyAsset.AssetsBean>?) : BaseQuickAdapter<MyAsset.AssetsBean, BaseViewHolder>(layoutResId, data) {


    lateinit var onConfirm: (View, Int) -> Unit

    override fun convert(helper: BaseViewHolder, item: MyAsset.AssetsBean) {

        helper.setText(R.id.tv_coin, item.coin)
        if(item.coin=="FCOIN"){
            helper.setText(R.id.tv_coin, "FIL")
        }

        if (SPUtil.getBoolean("hide")) {
            helper.getView<TextView>(R.id.tv1).transformationMethod= PasswordTransformationMethod.getInstance()
            helper.getView<TextView>(R.id.tv2).transformationMethod= PasswordTransformationMethod.getInstance()
        }
        else
        {
            helper.getView<TextView>(R.id.tv1).transformationMethod= HideReturnsTransformationMethod.getInstance()
            helper.getView<TextView>(R.id.tv2).transformationMethod= HideReturnsTransformationMethod.getInstance()
        }

        helper.setText(R.id.tv1, BigDecimalUtil.mul(item.balance, "1", 8))
        helper.setText(R.id.tv2, item.frozen)

    }

}
