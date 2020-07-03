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
import kotlinx.android.synthetic.main.fragment_mine_certification_three.*
import org.greenrobot.eventbus.EventBus
import java.io.File

class MineCertificationThreeFragment : BaseFragment()  ,View.OnClickListener,IContractView.ICertificationView{

    private val Certification:String="Certification"
    private var mCertificationPresenter=CertificationPresenter(this)
    private lateinit var mCertification: Certification
    private var mPhotoDialog: PhotoDialog? = null
    private var path:String=""

    override fun sendSms(msg: String) {
    }

    override fun commit() {
        SToast.showText("提交成功，待审核")
        activity?.finish()
        EventBus.getDefault().post(Constants.CODE_CERTIFICATION_BROAD)
    }

    public fun newInstance(certification: Certification):MineCertificationThreeFragment
    {
        var bundle = Bundle()
        bundle.putSerializable(Certification, certification);
        var mineCertificationOneFragment = MineCertificationThreeFragment();
        mineCertificationOneFragment.arguments = bundle;
        return mineCertificationOneFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCertification= arguments?.getSerializable(Certification) as Certification
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_mine_certification_three
    }

    override fun initView() {
        iv_id_hand.setOnClickListener(this)
        btn_commit.setOnClickListener(this)
    }

    override fun lazyLoad() {
    }

    override fun onClick(v: View?) {
        when(v)
        {
            btn_commit->{
                if (TextUtils.isEmpty(path)){
                    SToast.showText("请上传手持身份证照片")
                    return
                }
                var map=HashMap<String,String>()
                map["name"]=mCertification.name
                map["idNumber"]=mCertification.idNumber
                map["address"]=mCertification.address
                map["email"]=mCertification.email
                map["code"]=mCertification.code
                mCertificationPresenter.commitCertification(map,mCertification.front,mCertification.opposite,path)
            }
            iv_id_hand->{
                showPhotoDialog()
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
                            .minimumCompressSize(200)
                            .forResult(155)
                }

                override fun pickPhoto() {
                    PictureSelector.create(activity)
                            .openGallery(PictureMimeType.ofImage()) //全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                            .loadImageEngine(GlideEngine.createGlideEngine())
                            .imageSpanCount(3)// 每行显示个数 int
                            .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                            .isCamera(false)
                            .isCompress(true)
                            .minimumCompressSize(200)
                            .forResult(155) //结果回调onActivityResult code
                }
            })
            builder()
            show()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode ==155) {
            var selectList = PictureSelector.obtainMultipleResult(data)
            if (selectList.size > 0) {
                var media = selectList?.get(0)
                if (media != null) {
                    path = if (media.isCompressed) {
                        media.compressPath
                    } else {
                        media.path
                    }
                }
                path= FileUtils.uri2String(Uri.parse(path),activity!!).toString()
                if (path != null) {
                    iv_id_hand.setImageURI(Uri.fromFile(File(path)))
                }
            }
        }
    }


}