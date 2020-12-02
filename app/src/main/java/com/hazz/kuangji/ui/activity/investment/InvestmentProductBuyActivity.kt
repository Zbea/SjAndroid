package com.hazz.kuangji.ui.activity.investment

import android.content.Intent
import androidx.appcompat.widget.Toolbar
import com.hazz.kuangji.Constants
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Contract
import com.hazz.kuangji.mvp.model.InvestmentProduct
import com.hazz.kuangji.mvp.model.MyAsset
import com.hazz.kuangji.mvp.presenter.AssetPresenter
import com.hazz.kuangji.mvp.presenter.InvestmentProductPresenter
import com.hazz.kuangji.ui.activity.mine.MineExchangePwdActivity
import com.hazz.kuangji.utils.BigDecimalUtil
import com.hazz.kuangji.utils.SToast
import com.hazz.kuangji.utils.ToolBarCustom
import com.hazz.kuangji.utils.Utils
import com.hazz.kuangji.widget.SafeCheckDialog
import kotlinx.android.synthetic.main.activity_investment_product_buy.*
import kotlinx.android.synthetic.main.activity_investment_product_buy.tv_time
import org.greenrobot.eventbus.EventBus

/**
 * @Created by Zbea
 * @fileName InvestmentProductBuyActivity
 * @date 2020/11/25 11:03
 * @email xiaofeng9212@126.com
 * @describe 理财产品购买页面
 **/
class InvestmentProductBuyActivity : BaseActivity(),IContractView.IInvestmentProductView,IContractView.AssetView {
    private var mInvestmentProductPresenter= InvestmentProductPresenter(this)
    private var mAssetPresenter = AssetPresenter(this)
    private var mInvestmentProduct:InvestmentProduct?=null
    private var myAsset: MyAsset? = null
    private var avaiableAmount:String?=null
    private var mPasswordDialog:SafeCheckDialog?=null

    override fun getLists(items: List<InvestmentProduct>) {
    }

    override fun myAsset(msg: MyAsset) {
        myAsset=msg
        for (coin in myAsset?.assets!!) {
            if (coin.coin == "FCOIN") {
                avaiableAmount = coin.balance
                tv_balance.text="$avaiableAmount FIL"
            }
        }
    }

    override fun onSuccess() {
        SToast.showText("购买成功")
        EventBus.getDefault().post(Constants.CODE_INVESTMENT_BUY)
        finish()
    }

    override fun layoutId(): Int {
        return R.layout.activity_investment_product_buy
    }

    override fun initData() {
    }

    override fun initView() {

        mInvestmentProduct=intent.getSerializableExtra("investment") as InvestmentProduct

        ToolBarCustom.newBuilder(toolbar as Toolbar)
                .setTitle(mInvestmentProduct?.name+"(${mInvestmentProduct?.round}天)")
                .setOnLeftIconClickListener {finish() }

        tv_fee.text= BigDecimalUtil.mul(mInvestmentProduct?.rate,"100")
        tv_time.text=mInvestmentProduct?.round+"天"
        et_num.setText(mInvestmentProduct?.money)

        tv_all.setOnClickListener {
            et_num.setText(avaiableAmount)
        }

        tv_buy.setOnClickListener {

            var num=et_num.text.toString()

            if (num.isNullOrEmpty()){
                SToast.showText("请输入数量")
                return@setOnClickListener
            }

            if (!BigDecimalUtil.compare(avaiableAmount, num)) {
                SToast.showText("购买数量不能超过自己的余额")
                return@setOnClickListener
            }

            if (mPasswordDialog == null) {
                mPasswordDialog = SafeCheckDialog(this)
                        .setCancelListener { }
                        .setForgetListener {
                            startActivity(Intent(this, MineExchangePwdActivity::class.java))
                        }
                        .setConfirmListener { _, password ->
                            mInvestmentProduct?.id?.let { it1 -> mInvestmentProductPresenter.commitBuy(it1,num, Utils.encryptMD5(password)) }
                        }.setCancelListener {

                        }
            }
            mPasswordDialog?.show()

        }

    }

    override fun start() {
        mAssetPresenter.myAsset(true)
    }


}