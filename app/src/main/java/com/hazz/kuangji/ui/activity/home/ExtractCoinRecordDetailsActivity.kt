package com.hazz.kuangji.ui.activity.home

import android.view.View
import androidx.appcompat.widget.Toolbar
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.ExtractRecord
import com.hazz.kuangji.mvp.model.ExtractRule
import com.hazz.kuangji.mvp.presenter.ExtractCoinPresenter
import com.hazz.kuangji.utils.SToast
import com.hazz.kuangji.utils.ToolBarCustom
import kotlinx.android.synthetic.main.activity_extract_record_details.*
import kotlinx.android.synthetic.main.activity_list.mToolBar


class ExtractCoinRecordDetailsActivity : BaseActivity(), IContractView.IExtractView{

    private var mExtractCoinPresenter: ExtractCoinPresenter = ExtractCoinPresenter(this)
    private var record:ExtractRecord.ListBean?=null

    override fun extractSucceed(msg: String) {
        SToast.showText(msg)
        finish()
    }

    override fun extractRecord(msg: ExtractRecord) {
    }

    override fun extractRule(msg: ExtractRule) {
    }


    override fun layoutId(): Int {
        return R.layout.activity_extract_record_details
    }
    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
            .setTitle("转出详情")
            .setOnLeftIconClickListener { finish() }

        btn_cancel.setOnClickListener {
            if (record!=null)
            {
                mExtractCoinPresenter.cancel(record?.id!!)
            }
        }

    }

    override fun initData() {
        record= intent.getSerializableExtra("recordDetails") as ExtractRecord.ListBean?

        if (record==null)return

        tv_num.text = record?.amountAll+record?.coin
        tv_fee.text=record?.amountFee+record?.coin
        tv_time.text=record?.create_at
        if (record?.stat=="0")
        {
            tv_state.text="待审核"
            tv_state.setTextColor(resources.getColor(R.color.color_yellow))
            btn_cancel.visibility= View.VISIBLE
        }
        else if (record?.stat=="4")
        {
            tv_state.text="已完成"
            btn_cancel.visibility= View.GONE
        }
        else{
            tv_state.text="已取消"
            tv_state.setTextColor(resources.getColor(R.color.color_999999))
            btn_cancel.visibility= View.GONE
        }

    }

    override fun start() {
    }






}
