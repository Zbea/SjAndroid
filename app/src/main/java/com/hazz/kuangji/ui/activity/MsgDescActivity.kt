package com.hazz.kuangji.ui.activity

import androidx.appcompat.widget.Toolbar
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.LoginContract
import com.hazz.kuangji.mvp.model.bean.Msg
import com.hazz.kuangji.mvp.presenter.MsgPresenter
import com.hazz.kuangji.ui.adapter.MsgAdapter
import com.hazz.kuangji.utils.ToolBarCustom
import kotlinx.android.synthetic.main.msg_desc.*


class MsgDescActivity : BaseActivity(), LoginContract.MsgView {


    override fun getMsg(msg: List<Msg>) {

    }


    private var mCoinPresenter: MsgPresenter = MsgPresenter(this)

    override fun layoutId(): Int {
        return R.layout.msg_desc
    }

    override fun initData() {

    }


    private var mAdapter: MsgAdapter? = null
    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setLeftIcon(R.mipmap.back_white)
                .setTitle(getString(R.string.gonggao_desc))
                .setToolBarBgRescource(R.drawable.bg_hangqing)
                .setTitleColor(resources.getColor(R.color.color_white))
                .setOnLeftIconClickListener { view -> finish() }

        val msg = intent.getSerializableExtra("message") as Msg
        tv_title.text = msg.title
        tv_time.text = "发送时间："+msg.start_at
        tv_content.text = msg.content
    }

    override fun start() {


    }


}
