package com.hazz.kuangji.ui.activity.mine

import android.Manifest
import android.annotation.SuppressLint
import android.content.ClipData
import android.content.Context
import android.graphics.Bitmap
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.hazz.kuangji.Constants
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Invite
import com.hazz.kuangji.mvp.presenter.InvitePresenter
import com.hazz.kuangji.ui.adapter.InviteAdapter
import com.hazz.kuangji.utils.*
import kotlinx.android.synthetic.main.activity_mine_invitefriends.*


class InviteActivity : BaseActivity(), IContractView.IInviteView {

    private var createQRCode: Bitmap? = null
    private var mCoinPresenter: InvitePresenter = InvitePresenter(this)
    private var invitation_code = ""
    private var invitation_code_url = ""
    private var mAdapter: InviteAdapter? = null


    override fun getInviteRecord(lists: List<Invite>) {
        mAdapter?.setNewData(lists)
    }


    override fun layoutId(): Int {
        return R.layout.activity_mine_invitefriends
    }

    override fun initData() {
        StatusBarUtil.darkMode(this,false)
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        ToolBarCustom.newBuilder(toolbar as Toolbar)
            .setTitle(getString(R.string.invite_friends))
            .setOnLeftIconClickListener { finish() }

        invitation_code = SPUtil.getString("invitation_code")
        invitation_code_url = Constants.URL_INVITE_ADDRESS+ invitation_code

        tipsText.text = "您的专属邀请码:$invitation_code"

        val dip2px = Utils.dip2px(this, 180F)
        createQRCode = QRCodeUtils.createQRCode(invitation_code_url, dip2px, dip2px, null)
        qrCodeView.setImageBitmap(createQRCode)

        copy_invitation_btn.setOnClickListener {
            val cm = getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
            val clipData = ClipData.newPlainText("invitation_code_url", invitation_code_url)
            cm.primaryClip = clipData
            SToast.showText("已成功复制邀请链接")
        }

        rc_list.layoutManager = LinearLayoutManager(this)//创建布局管理
        mAdapter = InviteAdapter(R.layout.item_invite_record, null)
        rc_list.adapter = mAdapter
        mAdapter?.bindToRecyclerView(rc_list)

        tv_down.setOnClickListener {
            permissionsnew!!.request(
                Manifest.permission.WRITE_EXTERNAL_STORAGE

            ).subscribe { permission ->
                if (permission!!) {

                    SToast.showText("图片保存成功")

                    ImageUtils.saveBmp2Gallery(this, createQRCode, "qrcode")

                } else {
                    showMissingPermissionDialog()
                }
            }

        }

    }

    override fun start() {
        mCoinPresenter.getInvite()
    }


}
