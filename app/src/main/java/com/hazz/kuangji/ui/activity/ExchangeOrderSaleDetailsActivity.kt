package com.hazz.kuangji.ui.activity

import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.bean.Exchange
import com.hazz.kuangji.mvp.model.bean.ExchangeOrder
import com.hazz.kuangji.mvp.presenter.ExchangeSalePresenter
import com.hazz.kuangji.utils.GlideEngine
import com.hazz.kuangji.utils.SToast
import com.hazz.kuangji.utils.ToolBarCustom
import com.hazz.kuangji.widget.PhotoDialog
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import kotlinx.android.synthetic.main.activity_exchange_order_sale_details.*
import kotlinx.android.synthetic.main.activity_exchange_sale.tv_price
import kotlinx.android.synthetic.main.rule.mToolBar
import java.io.File

class ExchangeOrderSaleDetailsActivity : BaseActivity(), IContractView.IExchangeSaleView, View.OnClickListener {

    private lateinit var price: String
    private lateinit var amount: String
    private lateinit var money: String
    private lateinit var typePay: String
    private lateinit var typeCoin: String
    private lateinit var path: String
    private var mExchangeSalePresenter = ExchangeSalePresenter(this)
    private var mPhotoDialog: PhotoDialog? = null;

    override fun getExchange(data: Exchange) {

    }

    override fun commit(data: ExchangeOrder) {
        SToast.showText("提交订单成功")
        finish()
    }

    override fun layoutId(): Int {
        return R.layout.activity_exchange_order_sale_details
    }

    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setLeftIcon(R.mipmap.back_white)
                .setTitle("卖币信息详情")
                .setToolBarBgRescource(R.drawable.bg_hangqing)
                .setTitleColor(resources.getColor(R.color.color_white))
                .setOnLeftIconClickListener { finish() }

        tv_upload.setOnClickListener(this)
        tv_commit1.setOnClickListener(this)
    }

    override fun initData() {
        price = intent.getStringExtra("price")
        money = intent.getStringExtra("money")
        amount = intent.getStringExtra("amount")
        typePay = intent.getStringExtra("typePay")
        typeCoin = intent.getStringExtra("typeCoin")

        tv_price.text = "￥" + price
        tv_num.text = amount
        tv_total.text = "￥" + money

        when (typePay) {
            "wx" -> {
                tv_payee_type.text = "微信收款"
                ll_pay.visibility = View.VISIBLE
                ll_pay_bank.visibility = View.GONE
            }
            "zfb" -> {
                tv_payee_type.text = "支付宝收款"
                ll_pay.visibility = View.VISIBLE
                ll_pay_bank.visibility = View.GONE
            }
            else -> {
                tv_payee_type.text = "银行转账"
                ll_pay.visibility = View.GONE
                ll_pay_bank.visibility = View.VISIBLE
            }
        }
    }

    override fun start() {
    }

    override fun onClick(v: View?) {
        when (v) {
            tv_upload -> {
                showPhotoDialog()
            }
            tv_commit1 -> {
                if (typePay == "bank") {
                    if (TextUtils.isEmpty(et_bank_card.text.toString())) {
                        SToast.showText("银行卡号不能为空")
                        return
                    }
                    if (TextUtils.isEmpty(et_bank_type.text.toString())) {
                        SToast.showText("开户行不能为空")
                        return
                    }
                    if (TextUtils.isEmpty(et_bank_name.text.toString())) {
                        SToast.showText("开户姓名不能为空")
                        return
                    }
                    mExchangeSalePresenter.commitOrder(price, amount,  typePay,typeCoin, et_bank_card.text.toString(), et_bank_name.text.toString(), et_bank_type.text.toString())
                } else {
                    mExchangeSalePresenter.commitOrder(path, typeCoin, amount, price, typePay)
                }

            }
        }

    }

    private fun showPhotoDialog() {
        mPhotoDialog = PhotoDialog(this)
        mPhotoDialog?.run {
            setDialogClickListener(object : PhotoDialog.DialogClickListener {
                override fun takePhoto() {
                    PictureSelector.create(this@ExchangeOrderSaleDetailsActivity)
                            .openCamera(PictureMimeType.ofImage())
                            .forResult(PictureConfig.CHOOSE_REQUEST)
                }

                override fun pickPhoto() {
                    PictureSelector.create(this@ExchangeOrderSaleDetailsActivity)
                            .openGallery(PictureMimeType.ofImage()) //全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                            .loadImageEngine(GlideEngine.createGlideEngine())
                            .imageSpanCount(3)// 每行显示个数 int
                            .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                            .isCamera(false)
                            .forResult(PictureConfig.CHOOSE_REQUEST) //结果回调onActivityResult code
                }
            })
            builder()
            show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PictureConfig.CHOOSE_REQUEST) {
            var selectList = PictureSelector.obtainMultipleResult(data)
            path = selectList?.get(0)?.path.toString()
            iv_qrcode.setImageURI(Uri.fromFile(File(path)))
        }

    }


}