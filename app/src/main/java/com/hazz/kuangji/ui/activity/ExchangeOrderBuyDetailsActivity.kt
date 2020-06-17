package com.hazz.kuangji.ui.activity

import android.Manifest
import android.content.ClipData
import android.content.Context
import android.graphics.Bitmap
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.bean.ExchangeOrder
import com.hazz.kuangji.mvp.presenter.ExchangeOrderBuyDetailsPresenter
import com.hazz.kuangji.utils.GlideEngine
import com.hazz.kuangji.utils.ImageUtlis
import com.hazz.kuangji.utils.SToast
import com.hazz.kuangji.utils.ToolBarCustom
import com.hazz.kuangji.widget.CommonDialog
import kotlinx.android.synthetic.main.activity_exchange_order_details.*
import kotlinx.android.synthetic.main.rule.mToolBar

class ExchangeOrderBuyDetailsActivity : BaseActivity(), IContractView.IExchangeOrderBuyView, View.OnClickListener {

    private lateinit var code: String
    private lateinit var mData: ExchangeOrder
    private lateinit var bitmap: Bitmap
    private var mExchangeOrderBuyPresenter = ExchangeOrderBuyDetailsPresenter(this)

    override fun getOrder(data: ExchangeOrder) {
        mData = data
        tv_order_number.text = mData.order_code
        tv_order_date.text = mData.create_at
        tv_price.text = "￥" + mData.price
        tv_num.text = mData.num+mData.typcoin
        tv_price_total.text = "￥" + mData.money

        tv_commit.visibility = if (mData.is_pay == 0) View.VISIBLE else View.GONE

        when (mData.status) {
            0 -> {
                if (mData.is_pay==0)
                {
                    mData.state = "待付款"
                    ll_bottom.visibility = View.VISIBLE
                }
                else
                {
                    mData.state = "处理中"
                    ll_bottom.visibility = View.GONE
                }
            }
            else -> {
                mData.state = "已完成"
                ll_bottom.visibility = View.GONE
            }
        }
        tv_order_state.text = mData.state

        var requestOptions=RequestOptions()
        requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE)
        requestOptions.skipMemoryCache(true)

        when (mData.pay_type) {
            "wx" -> {
                ll_pay.visibility = View.VISIBLE
                ll_pay_bank.visibility = View.GONE
                tv_pay_title.text = "微信付款二维码"

                Glide.with(this).asBitmap().load(mData.wx).apply(requestOptions).into(object : SimpleTarget<Bitmap?>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap?>?) {
                        bitmap=resource
                        iv_qrcode.setImageBitmap(resource)
                    }
                })
            }
            "zfb" -> {
                ll_pay.visibility = View.VISIBLE
                ll_pay_bank.visibility = View.GONE
                tv_pay_title.text = "支付宝付款二维码"

                Glide.with(this).asBitmap().load(mData.zfb).apply(requestOptions).into(object : SimpleTarget<Bitmap?>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap?>?) {
                        bitmap=resource
                        iv_qrcode.setImageBitmap(resource)
                    }
                })
            }
            "bank" -> {
                ll_pay.visibility = View.GONE
                ll_pay_bank.visibility = View.VISIBLE

                tv_bank_card.text = mData.banknum
                tv_bank_name.text = mData.username
                tv_bank_typr.text = mData.bankname
            }
        }

    }

    override fun cancelOrder() {
        SToast.showText("取消订单成功")
        ll_bottom.visibility=View.GONE
    }

    override fun commitPay() {
        ll_bottom.visibility = View.GONE
        mData.state = "处理中"
        mData.status = 1
        mData.is_pay = 1
        SToast.showText("付款成功,请耐心等待后台审核")
    }

    override fun layoutId(): Int {
        return R.layout.activity_exchange_order_details
    }

    override fun initView() {

        code = intent.getStringExtra("code")

        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setLeftIcon(R.mipmap.back_white)
                .setTitle("买币订单详情")
                .setToolBarBgRescource(R.drawable.bg_hangqing)
                .setTitleColor(resources.getColor(R.color.color_white))
                .setOnLeftIconClickListener { view -> finish() }

        tv_cancel.setOnClickListener(this)
        tv_commit.setOnClickListener(this)
        tv_bank_card.setOnClickListener(this)
        tv_qrcode_save.setOnClickListener(this)

    }

    override fun initData() {
    }

    override fun start() {
        mExchangeOrderBuyPresenter.getOrder(code)
    }

    override fun onClick(v: View?) {
        when (v) {
            tv_commit -> mExchangeOrderBuyPresenter.commitPay(code)
            tv_cancel -> {
                var commonDialog = CommonDialog(this)
                commonDialog?.run {
                    setContent("确定取消订单?")
                    setDialogClickListener(object : CommonDialog.DialogClickListener {
                        override fun ok() {
                            mExchangeOrderBuyPresenter.cancelOrder(code)
                        }

                        override fun cancel() {
                        }
                    })
                    builder()
                    show()
                }
            }
            tv_bank_card -> {
                val cm = getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
                val clipData = ClipData.newPlainText("bank_code", tv_bank_card.text.toString())
                cm.primaryClip = clipData
                SToast.showText("已成功复制充值地址")
            }
            tv_qrcode_save ->
            {
                permissionsnew!!.request(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE

                ).subscribe { permission ->
                    if (permission!!) {

                        SToast.showText("付款二维码保存成功")

                        ImageUtlis.saveBmp2Gallery(this,bitmap,if (mData.pay_type=="wx") "微信付款码" else "支付宝付款码" )

                    } else {
                        showMissingPermissionDialog()
                    }
                }
            }
        }
    }




}