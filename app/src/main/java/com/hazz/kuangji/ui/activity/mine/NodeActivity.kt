package com.hazz.kuangji.ui.activity.mine

import android.content.Intent
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Certification
import com.hazz.kuangji.mvp.model.Node
import com.hazz.kuangji.mvp.model.Account
import com.hazz.kuangji.mvp.model.UploadModel
import com.hazz.kuangji.mvp.presenter.NodePresenter
import com.hazz.kuangji.ui.adapter.NodeAdapter
import com.hazz.kuangji.utils.ToolBarCustom
import kotlinx.android.synthetic.main.activity_node.*

class NodeActivity : BaseActivity(), IContractView.NodeView {

    override fun getAccount(msg: Account) {

    }

    override fun setHeader(msg: UploadModel) {

    }

    override fun getCertification(data: Certification) {

    }

    override fun getNode(msg: Node) {
        if(msg.direct!=null){
            tv_person.text=if(msg.direct == "0")"0.00000000" else msg.direct
        }
        if(msg.team!=null){
            tv_team.text=if(msg.team == "0")"0.00000000" else msg.team
        }

        datas = msg.invite_users
        if (!datas.isNullOrEmpty()) {
            mAdapter?.setNewData(datas)
        }

    }


    override fun layoutId(): Int {
        return R.layout.activity_node
    }

    override fun initData() {
        mNodePresenter.getNode()
    }

    private var mAdapter: NodeAdapter? = null
    private var mNodePresenter: NodePresenter = NodePresenter(this)
    private var datas:List<Node.InviteUsersBean>?=null

    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setTitle("我的团队")
                .setOnLeftIconClickListener { finish() }


    }

    override fun start() {
        recycle_view.layoutManager = LinearLayoutManager(this)//创建布局管理
        mAdapter = NodeAdapter(R.layout.item_node, null)
        recycle_view.adapter = mAdapter
        mAdapter?.bindToRecyclerView(recycle_view)
        mAdapter?.setOnItemClickListener { adapter, view, position ->
            startActivity(Intent(this,NodeTwoActivity::class.java).putExtra("node", datas?.get(position)))
        }
    }




}
