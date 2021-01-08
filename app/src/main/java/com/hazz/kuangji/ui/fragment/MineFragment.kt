package com.hazz.kuangji.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Handler
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
import com.hazz.kuangji.ui.activity.asset.TransferCoinActivity
import com.hazz.kuangji.ui.activity.home.ExchangeBuyActivity
import com.hazz.kuangji.ui.activity.investment.InvestmentActivity
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
import skin.support.SkinCompatManager
import java.util.*

class MineFragment : BaseFragment(), IContractView.NodeView {


    private var mNodePresenter: NodePresenter = NodePresenter(this)
    private var userInfo: UserInfo? = null
    private var mPhotoDialog: PhotoDialog? = null
    private var mData: Certification? = null

    //账户信息
    override fun getAccount(msg: Account) {
        if (mView==null||iv_header==null||iv_type==null)return
        SPUtil.putString("image",msg.profile_img)
        setHeaderImage()
        iv_type.visibility=View.VISIBLE
        when (msg.level) {
            "初级矿商" -> iv_type.setImageResource(R.mipmap.icon_mine_chuji)
            "中级矿商" -> iv_type.setImageResource(R.mipmap.icon_mine_zhongji)
            "高级矿商" -> iv_type.setImageResource(R.mipmap.icon_mine_gaoji)
            "超级矿商" -> iv_type.setImageResource(R.mipmap.icon_mine_chaoji)
            "合伙人" -> iv_type.setImageResource(R.mipmap.icon_mine_hehuo)
            else ->iv_type.visibility=View.GONE
        }
        iv_company_type.visibility=View.VISIBLE
        when (msg.team_level) {
            "1" -> iv_company_type.setImageResource(R.mipmap.icon_mine_company_city)
            "2" -> iv_company_type.setImageResource(R.mipmap.icon_mine_company_province)
            "3" -> iv_company_type.setImageResource(R.mipmap.icon_mine_company_area)
            else ->iv_company_type.visibility=View.GONE
        }

    }
    //头像上传
    override fun setHeader(msg: UploadModel) {
        if (mView==null)return
        SPUtil.putString("image",msg.path)
        setHeaderImage()
    }

    //实名认证
    override fun getCertification(data: Certification) {
        if (mView==null||tv_dot==null||iv_certification==null)return
        mData = data
        when (data.status) {
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


        iv_header.setOnClickListener {
            showPhotoDialog()
        }

        ll_buy.setOnClickListener {
            startActivity(Intent(activity, ExchangeBuyActivity::class.java))
        }

        ll_sale.setOnClickListener {
            if (isCertificated())
                startActivity(Intent(activity, TransferCoinActivity::class.java))
        }

        layout_invite.setOnClickListener {
            startActivity(Intent(activity, InviteActivity::class.java))
        }
        layout_investment.setOnClickListener {
            startActivity(Intent(activity, InvestmentActivity::class.java))
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
            startActivity(Intent(activity, MineDownloadActivity::class.java))
        }
        layout_about.setOnClickListener {
            startActivity(Intent(activity, AboutActivity::class.java))
        }
        layout_certification.setOnClickListener {
            if (mData?.status==1||mData?.status==0)
            {
                var intent = Intent(activity, MineCertificatedActivity::class.java)
                intent.putExtra("certification", mData)
                startActivity(intent)
            }
            else
            {
                startActivity(Intent(activity, MineCertificationActivity::class.java))
            }
        }

        iv_skin.setOnClickListener {
            if (!SPUtil.getBoolean("skin"))
            {
                SPUtil.putBoolean("skin",true)
                SkinCompatManager.getInstance().loadSkin("night", SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN); // 后缀加载
            }
            else
            {
                SPUtil.putBoolean("skin",false)
                SkinCompatManager.getInstance().restoreDefaultTheme();//恢复默认皮肤
            }
            EventBus.getDefault().post(Constants.CODE_SKIN_BROAD)
            Handler().postDelayed(Runnable {
                setHeaderImage()
            },100)

        }

    }

    override fun lazyLoad() {
        mNodePresenter.getCertification()
        mNodePresenter.getAccount()
    }

    /**
     * 设置头像
     */
    private fun setHeaderImage()
    {
        (activity as MainActivity).setImage()
        activity?.let {
            Glide.with(it).load(Constants.URL_INVITE + SPUtil.getString("image"))
                    .apply(RequestOptions.bitmapTransform(CircleCrop()).error(R.mipmap.icon_home_mine))
                    .into(iv_header)
        }
    }

    /**
     * 判断是否已经实名认证
     */
    private fun isCertificated():Boolean
    {
        when (mData?.status) {
            0 -> {
                SToast.showText("实名认证审核中，请稍等")
                return false
            }
            1 -> {
                return true
            }
            else -> {
                SToast.showText("尚未实名认证，请先前往实名认证")
                Handler().postDelayed(Runnable {
                    startActivity(Intent(activity, MineCertificationActivity::class.java))
                }, 500)
                return false
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
                path= FileUtils.uri2String(Uri.parse(path),activity!!).toString()
                if (path != null) {
                    mNodePresenter.upImage(path)
                }
            }
        }

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: String) {
        if (event == Constants.CODE_CERTIFICATION_BROAD) {
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    if (mData?.status!=1)
                    {
                        mNodePresenter.getCertification()
                    }
                }
            } , 0, 3*60*1000)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


}


