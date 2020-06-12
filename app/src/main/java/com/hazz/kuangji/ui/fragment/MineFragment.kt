package com.hazz.kuangji.ui.fragment

import android.content.Intent
import com.hazz.kuangji.Constants
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseFragment
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.bean.Node
import com.hazz.kuangji.mvp.model.bean.Shenfen
import com.hazz.kuangji.mvp.model.bean.UploadModel
import com.hazz.kuangji.mvp.model.bean.UserInfo
import com.hazz.kuangji.mvp.presenter.NodePresenter
import com.hazz.kuangji.ui.activity.*
import com.hazz.kuangji.utils.GlideEngine
import com.hazz.kuangji.utils.SPUtil
import com.hazz.kuangji.widget.PhotoDialog
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import kotlinx.android.synthetic.main.fragment_mine.*

class MineFragment : BaseFragment(), IContractView.NodeView {


    private var mNodePresenter: NodePresenter = NodePresenter(this)
    private var userInfo: UserInfo? = null
    private var mPhotoDialog :PhotoDialog?=null;

    override fun getShenfen(msg: Shenfen) {
        activity?.let { GlideEngine.createGlideEngine().loadImage(it,Constants.URL_INVITE+msg.profile_img,iv_header) }
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
        activity?.let { GlideEngine.createGlideEngine().loadImage(it,Constants.URL_INVITE+msg.path,iv_header) }
    }


    override fun getNode(msg: Node) {

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_mine
    }

    override fun initView() {


        userInfo = SPUtil.getObj("user", UserInfo::class.java)

        if (userInfo != null) {

            mTvUserName.text = userInfo!!.username
            mTvMobile.text =userInfo!!.mobile
        } else {
            val userName = SPUtil.getString("username")
            val mobile = SPUtil.getString("mobile")
            mTvMobile.text =mobile
            mTvUserName.text = userName
        }

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
            startActivity(Intent(activity,MineContactActivity::class.java))
        }
        layout_about.setOnClickListener {
            startActivity(Intent(activity, RegistRuleActivity::class.java).putExtra("type",1))
        }

    }

    override fun lazyLoad() {
        mNodePresenter.getShenfen()
    }

    private fun showPhotoDialog()
    {
        mPhotoDialog= PhotoDialog(activity)
        mPhotoDialog?.run {
            setDialogClickListener(object : PhotoDialog.DialogClickListener
            {
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
            var path=selectList?.get(0)?.path
            if (path != null) {
                mNodePresenter.upImage(path)
            }
        }

    }


}
