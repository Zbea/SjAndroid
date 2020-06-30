package com.hazz.kuangji.ui.activity

import android.text.Html
import androidx.appcompat.widget.Toolbar
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Xieyi
import com.hazz.kuangji.mvp.model.bean.SignRecord
import com.hazz.kuangji.mvp.presenter.XieyiPresenter
import com.hazz.kuangji.utils.ToolBarCustom
import kotlinx.android.synthetic.main.activity_rule.*


class RegistRuleActivity : BaseActivity(), IContractView.XieyiView {
    override fun getSignRecord(msg: SignRecord) {

    }

    override fun xieyi(msg: Xieyi) {
        var desc=msg.desc?.replace("关于我们：","      ")
        tv_rule.text=Html.fromHtml(desc)
    }

    override fun initData() {

    }

    override fun layoutId(): Int {
        return R.layout.activity_rule
    }

    private var mXieyiPresenter:XieyiPresenter= XieyiPresenter(this)
    private var type=0
    override fun initView() {
        val onLeftIconClickListener = ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setLeftIcon(R.mipmap.back_white)

                .setToolBarBgRescource(R.drawable.bg_hangqing)
                .setTitleColor(resources.getColor(R.color.color_white))
                .setOnLeftIconClickListener { view -> finish() }
         type= intent.getIntExtra("type",0)

        if(type==0){
            onLeftIconClickListener.setTitle("用户注册协议")
            mXieyiPresenter.xieyi("用户协议")
        }else{
            onLeftIconClickListener.setTitle("关于我们")
            mXieyiPresenter.xieyi("关于我们")
        }

    }
    override fun start() {

    }


}
