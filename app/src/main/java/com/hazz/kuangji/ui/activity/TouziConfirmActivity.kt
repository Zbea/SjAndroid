package com.hazz.kuangji.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.widget.Toolbar
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.LoginContract
import com.hazz.kuangji.mvp.model.bean.MyAsset
import com.hazz.kuangji.mvp.model.bean.Touzi
import com.hazz.kuangji.mvp.model.bean.Touzi.ProductsBean
import com.hazz.kuangji.mvp.model.bean.TouziRecord
import com.hazz.kuangji.mvp.presenter.TouziPresenter
import com.hazz.kuangji.mvp.presenter.ZichanPresenter
import com.hazz.kuangji.utils.BigDecimalUtil
import com.hazz.kuangji.utils.SToast
import com.hazz.kuangji.utils.ToolBarCustom
import com.hazz.kuangji.widget.SafeCheckDialog
import kotlinx.android.synthetic.main.invitefriends_touzi_confirm.*


class TouziConfirmActivity : BaseActivity(), LoginContract.TouziView, LoginContract.ZichanView {
    override fun touziRecord(msg: TouziRecord) {

    }

    override fun myAsset(msg: MyAsset) {
       // tv_yue.text="账号余额:"+msg.rvc+"RVC"
    }

    override fun touziList(msg: Touzi) {

    }

    override fun touziConfirm(msg: String) {
        SToast.showText(msg)
        finish()
    }


    override fun layoutId(): Int {
        return R.layout.invitefriends_touzi_confirm
    }

    override fun initData() {
        mZichanPresenter.myAsset()
    }

    private var mPasswordDialog: SafeCheckDialog? = null
    private var mTouziPresenter: TouziPresenter = TouziPresenter(this)
    private var mZichanPresenter: ZichanPresenter = ZichanPresenter(this)
    private var productsBean:ProductsBean?=null
    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setLeftIcon(R.mipmap.back_white)
                .setTitle(getString(R.string.touzi))
                .setToolBarBgRescource(R.drawable.bg_hangqing)
                .setTitleColor(resources.getColor(R.color.color_white))
                .setOnLeftIconClickListener { view -> finish() }

        tv_bt.setOnClickListener {
            if(!cb.isChecked){
                SToast.showText("请勾选条约")
                return@setOnClickListener
            }
            if (mPasswordDialog == null) {
                mPasswordDialog = SafeCheckDialog(this)
                        .setCancelListener { }
                        .setForgetListener {
                          startActivity(Intent(this,FindZijinPwdActivity::class.java))

                        }

                        .setConfirmListener { _, password ->

                            mTouziPresenter.touzi(productsBean!!.target_coin,productsBean!!.id,password)

                        }.setCancelListener {

                        }
            }
            mPasswordDialog!!.show()
        }

    }
    @SuppressLint("SetTextI18n")
    override fun start() {
        productsBean = intent.getSerializableExtra("touzi") as ProductsBean
        tv_start.text=BigDecimalUtil.mul(productsBean!!.price.toString(),"1",2)+"CNY"
        tv_daily.text=BigDecimalUtil.mul(productsBean!!.price.toString(),productsBean!!.rate,2)+"CNY"
        tv_total.text=BigDecimalUtil.mul(productsBean!!.price.toString(),productsBean!!.out,2)+"CNY"

        tv_rate.text="1CNY="+BigDecimalUtil.div(productsBean!!.coins.toString(),productsBean!!.price,2)+"RVC"
        et_amount.setText(BigDecimalUtil.mul(productsBean!!.coins.toString(),"1",4)+"RVC")
    }


}
