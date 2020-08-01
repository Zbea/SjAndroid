package com.hazz.kuangji.ui.activity.asset

import android.Manifest
import android.content.Intent
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import androidx.appcompat.widget.Toolbar
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.MyAsset
import com.hazz.kuangji.mvp.model.TibiRecord
import com.hazz.kuangji.mvp.presenter.TiBiPresenter
import com.hazz.kuangji.utils.*
import kotlinx.android.synthetic.main.activity_extract_coin.*
import kotlinx.android.synthetic.main.activity_extract_coin.et_num


class ExtractCoinActivity : BaseActivity(), IContractView.TibiView, TextWatcher {
    override fun afterTextChanged(s: Editable?) {

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        val div = BigDecimalUtil.div(rate, "100", 4)

        tv_need.text = BigDecimalUtil.mul(s.toString(), div, 2)
        tv_shiji.text = BigDecimalUtil.sub(s.toString(), tv_need.text.toString(), 2)
    }

    override fun tibiRecord(msg: TibiRecord) {

    }

    override fun tibiSucceed(msg: String) {
        SToast.showText(msg)
        finish()
    }


    override fun layoutId(): Int {
        return R.layout.activity_extract_coin
    }

    override fun initData() {
    }

    private var mTiBiPresenter: TiBiPresenter = TiBiPresenter(this)
    private var myAsset: MyAsset? = null
    private var currentName = "USDT"
    private var rate = "0.5%"
    private var avaiableAmount = "0"
    private var assets: List<MyAsset.AssetsBean>? = null

    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setTitle(getString(R.string.tibi))
                .setRightText("提币记录")
                .setRightTextIsShow(true)
                .setOnLeftIconClickListener { finish() }
                .setOnRightClickListener {
                    startActivity(Intent(this, ExtractCoinRecordActivity::class.java))

                }
        myAsset = intent.getSerializableExtra("amount") as MyAsset?
        assets = myAsset?.assets

        if (assets!=null)
        {
            for (coin in assets!!) {
                if (coin.coin == currentName) {
                    avaiableAmount = coin.balance
                    tv_lest.text = "可用" + coin.balance + currentName
                }
            }

            val config = myAsset!!.config
            if (config != null) {
                et_num.hint = "最小:" + config[0].value + "/" + "最大:" + config[1].value
                rate = config[2].value
            }

            tv_shouxu.text = "手续费为提币数量的" + config[2].value + "%"
        }


        et_num.addTextChangedListener(this)
    }

    override fun start() {
        tv_scan.setOnClickListener {
            permissionsnew!!.request(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE

            ).subscribe { permission ->
                if (permission!!) {
                    val intent = Intent(this, ScanCoinAddressActivity::class.java)
                    startActivityForResult(intent, 0)
                } else {
                    showMissingPermissionDialog()
                }
            }
        }

        tv_bt.setOnClickListener {
            if (TextUtils.isEmpty(et_num.text.toString())) {
                SToast.showText("请输入提币数量")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(et_pwd.text.toString())) {
                SToast.showText("请输入资金密码")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(et_address.text.toString())) {
                SToast.showText("请输入提币地址")
                return@setOnClickListener
            }

            mTiBiPresenter.tibi(et_num.text.toString(), currentName, et_pwd.text.toString(), et_address.text.toString())
        }

        tv_all.setOnClickListener {
            et_num.setText(avaiableAmount)
        }

        rg_extract.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId==R.id.rb_left)
            {
                currentName = "USDT"
                for (coin in assets!!) {
                    if (coin.coin == currentName) {
                        avaiableAmount = coin.balance
                        tv_lest.text = "可用" + coin.balance + currentName
                    }
                }
            }
            if (checkedId==R.id.rb_right)
            {
                currentName = "FIL"
                for (coin in assets!!) {
                    if (coin.coin == "FCOIN") {
                        avaiableAmount = coin.balance
                        tv_lest.text = "可用" + coin.balance + currentName
                    }
                    if (coin.coin == currentName) {
                        avaiableAmount = coin.balance
                        tv_lest.text = "可用" + coin.balance + currentName
                    }
                }
            }
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0) {
            val result = data?.getStringExtra("result")
            et_address.setText(result)
        }
    }

}
