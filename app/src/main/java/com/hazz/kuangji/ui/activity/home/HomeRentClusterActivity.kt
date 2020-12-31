package com.hazz.kuangji.ui.activity.home

import android.content.Intent
import android.os.Handler
import android.text.Html
import android.text.TextUtils
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.appcompat.widget.Toolbar
import com.hazz.kuangji.Constants
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.*
import com.hazz.kuangji.mvp.presenter.CertificationInfoPresenter
import com.hazz.kuangji.mvp.presenter.ClusterPresenter
import com.hazz.kuangji.mvp.presenter.ExchangeCoinPresenter
import com.hazz.kuangji.ui.activity.RuleActivity
import com.hazz.kuangji.ui.activity.mine.MineExchangePwdActivity
import com.hazz.kuangji.utils.*
import com.hazz.kuangji.widget.SafeCheckDialog
import kotlinx.android.synthetic.main.activity_charge.mToolBar
import kotlinx.android.synthetic.main.activity_home_rent.*
import kotlinx.android.synthetic.main.activity_home_rent_cluster.*
import kotlinx.android.synthetic.main.activity_home_rent_cluster.cb
import kotlinx.android.synthetic.main.activity_home_rent_cluster.et_address
import kotlinx.android.synthetic.main.activity_home_rent_cluster.et_name
import kotlinx.android.synthetic.main.activity_home_rent_cluster.et_phone
import kotlinx.android.synthetic.main.activity_home_rent_cluster.iv_product
import kotlinx.android.synthetic.main.activity_home_rent_cluster.mTvLogin
import kotlinx.android.synthetic.main.activity_home_rent_cluster.tv_amount
import kotlinx.android.synthetic.main.activity_home_rent_cluster.tv_info
import kotlinx.android.synthetic.main.activity_home_rent_cluster.tv_xieyi
import kotlinx.android.synthetic.main.activity_home_rent_cluster.tv_yue
import org.greenrobot.eventbus.EventBus


class HomeRentClusterActivity : BaseActivity(), IContractView.IExchangeCoinView, IContractView.ICertificationInfoView, IContractView.IClusterView {

    private var count = "0"
    private var mCertificationInfoPresenter: CertificationInfoPresenter = CertificationInfoPresenter(this)
    private var mMineExchangeCoinPresenter = ExchangeCoinPresenter(this)
    private var mClusterPresenter = ClusterPresenter(this)
    private var cluster: Cluster? = null
    private var id: String? = null
    private var mPasswordDialog: SafeCheckDialog? = null

    override fun getCertification(certification: Certification) {
        if (certification.status == 1) {
            et_name.setText(certification.name)
            et_phone.setText(SPUtil.getString("mobile"))
            et_address.setText(certification.address)
        }
    }

    override fun getExchange(data: Exchange) {
        if (data != null) {
            count = data?.usdtNum
            tv_yue.text = "账户余额:$count"
        }
    }

    override fun commitCoin() {
    }

    override fun getLists(item: Cluster) {
        cluster = item
        for ((id, c) in item.products.withIndex()) {
            var radioButton = LayoutInflater.from(this).inflate(R.layout.template_radiobutton, null) as RadioButton
            radioButton.text = c.name
            radioButton.id = id
            radioButton.isChecked = id == 0

            val rlp: LinearLayout.LayoutParams = LinearLayout.LayoutParams(250, LinearLayout.LayoutParams.MATCH_PARENT)
            radioButton.layoutParams = rlp

            rg_cluster.addView(radioButton)
        }
        id = item.products[0].id
        tv_amount.text = item.products[0].price
        tv_info.text = Html.fromHtml(item.products[0].desc)
        item.products[0]?.pic?.let { GlideEngine.createGlideEngine().loadImage(this, Constants.URL_INVITE + it, iv_product) }
    }

    override fun getLists(item: ClusterNode) {
        TODO("Not yet implemented")
    }

    override fun getEarningsLists(item: ClusterEarnings) {
    }

    override fun onSuccess() {
        SToast.showText("集群租用成功")
        EventBus.getDefault().post(Constants.CODE_CLUSTER_BUY)
        finish()
    }


    override fun layoutId(): Int {
        return R.layout.activity_home_rent_cluster
    }

    override fun initView() {

        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setTitle(intent.getStringExtra("clusters"))
                .setOnLeftIconClickListener { finish() }

    }

    override fun initData() {

        rg_cluster.setOnCheckedChangeListener { group, checkedId ->
            id = cluster?.products?.get(checkedId)?.id
            tv_amount.text = cluster?.products?.get(checkedId)?.price
            tv_info.text = Html.fromHtml(cluster?.products?.get(checkedId)?.desc)
            cluster?.products?.get(checkedId)?.pic?.let { GlideEngine.createGlideEngine().loadImage(this, Constants.URL_INVITE + it, iv_product) }
        }

        tv_xieyi.setOnClickListener {

            startActivity(Intent(this, RuleActivity::class.java))
        }

        mTvLogin.setOnClickListener {
            if (cb.isChecked) {

                if (tv_amount.text.toString().toDouble() > count.toDouble()) {
                    SToast.showTextLong("账户余额不足，请前往充值")
                    Handler().postDelayed(Runnable {
                        startActivity(Intent(this, ExchangeBuyActivity::class.java))
                    }, 500)
                    return@setOnClickListener
                }

                if (et_name.text.toString().isNullOrEmpty()) {
                    SToast.showText("请输入真实姓名")
                    return@setOnClickListener
                }
                if (et_phone.text.toString().isNullOrEmpty()) {
                    SToast.showText("请输入手机号码")
                    return@setOnClickListener
                }
                if (et_address.text.toString().isNullOrEmpty()) {
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
                                var map = HashMap<String, String>()
                                map["contractor"] = et_name.text.toString()
                                map["mobile"] = et_phone.text.toString()
                                map["address"] = et_address.text.toString()
                                map["product_id"] = id.toString()
                                map["trade_password"] = Utils.encryptMD5(password)
                                mClusterPresenter.commitCluster(map)
                            }
                }
                mPasswordDialog?.show()
            } else {
                SToast.showText("请阅读租用服务协议")
            }
        }
    }


        override fun start() {
            mMineExchangeCoinPresenter.getExchange()
            mCertificationInfoPresenter.getCertification()
            mClusterPresenter.getCluster()
        }


    }
