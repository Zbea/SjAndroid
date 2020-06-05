package com.hazz.kuangji.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.appcompat.widget.Toolbar
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.bean.MyAsset
import com.hazz.kuangji.mvp.model.bean.TibiRecord
import com.hazz.kuangji.mvp.presenter.TiBiPresenter
import com.hazz.kuangji.utils.*
import kotlinx.android.synthetic.main.coin_choose.view.*
import kotlinx.android.synthetic.main.invitefriends.mToolBar
import kotlinx.android.synthetic.main.tibi.*


class TibiActivity : BaseActivity(), IContractView.TibiView, TextWatcher {
    override fun afterTextChanged(s: Editable?) {

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        val div = BigDecimalUtil.div(rate, "100", 4)

        tv_need.text = BigDecimalUtil.mul(s.toString(), div, 2)
        tv_shiji.text = BigDecimalUtil.sub(s.toString(), tv_need.text.toString(), 2)
    }

    override fun tibiRecord(msg: TibiRecord) {

    }

    override fun tibiSucceed(msg: String) {
        SToast.showText(msg)
        finish()
    }


    override fun layoutId(): Int {
        return R.layout.tibi
    }

    override fun initData() {

    }

    private var mTiBiPresenter: TiBiPresenter = TiBiPresenter(this)
    private var myAsset: MyAsset? = null
    private var popWnd: PopupWindow? = null
    private var currentName = "USDT"
    private var rate = "0.5%"
    private var avaiableAmount = "0"
    private var assets: List<MyAsset.AssetsBean>? = null

    @SuppressLint("SetTextI18n")
    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setLeftIcon(R.mipmap.back_white)
                .setTitle(getString(R.string.tibi))
                .setTitleColor(resources.getColor(R.color.color_white))
                .setRightText("提币记录")
                .setToolBarBgRescource(R.drawable.bg_hangqing)
                .setRightTextIsShow(true)
                .setOnLeftIconClickListener { view -> finish() }
                .setOnRightClickListener {
                    startActivity(Intent(this, TibiRecordActivity::class.java))

                }
        myAsset = intent.getSerializableExtra("amount") as MyAsset?
        assets = myAsset!!.assets

        for (coin in assets!!) {
            if (coin.coin == currentName) {
                avaiableAmount = coin.balance
                tv_lest.text = "可用" + coin.balance + currentName
            }
        }
        //
        val config = myAsset!!.config
        if (config != null) {
            et_num.hint = "最小:" + config[0].value + "/" + "最大:" + config[1].value
            rate = config[2].value
        }

        tv_shouxu.text = "手续费为提币数量的" + config[2].value + "%"

        et_num.addTextChangedListener(this)
    }

    override fun start() {
        tv_scan.setOnClickListener {
            permissionsnew!!.request(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE

            ).subscribe { permission ->
                if (permission!!) {
                    val intent = Intent(this, ScanCoinAddressActivity::class.java)
                    startActivityForResult(intent, 0)
                } else {
                    showMissingPermissionDialog()
                }
            }
        }

        tv_bt.setOnClickListener {
            if (TextUtils.isEmpty(et_num.text.toString())) {
                SToast.showText("请输入提币数量")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(et_pwd.text.toString())) {
                SToast.showText("请输入资金密码")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(et_address.text.toString())) {
                SToast.showText("请输入提币地址")
                return@setOnClickListener
            }

            mTiBiPresenter.tibi(et_num.text.toString(), currentName, et_pwd.text.toString(), et_address.text.toString())
        }

        tv_all.setOnClickListener {
            et_num.setText(avaiableAmount)
        }

        rl_choose.setOnClickListener {
            Log.d("junjun", "点击")
            showPop()
        }
    }


    @SuppressLint("SetTextI18n")
    private fun showPop() {
        val contentView = LayoutInflater.from(this).inflate(R.layout.coin_choose, null, false)
        popWnd = PopupWindow(
                contentView,
                rl_choose.measuredWidth, ViewGroup.LayoutParams.WRAP_CONTENT, true
        )
        contentView.tv1.setOnClickListener {
            currentName = "USDT"
            et_coin.text = "USDT"
            popWnd?.dismiss()

            for (coin in assets!!) {
                if (coin.coin == currentName) {
                    avaiableAmount = coin.balance
                    tv_lest.text = "可用" + coin.balance + currentName
                }
            }
        }
        contentView.tv2.setOnClickListener {
            currentName = "FIL"
            et_coin.text = "FIL"
            popWnd?.dismiss()

            for (coin in assets!!) {
                if (coin.coin == "FCOIN") {
                    avaiableAmount = coin.balance
                    tv_lest.text = "可用" + coin.balance + currentName
                }
                if (coin.coin == currentName) {
                    avaiableAmount = coin.balance
                    tv_lest.text = "可用" + coin.balance + currentName
                }
            }
        }
        popWnd?.isTouchable = true
        popWnd?.isOutsideTouchable = true
        popWnd?.showAsDropDown(rl_choose, 0, 0, Gravity.LEFT)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0) {
            val result = data?.getStringExtra("result")
            et_address.setText(result)
        }
    }

}
