package com.hazz.kuangji.ui.activity

import android.graphics.Color
import android.text.Html
import androidx.appcompat.widget.Toolbar
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.bean.Msg
import com.hazz.kuangji.mvp.presenter.MsgPresenter
import com.hazz.kuangji.ui.adapter.MsgAdapter
import com.hazz.kuangji.utils.ToolBarCustom
import kotlinx.android.synthetic.main.activity_home_kuangji_desc.*
import kotlinx.android.synthetic.main.activity_home_kuangji_desc.mToolBar
import kotlinx.android.synthetic.main.activity_home_kuangji_desc.tv_content
import kotlinx.android.synthetic.main.msg_desc.*


class HomeKuangjiDescActivity : BaseActivity(), IContractView.MsgView {

    val css="<style>* {font-size:14px;line-height:20px;color:#fff;} p {color:#fff;font-size:14px;} a {color:#fff;font-size:13px;} img {max-width:310px;}pre {font-size:9pt;line-height:12pt;font-family:Courier New,Arial;border:1px solid #fff;border-left:5px solid #6CE26C;background:#f6f6f6;padding:5px;}</style>"


    override fun getMsg(msg: List<Msg>) {

    }


    private var mCoinPresenter: MsgPresenter = MsgPresenter(this)

    override fun layoutId(): Int {
        return R.layout.activity_home_kuangji_desc
    }

    override fun initData() {

    }


    private var mAdapter: MsgAdapter? = null
    override fun initView() {
        val title = intent.getStringExtra("name")
        val content = intent.getStringExtra("desc")

        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setLeftIcon(R.mipmap.back_white)
                .setTitle(title)
                .setToolBarBgRescource(R.drawable.bg_hangqing)
                .setTitleColor(resources.getColor(R.color.color_white))
                .setOnLeftIconClickListener { view -> finish() }

        tv_content.loadData(css+content, "text/html", "UTF-8");
        tv_content.setBackgroundColor(Color.TRANSPARENT);//这个可以设置背景
        tv_content.settings.javaScriptEnabled=true
    }

    override fun start() {


    }


}
