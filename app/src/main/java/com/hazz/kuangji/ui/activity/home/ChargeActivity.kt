package com.hazz.kuangji.ui.activity.home

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
import kotlinx.android.synthetic.main.activity_charge.tv_bt
import kotlinx.android.synthetic.main.activity_extract_coin.*


class ChargeActivity : BaseActivity(), IContractView.ChargeView {

    private var type = 0
    private var mChargePresenter: ChargePresenter = ChargePresenter(this)
    private var createQRCode: Bitmap? = null
    private var charge: Charge? = null

    override fun chargeRecord(msg: ChargeRecord) {

    }


    override fun getAddress(msg: Charge) {
        charge = msg
        tv_bt.visibility = View.VISIBLE
        type=1

        if (msg.BZZ==null){
            rb_3.visibility=View.GONE
        }
        else{
            rb_3.visibility=View.VISIBLE
        }

        if (msg.CHIA==null){
            rb_4.visibility=View.GONE
        }
        else{
            rb_4.visibility=View.VISIBLE
        }
        setView()
    }

    override fun layoutId(): Int {
        return R.layout.activity_charge
    }

    override fun initData() {
    }

    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setTitle(getString(R.string.charge))
                .setRightOneIconIsShow(true)
            .setRightOneIcon(R.mipmap.icon_extract_record)
                .setOnLeftIconClickListener { finish() }
            .setOnRightIconClickListener {
                startActivity(Intent(this, ChargeRecordActivity::class.java))
            }
        rg_charge.setOnCheckedChangeListener { group, checkedId ->

            when (checkedId) {
                R.id.rb_1 -> {
                    rg_usdt.visibility=View.VISIBLE
                    rg_usdt.check(R.id.rb_trc)
                    type=1
                    setView()
                }
                R.id.rb_2 -> {
                    type = 3
                    rg_usdt.visibility=View.GONE
                    setView()
                }
                R.id.rb_2 -> {
                    type = 4
                    rg_usdt.visibility=View.GONE
                    setView()
                }
                else -> {
                    type = 5
                    rg_usdt.visibility=View.GONE
                    setView()
                }
            }
        }

        rg_usdt.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_trc -> {
                    type = 1
                    setView()

                }
                R.id.rb_erc -> {
                    type = 2
                    setView()
                }
            }
        }

        btn_copy.setOnClickListener {

            val cm = getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
            val clipData = ClipData.newPlainText("address", tv_address.text.toString())

            cm.primaryClip = clipData

            SToast.showText("已成功复制转入地址")

        }

        tv_bt.setOnClickListener {
            permissionsnew!!.request(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE

            ).subscribe { permission ->
                if (permission!!) {

                    SToast.showText("二维码保存成功")

                    ImageUtils.saveBmp2Gallery(this, createQRCode, "charge_qrcode")

                } else {
                    showMissingPermissionDialog()
                }
            }

        }

    }

    override fun start() {
        mChargePresenter.charge()
    }

    private fun setView() {
        if (charge == null) return
        var address = when (type) {
            1 -> charge?.TRC
            2 -> charge?.ERC
            3 -> charge?.FIL
            4 -> charge?.BZZ
            5 -> charge?.CHIA
            else -> ""
        }
        tv_address.text = address
        val dip2px = Utils.dip2px(this, 180F)
        createQRCode = address?.let { QRCodeUtils.createQRCode(it, dip2px, dip2px, null) }
        iv.setImageBitmap(createQRCode)
    }


    override fun onDestroy() {
        super.onDestroy()
        createQRCode?.recycle()
        createQRCode = null
    }


}
