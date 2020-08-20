package com.hazz.kuangji.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.hazz.kuangji.Constants
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseFragment
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.*
import com.hazz.kuangji.mvp.presenter.NodePresenter
import com.hazz.kuangji.ui.activity.*
import com.hazz.kuangji.ui.activity.asset.IncomingActivity
import com.hazz.kuangji.ui.activity.home.MsgListActivity
import com.hazz.kuangji.ui.activity.mine.*
import com.hazz.kuangji.utils.*
import com.hazz.kuangji.widget.PhotoDialog
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import kotlinx.android.synthetic.main.fragment_mine.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MineFragment : BaseFragment(), IContractView.NodeView {


    private var mNodePresenter: NodePresenter = NodePresenter(this)
    private var userInfo: UserInfo? = null
    private var mPhotoDialog: PhotoDialog? = null
    private var status= 3
    private var mData: Certification? = null

    override fun getAccount(msg: Account) {
        if (mView==null||iv_header==null||iv_type==null)return
        sl_refresh?.isRefreshing=false
        activity?.let {
            Glide.with(it).load(Constants.URL_INVITE + msg.profile_img)
                    .apply(RequestOptions.bitmapTransform(CircleCrop()))
                    .into(iv_header)
        }
        iv_type.visibility=View.VISIBLE
        when (msg.level) {
            "总裁" -> iv_type.setImageResource(R.mipmap.icon_mine_zongcai)
            "初级矿商" -> iv_type.setImageResource(R.mipmap.icon_mine_chuji)
            "中级矿商" -> iv_type.setImageResource(R.mipmap.icon_mine_zhongji)
            "高级矿商" -> iv_type.setImageResource(R.mipmap.icon_mine_gaoji)
            "超级矿商" -> iv_type.setImageResource(R.mipmap.icon_mine_chaoji)
            "节点合伙人" -> iv_type.setImageResource(R.mipmap.icon_mine_jiedianhehuo)
            "联创合伙人" -> iv_type.setImageResource(R.mipmap.icon_mine_lianchuang)
            else ->iv_type.visibility=View.GONE
        }
    }

    override fun setHeader(msg: UploadModel) {
        if (mView==null)return
        activity?.let {
            Glide.with(it).load(Constants.URL_INVITE + msg.path)
                    .apply(RequestOptions.bitmapTransform(CircleCrop()))
                    .into(iv_header)
        }
    }

    override fun getCertification(data: Certification) {
        if (mView==null||tv_dot==null||iv_certification==null)return
        mData = data
        status = data.status
        when (status) {
            0 -> {
                tv_dot.visibility= View.GONE
                iv_certification.setImageResource(R.mipmap.icon_mine_certification)
            }
            1 -> {
                tv_dot.visibility= View.GONE
                iv_certification.setImageResource(R.mipmap.icon_mine_certificated)
                SPUtil.putObj("certification", mData!!)
            }
            2 -> {
                tv_dot.visibility= View.VISIBLE
                iv_certification.setImageResource(R.mipmap.icon_mine_certification)
                SToast.showTextLong(data.reason + "请重新实名认证")
            }
            3 -> {
                tv_dot.visibility= View.VISIBLE
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

    @SuppressLint("WrongConstant")
    override fun initView() {
        EventBus.getDefault().register(this)
        userInfo = SPUtil.getObj("user", UserInfo::class.java)
        mData=SPUtil.getObj("certification", Certification::class.java)

        val userName = SPUtil.getString("username")
        val mobile = SPUtil.getString("mobile")
        mTvMobile.text = mobile
        mTvUserName.text = userName

        sl_refresh=activity?.findViewById(R.id.sl_refresh_mine)
        sl_refresh?.isRefreshing=true
        sl_refresh?.setColorSchemeResources(R.color.color_main)
        sl_refresh?.setOnRefreshListener {
            lazyLoad()
        }

        iv_header.setOnClickListener {
            showPhotoDialog()
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
        layout_manager.setOnClickListener {
            startActivity(Intent(activity, ContractManagerActivity::class.java))
        }
        layout_team.setOnClickListener {
            startActivity(Intent(activity, IncomingActivity::class.java).setFlags(0))
        }
        layout_contact.setOnClickListener {
            startActivity(Intent(activity, MineContactActivity::class.java).setFlags(0))
        }
        layout_download.setOnClickListener {
            startActivity(Intent(activity, MineContactActivity::class.java).setFlags(1))
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
        mNodePresenter.getCertification()
        mNodePresenter.getAccount()
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


