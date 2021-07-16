package com.hazz.kuangji.ui.activity.mine

import android.content.Intent
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.ValueAdd
import com.hazz.kuangji.mvp.presenter.ValueAddPresenter
import com.hazz.kuangji.ui.activity.LoginActivity
import com.hazz.kuangji.ui.adapter.ValueAddAdapter
import com.hazz.kuangji.utils.*
import com.hazz.kuangji.widget.CommonDialog
import com.hazz.kuangji.widget.RewardItemDeco
import com.hazz.kuangji.widget.SafeCheckDialog
import com.scwang.smartrefresh.layout.util.DensityUtil
import kotlinx.android.synthetic.main.activity_home_rent.*
import kotlinx.android.synthetic.main.activity_list.mToolBar
import kotlinx.android.synthetic.main.activity_list.rc_list
import kotlinx.android.synthetic.main.activity_value_add_page.*
import kotlinx.android.synthetic.main.fragment_mill.*


class ValueAddActivity : BaseActivity(), IContractView.IValueAddView {

    private var mValueAddPresenter: ValueAddPresenter = ValueAddPresenter(this)
    private var mAdapter: ValueAddAdapter? = null
    private var pos = 0
    private var item: ValueAdd? = null

    override fun getList(item: ValueAdd) {
        if (item == null) return
        this.item = item
        tv_money.text = item.amount + " FIL"
        mAdapter?.setNewData(item.list)
    }

    override fun onSuccess(msg: String) {
        mValueAddPresenter.getList()
        SToast.showText(msg)
    }

    override fun layoutId(): Int {
        return R.layout.activity_value_add_page
    }

    override fun initData() {
    }

    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
            .setTitle("增值服务")
            .setOnLeftIconClickListener { finish() }

        rc_list.layoutManager = LinearLayoutManager(this)//创建布局管理
        mAdapter = ValueAddAdapter(R.layout.item_value_add, null)
        rc_list.adapter = mAdapter
        mAdapter?.bindToRecyclerView(rc_list)
        mAdapter?.setEmptyView(R.layout.fragment_empty)
        val leftRightOffset = DensityUtil.dp2px(15f)
        rc_list.addItemDecoration(RewardItemDeco(0, 0, 0, leftRightOffset, 0))
        mAdapter?.setOnItemChildClickListener { _, view, position ->
            if (view.id == R.id.btn_commit) {
                pos = position
                SafeCheckDialog(this)
                    .setCancelListener { }
                    .setForgetListener {
                        startActivity(Intent(this, ChangePwdActivity::class.java))
                    }
                    .setConfirmListener { _, password ->
                        item?.list?.get(position)
                            ?.let { mValueAddPresenter.extract(it?.id, Utils.encryptMD5(password)) }
                    }.setCancelListener {

                    }
                    .show()
            }
        }

    }

    override fun start() {
        mValueAddPresenter.getList()
    }


}
