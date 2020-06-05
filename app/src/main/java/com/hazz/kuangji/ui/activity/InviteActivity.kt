package com.hazz.kuangji.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.widget.Toolbar
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.bean.Coin
import com.hazz.kuangji.mvp.model.bean.Friends
import com.hazz.kuangji.mvp.model.bean.UserInfo
import com.hazz.kuangji.mvp.presenter.CoinPresenter
import com.hazz.kuangji.net.UrlPaths.URL_INVITE
import com.hazz.kuangji.utils.*
import kotlinx.android.synthetic.main.invitefriends.*


class InviteActivity : BaseActivity(), IContractView.CoinView {
    override fun getCoin(msg: List<Coin>) {

    }

    override fun getFriends(msg: Friends) {
        invitedCodeTextView.text="已邀请好友"+msg.number+"人"
    }


    private var createQRCode: Bitmap? = null
    private var mCoinPresenter: CoinPresenter = CoinPresenter(this)
    private  var userInfo:UserInfo?=null
    private  var invitation_code=""
    private  var invitation_code_url=""

    override fun layoutId(): Int {
        return R.layout.invitefriends
    }

    override fun initData() {
        mCoinPresenter.getFriends()
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setLeftIcon(R.mipmap.back_white)
                .setTitle(getString(R.string.invite_friends))
                .setTitleColor(resources.getColor(R.color.color_white))
                .setRightText("邀请记录")
                .setRightTextIsShow(true)
                .setOnLeftIconClickListener { view -> finish() }
                .setOnRightClickListener {
                    startActivity(Intent(this,InviteRecordActivity::class.java))

                }
        userInfo = SPUtil.getObj("user", UserInfo::class.java)
        invitation_code = SPUtil.getString("invitation_code")
        invitation_code_url = SPUtil.getString("invitation_code_url")
        val dip2px = Utils.dip2px(this, 180F)
       if(userInfo!=null){
           tipsText.text="邀请码:   "+userInfo!!.invitation_code

           createQRCode =
                   QRCodeUtils.createQRCode(URL_INVITE+"#/home?code="+userInfo!!.invitation_code, dip2px, dip2px, null)
           qrCodeView.setImageBitmap(createQRCode)
       }else{
           tipsText.text= "邀请码:   $invitation_code"
           createQRCode =
                   QRCodeUtils.createQRCode("$URL_INVITE#/home?code=$invitation_code", dip2px, dip2px, null)
           qrCodeView.setImageBitmap(createQRCode)
       }



    }

    override fun start() {
        copy_invitation_btn.setOnClickListener {
            val cm = getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
            if(userInfo!=null){
                val clipData = ClipData.newPlainText("invitation_code_url",URL_INVITE+"#/home?code="+userInfo!!.invitation_code)
                cm.primaryClip = clipData
            }else{
                val clipData = ClipData.newPlainText("invitation_code_url", "$URL_INVITE#/home?code=$invitation_code")
                cm.primaryClip = clipData
            }



            SToast.showText("已成功复制邀请链接")
        }


        tv_copy.setOnClickListener {
            val cm = getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
            if(userInfo!=null){
                val clipData = ClipData.newPlainText("invitation_code",userInfo!!.invitation_code)
                cm.primaryClip = clipData
            }else{
                val clipData = ClipData.newPlainText("invitation_code",invitation_code)
                cm.primaryClip = clipData
            }


            SToast.showText("已成功复制邀请码")
        }



        tv_down.setOnClickListener {
            permissionsnew!!.request(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE

            ).subscribe { permission ->
                if (permission!!) {

                    SToast.showText("图片保存成功")

                    ImageUtlis.saveBmp2Gallery(this,createQRCode,"qrcode")

                } else {
                    showMissingPermissionDialog()
                }
            }

        }

    }


}
