package com.hazz.kuangji.ui.activity.home

import android.view.View
import androidx.appcompat.widget.Toolbar
import com.hazz.kuangji.Constants
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.ExchangeOrder
import com.hazz.kuangji.mvp.presenter.ExchangeOrderSaleDetailsPresenter
import com.hazz.kuangji.utils.BigDecimalUtil
import com.hazz.kuangji.utils.GlideEngine
import com.hazz.kuangji.utils.SToast
import com.hazz.kuangji.utils.ToolBarCustom
import com.hazz.kuangji.widget.CommonDialog
import kotlinx.android.synthetic.main.activity_exchange_order_sale_details.*
import kotlinx.android.synthetic.main.activity_exchange_order_sale_commit.iv_qrcode
import kotlinx.android.synthetic.main.activity_exchange_order_sale_commit.ll_pay
import kotlinx.android.synthetic.main.activity_exchange_order_sale_commit.ll_pay_bank
import kotlinx.android.synthetic.main.activity_exchange_order_sale_commit.tv_num
import kotlinx.android.synthetic.main.activity_exchange_order_sale_commit.tv_payee_type
import kotlinx.android.synthetic.main.activity_exchange_order_sale_details.tv_commit
import kotlinx.android.synthetic.main.activity_rule.mToolBar
import org.greenrobot.eventbus.EventBus

class ExchangeOrderSaleDetailsActivity : BaseActivity(), IContractView.IExchangeSaleDetailsView{

    private lateinit var code: String
    private var mExchangeSalePresenter = ExchangeOrderSaleDetailsPresenter(this)

    override fun getOrder(data: ExchangeOrder) {
        tv_order_number.text = data.order_code
        tv_date.text = data.create_at
        tv_num.text = data.num+data.type.toUpperCase()
        tv_total.text = "￥" + BigDecimalUtil.mul(data.num,data.price)

        when (data.status) {
            0 -> {
                data.state = "处理中"
            }
            else -> {
                data.state = "已完成"
                ll_bottom.visibility = View.GONE
            }
        }
        tv_order_state.text = data.state

        when (data.typePay) {
            "wx" -> {
                ll_pay.visibility = View.VISIBLE
                ll_pay_bank.visibility = View.GONE
                tv_payee_type.text="微信收款"
                GlideEngine.createGlideEngine().loadImage(this, Constants.URL_INVITE+data.wx_url, iv_qrcode)
            }
            "zfb" -> {
                ll_pay.visibility = View.VISIBLE
                ll_pay_bank.visibility = View.GONE
                tv_payee_type.text="支付宝收款"
                GlideEngine.createGlideEngine().loadImage(this, Constants.URL_INVITE+data.zfb_url, iv_qrcode)
            }
            "bank" -> {
                ll_pay.visibility = View.GONE
                ll_pay_bank.visibility = View.VISIBLE
                tv_payee_type.text="银行转账"
                tv_bank_card.text = data.bank_code
                tv_bank_name.text = data.payee
                tv_bank_type.text = data.bank_name
            }
        }
    }

    override fun cancelOrder() {
        EventBus.getDefault().post("10002")
        tv_order_state.text = "已取消"
        ll_bottom.visibility = View.GONE
        SToast.showText("取消订单成功")
    }


    override fun layoutId(): Int {
        return R.layout.activity_exchange_order_sale_details
    }

    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setLeftIcon(R.mipmap.back_white)
                .setTitle("卖币订单详情")
                .setToolBarBgRescource(R.drawable.bg_main_gradient)
                .setTitleColor(resources.getColor(R.color.color_white))
                .setOnLeftIconClickListener { finish() }

        tv_commit.setOnClickListener{
            var commonDialog = CommonDialog(this)
            commonDialog.run {
                setContent("确定取消订单?")
                setDialogClickListener(object : CommonDialog.DialogClickListener {
                    override fun ok() {
                        mExchangeSalePresenter.cancelOrder(code)
                    }

                    override fun cancel() {
                    }
                })
                builder()
                show()
            }
        }
    }

    override fun initData() {
        code = intent.getStringExtra("code")
    }

    override fun start() {
        mExchangeSalePresenter.getOrder(code)
    }





}