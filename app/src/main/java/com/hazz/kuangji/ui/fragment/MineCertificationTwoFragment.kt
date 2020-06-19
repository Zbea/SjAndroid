package com.hazz.kuangji.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.text.TextUtils
import android.view.View
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseFragment
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.bean.Certification
import com.hazz.kuangji.mvp.model.bean.UserInfo
import com.hazz.kuangji.mvp.presenter.CertificationPresenter
import com.hazz.kuangji.utils.GlideEngine
import com.hazz.kuangji.utils.SPUtil
import com.hazz.kuangji.utils.SToast
import com.hazz.kuangji.widget.PhotoDialog
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import kotlinx.android.synthetic.main.activity_exchange_order_sale_commit.*
import kotlinx.android.synthetic.main.fragment_mine_certification_one.*
import kotlinx.android.synthetic.main.fragment_mine_certification_one.btn_next
import kotlinx.android.synthetic.main.fragment_mine_certification_one.tv_get_code
import kotlinx.android.synthetic.main.fragment_mine_certification_two.*
import java.io.File

class MineCertificationTwoFragment : BaseFragment(), View.OnClickListener {

    private var mPhotoDialog: PhotoDialog? = null;
    private var frontPath: String = ""
    private var oppositePath: String = ""
    private var isFront: Boolean = true

    public fun newInstance(): MineCertificationTwoFragment {
//        var bundle = Bundle()
//        bundle.putString(PHONE, phone);
        var mineCertificationOneFragment = MineCertificationTwoFragment();
//        mineCertificationOneFragment.arguments = bundle;
        return mineCertificationOneFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        mPhone= arguments?.getString(PHONE).toString()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_mine_certification_two
    }

    override fun initView() {

        iv_id_front.setOnClickListener(this)
        iv_id_opposite.setOnClickListener(this)
        btn_next.setOnClickListener(this)

    }

    override fun lazyLoad() {
    }

    override fun onClick(v: View?) {
        when (v) {
            iv_id_front -> {
                isFront = true
                showPhotoDialog()
            }
            iv_id_opposite -> {
                isFront = false
                showPhotoDialog()
            }
            btn_next -> {
                var name = et_name.text.toString()
                var idNumber = et_id_code.text.toString()
                var address = et_address.text.toString()
                var email = et_email.text.toString()

                if (TextUtils.isEmpty(frontPath)) {
                    SToast.showText("请上传身份证正面照")
                    return
                }
                if (TextUtils.isEmpty(oppositePath)) {
                    SToast.showText("请上传身份证反面照")
                    return
                }
                if (TextUtils.isEmpty(name)) {
                    SToast.showText("姓名不能为空")
                    return
                }
                if (TextUtils.isEmpty(idNumber)) {
                    SToast.showText("身份证号不能为空")
                    return
                }
                if (TextUtils.isEmpty(address)) {
                    SToast.showText("现居住地址不能为空")
                    return
                }
                if (TextUtils.isEmpty(email)) {
                    SToast.showText("邮箱不能为空")
                    return
                }

                var mCertification=Certification()
                mCertification.name=name
                mCertification.idNumber=idNumber
                mCertification.address=address
                mCertification.email=email
                mCertification.front=frontPath
                mCertification.opposite=oppositePath

                fragmentManager?.beginTransaction()
                        ?.replace(R.id.fl_content, MineCertificationThreeFragment().newInstance(mCertification))
                        ?.addToBackStack(null)
                        ?.commitAllowingStateLoss()
            }
        }
    }

    private fun showPhotoDialog() {
        mPhotoDialog = PhotoDialog(activity)
        mPhotoDialog?.run {
            setDialogClickListener(object : PhotoDialog.DialogClickListener {
                override fun takePhoto() {
                    PictureSelector.create(activity)
                            .openCamera(PictureMimeType.ofImage())
                            .forResult(PictureConfig.CHOOSE_REQUEST)
                }

                override fun pickPhoto() {
                    PictureSelector.create(activity)
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
            if (selectList.size > 0) {
                var path = selectList?.get(0)?.path
                if (path != null) {
                    if (isFront) {
                        frontPath = path
                        iv_id_front.setImageURI(Uri.fromFile(File(path)))
                    } else {
                        oppositePath = path
                        iv_id_opposite.setImageURI(Uri.fromFile(File(path)))
                    }
                }
            }
        }

    }


}