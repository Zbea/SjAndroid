package com.hazz.kuangji.mvp.contract

import com.hazz.kuangji.mvp.model.*
import com.hazz.kuangji.net.BaseView


/**
 * Description:
 * Date：2019/4/9-14:03
 * Author: cwh
 */
interface IContractView {

    interface MainView: BaseView {

    }

    interface LoginView: BaseView {

        fun loginSuccess(msg: UserInfo)
        fun sendSms(msg:String)
        fun registerSucceed(msg:String)
    }

    interface CoinView: BaseView {

        fun getCoin(msg:List<Coin>)
        fun getFriends(msg: Friends)
    }

    interface MsgView: BaseView {

        fun getMsg(msg:List<Msg>)

    }
    interface NodeView: BaseView {

        fun getNode(msg: Node)
        fun getAccount(msg: Account)
        fun setHeader(msg: UploadModel)
        fun getCertification(data: Certification)
    }

    interface TibiView: BaseView {

        fun tibiSucceed(msg:String)
        fun tibiRecord(msg: TibiRecord)
    }

    interface ShouyiView: BaseView {

        fun inComing(msg: InComing)
    }
    interface kuangjiView: BaseView {

        fun getKuangji(msg: Mill)
        fun getMingxi(msg: Mingxi)
    }
    interface HomeView: BaseView {

        fun getHome(msg:Home)
        fun zuyongSucceed(data: Contract)
    }
    interface ChargeView: BaseView {

        fun getAddress(msg: Charge)

        fun chargeRecord(msg: ChargeRecord)
    }
    interface TouziView: BaseView {

        fun touziList(msg: Touzi)
        fun touziConfirm(msg:String)
        fun touziRecord(msg: TouziRecord)
    }

    interface AssetView: BaseView {

        fun myAsset(msg: MyAsset)
    }
    interface XieyiView: BaseView {

        fun xieyi(msg:Xieyi)
        fun getSignRecord(msg: SignRecord)
    }

    //一键买入
    interface IExchangeBuyView: BaseView {
        fun getExchange(data: Exchange)
        fun commitOrder(data: ExchangeOrder)
    }
    //一键买入订单详情
    interface IExchangeOrderBuyView: BaseView {
        fun getOrder(data: ExchangeOrder)
        fun cancelOrder()
        fun commitPay()
    }

    //交易记录
    interface IExchangeRecordView: BaseView {
        fun setListView(data:List<ExchangeRecord>)
    }

    //一键卖出
    interface IExchangeSaleView: BaseView {
        fun getExchange(data: Exchange)
        fun commit(data: ExchangeOrder)
    }

    //一键卖出
    interface IExchangeSaleDetailsView: BaseView {
        fun getOrder(data: ExchangeOrder)
        fun cancelOrder()
    }

    //币币兑换
    interface IExchangeCoinView: BaseView {
        fun getExchange(data: Exchange)
        fun commitCoin()
    }

    //认证
    interface ICertificationView: BaseView {
        fun sendSms(msg:String)
        fun commit()
    }

    //
    interface ICertificationInfoView: BaseView {

        fun getCertification(certification : Certification)

    }

    //转账
    interface ITransferCoinView: BaseView {

        fun commit()
        fun getTransferCoin(data:List<TransferCoin>)

    }

    interface IContractManagerView: BaseView {

        fun getContracts(datas:List<Contract>)
        fun setSign(data: Contract)

    }
}