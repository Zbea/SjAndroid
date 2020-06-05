package com.hazz.kuangji.ui.activity

import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.bean.Coin
import com.hazz.kuangji.mvp.model.bean.Friends
import com.hazz.kuangji.mvp.presenter.CoinPresenter
import com.hazz.kuangji.ui.adapter.FriendsAdapter
import com.hazz.kuangji.utils.*
import com.hazz.kuangji.widget.RewardItemDeco
import com.scwang.smartrefresh.layout.util.DensityUtil
import kotlinx.android.synthetic.main.invitefriends_record.*


class InviteRecordActivity : BaseActivity(), IContractView.CoinView {
    override fun getCoin(msg: List<Coin>) {

    }

    override fun getFriends(msg: Friends) {
        tv_num.text="已邀请好友"+msg.number+"人"
        mAdapter!!.setNewData(msg.invite_users)
    }



    private var mCoinPresenter: CoinPresenter = CoinPresenter(this)

    override fun layoutId(): Int {
        return R.layout.invitefriends_record
    }

    override fun initData() {
        mCoinPresenter.getFriends()
    }


    private var mAdapter: FriendsAdapter?=null
    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setLeftIcon(R.mipmap.back_white)
                .setTitle(getString(R.string.invite_friends_record))
                .setToolBarBgRescource(R.drawable.bg_hangqing)
                .setTitleColor(resources.getColor(R.color.color_white))
                .setOnLeftIconClickListener { view -> finish() }


        recycle_view.layoutManager = LinearLayoutManager(this)//创建布局管理
        mAdapter = FriendsAdapter(R.layout.item_friends, null)
        recycle_view.adapter = mAdapter
        mAdapter!!.bindToRecyclerView(recycle_view)
        mAdapter!!.setEmptyView(R.layout.fragment_empty)
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

    }
    override fun start() {


    }


}
