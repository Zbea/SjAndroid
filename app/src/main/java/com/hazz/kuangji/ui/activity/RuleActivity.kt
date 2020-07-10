package com.hazz.kuangji.ui.activity

import androidx.appcompat.widget.Toolbar
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.utils.ToolBarCustom
import kotlinx.android.synthetic.main.activity_rule.*


class RuleActivity : BaseActivity() {

    override fun initData() {

    }

    override fun layoutId(): Int {
        return R.layout.activity_rule
    }


    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setLeftIcon(R.mipmap.back_white)
                .setTitle("矿机租赁服务协议")
                .setToolBarBgRescource(R.drawable.bg_blue_gradient)
                .setTitleColor(resources.getColor(R.color.color_white))
                .setOnLeftIconClickListener { finish() }



    }
    override fun start() {
    var text="一、租赁服务协议的确认\n" +
            "1、请您先仔细阅读本协议内容，充分理解本协议及各条款。如果您对本协议内容有任何疑问，请勿进行下一步操作。您可通过官方在线客服进行咨询，以便我们为您解释和说明。您通过页面点击或其他方式确认即表示您已同意本协议。\n" +
            "2、如我们对本协议进行修改，我们将通过网站公告的方式提前予以公布。\n" +
            "3、您确认：a）您年满18周岁并具有完全民事行为能力。b）您接受并使用我们提供的服务在您的居住地/国家符合适用法律法规和相关政策，且不违反您对于任何其他第三方的义务。\n" +
            "您发现当由于事实或法律法规变化您无法承诺本条a和/或b款规定的内容时，您会立即停止使用实久耘矿提供的服务并通过客服务渠道告知Filecoin停止服务。终止服务后，您使用实久耘矿服务的权利立即终止。您同意：在这种情况下，实久耘矿没有义务将任何未处理的信息或未完成的服务传输给您或任何第三方。\n" +
            " 二、矿机的租赁\n" +
            "1、客户同意，在注册账户时提供合法，真实，准确和完整的个人信息。如有变化，及时更新用户信息。实久耘矿保留根据提供的信息对帐户施加限制，暂停或终止使用实久耘矿矿机购买和矿机托管服务的权利。您的订单是向实久耘矿就矿机租赁服务发出的请求，并不代表实久耘矿已经接受您的订单。实久耘矿保留拒绝接受用户订单的权利。如果我们接受您的订单，则代表实久耘矿和您之间就该订单所约定的矿机租赁服务产生了一份具有约束力的协议（“矿机租赁服务协议”），该协议约定了矿机租赁的具体内容。 在此之前，订单被视为待处理，实久耘矿保留拒绝接受订单以及拒绝接受用户付款的权利。\n" +
            "2、用户在线下单并支付成功后，订单无法撤销。实久耘矿不支持任何取消订单和退款的请求，请下单前仔细阅读并确认本协议条款 。\n" +
            "3、矿机租赁价格以USDT定价，我们将按照汇率将价格转化为等值泰达币。当虚拟货币市场行情发生非正常波动时，实久耘矿有权手动调整相关汇率。"
        tv_rule.text=text
    }


}
