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
import com.hazz.kuangji.mvp.presenter.AssetPresenter
import com.hazz.kuangji.mvp.presenter.ExtractCoinPresenter
import com.hazz.kuangji.utils.*
import kotlinx.android.synthetic.main.activity_extract_coin.*
import kotlinx.android.synthetic.main.activity_extract_coin.et_num


class ExtractCoinActivity : BaseActivity(), IContractView.TibiView, IContractView.AssetView,TextWatcher {

    private var mExtractCoinPresenter: ExtractCoinPresenter = ExtractCoinPresenter(this)
    private var mAssetPresenter: AssetPresenter = AssetPresenter(this)
    private var myAsset: MyAsset? = null
    private var currentName = "USDT"
    private var rate = "0.5%"
    private var rateAmount="10"
    private var avaiableAmount = "0"
    private var assets: List<MyAsset.AssetsBean>? = null

    override fun myAsset(msg: MyAsset) {
        myAsset=msg
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

            rateAmount= config[6].value
        }
    }

    override fun tibiRecord(msg: TibiRecord) {

    }

    override fun tibiSucceed(msg: String) {
        SToast.showText(msg)
        finish()
    }

    override fun afterTextChanged(s: Editable?) {
    }
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        val div = BigDecimalUtil.div(rate, "100", 4)
        val fee=BigDecimalUtil.mul(s.toString(), div, 4)
        if (BigDecimalUtil.compare(rateAmount.toString(),fee))
        {
            tv_need.text = rateAmount
            var surplus=BigDecimalUtil.sub(s.toString(), tv_need.text.toString(), 4)
            tv_shiji.text = if (surplus.toDouble()<0) "0" else surplus
        }
        else
        {
            tv_need.text = fee
            tv_shiji.text = BigDecimalUtil.sub(s.toString(), tv_need.text.toString(), 4)
        }
    }


    override fun layoutId(): Int {
        return R.layout.activity_extract_coin
    }

    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setTitle(getString(R.string.tibi))
                .setRightText("明细")
                .setRightTextIsShow(true)
                .setOnLeftIconClickListener { finish() }
                .setOnRightClickListener {
                    startActivity(Intent(this, ExtractCoinRecordActivity::class.java))
                }

        et_num.addTextChangedListener(this)
    }

    override fun start() {
        mAssetPresenter.myAsset(true)
    }

    override fun initData() {
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

            mExtractCoinPresenter.tibi(et_num.text.toString(), currentName, et_pwd.text.toString(), et_address.text.toString())
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
            else
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
            et_num.setText("")
            tv_need.text="0"
            tv_shiji.text="0"
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
