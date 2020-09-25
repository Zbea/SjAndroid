package com.hazz.kuangji.ui.activity.asset

import android.Manifest
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Charge
import com.hazz.kuangji.mvp.model.ChargeRecord
import com.hazz.kuangji.mvp.presenter.ChargePresenter
import com.hazz.kuangji.utils.*
import kotlinx.android.synthetic.main.activity_charge.*
import kotlinx.android.synthetic.main.activity_charge.mToolBar


class ChargeActivity : BaseActivity(), IContractView.ChargeView {

    private var type=1
    private var mChargePresenter: ChargePresenter = ChargePresenter(this)
    private var createQRCode:Bitmap?=null
    private var charge:Charge?=null

    override fun chargeRecord(msg: ChargeRecord) {

    }


    override fun getAddress(msg: Charge) {
        charge=msg
        tv_bt.visibility=View.VISIBLE
        setView()
    }

    private fun setView(){
        if (charge==null)return
        var address=when(type){
            1-> charge?.ERC20
            2-> charge?.OMNI
            else -> charge?.TRC20
        }
        tv_address.text=address
        val dip2px = Utils.dip2px(this, 180F)
        createQRCode = address?.let { QRCodeUtils.createQRCode(it, dip2px, dip2px, null) }
        iv.setImageBitmap(createQRCode)
    }


    override fun layoutId(): Int {
        return R.layout.activity_charge
    }

    override fun initData() {
        StatusBarUtil.darkMode(this)
    }

    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setTitle(getString(R.string.charge))
                .setRightText("明细")
                .setRightTextIsShow(true)
                .setOnLeftIconClickListener { finish() }
                .setOnRightClickListener {
                    startActivity(Intent(this, ChargeRecordActivity::class.java))
                }
        rg_charge.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId==R.id.rb_left)
            {
                type=1
                setView()
                tv_usdt.text="Erc20-USDT"
                tv_usdt1.text="Erc20-USDT"
            }
            else if (checkedId==R.id.rb_right)
            {
                type=3
                setView()
                tv_usdt.text="Trc20-USDT"
                tv_usdt1.text="Trc20-USDT"
            }
            else
            {
                type=2
                setView()
                tv_usdt.text="OMNI-USDT"
                tv_usdt1.text="OMNI-USDT"

            }
        }

        iv_copy.setOnClickListener {

            val cm = getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
            val clipData = ClipData.newPlainText("invitation_code",tv_address.text.toString())

            cm.primaryClip = clipData

            SToast.showText("已成功复制充值地址")

        }

        tv_bt.setOnClickListener {
            permissionsnew!!.request(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE

            ).subscribe { permission ->
                if (permission!!) {

                    SToast.showText("充值二维码保存成功")

                    ImageUtils.saveBmp2Gallery(this,createQRCode,"charge_qrcode")

                } else {
                    showMissingPermissionDialog()
                }
            }

        }

    }

    override fun start() {
        mChargePresenter.charge("USDT")
    }

    override fun onDestroy() {
        super.onDestroy()
        createQRCode?.recycle()
        createQRCode=null
    }


}
