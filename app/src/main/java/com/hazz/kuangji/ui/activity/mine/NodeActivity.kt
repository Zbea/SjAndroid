package com.hazz.kuangji.ui.activity.mine

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.bean.Certification
import com.hazz.kuangji.mvp.model.bean.Node
import com.hazz.kuangji.mvp.model.bean.Account
import com.hazz.kuangji.mvp.model.bean.UploadModel
import com.hazz.kuangji.mvp.presenter.NodePresenter
import com.hazz.kuangji.ui.adapter.NodeAdapter
import com.hazz.kuangji.utils.ToolBarCustom
import com.hazz.kuangji.widget.RewardItemDeco
import com.scwang.smartrefresh.layout.util.DensityUtil
import kotlinx.android.synthetic.main.activity_node.*


class NodeActivity : BaseActivity(), IContractView.NodeView {
    override fun getAccount(msg: Account) {

    }

    override fun setHeader(msg: UploadModel) {

    }

    override fun getCertification(data: Certification) {

    }

    @SuppressLint("SetTextI18n")
    override fun getNode(msg: Node) {
        if(msg.direct!=null){
            tv_person.text=msg.direct
        }
        if(msg.team!=null){
            tv_team.text=msg.team
        }

        val invite_users = msg.invite_users
        if (!invite_users.isNullOrEmpty()) {
            if (invite_users.size > 3) {
                if (invite_users[0] != null) {
                    type1=invite_users[0]
                    tv_name1.text = invite_users[0].username
                    tv_amount1.text = invite_users[0].self_purchase + "USDT"
                }
                if (invite_users[1] != null) {
                    type2=invite_users[1]
                    tv_name2.text = invite_users[1].username
                    tv_amount2.text = invite_users[1].self_purchase + "USDT"
                }
                if (invite_users[2] != null) {
                    type3=invite_users[2]
                    tv_name3.text = invite_users[2].username
                    tv_amount3.text = invite_users[2].self_purchase + "USDT"
                }
                val subList = invite_users.subList(3, invite_users.size )
                mAdapter!!.setNewData(subList)
            } else {
                if(invite_users.size==1){
                    if (invite_users[0] != null) {
                        type1=invite_users[0]
                        tv_name1.text = invite_users[0].username
                        tv_amount1.text = invite_users[0].self_purchase + "USDT"
                    }
                }
                if(invite_users.size==2){
                    if (invite_users[0] != null) {
                        type1=invite_users[0]
                        tv_name1.text = invite_users[0].username
                        tv_amount1.text = invite_users[0].self_purchase + "USDT"
                    }
                    if (invite_users[1] != null) {
                        type2=invite_users[1]
                        tv_name2.text = invite_users[1].username
                        tv_amount2.text = invite_users[1].self_purchase + "USDT"
                    }
                }
                if(invite_users.size==3){
                    if (invite_users[0] != null) {
                        type1=invite_users[0]
                        tv_name1.text = invite_users[0].username
                        tv_amount1.text = invite_users[0].self_purchase + "USDT"
                    }
                    if (invite_users[1] != null) {
                        type2=invite_users[1]
                        tv_name2.text = invite_users[1].username
                        tv_amount2.text = invite_users[1].self_purchase + "USDT"
                    }

                    if (invite_users[2] != null) {
                        type3=invite_users[2]
                        tv_name3.text = invite_users[2].username
                        tv_amount3.text = invite_users[2].self_purchase + "USDT"
                    }
                }

            }
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
    private var type1: Node.InviteUsersBean?=null
    private var type2: Node.InviteUsersBean?=null
    private var type3: Node.InviteUsersBean?=null

    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setLeftIcon(R.mipmap.back_white)
                .setTitle("我的团队")
                .setToolBarBgRescource(R.drawable.bg_hangqing)
                .setTitleColor(resources.getColor(R.color.color_white))
                .setOnLeftIconClickListener { view -> finish() }


    }

    override fun start() {
        recycle_view.layoutManager = LinearLayoutManager(this)//创建布局管理
        mAdapter = NodeAdapter(R.layout.item_node, null)
        recycle_view.adapter = mAdapter
        mAdapter!!.bindToRecyclerView(recycle_view)

        val leftRightOffset = DensityUtil.dp2px(10f)

        recycle_view.addItemDecoration(
                RewardItemDeco(
                        0,
                        0,
                        0,
                        leftRightOffset,
                        0
                )
        )


        rl_1.setOnClickListener {
            if(type1!=null){
                startActivity(Intent(this, NodeSecondActivity::class.java).putExtra("type",type1))
            }
        }

        rl_2.setOnClickListener {
            if(type2!=null){
                startActivity(Intent(this, NodeSecondActivity::class.java).putExtra("type",type2))

            }
        }
        rl_3.setOnClickListener {
            if(type3!=null){
                startActivity(Intent(this, NodeSecondActivity::class.java).putExtra("type",type3))

            }
        }
    }




}
