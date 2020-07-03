package com.hazz.kuangji.ui.activity.home

import android.content.Intent
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import androidx.appcompat.widget.Toolbar
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Home
import com.hazz.kuangji.mvp.model.Certification
import com.hazz.kuangji.mvp.model.MyAsset
import com.hazz.kuangji.mvp.presenter.CertificationInfoPresenter
import com.hazz.kuangji.mvp.presenter.HomePresenter
import com.hazz.kuangji.mvp.presenter.AssetPresenter
import com.hazz.kuangji.ui.activity.mine.MineExchangePwdActivity
import com.hazz.kuangji.ui.activity.RuleActivity
import com.hazz.kuangji.utils.*
import com.hazz.kuangji.widget.SafeCheckDialog
import kotlinx.android.synthetic.main.activity_charge.mToolBar
import kotlinx.android.synthetic.main.activity_home_rent.*


class HomeRentActivity : BaseActivity(), IContractView.HomeView, TextWatcher, IContractView.AssetView ,IContractView.ICertificationInfoView {

    private var mHomePresenter: HomePresenter = HomePresenter(this)
    private var rate = "0.1"
    private var coin = "USDT"
    private var id = ""
    private var price = ""
    private var amount:String="0"
    private var mAssetPresenter: AssetPresenter = AssetPresenter(this)
    private var mCertificationInfoPresenter: CertificationInfoPresenter = CertificationInfoPresenter(this)

    override fun getCertification(certification: Certification) {
        if (certification.status==1)
        {
            et_name.setText(certification.name)
            et_phone.setText(SPUtil.getString("mobile"))
            et_address.setText(certification.address)
        }
    }


    override fun myAsset(msg: MyAsset) {

        val assets = msg.assets
        if (!assets.isNullOrEmpty()) {
            for (a in assets) {
                if (a.coin == "USDT") {
                    tv_yue.text = "账户余额:" + a.balance
                }
            }
        }
    }

    override fun afterTextChanged(s: Editable?) {
        setEarningsView(s.toString())
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    override fun getHome(msg: Home) {

    }

    override fun zuyongSucceed(msg: String) {
        SToast.showText(msg)
        finish()

    }


    private var produce: Home.ProductsBean? = null


    override fun layoutId(): Int {
        return R.layout.activity_home_rent
    }

    override fun initData() {
        mAssetPresenter.myAsset()
        mCertificationInfoPresenter.getCertification()
    }

    // private var mChargePresenter: ChargePresenter = ChargePresenter(this)

    override fun initView() {

        produce = intent.getSerializableExtra("produce") as Home.ProductsBean?

        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setLeftIcon(R.mipmap.back_white)
                .setTitle(produce!!.name)
                .setTitleColor(resources.getColor(R.color.color_white))
                .setToolBarBgRescource(R.drawable.bg_blue_gradient)
                .setOnLeftIconClickListener { finish() }

        coin = produce!!.coin
        id = produce!!.id
        price = produce!!.price
        amount=BigDecimalUtil.mul(produce!!.price, "1", 4)
        tv_amount.text = BigDecimalUtil.mul(produce?.price, "1", 4) + "USDT"
        //tv_suanli.text = produce!!.power
        tv_time.text = produce!!.round + "天"
        rate = produce!!.rate

        if (produce?.rent_type == "1")
        {
            et_num.setText(amount)
            setEarningsView(amount)
            et_num.background=resources.getDrawable(R.drawable.bg_edit_solid_gray)
            et_num.isFocusable=false
            et_num.isFocusableInTouchMode=false
        }


    }

    private fun setEarningsView(s:String)
    {
        val div = BigDecimalUtil.mul(s, rate, 4)
        tv_yuji.text = "预计每日收益" + BigDecimalUtil.mul(div, "0.7", 4) + "FIL"
    }

    private var mPasswordDialog: SafeCheckDialog? = null
    override fun start() {
        tv_xieyi.setOnClickListener {

            startActivity(Intent(this, RuleActivity::class.java))
        }

        et_num.addTextChangedListener(this)

        mTvLogin.setOnClickListener {
            if (cb.isChecked) {

                if (TextUtils.isEmpty(et_num.text.toString())) {
                    SToast.showText("请输入数量")
                    return@setOnClickListener
                }

                if (produce!!.rent_type.equals("0"))
                {
                    if (et_num.text.toString().toDouble()<100)
                    {
                        SToast.showText("最低充值100USDT")
                        return@setOnClickListener
                    }
                    if (et_num.text.toString().toDouble()>amount.toDouble())
                    {
                        SToast.showText("充值不能超过$amount USDT")
                        return@setOnClickListener
                    }
                }


                if (TextUtils.isEmpty(et_name.text.toString())) {
                    SToast.showText("请输入真实姓名")
                    return@setOnClickListener
                }
                if (TextUtils.isEmpty(et_phone.text.toString())) {
                    SToast.showText("请输入手机号码")
                    return@setOnClickListener
                }
                if (TextUtils.isEmpty(et_phone.text.toString())) {
                    SToast.showText("请输入收货地址")
                    return@setOnClickListener
                }

                if (mPasswordDialog == null) {
                    mPasswordDialog = SafeCheckDialog(this)
                            .setCancelListener { }
                            .setForgetListener {
                                startActivity(Intent(this, MineExchangePwdActivity::class.java))

                            }

                            .setConfirmListener { _, password ->

                                mHomePresenter.zuyong(coin, id, password, et_num.text.toString(), et_name.text.toString(), et_phone.text.toString(), et_address.text.toString())

                            }.setCancelListener {

                            }
                }
                mPasswordDialog?.show()

            } else {
                SToast.showText("请阅读租用服务协议")
            }

        }
    }



}
