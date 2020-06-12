package com.hazz.kuangji.ui.activity

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


class HomeKuangjiDescActivity : BaseActivity(), IContractView.MsgView {


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

        tv_content.text= Html.fromHtml(content)

    }

    override fun start() {


    }


}
