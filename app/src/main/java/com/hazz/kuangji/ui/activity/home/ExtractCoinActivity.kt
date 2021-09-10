package com.hazz.kuangji.ui.activity.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.ExtractRecord
import com.hazz.kuangji.mvp.model.ExtractRule
import com.hazz.kuangji.mvp.model.Asset
import com.hazz.kuangji.mvp.model.AssetCoin
import com.hazz.kuangji.mvp.presenter.AssetPresenter
import com.hazz.kuangji.mvp.presenter.ExtractCoinPresenter
import com.hazz.kuangji.utils.*
import kotlinx.android.synthetic.main.activity_extract_coin.*


class ExtractCoinActivity : BaseActivity(), IContractView.IExtractView, IContractView.IAssetView, TextWatcher {

    private var mExtractCoinPresenter: ExtractCoinPresenter = ExtractCoinPresenter(this)
    private var mAssetPresenter: AssetPresenter = AssetPresenter(this)
    private var assets: List<Asset>? = null
    private var currentName = "USDT"
    private var rateUsdt = "1"
    private var rateAmountUsdt = "1"
    private var rateFil = "1"
    private var rateAmountFil = "0.1"
    private var rateTrc = "1"
    private var rateAmountTrc = "1"
    private var avaiableAmount = "0"
    private var rateAmountBzz = "0.1"
    private var rateBzz = "1"
    private var rateAmountChia = "0.1"
    private var rateChia = "1"
    private var min = "1"
    private var max = "20000"
    private var isTrc = false
    private var num = ""
    private var max_fil = "0.00"
    private var extractRule: ExtractRule? = null

    override fun myAsset(items: List<Asset>) {
        assets = items
        for (coin in items) {
            if (coin.coin == currentName) {
                avaiableAmount = coin.withdraw_max
                tv_lest.text = "可转" + coin.withdraw_max + currentName
            }
        }
    }

    override fun getAssetCoinList(items: List<AssetCoin>) {
        TODO("Not yet implemented")
    }

    override fun extractRecord(msg: ExtractRecord) {
    }

    override fun extractRule(msg: ExtractRule) {
        extractRule = msg
        val ruleBean = msg.ERC
        if (ruleBean != null) {
            min = ruleBean.amountMin
            max = ruleBean.amountMax
            rateUsdt = ruleBean.feeRate
            rateAmountUsdt = ruleBean.feeMin
        }
        val ruleFil = msg.FIL
        if (ruleFil != null) {
            rateAmountFil = ruleFil.feeMin
            rateFil = ruleFil.feeRate
            max_fil = ruleFil.amountMax
        }
        val ruleFil2 = msg.FIL2
        if (ruleFil2 != null) {
            rateAmountFil = ruleFil2.feeMin
            rateFil = ruleFil2.feeRate
            max_fil = ruleFil2.amountMax
        }
        val ruleTrc = msg.TRC
        if (ruleTrc != null) {
            rateAmountTrc = ruleTrc.feeMin
            rateTrc = ruleTrc.feeRate
        }
        val ruleBzz = msg.BZZ
        if (ruleBzz != null) {
            rateAmountBzz = ruleBzz.feeMin
            rateBzz = ruleBzz.feeRate
            rb_bzz.visibility= View.VISIBLE
        }
        else
        {
            rb_bzz.visibility= View.GONE
        }
        val ruleChia = msg.CHIA
        if (ruleChia != null) {
            rateAmountChia = ruleChia.feeMin
            rateChia = ruleChia.feeRate
            rb_chia.visibility= View.VISIBLE
        }
        else
        {
            rb_chia.visibility= View.GONE
        }

        et_num.hint = "最小:$min / 最大:$max"

    }

