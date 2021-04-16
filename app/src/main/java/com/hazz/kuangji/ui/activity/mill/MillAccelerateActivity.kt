package com.hazz.kuangji.ui.activity.mill

import android.content.Intent
import android.os.Handler
import android.text.Editable
import android.text.Html
import android.text.TextUtils
import android.text.TextWatcher
import androidx.appcompat.widget.Toolbar
import com.hazz.kuangji.Constants
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Home
import com.hazz.kuangji.mvp.model.MyAsset
import com.hazz.kuangji.mvp.presenter.AssetPresenter
import com.hazz.kuangji.ui.activity.mine.MineExchangePwdActivity
import com.hazz.kuangji.ui.activity.asset.ChargeActivity
import com.hazz.kuangji.ui.activity.mine.ContractDetailsActivity
import com.hazz.kuangji.utils.*
import com.hazz.kuangji.widget.SafeCheckDialog
import kotlinx.android.synthetic.main.activity_charge.mToolBar
import kotlinx.android.synthetic.main.activity_exchange_coin.*
import kotlinx.android.synthetic.main.activity_mill_accelerate_rent.*


/**
 * @Created by Zbea
 * @fileName MillAccelerateActivity
 * @date 2021/4/14 17:31
 * @email xiaofeng9212@126.com
 * @describe 购买加速包
 **/
class MillAccelerateActivity : BaseActivity(), IContractView.HomeView, IContractView.AssetView {


    private var id = ""
    private var usableUSDT = "0.00000000"
    private var usableFIL = "0.00000000"
    private var price = "0.00000000" //每T需补 usdt
    private var pledge = "0.00000000" //每T质押币 fil
    private var gas = "0.00000000" //每Tgas fil
    private var totalFIL = "0.00000000" //所需总fil
    private var totalUSDT = "0.00000000" //总需补usdt
    private var max = "0.00000000" //最大加速多少
    private var mAssetPresenter: AssetPresenter = AssetPresenter(this)
    private var produce: Home.ProductsBean? = null
    private var mPasswordDialog: SafeCheckDialog? = null

    override fun myAsset(msg: MyAsset) {
        if (msg != null) {
            val assets = msg.assets
            for (coin in assets) {
                if (coin.coin == "USDT") {
                    usableUSDT = coin.balance
                }
                if (coin.coin == "FCOIN") {
                    usableFIL = coin.balance
                }
            }
            tv_yue.text = "账户余额：$usableUSDT USDT  /  $usableFIL FIL"
        }
    }

    override fun getHome(msg: Home) {
    }

    override fun zuyongSucceed(msg: String) {
        SToast.showText("加速成功，请及时为合同签名")
        startActivity(Intent(this, ContractDetailsActivity::class.java).putExtra("contract_code", msg)
                .putExtra("contract_sign", "0"))
        if (mDialog != null) mDialog?.dismiss()
        finish()
    }


    override fun layoutId(): Int {
        return R.layout.activity_mill_accelerate_rent
    }


    override fun initView() {

        produce = intent.getSerializableExtra("produce") as Home.ProductsBean?

        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setTitle(produce?.name.toString())
                .setOnLeftIconClickListener { finish() }

        id = produce?.id.toString()
        produce?.pic?.let { GlideEngine.createGlideEngine().loadImage(this, Constants.URL_INVITE + it, iv_product) }
        if (produce?.desc != null)
            tv_info.text = Html.fromHtml(produce?.desc)

        tv_money_t.text = price
        tv_pledge_t.text = pledge
        tv_gas_t.text = gas

    }

    override fun initData() {

        et_num.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                setCalculate(s.toString())
            }
        })

        tv_accelerate.setOnClickListener {

            if (TextUtils.isEmpty(et_num.text.toString())) {
                SToast.showText("请输入数量")
                return@setOnClickListener
            }

            if (et_num.text.toString().toFloat() < 1) {
                SToast.showText("最少购买1T")
                return@setOnClickListener
            }

            if (et_num.text.toString().toFloat()>max.toFloat()) {
                SToast.showText("最多加速$max T")
                return@setOnClickListener
            }

            if (totalUSDT.toFloat() > usableUSDT.toFloat()) {
                SToast.showTextLong("账户USDT余额不足，请前往充值")
                Handler().postDelayed(Runnable {
                    startActivity(Intent(this, ChargeActivity::class.java))
                }, 500)
                return@setOnClickListener
            }

            if (totalFIL.toFloat() > usableFIL.toFloat()) {
                SToast.showTextLong("账户FIL余额不足，请前往充值")
                Handler().postDelayed(Runnable {
                    startActivity(Intent(this, ChargeActivity::class.java))
                }, 500)
                return@setOnClickListener
            }

            if (mPasswordDialog == null) {
                mPasswordDialog = SafeCheckDialog(this)
                        .setCancelListener { }
                        .setForgetListener {
                            startActivity(Intent(this, MineExchangePwdActivity::class.java))
                        }
                        .setConfirmListener { _, password ->

                        }.setCancelListener {

                        }
            }
            mPasswordDialog?.show()


        }
    }

    override fun start() {
        mAssetPresenter.myAsset(true)
    }

    /**
     * 计算输入变化
     */
    private fun setCalculate(num: String) {

        totalUSDT = BigDecimalUtil.mul(num, price, 8)

        var totalPledge = BigDecimalUtil.mul(num, pledge, 8)
        var totalGas = BigDecimalUtil.mul(num, gas, 8)

        totalFIL = BigDecimalUtil.add(totalPledge, totalGas)

        tv_money.text = totalUSDT
        tv_pledge.text = totalPledge
        tv_gas.text = totalGas

    }

}
