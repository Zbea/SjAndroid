package com.hazz.kuangji.ui.activity.mine

import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Coin
import com.hazz.kuangji.mvp.model.Friends
import com.hazz.kuangji.mvp.presenter.CoinPresenter
import com.hazz.kuangji.ui.adapter.FriendsAdapter
import com.hazz.kuangji.utils.*
import kotlinx.android.synthetic.main.activity_invitefriends_record.*


class InviteRecordActivity : BaseActivity(), IContractView.CoinView {
    override fun getCoin(msg: List<Coin>) {

    }

    override fun getFriends(msg: Friends) {
        tv_num.text="已邀请好友"+msg.number+"人"
        mAdapter!!.setNewData(msg.invite_users)
    }



    private var mCoinPresenter: CoinPresenter = CoinPresenter(this)

    override fun layoutId(): Int {
        return R.layout.activity_invitefriends_record
    }

    override fun initData() {
        mCoinPresenter.getFriends()
    }


    private var mAdapter: FriendsAdapter?=null
    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setTitle(getString(R.string.invite_friends_record))
                .setOnLeftIconClickListener { finish() }


        recycle_view.layoutManager = LinearLayoutManager(this)//创建布局管理
        mAdapter = FriendsAdapter(R.layout.item_friends, null)
        recycle_view.adapter = mAdapter
        mAdapter?.bindToRecyclerView(recycle_view)
        mAdapter?.setEmptyView(R.layout.fragment_empty)

    }
    override fun start() {


    }


}
