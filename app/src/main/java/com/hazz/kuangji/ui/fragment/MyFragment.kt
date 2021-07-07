package com.hazz.kuangji.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.hazz.kuangji.Constants
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseFragment
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Account
import com.hazz.kuangji.mvp.model.Certification
import com.hazz.kuangji.mvp.model.UploadModel
import com.hazz.kuangji.mvp.presenter.CertificationInfoPresenter
import com.hazz.kuangji.mvp.presenter.MyAccountPresenter
import com.hazz.kuangji.ui.activity.LoginActivity
import com.hazz.kuangji.ui.activity.mine.*
import com.hazz.kuangji.utils.*
import com.hazz.kuangji.widget.CommonDialog
import com.hazz.kuangji.widget.PhotoDialog
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import kotlinx.android.synthetic.main.fragment_mine.*

class MyFragment : BaseFragment(), IContractView.IAccountView,IContractView.ICertificationInfoView  {

    private val mCertificationInfoPresenter = CertificationInfoPresenter(this)
    private var mMyAccountPresenter: MyAccountPresenter = MyAccountPresenter(this)
    private var mPhotoDialog: PhotoDialog? = null

    //账户信息
    override fun getAccount(msg: Account) {
        if (mView==null||iv_header==null)return
        SPUtil.putString("mobile",msg.mobile)
        SPUtil.putString("username",msg.userName)
        SPUtil.putString("image",msg.img)
        SPUtil.putString("invitation_code",msg.inviteCode)
        mTvMobile.text = msg.mobile
        mTvUserName.text = msg.userName
        setHeaderImage()
    }
    //头像上传
    override fun setHeader(msg: UploadModel) {
        if (mView==null)return
        SPUtil.putString("image",msg.path)
        setHeaderImage()
    }

    //实名认证
    override fun getCertification(data: Certification) {
        if (mView==null||iv_certification==null)return
        when (data.stat) {
            "0" -> {
                iv_certification.setImageResource(R.mipmap.icon_mine_certification)
            }
            "1" -> {
                iv_certification.setImageResource(R.mipmap.icon_mine_certificated)
                SPUtil.putObj("certification", data!!)
            }
            "2" -> {
                iv_certification.setImageResource(R.mipmap.icon_mine_certification)
                SToast.showText(data.reason + "请重新实名认证")
            }
            else -> {
                iv_certification.setImageResource(R.mipmap.icon_mine_certification)
                SToast.showText("尚未实名认证，请立即实名认证")
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_mine
    }

    @SuppressLint("WrongConstant")
    override fun initView() {

        var layoutParams: LinearLayout.LayoutParams= tv_top.layoutParams as LinearLayout.LayoutParams
        layoutParams.topMargin= activity?.let { DensityUtils.getStatusBarHeight(it) }!!
        tv_top.layoutParams=layoutParams

        iv_header.setOnClickListener {
            showPhotoDialog()
        }

        layout_invite.setOnClickListener {
            startActivity(Intent(activity, InviteActivity::class.java))
        }

        layout_setting.setOnClickListener {
            startActivity(Intent(activity, SettingActivity::class.java))
        }


        layout_contact.setOnClickListener {
            startActivity(Intent(activity, MineContactActivity::class.java).setFlags(0))
        }
        layout_download.setOnClickListener {
            startActivity(Intent(activity, DownloadActivity::class.java))
        }

        layout_certification.setOnClickListener {
            var mCertification= SPUtil.getObj("certification", Certification::class.java)
            if (mCertification?.stat=="1"||mCertification?.stat=="0")
            {
                var intent = Intent(activity, MineCertificatedActivity::class.java)
                intent.putExtra("certification", mCertification)
                startActivity(intent)
            }
            else
            {
                startActivity(Intent(activity, MineCertificationActivity::class.java))
            }
        }

        tv_logout.setOnClickListener {
            CommonDialog(context).run {
                setContent("确认要退出登录吗？")
                setDialogClickListener(object : CommonDialog.DialogClickListener
                {
                    override fun ok() {
                        SPUtil.putString("token", "")
                        SPUtil.putString("mobile", "")
                        SPUtil.putString("username", "")
                        SPUtil.removeObj("certification")
                        startActivity(Intent(context, LoginActivity::class.java))
                        ActivityManager.getInstance().finishOthers(LoginActivity::class.java)
                    }
                    override fun cancel() {
                    }
                })
                builder()
            }
        }


    }

    override fun lazyLoad() {
        mCertificationInfoPresenter.getCertification()
        mMyAccountPresenter.getAccount()
    }

    /**
     * 设置头像
     */
    private fun setHeaderImage()
    {
        activity?.let {
            Glide.with(it).load(Constants.URL_BASE + SPUtil.getString("image"))
                    .apply(RequestOptions.bitmapTransform(CircleCrop()).error(R.mipmap.icon_home_mine))
                    .into(iv_header)
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
                path= FileUtils.uri2String(Uri.parse(path),activity!!).toString()
                if (path != null) {
                    mMyAccountPresenter.upImage(path)
                }
            }
        }

    }


}


