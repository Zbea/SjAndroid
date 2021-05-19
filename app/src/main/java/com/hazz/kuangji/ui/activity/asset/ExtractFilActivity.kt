package com.hazz.kuangji.ui.activity.asset

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import androidx.appcompat.widget.Toolbar
import com.hazz.kuangji.Constants
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.AssetCluster
import com.hazz.kuangji.mvp.model.AssetClusterEarningsDetails
import com.hazz.kuangji.mvp.model.ExtractRecord
import com.hazz.kuangji.mvp.presenter.AssetClusterPresenter
import com.hazz.kuangji.utils.*
import com.luck.picture.lib.tools.ToastUtils
import kotlinx.android.synthetic.main.activity_exchange_coin.*
import kotlinx.android.synthetic.main.activity_extract_coin.*
import kotlinx.android.synthetic.main.activity_extract_coin.et_num
import kotlinx.android.synthetic.main.activity_extract_coin.mToolBar
import org.greenrobot.eventbus.EventBus


class ExtractFilActivity : BaseActivity(), IContractView.IAssetClusterView {

    private val assetClusterPresenter=AssetClusterPresenter(this)
    private var assetCluster: AssetCluster? =null
    private var rateFil = "1"
    private var rateAmountFil = "0.1"
    private var avaiableAmount = "0"
    private var min = "1"
    private var max = "20000"
    private var num = ""

    override fun getCluster(msg: AssetCluster) {
        TODO("Not yet implemented")
    }

    override fun getList(item: AssetClusterEarningsDetails) {
        TODO("Not yet implemented")
    }

    override fun onSuccess() {
        EventBus.getDefault().post(Constants.CODE_CLUSTER_EXTRACT)
        ToastUtils.s(this,"提现成功,等待审核")
        finish()
    }


    override fun getExtractList(item: ExtractRecord) {
        TODO("Not yet implemented")
    }


    override fun layoutId(): Int {
        return R.layout.activity_extract_fil
    }

    @SuppressLint("WrongConstant")
    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setTitle(getString(R.string.tibi))
                .setRightText("明细")
                .setRightTextIsShow(true)
                .setOnLeftIconClickListener { finish() }
                .setOnRightClickListener {
                    startActivity(Intent(this, ExtractCoinRecordActivity::class.java).setFlags(2))
                }

        assetCluster=intent.getSerializableExtra("asset") as  AssetCluster
        avaiableAmount = assetCluster?.max_withdraw.toString()
        tv_lest.text = "可提" + avaiableAmount + "FIL"
        val config = assetCluster!!.config
        if (config != null) {
            min=config[0].value
            max=config[1].value
            et_num.hint = "最小:$min/最大:$max"
            rateAmountFil=config[2].value
            rateFil = config[3].value
        }
        tv_shouxu.text = "FIL手续费最少扣除" + rateAmountFil + "FIL"

        et_num.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                num = s.toString()
                setCalculate()
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    override fun start() {
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

        tv_all.setOnClickListener {
            et_num.setText(avaiableAmount)
        }

        tv_bt.setOnClickListener {
            var amount = et_num.text.toString()
            if (TextUtils.isEmpty(amount)) {
                SToast.showText("请输入提现数量")
                return@setOnClickListener
            }
            if (!BigDecimalUtil.compare(max, amount)) {
                SToast.showText("提现数量不能超过$max")
                return@setOnClickListener
            }
            if (!BigDecimalUtil.compare(amount, min)) {
                SToast.showText("提现数量不能低于$min")
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

            var map= HashMap<String,String>()
            map["amount"]=amount
            map["external_wallet_address"]=et_address.text.toString()
            map["trade_password"]=Utils.encryptMD5(et_pwd.text.toString())
            assetClusterPresenter.extractCluster(map)
        }

    }

    /**
     * 开始计算
     */
    private fun setCalculate() {
        val div = BigDecimalUtil.div(rateFil, "100", 8)
        val fee = BigDecimalUtil.mul(num, div, 8)
        if (BigDecimalUtil.compare(rateAmountFil, fee)) {
            tv_need.text = rateAmountFil
            var surplus = BigDecimalUtil.sub(num, tv_need.text.toString(), 8)
            tv_shiji.text = if (surplus.toDouble() < 0) "0" else surplus
        } else {
            tv_need.text = fee
            tv_shiji.text = BigDecimalUtil.sub(num, tv_need.text.toString(), 8)
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
