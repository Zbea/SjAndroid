package com.hazz.kuangji.ui.activity.mine

import android.content.Intent
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.hazz.kuangji.Constants
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Contract
import com.hazz.kuangji.mvp.presenter.ContractManagerPresenter
import com.hazz.kuangji.ui.adapter.ContractListAdapter
import com.hazz.kuangji.utils.ToolBarCustom
import com.hazz.kuangji.widget.RewardItemDeco
import com.scwang.smartrefresh.layout.util.DensityUtil
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.activity_rule.mToolBar
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @Created by Zbea
 * @fileName ContractManagerActivity
 * @date 2020/8/18 14:33
 * @email xiaofeng9212@126.com
 * @describe 合同管理
 **/
class ContractManagerActivity :BaseActivity(),IContractView.IContractManagerView{

    private var mAdapter: ContractListAdapter? =null
    private var mContracts:List<Contract> = ArrayList()
    private val mContractManagerPresenter=ContractManagerPresenter(this)

    override fun getContracts(datas: List<Contract>) {
        mContracts=datas
        mAdapter?.setNewData(datas)
    }
    override fun setSign(data: Contract) {
    }

    override fun layoutId(): Int {
        return R.layout.activity_list
    }

    override fun initData() {
        EventBus.getDefault().register(this)
    }

    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setTitle("合同管理")
                .setToolBarBgRescource(R.drawable.bg_line_bottom)
                .setOnLeftIconClickListener { finish() }

        rc_list.layoutManager = LinearLayoutManager(this)//创建布局管理
        mAdapter = ContractListAdapter(R.layout.item_contract_record, null)
        rc_list.adapter = mAdapter
        mAdapter?.bindToRecyclerView(rc_list)
        mAdapter?.setEmptyView(R.layout.fragment_empty)
        val leftRightOffset = DensityUtil.dp2px(15f)
        rc_list.addItemDecoration(RewardItemDeco(0, 0, 0, leftRightOffset, 0))

        mAdapter?.setOnItemClickListener { _, _, position ->
            var contract=mContracts[position]
            startActivity(Intent(this, ContractDetailsActivity::class.java).putExtra("contract_code",contract.id)
                    .putExtra("contract_sign",contract.is_sign))
        }

    }

    override fun start() {
        mContractManagerPresenter.getContracts(true)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(data: Contract) {
        if (data!=null)
        {
            mContractManagerPresenter.getContracts(false)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}