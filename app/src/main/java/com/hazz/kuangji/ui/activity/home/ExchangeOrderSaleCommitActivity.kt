package com.hazz.kuangji.ui.activity.home

import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.hazz.kuangji.Constants
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Exchange
import com.hazz.kuangji.mvp.model.ExchangeOrder
import com.hazz.kuangji.mvp.presenter.ExchangeSalePresenter
import com.hazz.kuangji.utils.FileUtils
import com.hazz.kuangji.utils.GlideEngine
import com.hazz.kuangji.utils.SToast
import com.hazz.kuangji.utils.ToolBarCustom
import com.hazz.kuangji.widget.PhotoDialog
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import kotlinx.android.synthetic.main.activity_exchange_order_sale_commit.*
import kotlinx.android.synthetic.main.activity_exchange_order_sale_commit.iv_qrcode
import kotlinx.android.synthetic.main.activity_exchange_order_sale_commit.ll_pay
import kotlinx.android.synthetic.main.activity_exchange_order_sale_commit.ll_pay_bank
import kotlinx.android.synthetic.main.activity_exchange_order_sale_commit.tv_num
import kotlinx.android.synthetic.main.activity_exchange_order_sale_commit.tv_payee_type
import kotlinx.android.synthetic.main.activity_exchange_order_sale_commit.tv_total
import kotlinx.android.synthetic.main.activity_exchange_order_sale_commit.tv_upload
import kotlinx.android.synthetic.main.activity_exchange_sale.tv_price
import kotlinx.android.synthetic.main.activity_rule.mToolBar
import org.greenrobot.eventbus.EventBus
import java.io.File

class ExchangeOrderSaleCommitActivity : BaseActivity(), IContractView.IExchangeSaleView, View.OnClickListener {

    private var price="0"
    private var amount="0"
    private var money="0"
    private var typePay="wx"
    private var typeCoin="usdt"
    private lateinit var data: Exchange
    private var oldPath=""
    private var path=""
    private var mExchangeSalePresenter = ExchangeSalePresenter(this)
    private var mPhotoDialog: PhotoDialog? = null;

    override fun getExchange(data: Exchange) {

    }

    override fun commit(data: ExchangeOrder) {
        EventBus.getDefault().post(Constants.CODE_EXCHANGE_SALE)
        var intent=Intent(this, ExchangeOrderSaleDetailsActivity::class.java)
        intent.putExtra("code",data.order_code)
        startActivity(intent)
        finish()
    }

    override fun layoutId(): Int {
        return R.layout.activity_exchange_order_sale_commit
    }

    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setLeftIcon(R.mipmap.back_white)
                .setTitle("卖币信息确认")
                .setToolBarBgRescource(R.drawable.bg_blue_gradient)
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
        data= intent.getSerializableExtra("exchange") as Exchange

        tv_price.text = "￥" + price
        tv_num.text = amount
        tv_total.text = "￥" + money

        when (typePay) {
            "wx" -> {
                tv_payee_type.text = "微信收款"
                ll_pay.visibility = View.VISIBLE
                ll_pay_bank.visibility = View.GONE
                if (!TextUtils.isEmpty(data.wxUrl))
                {
                    oldPath=data.wxUrl
                    GlideEngine.createGlideEngine().loadImage(this, Constants.URL_INVITE+data.wxUrl, iv_qrcode)
                }
            }
            "zfb" -> {
                tv_payee_type.text = "支付宝收款"
                ll_pay.visibility = View.VISIBLE
                ll_pay_bank.visibility = View.GONE
                if (!TextUtils.isEmpty(data.zfbUrl))
                {
                    oldPath=data.zfbUrl
                    GlideEngine.createGlideEngine().loadImage(this, Constants.URL_INVITE+data.zfbUrl, iv_qrcode)
                }
            }
            else -> {
                tv_payee_type.text = "银行转账"
                ll_pay.visibility = View.GONE
                ll_pay_bank.visibility = View.VISIBLE
                if (!TextUtils.isEmpty(data.bankCode))
                {
                    et_bank_card.setText(data.bankCode)
                    et_bank_name.setText(data.payee)
                    et_bank_type.setText(data.bankName)
                }
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
                var password=et_password.text.toString()
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
                    if (TextUtils.isEmpty(password))
                    {
                        SToast.showText("资金密码不能为空")
                        return
                    }
                    mExchangeSalePresenter.commitOrder(price, amount, typePay,typeCoin, et_bank_card.text.toString(), et_bank_name.text.toString(), et_bank_type.text.toString(),password)
                } else {
                    if (TextUtils.isEmpty(path)) {
                        if (TextUtils.isEmpty(oldPath))
                        {
                            SToast.showText("请上传二维码")
                        }
                        else
                        {
                            if (TextUtils.isEmpty(password))
                            {
                                SToast.showText("资金密码不能为空")
                                return
                            }
                            mExchangeSalePresenter.commitOrder(path,oldPath,typeCoin, amount, price, typePay,password)
                        }
                    }
                    else
                    {
                        if (password.isEmpty())
                        {
                            SToast.showText("资金密码不能为空")
                            return
                        }
                        oldPath=""
                        mExchangeSalePresenter.commitOrder(path,oldPath,typeCoin, amount, price, typePay,password)
                    }

                }

            }
        }

    }

    private fun showPhotoDialog() {
        mPhotoDialog = PhotoDialog(this)
        mPhotoDialog?.run {
            setDialogClickListener(object : PhotoDialog.DialogClickListener {
                override fun takePhoto() {
                    PictureSelector.create(this@ExchangeOrderSaleCommitActivity)
                            .openCamera(PictureMimeType.ofImage())
                            .isCompress(true)
                            .minimumCompressSize(100)
                            .compressQuality(45)
                            .forResult(PictureConfig.CHOOSE_REQUEST)
                }

                override fun pickPhoto() {
                    PictureSelector.create(this@ExchangeOrderSaleCommitActivity)
                            .openGallery(PictureMimeType.ofImage()) //全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                            .loadImageEngine(GlideEngine.createGlideEngine())
                            .imageSpanCount(3)// 每行显示个数 int
                            .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                            .isCamera(false)
                            .isCompress(true)
                            .minimumCompressSize(100)
                            .compressQuality(45)
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
            if (selectList.size>0)
            {
                var media = selectList?.get(0)
                if (media != null) {
                    path = if (media.isCompressed) {
                        media.compressPath
                    } else {
                        media.path
                    }
                }
                path= FileUtils.uri2String(Uri.parse(path),this).toString()
                iv_qrcode.setImageURI(Uri.fromFile(File(path)))
            }
        }

    }

}