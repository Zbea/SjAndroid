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
import com.hazz.kuangji.mvp.model.bean.Charge
import com.hazz.kuangji.mvp.model.bean.ChargeRecord
import com.hazz.kuangji.mvp.model.bean.UserInfo
import com.hazz.kuangji.mvp.presenter.ChargePresenter
import com.hazz.kuangji.utils.*
import kotlinx.android.synthetic.main.activity_charge.*
import kotlinx.android.synthetic.main.activity_charge.mToolBar


class ChargeActivity : BaseActivity(), IContractView.ChargeView {

    private var isType: Boolean=true;

    override fun chargeRecord(msg: ChargeRecord) {

    }


    override fun getAddress(msg: Charge) {
        tv_bt.visibility=View.VISIBLE
        var address:String = if (isType) msg.addr else msg.address
        tv_address.text=address
        val dip2px = Utils.dip2px(this, 180F)
        createQRCode = QRCodeUtils.createQRCode(address, dip2px, dip2px, null)
        iv.setImageBitmap(createQRCode)

    }

    private fun setView(){
        tv_address.text=""
        iv.setImageBitmap(null)
        createQRCode?.recycle()
        createQRCode=null
        tv_bt.visibility=View.GONE
    }

    private var createQRCode:Bitmap?=null
    private var userInfo: UserInfo? = null
    override fun layoutId(): Int {
        return R.layout.activity_charge
    }

    override fun initData() {
        mChargePresenter.charge("USDT")
    }

    private var mChargePresenter: ChargePresenter = ChargePresenter(this)

    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setLeftIcon(R.mipmap.back_white)
                .setTitle(getString(R.string.charge))
                .setTitleColor(resources.getColor(R.color.color_white))
                .setRightText("充值记录")
                .setToolBarBgRescource(R.drawable.bg_hangqing)
                .setRightTextIsShow(true)
                .setOnLeftIconClickListener { view -> finish() }
                .setOnRightClickListener {
                 startActivity(Intent(this, ChargeRecordActivity::class.java))

                }
        rg_charge.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId==R.id.rb_left)
            {
                isType=true;
                setView()
                tv_usdt.text="Erc20-USDT"
                tv_usdt1.text="Erc20-USDT"
                mChargePresenter.charge("USDT")
            }
            else
            {
                isType=false;
                setView()
                tv_usdt.text="OMNI-USDT"
                tv_usdt1.text="OMNI-USDT"
                mChargePresenter.chargeOmni()
            }
        }

    }

    override fun start() {
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

                    ImageUtlis.saveBmp2Gallery(this,createQRCode,"charge_qrcode")

                } else {
                    showMissingPermissionDialog()
                }
            }

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        createQRCode?.recycle()
        createQRCode=null
    }


}
