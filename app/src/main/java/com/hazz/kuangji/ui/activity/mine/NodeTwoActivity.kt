package com.hazz.kuangji.ui.activity.mine

import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.hazz.kuangji.Constants
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.model.Node
import com.hazz.kuangji.ui.adapter.NodeTwoAdapter
import com.hazz.kuangji.utils.ToolBarCustom
import kotlinx.android.synthetic.main.activity_node.mToolBar
import kotlinx.android.synthetic.main.activity_node.recycle_view
import kotlinx.android.synthetic.main.activity_node_two.*

/**
 * @Created by Zbea
 * @fileName NodeTwoActivity
 * @date 2020/9/27 14:33
 * @email xiaofeng9212@126.com
 * @describe 团队二级子页面
 **/
class NodeTwoActivity : BaseActivity() {



    private var mAdapter: NodeTwoAdapter? = null
    private var data:Node.InviteUsersBean?=null


    override fun layoutId(): Int {
        return R.layout.activity_node_two
    }

    override fun initData() {
    }




    override fun initView() {

        data=intent.getSerializableExtra("node") as Node.InviteUsersBean

        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setTitle(data?.username+"的团队")
                .setOnLeftIconClickListener { finish() }

        Glide.with(this).load(Constants.URL_INVITE+data?.profile_img)
                .apply(RequestOptions.bitmapTransform(CircleCrop()).error(R.mipmap.icon_home_mine))
                .into(iv_header)

        tv_name.text=data?.username

        if(data?.team!=null){
            tv_team.text=if(data?.team == "0")"0.00000000" else data?.team
            tv_team_t.text=if(data?.team_t == "0")"0.00000000" else data?.team_t
        }
        if(data?.direct_purchase!=null){
            tv_direct.text=if(data?.direct_purchase == "0")"0.00000000" else data?.direct_purchase
            tv_direct_t.text=if(data?.direct_t == "0")"0.00000000" else data?.direct_t
        }
        val childrens = data?.children
        recycle_view.layoutManager = LinearLayoutManager(this)//创建布局管理
        mAdapter = NodeTwoAdapter(R.layout.item_node_two, childrens)
        recycle_view.adapter = mAdapter
        mAdapter?.bindToRecyclerView(recycle_view)

    }

    override fun start() {
    }




}
