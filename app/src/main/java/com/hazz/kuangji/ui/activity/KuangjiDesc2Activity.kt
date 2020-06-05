package com.hazz.kuangji.ui.activity

import androidx.appcompat.widget.Toolbar
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.bean.Msg
import com.hazz.kuangji.mvp.presenter.MsgPresenter
import com.hazz.kuangji.ui.adapter.MsgAdapter
import com.hazz.kuangji.utils.ToolBarCustom
import kotlinx.android.synthetic.main.kuangji_desc.*


class KuangjiDesc2Activity : BaseActivity(), IContractView.MsgView {


    override fun getMsg(msg: List<Msg>) {

    }


    private var mCoinPresenter: MsgPresenter = MsgPresenter(this)

    override fun layoutId(): Int {
        return R.layout.kuangji_desc2
    }

    override fun initData() {

    }


    private var mAdapter: MsgAdapter? = null
    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setLeftIcon(R.mipmap.back_white)
                .setTitle("详情")
                .setToolBarBgRescource(R.drawable.bg_hangqing)
                .setTitleColor(resources.getColor(R.color.color_white))
                .setOnLeftIconClickListener { view -> finish() }

        val title = intent.getStringExtra("name")

        tv_title.text=title
        var content="1. 数据以 ETH algorithm + AI 预测模型为主 \n" +
                "2. 每台耗电 80W *24 小时= 1.92 度/日之用电量 \n" +
                "3. 若年产币八颗 \n" +
                "4.算力：270-300Mhash \n" +
                "5.毛利：8 颗* 以太币市价 US\$200 - 每年总电价\n" +
                "(1.92 度/*365 天*每度电价) "

        tv_content.text=content
        tv_content.textSize=16f

       // Glide.with(this).load(R.mipmap.kuangji1).into(iv)
    }

    override fun start() {


    }


}