    override fun extractSucceed(msg: String) {
        SToast.showText(msg)
        finish()
    }

    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        num = s.toString()
        setCalculate()
    }


    override fun layoutId(): Int {
        return R.layout.activity_extract_coin
    }

    @SuppressLint("WrongConstant")
    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
            .setTitle(getString(R.string.tibi))
            .setRightOneIconIsShow(true)
            .setRightOneIcon(R.mipmap.icon_extract_record)
            .setOnLeftIconClickListener { finish() }
            .setOnRightIconClickListener {
                startActivity(
                    Intent(
                        this,
                        ExtractCoinRecordActivity::class.java
                    ).setFlags(1)
                )
            }

        et_num.addTextChangedListener(this)

        rg_extract?.setOnCheckedChangeListener { group, checkedId ->
            if (assets == null)
                return@setOnCheckedChangeListener
            avaiableAmount = "0.00000000"
            if (checkedId == R.id.rb_usdt) {
                currentName = "USDT"
                for (coin in assets!!) {
                    if (coin.coin == currentName) {
                        avaiableAmount = coin?.withdraw_max
                    }
                }
                min = extractRule?.ERC?.amountMin.toString()
                max = extractRule?.ERC?.amountMax.toString()

            } else if (checkedId == R.id.rb_bzz) {
                currentName = "BZZ"
                for (coin in assets!!) {
                    if (coin.coin == "BZZ") {
                        avaiableAmount = coin?.withdraw_max
                    }
                }
                min = extractRule?.BZZ?.amountMin.toString()
                max = extractRule?.BZZ?.amountMax.toString()
            } else if (checkedId == R.id.rb_chia) {
                currentName = "CHIA"
                for (coin in assets!!) {
                    if (coin.coin == "CHIA") {
                        avaiableAmount = coin?.withdraw_max
                    }
                }
                min = extractRule?.CHIA?.amountMin.toString()
                max = extractRule?.CHIA?.amountMax.toString()
            }
            else if (checkedId == R.id.rb_fil2) {
                currentName = "FIL2"
                for (coin in assets!!) {
                    if (coin.coin == "FIL2") {
                        avaiableAmount = coin?.withdraw_max
                    }
                }
                min = extractRule?.FIL2?.amountMin.toString()
                max = extractRule?.FIL2?.amountMax.toString()
            }
            else {
                currentName = "FIL"
                for (coin in assets!!) {
                    if (coin.coin == "FIL") {
                        avaiableAmount = coin?.withdraw_max
                    }
                }
                min = extractRule?.FIL?.amountMin.toString()
                max = extractRule?.FIL?.amountMax.toString()
            }
            et_num.hint = "最小:$min / 最大:$max"
            tv_lest.text = "可转$avaiableAmount $currentName"
            et_num.setText("")
            tv_money_fee.text = "0"
            tv_money.text = "0"
        }
    }

    override fun start() {
        mAssetPresenter.myAsset(false)
        mExtractCoinPresenter.rule( )
    }

    @SuppressLint("SetTextI18n")
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

        et_address.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) return
                var addr = s.toString()
                isTrc = addr.subSequence(0, 1) == "T"
                if (!num.isNullOrEmpty())
                    setCalculate()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        tv_bt.setOnClickListener {
            var amount = et_num.text.toString()
            if (TextUtils.isEmpty(amount)) {
                SToast.showText("请输入转出数量")
                return@setOnClickListener
            }

            if (!BigDecimalUtil.compare(max, amount)) {
                SToast.showText("转出数量不能超过$max")
                return@setOnClickListener
            }
            if (!BigDecimalUtil.compare(amount, min)) {
                SToast.showText("转出数量不能低于$min")
                return@setOnClickListener
            }


            if (TextUtils.isEmpty(et_pwd.text.toString())) {
                SToast.showText("请输入资金密码")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(et_address.text.toString())) {
                SToast.showText("请输入转出地址")
                return@setOnClickListener
            }

            if (!isTrc) {
                if (!BigDecimalUtil.compare(amount, getCurrentRateAmount())) {
                    SToast.showText("转出数量不能低于扣除费用")
                    return@setOnClickListener
                }
            }

            mExtractCoinPresenter.extract(et_num.text.toString(),
                currentName,
                et_pwd.text.toString(),
                et_address.text.toString()
            )
        }

        tv_all.setOnClickListener {
            et_num.setText(avaiableAmount)
        }

    }

    /**
     * 开始计算
     */
    private fun setCalculate() {
        val fee = BigDecimalUtil.mul(num, getCurrentRate(), 8)
        if (BigDecimalUtil.compare(getCurrentRateAmount(), fee)) {
            tv_money_fee.text = getCurrentRateAmount()
            var surplus = BigDecimalUtil.sub(num, getCurrentRateAmount(), 8)
            tv_money.text = if (surplus.toDouble() < 0) "0" else surplus
        } else {
            tv_money_fee.text = fee
            tv_money.text = BigDecimalUtil.sub(num, fee, 8)
        }
    }


    /**
     * 获得当前选中的rate
     */
    private fun getCurrentRate(): String {
        return if (currentName == "USDT") {
            if (isTrc)
            {
                rateTrc
            }
            else{
                rateUsdt
            }
        } else if (currentName == "BZZ") {
            rateBzz
        } else if (currentName == "CHIA") {
            rateChia
        } else {
            rateFil
        }
    }

    /**
     * 获得当前选中的所需最低费用
     */
    private fun getCurrentRateAmount(): String {
        return if (currentName == "USDT")
            if (isTrc)
            {
                rateAmountTrc
            }
            else{
                rateAmountUsdt
            }
        else if (currentName == "BZZ") {
            rateAmountBzz
        } else if (currentName == "CHIA") {
            rateAmountChia
        } else rateAmountFil
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0) {
            val result = data?.getStringExtra("result")
            et_address.setText(result)
        }
    }


}
