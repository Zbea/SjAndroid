package com.hazz.kuangji.ui.activity.home

import android.graphics.Color
import androidx.appcompat.widget.Toolbar
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Msg
import com.hazz.kuangji.mvp.presenter.MsgPresenter
import com.hazz.kuangji.utils.ToolBarCustom
import kotlinx.android.synthetic.main.activity_msg_desc.*
import kotlinx.android.synthetic.main.activity_msg_desc.mToolBar
import kotlinx.android.synthetic.main.activity_msg_desc.tv_content
import kotlinx.android.synthetic.main.activity_msg_desc.tv_title


class MsgDescActivity : BaseActivity(){

    val css="<style>* {font-size:14px;line-height:20px;color:#fff;} p {color:#fff;font-size:14px;} a {color:#fff;font-size:13px;} img {max-width:310px;}pre {font-size:9pt;line-height:12pt;font-family:Courier New,Arial;border:1px solid #ddd;border-left:5px solid #6CE26C;background:#f6f6f6;padding:5px;}</style>"

    override fun layoutId(): Int {
        return R.layout.activity_msg_desc
    }

    override fun initData() {

    }

    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setLeftIcon(R.mipmap.back_white)
                .setOnLeftIconClickListener {finish() }

        val msg = intent.getSerializableExtra("message") as Msg
        tv_title.text = msg.title
        tv_time.text = msg.start_at

        tv_content.loadData(css+msg.content, "text/html", "UTF-8");
        tv_content.setBackgroundColor(Color.TRANSPARENT);//这个可以设置背景
        tv_content.settings.javaScriptEnabled=true
    }

    override fun start() {
    }


}
