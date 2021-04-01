package com.hazz.kuangji.ui.activity.asset

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
import com.hazz.kuangji.mvp.model.MyAsset
import com.hazz.kuangji.mvp.model.ExtractRecord
import com.hazz.kuangji.mvp.presenter.AssetPresenter
import com.hazz.kuangji.mvp.presenter.ExtractCoinPresenter
import com.hazz.kuangji.utils.*
import kotlinx.android.synthetic.main.activity_exchange_coin.*
import kotlinx.android.synthetic.main.activity_extract_coin.*
import kotlinx.android.synthetic.main.activity_extract_coin.et_num
import kotlinx.android.synthetic.main.activity_extract_coin.mToolBar


class ExtractCoinActivity : BaseActivity(), IContractView.TibiView, IContractView.AssetView,TextWatcher {

    private var mExtractCoinPresenter: ExtractCoinPresenter = ExtractCoinPresenter(this)
    private var mAssetPresenter: AssetPresenter = AssetPresenter(this)
    private var myAsset: MyAsset? = null
    private var currentName = "USDT"
    private var rateUsdt = "1"
    private var rateAmountUsdt="1"
    private var rateFil = "1"
    private var rateAmountFil="0.1"
    private var rateTrc = "1"
    private var rateAmountTrc="1"
    private var avaiableAmount = "0"
    private var min="10"
    private var max="20000"
    private var assets: List<MyAsset.AssetsBean>? = null
    private var isTrc=false
    private var num=""
    private var max_fil="0.00"

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
                min=config[0].value
                max=config[1].value
                et_num.hint = "最小:$min/最大:$max"
                rateUsdt = config[2].value
                rateAmountUsdt= config[6].value
                rateAmountFil=config[7].value
                rateFil = config[8].value
                rateTrc =config[10].value
                rateAmountTrc=config[9].value
                max_fil=config[11].value
            }
            setFee()
        }
    }

    override fun tibiRecord(msg: ExtractRecord) {

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
        num=s.toString()
        setCalculate()
    }


    override fun layoutId(): Int {
        return R.layout.activity_extract_coin
    }

    @SuppressLint("WrongConstant")
    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setTitle(getString(R.string.tibi))
                .setRightText("明细")
                .setRightTextIsShow(true)
                .setOnLeftIconClickListener { finish() }
                .setOnRightClickListener {
                    startActivity(Intent(this, ExtractCoinRecordActivity::class.java).setFlags(1))
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

        et_address.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s.isNullOrEmpty())return
                var addr=s.toString()
                isTrc=addr.subSequence(0,1)=="T"
                if (!num.isNullOrEmpty())
                    setCalculate()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        tv_bt.setOnClickListener {
            var amount=et_num.text.toString()
            if (TextUtils.isEmpty(amount)) {
                SToast.showText("请输入提币数量")
                return@setOnClickListener
            }

            if (!BigDecimalUtil.compare(max,amount))
            {
                SToast.showText("提币数量不能超过$max")
                return@setOnClickListener
            }
            if (!BigDecimalUtil.compare(amount,min))
            {
                SToast.showText("提币数量不能低于$min")
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

            if (!isTrc)
            {
                if (!BigDecimalUtil.compare(amount,getCurrentRateAmount()))
                {
                    SToast.showText("提币数量不能低于扣除费用")
                    return@setOnClickListener
                }
            }

            mExtractCoinPresenter.tibi(et_num.text.toString(), currentName, et_pwd.text.toString(), et_address.text.toString())
        }

        tv_all.setOnClickListener {
            et_num.setText(avaiableAmount)
        }

        rg_extract?.setOnCheckedChangeListener { group, checkedId ->

            if (assets==null)
                return@setOnCheckedChangeListener

            if (checkedId==R.id.rb_left)
            {
                currentName = "USDT"
                for (coin in assets!!) {
                    if (coin.coin == currentName) {
                        avaiableAmount = coin.balance
                        tv_lest.text = "可用" + coin.balance + currentName
                    }
                }
                tv_tips.visibility= View.GONE
            }
            else
            {

                currentName = "FIL"
                for (coin in assets!!) {
                    if (coin.coin == "FCOIN") {
                        avaiableAmount = coin.balance
                        tv_lest.text = "可用" + coin.balance + currentName
                    }
                }
                tv_tips.text="提币超过$max_fil FIL后将影响您的矿机产出算力"
                tv_tips.visibility= View.VISIBLE
            }
            et_num.setText("")
            tv_need.text="0"
            tv_shiji.text="0"
            setFee()
        }
    }

    /**
     * 开始计算
     */
    private fun setCalculate()
    {
        val div = BigDecimalUtil.div(getCurrentRate(), "100", 4)
        val fee=BigDecimalUtil.mul(num, div, 4)
        if (isTrc)
        {
            val div = BigDecimalUtil.div(rateTrc, "100", 4)
            val fee=BigDecimalUtil.mul(num, div, 4)
            if (BigDecimalUtil.compare(rateAmountTrc,fee))
            {
                tv_need.text = rateAmountTrc
                var surplus=BigDecimalUtil.sub(num, tv_need.text.toString(), 4)
                tv_shiji.text = if (surplus.toDouble()<0) "0" else surplus
            }
            else
            {
                tv_need.text = fee
                tv_shiji.text = BigDecimalUtil.sub(num, tv_need.text.toString(), 4)
            }
        }
        else{
            if (BigDecimalUtil.compare(getCurrentRateAmount(),fee))
            {
                tv_need.text = getCurrentRateAmount()
                var surplus=BigDecimalUtil.sub(num, tv_need.text.toString(), 4)
                tv_shiji.text = if (surplus.toDouble()<0) "0" else surplus
            }
            else
            {
                tv_need.text = fee
                tv_shiji.text = BigDecimalUtil.sub(num, tv_need.text.toString(), 4)
            }
        }
    }

    /**
     * 设置费用
     */
    private fun setFee(){
        //手续费为"+getCurrentRate()+"%,
        tv_shouxu.text =if (currentName=="USDT")
        {
           "Trc手续费最少扣除"+rateAmountTrc+"USDT ; Erc手续费最少扣除"+getCurrentRateAmount()+currentName
        }
        else
        {
            "FIL手续费最少扣除"+getCurrentRateAmount()+currentName
        }

    }

    /**
     * 获得当前rate
     */
    private fun getCurrentRate():String{
        return if (currentName=="USDT") rateUsdt else rateFil
    }

    /**
     * 获得当前币最低
     */
    private fun getCurrentRateAmount():String{
        return if (currentName=="USDT") rateAmountUsdt else rateAmountFil
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0) {
            val result = data?.getStringExtra("result")
            et_address.setText(result)
        }
    }



}
