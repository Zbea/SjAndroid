package com.hazz.kuangji.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.hazz.kuangji.Constants
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseFragment
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Certification
import com.hazz.kuangji.mvp.presenter.CertificationPresenter
import com.hazz.kuangji.utils.FileUtils
import com.hazz.kuangji.utils.GlideEngine
import com.hazz.kuangji.utils.SToast
import com.hazz.kuangji.widget.PhotoDialog
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import kotlinx.android.synthetic.main.fragment_mine_certification_one.btn_next
import kotlinx.android.synthetic.main.fragment_mine_certification_two.*
import org.greenrobot.eventbus.EventBus
import java.io.File

class MineCertificationTwoFragment : BaseFragment(), View.OnClickListener, IContractView.ICertificationView{

    private var mCertificationPresenter= CertificationPresenter(this)
    private var CERTIFICATION: String = "certification"
    private lateinit var mCertification: Certification
    private var mPhotoDialog: PhotoDialog? = null
    private var frontPath: String = ""
    private var oppositePath: String = ""
    private var handPath: String = ""
    private var type = 0


    override fun sendSms(msg: String) {
        TODO("Not yet implemented")
    }

    override fun commit() {
        SToast.showText("提交成功，待审核")
        activity?.finish()
        EventBus.getDefault().post(Constants.CODE_CERTIFICATION_BROAD)
    }


    public fun newInstance(certification: Certification): MineCertificationTwoFragment {
        var bundle = Bundle()
        bundle.putSerializable(CERTIFICATION, certification);
        var mineCertificationOneFragment = MineCertificationTwoFragment();
        mineCertificationOneFragment.arguments = bundle;
        return mineCertificationOneFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCertification= arguments?.getSerializable(CERTIFICATION) as Certification
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_mine_certification_two
    }

    override fun initView() {

        rl_id_front.setOnClickListener(this)
        rl_id_opposite.setOnClickListener(this)
        rl_id_hand.setOnClickListener(this)
        btn_next.setOnClickListener(this)

    }

    override fun lazyLoad() {
    }

    override fun onClick(v: View?) {
        when (v) {
            rl_id_front -> {
                type =0
                showPhotoDialog()
            }
            rl_id_opposite -> {
                type = 1
                showPhotoDialog()
            }
            rl_id_hand -> {
                type = 2
                showPhotoDialog()
            }
            btn_next -> {

                if (TextUtils.isEmpty(frontPath)) {
                    SToast.showText("请上传身份证正面照")
                    return
                }
                if (TextUtils.isEmpty(oppositePath)) {
                    SToast.showText("请上传身份证反面照")
                    return
                }
                if (TextUtils.isEmpty(handPath)) {
                    SToast.showText("请上传手持身份证照")
                    return
                }

                var map=HashMap<String,String>()
                map["name"]= mCertification.name
                map["idNumber"]= mCertification.idNumber
                map["address"]=mCertification.address
                map["email"]=mCertification.email
                map["code"]=mCertification.code
                mCertificationPresenter.commitCertification(map,frontPath,oppositePath,handPath)

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
                            .isCompress(true)
                            .isEnableCrop(true)
                            .isDragFrame(true)
                            .scaleEnabled(true)
                            .minimumCompressSize(100)
                            .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                            .forResult(PictureConfig.CHOOSE_REQUEST)
                }

                override fun pickPhoto() {
                    PictureSelector.create(activity)
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
                path = FileUtils.uri2String(Uri.parse(path), activity!!).toString()

                if (path != null) {
                    when(type)
                    {
                        0->{
                            frontPath = path
                            iv_id_front.setImageURI(Uri.fromFile(File(path)))
                        }
                        1->{
                            oppositePath = path
                            iv_id_opposite.setImageURI(Uri.fromFile(File(path)))
                        }
                        else->{
                            handPath = path
                            iv_id_hand.setImageURI(Uri.fromFile(File(path)))
                        }
                    }
                }
            }
        }

    }




}