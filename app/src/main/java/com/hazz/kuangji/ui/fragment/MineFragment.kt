package com.hazz.kuangji.ui.fragment

import android.content.Intent
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseFragment
import com.hazz.kuangji.mvp.contract.LoginContract
import com.hazz.kuangji.mvp.model.bean.Node
import com.hazz.kuangji.mvp.model.bean.Shenfen
import com.hazz.kuangji.mvp.model.bean.UserInfo
import com.hazz.kuangji.mvp.presenter.NodePresenter
import com.hazz.kuangji.ui.activity.*
import com.hazz.kuangji.utils.SPUtil
import kotlinx.android.synthetic.main.fragment_mine.*

class MineFragment : BaseFragment(), LoginContract.NodeView {

    override fun getShenfen(msg: Shenfen) {

        when (msg.level) {
            "总裁" -> iv_type.setImageResource(R.mipmap.zongcai)
            "初级矿商" -> iv_type.setImageResource(R.mipmap.chuji)
            "中级矿商" -> iv_type.setImageResource(R.mipmap.zhongji)
            "高级矿商" -> iv_type.setImageResource(R.mipmap.gaoji)
            "超级矿商" -> iv_type.setImageResource(R.mipmap.chaoji)
            "节点合伙人" -> iv_type.setImageResource(R.mipmap.jiedianhehuo)
            "联创合伙人" -> iv_type.setImageResource(R.mipmap.lianchuang)

        }

    }


    override fun getNode(msg: Node) {

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_mine
    }

    private var mNodePresenter: NodePresenter = NodePresenter(this)
    private var userInfo: UserInfo? = null
    override fun initView() {


        userInfo = SPUtil.getObj("user", UserInfo::class.java)

        if (userInfo != null) {

            mTvUserName.text = userInfo!!.username
            mTvMobile.text =userInfo!!.mobile
        } else {
            val userName = SPUtil.getString("username")
            val mobile = SPUtil.getString("mobile")
            mTvMobile.text =mobile
            mTvUserName.text = userName
        }



        iv_msg.setOnClickListener {
            startActivity(Intent(activity, MsgListActivity::class.java))
        }
        layout_invite.setOnClickListener {
            startActivity(Intent(activity, InviteActivity::class.java))
        }
        layout_setting.setOnClickListener {
            startActivity(Intent(activity, SettingActivity::class.java))
        }
        layout_jiedian.setOnClickListener {
            startActivity(Intent(activity, NodeActivity::class.java))
        }
        layout_team.setOnClickListener {
            startActivity(Intent(activity, IncomingActivity::class.java))
        }
        layout_contact.setOnClickListener {
            startActivity(Intent(activity,ContactMyActivity::class.java))
        }
        layout_about.setOnClickListener {
            startActivity(Intent(activity, RegistRuleActivity::class.java).putExtra("type",1))
        }

    }

    override fun lazyLoad() {
        mNodePresenter.getShenfen()
    }



}
