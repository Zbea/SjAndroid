package com.hazz.kuangji.ui.activity.mine

import android.content.Intent
import android.net.Uri
import android.os.CountDownTimer
import android.text.TextUtils
import android.util.Log
import androidx.appcompat.widget.Toolbar
import com.hazz.kuangji.Constants
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.presenter.CertificationPresenter
import com.hazz.kuangji.utils.*
import com.hazz.kuangji.widget.PhotoDialog
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import kotlinx.android.synthetic.main.activity_charge.*
import kotlinx.android.synthetic.main.activity_charge.mToolBar
import kotlinx.android.synthetic.main.activity_mine_certification.*
import org.greenrobot.eventbus.EventBus
import java.io.File

class MineCertificationActivity : BaseActivity(), IContractView.ICertificationView {

    private var mCertificationPresenter = CertificationPresenter(this)
    private var mPhone = ""
    private var countDownTimer: CountDownTimer? = null
    private var mPhotoDialog: PhotoDialog? = null
    private var frontPath: String = ""
    private var oppositePath: String = ""
    private var handPath: String = ""
    private var type = 0

    override fun sendSms(msg: String) {
        mDialog?.dismiss()
        SToast.showText(msg)
        showCountDownView()
    }

    override fun commit() {
        SToast.showText("提交成功，待审核")
        EventBus.getDefault().post(Constants.CODE_CERTIFICATION_BROAD)
        finish()
    }

    override fun layoutId(): Int {
        return R.layout.activity_mine_certification
    }

    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
            .setTitle("实名认证")
            .setOnLeftIconClickListener { finish() }

        var mobile = SPUtil.getString("mobile")
        if (!mobile.isNullOrEmpty() && mobile != "null") {
            tv_phone.setText(mobile)
        }
    }

    override fun initData() {

        tv_get_code.setOnClickListener {
            val mobile = tv_phone.text.toString()
            if (mobile.isNullOrEmpty()) {
                SToast.showText("请输入手机号")
                return@setOnClickListener
            }
            mCertificationPresenter.sendSMs(mobile)
        }

        iv_id_front.setOnClickListener {
            type = 0
            showPhotoDialog()
        }
        iv_id_opposite.setOnClickListener {
            type = 1
            showPhotoDialog()
        }

        iv_id_hand.setOnClickListener {
            type = 2
            showPhotoDialog()
        }


        btn_next.setOnClickListener {
            mPhone = et_code.text.toString()
            var name = et_name.text.toString()
            var idNumber = et_id_code.text.toString()
            var address = et_address.text.toString()
            var email = et_email.text.toString()
            if (mPhone.isNullOrEmpty()) {
                SToast.showText("请输入验证码")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(name)) {
                SToast.showText("姓名不能为空")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(idNumber)) {
                SToast.showText("身份证号不能为空")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(address)) {
                SToast.showText("现居住地址不能为空")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(email)) {
                SToast.showText("邮箱不能为空")
                return@setOnClickListener
            }

            var map = HashMap<String, String>()
            map["real_name"] = name
            map["id_num"] = idNumber
            map["address"] = address
            map["email"] = email
            map["captcha"] = mPhone
            mCertificationPresenter.commitCertification(map, frontPath, oppositePath, handPath)
        }

    }

    override fun start() {
    }


    private fun showCountDownView() {
        if (tv_get_code == null) return
        tv_get_code.isEnabled = false
        tv_get_code.isClickable = false
        countDownTimer = object : CountDownTimer(60 * 1000, 1000) {
            override fun onFinish() {
                tv_get_code?.isEnabled = true
                tv_get_code?.isClickable = true
                tv_get_code?.text = getString(R.string.mine_get_check_code)
            }

            override fun onTick(millisUntilFinished: Long) {
                tv_get_code?.text = "${millisUntilFinished / 1000}s"
            }
        }.start()

    }

    private fun showPhotoDialog() {
        mPhotoDialog = PhotoDialog(this)
        mPhotoDialog?.run {
            setDialogClickListener(object : PhotoDialog.DialogClickListener {
                override fun takePhoto() {
                    PictureSelector.create(this@MineCertificationActivity)
                        .openCamera(PictureMimeType.ofImage())
                        .isCompress(true)
                        .isEnableCrop(true)
                        .isDragFrame(true)
                        .scaleEnabled(true)
                        .minimumCompressSize(100)
                        .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                        .forResult(PictureConfig.CHOOSE_REQUEST)
                }

                override fun pickPhoto() {
                    PictureSelector.create(this@MineCertificationActivity)
                        .openGallery(PictureMimeType.ofImage()) //全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                        .loadImageEngine(GlideEngine.createGlideEngine())
                        .imageSpanCount(3)// 每行显示个数 int
                        .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                        .isCamera(false)
                        .isCompress(true)
                        .isEnableCrop(true)
                        .isDragFrame(true)
                        .scaleEnabled(true)
                        .minimumCompressSize(100)
                        .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
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
            var path = ""
            if (selectList.size > 0) {
                var media = selectList?.get(0)
                if (media != null) {
                    path = media.path
                    if (media.isCut) {
                        path = media.cutPath
                    }
                    if (media.isCompressed) {
                        path = media.compressPath
                    }
                }
                path = FileUtils.uri2String(Uri.parse(path), this).toString()
                Log.i("sj",path)
                if (path != null) {
                    when (type) {
                        0 -> {
                            frontPath = path
                            iv_id_front.setImageURI(Uri.fromFile(File(path)))
                        }
                        1 -> {
                            oppositePath = path
                            iv_id_opposite.setImageURI(Uri.fromFile(File(path)))
                        }
                        else -> {
                            handPath = path
                            iv_id_hand.setImageURI(Uri.fromFile(File(path)))
                        }
                    }
                }
            }
        }

    }


}