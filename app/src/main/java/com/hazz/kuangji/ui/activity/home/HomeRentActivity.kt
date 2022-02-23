package com.hazz.kuangji.ui.activity.home

import android.content.Intent
import android.os.Handler
import android.text.Editable
import android.text.Html
import android.text.TextUtils
import android.text.TextWatcher
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.hazz.kuangji.Constants
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Asset
import com.hazz.kuangji.mvp.model.AssetCoin
import com.hazz.kuangji.mvp.model.Home
import com.hazz.kuangji.mvp.model.MinerFILInfo
import com.hazz.kuangji.mvp.presenter.AssetPresenter
import com.hazz.kuangji.mvp.presenter.MinerRentPresenter
import com.hazz.kuangji.ui.activity.AgreementActivity
import com.hazz.kuangji.ui.activity.mine.ChangePwdActivity
import com.hazz.kuangji.utils.*
import com.hazz.kuangji.widget.SafeCheckDialog
import kotlinx.android.synthetic.main.activity_charge.mToolBar
import kotlinx.android.synthetic.main.activity_home_rent.*
import kotlinx.android.synthetic.main.activity_home_rent.et_num
import kotlinx.android.synthetic.main.activity_home_rent.iv_product
import kotlinx.android.synthetic.main.activity_home_rent.tv_construction_day
import kotlinx.android.synthetic.main.activity_home_rent.tv_day
import kotlinx.android.synthetic.main.activity_home_rent.tv_gas
import kotlinx.android.synthetic.main.activity_home_rent.tv_gas_t
import kotlinx.android.synthetic.main.activity_home_rent.tv_info
import kotlinx.android.synthetic.main.activity_home_rent.tv_money_t
import kotlinx.android.synthetic.main.activity_home_rent.tv_num
import kotlinx.android.synthetic.main.activity_home_rent.tv_package_day
import kotlinx.android.synthetic.main.activity_home_rent.tv_pledge
import kotlinx.android.synthetic.main.activity_home_rent.tv_pledge_t
import kotlinx.android.synthetic.main.activity_home_rent.tv_yue
import kotlinx.android.synthetic.main.activity_mill_accelerate_rent.*
import org.greenrobot.eventbus.EventBus


class HomeRentActivity : BaseActivity(), IContractView.IMinerRentView, IContractView.IAssetView {

    private var mHomePresenter: MinerRentPresenter = MinerRentPresenter(this)
    private var id = ""
    private var usableUSDT = "0.00000000"
    private var usableFIL = "0.00000000"
    private var price = "0.00000000" //每T单价 usdt
    private var pledge = "0.00000000" //每T质押币 fil
    private var gas = "0.00000000" //每Tgas fil
    private var totalFIL = "0.00000000" //所需总fil
    private var totalUSDT = "0.00000000" //所需总usdt
    private var mAssetPresenter: AssetPresenter = AssetPresenter(this)
    private var mPasswordDialog: SafeCheckDialog? = null
    private var item:MinerFILInfo?=null

    override fun myAsset(msg: List<Asset>) {
        if (msg != null) {
            for (coin in msg) {
                if (coin.coin == "USDT") {
                    usableUSDT = coin.amount
                }
                if (coin.coin == "FIL") {
                    usableFIL = coin.amount
                }
            }
            tv_yue.text = "賬戶余額：$usableUSDT USDT  /  $usableFIL FIL"
        }
    }

    override fun getAssetCoinList(items: List<AssetCoin>) {
        TODO("Not yet implemented")
    }

    override fun getInfo(item: MinerFILInfo) {
        this.item = item

        ToolBarCustom.newBuilder(mToolBar as Toolbar)
            .setTitle(item?.name)

        Glide.with(this)
            .load(Constants.URL_BASE + item.img)
            .apply(RequestOptions().transform(RoundedCorners(30)))
            .into(iv_product)

        if (item?.detail != null)
            tv_info.text = Html.fromHtml(item?.detail)

        price = item?.usdtPrice
        pledge = item?.pledgePrice
        gas = item?.gasPrice
        tv_money_t.text = price
        tv_pledge_t.text = pledge
        tv_gas_t.text = gas
        tv_construction_day.text = item?.buildTerm
        tv_package_day.text = item?.sealTerm
        tv_day.text = item?.allTerm
    }

    override fun onSucceed(msg: String) {
        EventBus.getDefault().post(Constants.CODE_BUY_BROAD)
        SToast.showText("購買成功")
        if (mDialog != null) mDialog?.dismiss()
        finish()
    }


    override fun layoutId(): Int {
        return R.layout.activity_home_rent
    }


    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setOnLeftIconClickListener { finish() }
        id = intent.getStringExtra("produceId")
        if (id==null)return
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

        tv_xieyi.setOnClickListener {
            startActivity(Intent(this, AgreementActivity::class.java).setFlags(1))
        }

        mTvLogin.setOnClickListener {
            if (cb.isChecked) {

                if (TextUtils.isEmpty(et_num.text.toString())) {
                    SToast.showText("請輸入數量")
                    return@setOnClickListener
                }


                if (et_num.text.toString().toFloat()<1) {
                    SToast.showText("最少購買1T")
                    return@setOnClickListener
                }

                if (totalUSDT.toFloat() > usableUSDT.toFloat()) {
                    SToast.showTextLong("賬戶USDT余額不足，請轉入USDT")
                    Handler().postDelayed(Runnable {
                        startActivity(Intent(this, ChargeActivity::class.java))
                    }, 500)
                    return@setOnClickListener
                }

                if (totalFIL.toFloat() > usableFIL.toFloat()) {
                    SToast.showTextLong("賬戶FIL余額不足，請轉入FIL")
                    Handler().postDelayed(Runnable {
                        startActivity(Intent(this, ChargeActivity::class.java))
                    }, 500)
                    return@setOnClickListener
                }

                if (mPasswordDialog == null) {
                    mPasswordDialog = SafeCheckDialog(this)
                            .setCancelListener { }
                            .setForgetListener {
                                startActivity(Intent(this, ChangePwdActivity::class.java))
                            }
                            .setConfirmListener { _, password ->
                                mHomePresenter.buyRent(id, password, et_num.text.toString())
                            }.setCancelListener {

                            }
                }
                mPasswordDialog?.show()

            } else {
                SToast.showText("請閱讀租用服務協議")
            }

        }
    }

    override fun start() {
        mAssetPresenter.myAsset(true)
        mHomePresenter.getInfo(id)
    }

    /**
     * 计算输入变化
     */
    private fun setCalculate(num: String) {

        totalUSDT=BigDecimalUtil.mul(num,price,8)

        var totalPledge=BigDecimalUtil.mul(num,pledge,8)
        var totalGas=BigDecimalUtil.mul(num,gas,8)

        totalFIL=BigDecimalUtil.add(totalPledge,totalGas)

        tv_num.text=totalUSDT
        tv_pledge.text=totalPledge
        tv_gas.text=totalGas

    }



}
