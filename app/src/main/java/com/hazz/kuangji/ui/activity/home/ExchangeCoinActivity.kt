package com.hazz.kuangji.ui.activity.home

import android.content.Intent
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.widget.Toolbar
import com.hazz.kuangji.Constants
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.bean.Certification
import com.hazz.kuangji.mvp.model.bean.Exchange
import com.hazz.kuangji.mvp.presenter.CertificationInfoPresenter
import com.hazz.kuangji.mvp.presenter.ExchangeCoinPresenter
import com.hazz.kuangji.ui.activity.mine.MineCertificationActivity
import com.hazz.kuangji.ui.activity.mine.MineExchangePwdActivity
import com.hazz.kuangji.utils.*
import com.hazz.kuangji.widget.SafeCheckDialog
import kotlinx.android.synthetic.main.activity_exchange_coin.*
import kotlinx.android.synthetic.main.activity_exchange_coin.tv_commit
import kotlinx.android.synthetic.main.activity_rule.mToolBar
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class ExchangeCoinActivity : BaseActivity(), IContractView.IExchangeCoinView, IContractView.ICertificationInfoView {

    private var mMineExchangeCoinPresenter = ExchangeCoinPresenter(this)
    private val mCertificationInfoPresenter = CertificationInfoPresenter(this)
    private var type = "UTOF"//0为u转f ，1为f转u
    private val uStr = "USDT"
    private val fStr = "FIL"
    private var mData = Exchange()
    private var amount = "0"
    private var amountRate = ""
    private var rate = "0"
    private var max = "0"
    private var mCertification: Certification? = null
    private var mPasswordDialog: SafeCheckDialog? = null

    //验证实名认证
    override fun getCertification(certification: Certification) {
        mCertification = certification
        if (certification.status == 1) {
            SPUtil.putObj("certification", certification)
        }
    }


    override fun getExchange(data: Exchange) {
        mData = data
        max = mData.usdtNum
        rate = mData.usdtrate
        type = "UTOF"
        tv_u_f.text = "$uStr  转  $fStr"
        tv_usdt.text = uStr
        tv_price_usdt_title.text = "$uStr 单价："
        tv_price_usdt.text = mData.usdtPrice
        tv_count_usdt_title.text = "$uStr 数量："
        tv_count_usdt.text = mData.usdtNum

        tv_fil.text = fStr
        tv_price_fil_title.text = "$fStr 单价："
        tv_price_fil.text = mData.filPrice
        tv_count_fil_title.text = "$fStr 数量："
        tv_count_fil.text = mData.filNum

    }

    override fun commitCoin() {
        mMineExchangeCoinPresenter.getExchange()
        SToast.showText("兑换成功")
        et_count_usdt.setText("")
        tv_num_fil.text = ""
    }

    override fun layoutId(): Int {
        return R.layout.activity_exchange_coin
    }

    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setLeftIcon(R.mipmap.back_white)
                .setTitle("币币兑换")
                .setToolBarBgRescource(R.drawable.bg_hangqing)
                .setTitleColor(resources.getColor(R.color.color_white))
                .setOnLeftIconClickListener { finish() }
                .setRightText("兑换明细")
                .setRightTextIsShow(true)
                .setRightTextColor(resources.getColor(R.color.color_white))
                .setOnRightClickListener {
                    startActivity(Intent(this, ExchangeCoinRecordActivity::class.java))
                }

        et_count_usdt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                amount = s.toString()
                amountRate = BigDecimalUtil.mul(amount, rate)
                tv_num_fil.text = amountRate
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        ll_transition.setOnClickListener {
            et_count_usdt.setText("")
            tv_num_fil.text = ""
            if (type == "UTOF") {
                type = "FTOU"
                rate = mData.filrate
                max = mData.filNum

                tv_u_f.text = "$fStr  转  $uStr"
                tv_usdt.text = "$fStr"
                tv_price_usdt_title.text = "$fStr 单价："
                tv_price_usdt.text = mData.filPrice
                tv_count_usdt_title.text = "$fStr 数量："
                tv_count_usdt.text = mData.filNum

                tv_fil.text = "$uStr"
                tv_price_fil_title.text = "$uStr 单价："
                tv_price_fil.text = mData.usdtPrice
                tv_count_fil_title.text = "$uStr 数量："
                tv_count_fil.text = mData.usdtNum
            } else {
                type = "UTOF"
                rate = mData.usdtrate
                max = mData.usdtNum

                tv_u_f.text = "$uStr  转  $fStr"
                tv_usdt.text = "$uStr"
                tv_price_usdt_title.text = "$uStr 单价："
                tv_price_usdt.text = mData.usdtPrice
                tv_count_usdt_title.text = "$uStr 数量："
                tv_count_usdt.text = mData.usdtNum

                tv_fil.text = "$fStr"
                tv_price_fil_title.text = "$fStr 单价："
                tv_price_fil.text = mData.filPrice
                tv_count_fil_title.text = "$fStr 数量："
                tv_count_fil.text = mData.filNum
            }
        }

        tv_commit.setOnClickListener {
            if (!BigDecimalUtil.compare(max, amount)) {
                SToast.showText("兑换数量不能超过自己的库存")
                return@setOnClickListener
            }
            if (amount.toInt() <= 0) {
                SToast.showText("兑换数量必须大于0")
                return@setOnClickListener
            }

            when (mCertification?.status) {
                0 -> {
                    SToast.showText("实名认证审核中，请稍等")
                }
                1 -> {

                    if (mPasswordDialog == null) {
                        mPasswordDialog = SafeCheckDialog(this)
                                .setCancelListener { }
                                .setForgetListener {
                                    startActivity(Intent(this, MineExchangePwdActivity::class.java))
                                }
                                .setConfirmListener { _, password ->
                                    mMineExchangeCoinPresenter.commitExchangeCoin(type, amount, amountRate, Utils.encryptMD5(password))

                                }.setCancelListener {

                                }
                    }
                    mPasswordDialog?.show()
                }
                else -> {
                    SToast.showText("尚未实名认证，请前往实名认证")
                    Handler().postDelayed(Runnable {
                        val intent = Intent(this, MineCertificationActivity::class.java)
                        startActivity(intent)
                    }, 500)
                }
            }

        }

    }

    override fun initData() {
        EventBus.getDefault().register(this)
        mCertification = SPUtil.getObj("certification", Certification::class.java)
    }

    override fun start() {
        mMineExchangeCoinPresenter.getExchange()
        if (mCertification == null) {
            mCertificationInfoPresenter.getCertification()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: String) {
        if (event== Constants.CODE_CERTIFICATION_BROAD)
        {
            mCertificationInfoPresenter.getCertification()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


}