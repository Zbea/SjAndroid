package com.hazz.kuangji.ui.activity.home

import android.text.Html
import androidx.appcompat.widget.Toolbar
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Msg
import com.hazz.kuangji.mvp.presenter.MsgPresenter
import com.hazz.kuangji.utils.ToolBarCustom
import kotlinx.android.synthetic.main.activity_agreement.*
import kotlinx.android.synthetic.main.activity_msg_desc.*
import kotlinx.android.synthetic.main.activity_msg_desc.mToolBar


class MsgDescActivity : BaseActivity(), IContractView.MsgView {

    private var mMsgPresenter: MsgPresenter = MsgPresenter(this)
    private var id=""

    override fun getMsg(msg: Msg) {
    }
    override fun getMsgDetails(msg: Msg.MsgBean) {
        tv_time.text = "发布时间："+msg.createAt
        if (msg.detail!=null)
            tv_content.loadData(msg.detail, "text/html", "UTF-8")
    }

    override fun layoutId(): Int {
        return R.layout.activity_msg_desc
    }
    override fun initData() {
    }
    override fun initView() {

        val msg = intent.getSerializableExtra("message") as Msg.MsgBean
        id=msg.id

        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setTitle(msg.title)
                .setOnLeftIconClickListener {finish() }
    }

    override fun start() {
        mMsgPresenter.getMsgDetails(id)
    }




}
