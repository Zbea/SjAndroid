package com.hazz.kuangji.ui.fragment

import android.content.Intent
import android.net.Uri
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.hazz.kuangji.Constants
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseFragment
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.bean.*
import com.hazz.kuangji.mvp.presenter.NodePresenter
import com.hazz.kuangji.ui.activity.*
import com.hazz.kuangji.utils.*
import com.hazz.kuangji.widget.PhotoDialog
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import kotlinx.android.synthetic.main.activity_exchange_sale.*
import kotlinx.android.synthetic.main.fragment_mine.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MineFragment : BaseFragment(), IContractView.NodeView {


    private var mNodePresenter: NodePresenter = NodePresenter(this)
    private var userInfo: UserInfo? = null
    private var mPhotoDialog: PhotoDialog? = null
    private var status: Int = 3
    private var mData: Certification? = null

    override fun getAccount(msg: Account) {
        if (iv_header != null) {
            activity?.let {
                Glide.with(it).load(Constants.URL_INVITE + msg.profile_img)
                        .apply(RequestOptions.bitmapTransform(CircleCrop()))
                        .into(iv_header)
            }
        }

        when (msg.level) {
            "总裁" -> iv_type.setImageResource(R.mipmap.zongcai)
            "初级矿商" -> iv_type.setImageResource(R.mipmap.chuji)
            "中级矿商" -> iv_type.setImageResource(R.mipmap.zhongji)
            "高级矿商" -> iv_type.setImageResource(R.mipmap.gaoji)
            "超级矿商" -> iv_type.setImageResource(R.mipmap.chaoji)
            "节点合伙人" -> iv_type.setImageResource(R.mipmap.jiedianhehuo)
            "联创合伙人" -> iv_type.setImageResource(R.mipmap.lianchuang)
        }
    }

    override fun setHeader(msg: UploadModel) {
        activity?.let {
            Glide.with(it).load(Constants.URL_INVITE + msg.path)
                    .apply(RequestOptions.bitmapTransform(CircleCrop()))
                    .into(iv_header)
        }
    }

    override fun getCertification(data: Certification) {
        mData = data
        status = data.status
        when (status) {
            0 -> {
                iv_certification.setImageResource(R.mipmap.icon_mine_certification)
            }
            1 -> {
                iv_certification.setImageResource(R.mipmap.icon_mine_certificated)
                SPUtil.putObj("certification", mData!!)
            }
            2 -> {
                iv_certification.setImageResource(R.mipmap.icon_mine_certification)
                SToast.showTextLong(data.reason + "请重新实名认证")
            }
            3 -> {
                iv_certification.setImageResource(R.mipmap.icon_mine_certification)
                SToast.showTextLong("尚未实名认证，请立即实名认证")
            }
        }
    }


    override fun getNode(msg: Node) {

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_mine
    }

    override fun initView() {
        EventBus.getDefault().register(this)
        userInfo = SPUtil.getObj("user", UserInfo::class.java)
        mData=SPUtil.getObj("certification", Certification::class.java)

        val userName = SPUtil.getString("username")
        val mobile = SPUtil.getString("mobile")
        mTvMobile.text = mobile
        mTvUserName.text = userName

        iv_header.setOnClickListener {
            showPhotoDialog()
        }

        iv_msg.setOnClickListener {
            startActivity(Intent(activity, MsgListActivity::class.java))
        }
        layout_invite.setOnClickListener {
            startActivity(Intent(activity, InviteActivity::class.java))
        }
        layout_setting.setOnClickListener {
            startActivity(Intent(activity, SettingActivity::class.java))
        }
        layout_jiedian.setOnClickListener {
            startActivity(Intent(activity, NodeActivity::class.java))
        }
        layout_team.setOnClickListener {
            startActivity(Intent(activity, IncomingActivity::class.java))
        }
        layout_contact.setOnClickListener {
            startActivity(Intent(activity, MineContactActivity::class.java))
        }
        layout_about.setOnClickListener {
            startActivity(Intent(activity, RegistRuleActivity::class.java).putExtra("type", 1))
        }
        layout_certification.setOnClickListener {
            when (status) {
                0, 1 -> {
                    var intent = Intent(activity, MineCertificatedActivity::class.java)
                    intent.putExtra("certification", mData)
                    startActivity(intent)
                }
                2, 3 -> {   
                    startActivity(Intent(activity, MineCertificationActivity::class.java))
                }
            }

        }

    }

    override fun lazyLoad() {
        Log.i("sj","11111111111")
        mNodePresenter.getAccount()
        mNodePresenter.getCertification()
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
                    mNodePresenter.upImage(path)
                }
            }
        }

    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            if (mData?.status==0)
            {
                mNodePresenter.getCertification()
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: String) {
        if (event == Constants.CODE_CERTIFICATION_BROAD) {
            mNodePresenter.getCertification()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


}


