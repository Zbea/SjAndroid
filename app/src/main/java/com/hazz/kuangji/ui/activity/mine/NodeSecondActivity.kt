package com.hazz.kuangji.ui.activity.mine

import androidx.recyclerview.widget.LinearLayoutManager
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.model.Node
import com.hazz.kuangji.ui.adapter.NodeSecondAdapter
import com.hazz.kuangji.widget.RewardItemDeco
import com.scwang.smartrefresh.layout.util.DensityUtil
import kotlinx.android.synthetic.main.activity_node_second.*


class NodeSecondActivity : BaseActivity() {



    override fun layoutId(): Int {
        return R.layout.activity_node_second
    }

    override fun initData() {

    }

    private var mAdapter: NodeSecondAdapter? = null

    private var node: Node.InviteUsersBean?=null
    override fun initView() {
        recycle_view.layoutManager = LinearLayoutManager(this)//创建布局管理
        mAdapter = NodeSecondAdapter(R.layout.item_node, null)
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
        node= intent.getSerializableExtra("type") as Node.InviteUsersBean?

        when(node!!.level){
            "0"-> iv_type.setImageResource(R.mipmap.v0)
            "1"-> iv_type.setImageResource(R.mipmap.v1)
            "2"-> iv_type.setImageResource(R.mipmap.v2)
            "3"-> iv_type.setImageResource(R.mipmap.v3)
            "4"-> iv_type.setImageResource(R.mipmap.v4)
            "5"-> iv_type.setImageResource(R.mipmap.v5)
            "6"-> iv_type.setImageResource(R.mipmap.v6)
            "7"-> iv_type.setImageResource(R.mipmap.v7)
        }
        mTvUserName.text=node!!.username
        tv_person.text=node!!.self_purchase
        tv_yeji.text=node!!.direct_purchase


        val invite_users = node!!.children
        if (!invite_users.isNullOrEmpty()) {
            if (invite_users.size > 3) {
                if (invite_users[0] != null) {

                    tv_name1.text = invite_users[0].username
                    tv_amount1.text = invite_users[0].self_purchase + "USDT"
                }
                if (invite_users[1] != null) {

                    tv_name2.text = invite_users[1].username
                    tv_amount2.text = invite_users[1].self_purchase + "USDT"
                }
                if (invite_users[2] != null) {

                    tv_name3.text = invite_users[2].username
                    tv_amount3.text = invite_users[2].self_purchase + "USDT"
                }
                val subList = invite_users.subList(3, invite_users.size )
                mAdapter!!.setNewData(subList)
            } else {
                if(invite_users.size==1){
                    if (invite_users[0] != null) {

                        tv_name1.text = invite_users[0].username
                        tv_amount1.text = invite_users[0].self_purchase + "USDT"
                    }
                }
                if(invite_users.size==2){
                    if (invite_users[0] != null) {

                        tv_name1.text = invite_users[0].username
                        tv_amount1.text = invite_users[0].self_purchase + "USDT"
                    }
                    if (invite_users[1] != null) {

                        tv_name2.text = invite_users[1].username
                        tv_amount2.text = invite_users[1].self_purchase + "USDT"
                    }
                }
                if(invite_users.size==3){
                    if (invite_users[0] != null) {

                        tv_name1.text = invite_users[0].username
                        tv_amount1.text = invite_users[0].self_purchase + "USDT"
                    }
                    if (invite_users[1] != null) {

                        tv_name2.text = invite_users[1].username
                        tv_amount2.text = invite_users[1].self_purchase + "USDT"
                    }

                    if (invite_users[2] != null) {

                        tv_name3.text = invite_users[2].username
                        tv_amount3.text = invite_users[2].self_purchase + "USDT"
                    }
                }

            }
        }
    }

    override fun start() {


        iv_back.setOnClickListener {
            finish()
        }
    }


}
