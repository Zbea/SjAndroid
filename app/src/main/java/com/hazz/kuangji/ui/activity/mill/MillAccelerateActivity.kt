package com.hazz.kuangji.ui.activity.mill

import android.content.Intent
import android.os.Handler
import android.text.Editable
import android.text.Html
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.hazz.kuangji.Constants
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.AccelerateInfo
import com.hazz.kuangji.mvp.model.Asset
import com.hazz.kuangji.mvp.model.AssetCoin
import com.hazz.kuangji.mvp.presenter.AcceleratePresenter
import com.hazz.kuangji.mvp.presenter.AssetPresenter
import com.hazz.kuangji.ui.activity.home.ChargeActivity
import com.hazz.kuangji.ui.activity.mine.ChangePwdActivity
import com.hazz.kuangji.utils.*
import com.hazz.kuangji.widget.SafeCheckDialog
import kotlinx.android.synthetic.main.activity_charge.mToolBar
import kotlinx.android.synthetic.main.activity_home_rent.*
import kotlinx.android.synthetic.main.activity_mill_accelerate_rent.*
import kotlinx.android.synthetic.main.activity_mill_accelerate_rent.et_num
import kotlinx.android.synthetic.main.activity_mill_accelerate_rent.iv_product
import kotlinx.android.synthetic.main.activity_mill_accelerate_rent.tv_construction_day
import kotlinx.android.synthetic.main.activity_mill_accelerate_rent.tv_day
import kotlinx.android.synthetic.main.activity_mill_accelerate_rent.tv_gas
import kotlinx.android.synthetic.main.activity_mill_accelerate_rent.tv_gas_t
import kotlinx.android.synthetic.main.activity_mill_accelerate_rent.tv_info
import kotlinx.android.synthetic.main.activity_mill_accelerate_rent.tv_money_t
import kotlinx.android.synthetic.main.activity_mill_accelerate_rent.tv_num
import kotlinx.android.synthetic.main.activity_mill_accelerate_rent.tv_package_day
import kotlinx.android.synthetic.main.activity_mill_accelerate_rent.tv_pledge
import kotlinx.android.synthetic.main.activity_mill_accelerate_rent.tv_pledge_t
import kotlinx.android.synthetic.main.activity_mill_accelerate_rent.tv_yue
import org.greenrobot.eventbus.EventBus
import java.math.RoundingMode

/**
 * @Created by Zbea
 * @fileName MillAccelerateActivity
 * @date 2021/4/14 17:31
 * @email xiaofeng9212@126.com
 * @describe 购买加速包
 **/
class MillAccelerateActivity : BaseActivity(), IContractView.IAccelerateView, IContractView.IAssetView {

    private var id = ""
    private var usableUSDT = "0.00000000"
    private var usableFIL = "0.00000000"
    private var price = "0.00000000" //每T需补 usdt
    private var pledge = "0.00000000" //每T质押 fil
    private var gas = "0.00000000" //每Tgas fil
    private var totalFIL = "0.00000000" //所需总fil
    private var totalUSDT = "0.00000000" //总需补usdt
    private var max = "0.00000000" //最大加速多少
    private var mAssetPresenter: AssetPresenter = AssetPresenter(this)
    private var mAcceleratePresenter: AcceleratePresenter = AcceleratePresenter(this)
    private var mPasswordDialog: SafeCheckDialog? = null
    private var item: AccelerateInfo? = null

    override fun onFail(msg: String) {
        ll_accelerate.visibility = View.GONE
        SToast.showText(msg)
    }

    override fun getAccelerateInfo(item: AccelerateInfo) {
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
        max = BigDecimalUtil.mul(item?.maxBoostT,"1",2, RoundingMode.UP)
        tv_money_t.text = price
        tv_pledge_t.text = pledge
        tv_gas_t.text = gas
        tv_construction_day.text = item?.buildTerm
        tv_package_day.text = item?.sealTerm
        tv_day.text = item?.allTerm
        et_num.hint = "请输入加速算力（最大$max T）"
    }

    override fun onSuccess(msg: String) {
        EventBus.getDefault().post(Constants.CODE_BUY_BROAD)
        max = BigDecimalUtil.sub(max, et_num.text.toString(), 2)
        et_num.setText("")
        et_num.hint = "请输入加速算力（最大$max T）"
        SToast.showText("加速成功")
    }

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
            tv_yue.text = "账户余额：$usableUSDT USDT  /  $usableFIL FIL"
        }
    }

    override fun getAssetCoinList(items: List<AssetCoin>) {
        TODO("Not yet implemented")
    }

    override fun layoutId(): Int {
        return R.layout.activity_mill_accelerate_rent
    }


    override fun initView() {
        id = intent.getStringExtra("orderId")
    }

    override fun initData() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
            .setTitle("加速服务器")
            .setOnLeftIconClickListener { finish() }

        tv_all.setOnClickListener {
            et_num.setText(max)
        }

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

            if (et_num.text.toString().toFloat() > max.toFloat()) {
                SToast.showText("最多加速$max T")
                return@setOnClickListener
            }

            if (totalUSDT.toFloat() > usableUSDT.toFloat()) {
                SToast.showTextLong("账户USDT余额不足，请转入USDT")
                Handler().postDelayed(Runnable {
                    startActivity(Intent(this, ChargeActivity::class.java))
                }, 500)
                return@setOnClickListener
            }

            if (totalFIL.toFloat() > usableFIL.toFloat()) {
                SToast.showTextLong("账户FIL余额不足，请转入FIL")
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

                        var map = HashMap<String, String>()
                        map["amount"] = et_num.text.toString()
                        map["order_id"] = id
                        map["product_id"] = item?.id.toString()
                        map["trade_password"] = Utils.encryptMD5(password)
                        mAcceleratePresenter.commitAccelerate(map)

                    }.setCancelListener {
                    }
            }
            mPasswordDialog?.show()
        }
    }

    override fun start() {
        mAssetPresenter.myAsset(true)
        mAcceleratePresenter.getAccelerateInfo(id)
    }

    /**
     * 计算输入变化
     */
    private fun setCalculate(num: String) {

        totalUSDT = BigDecimalUtil.mul(num, price, 8)

        var totalPledge = BigDecimalUtil.mul(num, pledge, 8)
        var totalGas = BigDecimalUtil.mul(num, gas, 8)

        totalFIL = BigDecimalUtil.add(totalPledge, totalGas)

        tv_num.text = totalUSDT
        tv_pledge.text = totalPledge
        tv_gas.text = totalGas

    }


}
