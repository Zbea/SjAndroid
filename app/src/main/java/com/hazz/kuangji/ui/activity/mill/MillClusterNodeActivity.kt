package com.hazz.kuangji.ui.activity.mill

import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.*
import com.hazz.kuangji.mvp.presenter.ClusterPresenter
import com.hazz.kuangji.ui.adapter.ClusterNodeAdapter
import com.hazz.kuangji.utils.ToolBarCustom
import kotlinx.android.synthetic.main.activity_mill_cluster_node.*

class MillClusterNodeActivity : BaseActivity(), IContractView.IClusterView {

    private var mAdapter: ClusterNodeAdapter? = null
    private var mNodePresenter = ClusterPresenter(this)
    private var id:String?=null

    override fun getLists(item: Cluster) {
    }
    override fun getEarningsLists(item: ClusterEarnings) {
    }
    override fun onSuccess() {
    }
    override fun getLists(item: ClusterNode) {

        tv_direct.text=item.direct_t
        tv_myself.text=item.self_t
        tv_team.text=item.team
        tv_team_myself.text=item.invite

        mAdapter?.setNewData(item.cluster_users)
    }


    override fun layoutId(): Int {
        return R.layout.activity_mill_cluster_node
    }

    override fun initData() {
        id?.let { mNodePresenter.getClusterNode(it) }
    }

    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setTitle("集群方案团队")
                .setOnLeftIconClickListener { finish() }

        id=intent.getStringExtra("nodeId")

    }

    override fun start() {
        recycle_view.layoutManager = LinearLayoutManager(this)//创建布局管理
        mAdapter = ClusterNodeAdapter(R.layout.item_cluster_node, null)
        recycle_view.adapter = mAdapter
        mAdapter?.bindToRecyclerView(recycle_view)
    }




}
