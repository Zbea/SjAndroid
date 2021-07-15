package com.hazz.kuangji.mvp.contract

import com.hazz.kuangji.mvp.model.*
import com.hazz.kuangji.net.BaseView


/**
 * Description:
 * Date：2019/4/9-14:03
 * Author: cwh
 */
interface IContractView {


    interface LoginView: BaseView {

        fun loginSuccess(msg: UserInfo)
        fun sendSms(msg:String)
        fun registerSucceed(msg:String)
    }

    interface IInviteView: BaseView {
        fun getInviteRecord(lists: List<Invite>)
    }

    interface MsgView: BaseView {
        fun getMsg(msg:Msg)
        fun getMsgDetails(msg:Msg.MsgBean)
    }
    interface IAccountView: BaseView {
        fun getAccount(msg: Account)
        fun setHeader(msg: UploadModel)
    }

    interface IExtractView: BaseView {
        fun extractSucceed(msg:String)
        fun extractRecord(msg: ExtractRecord)
        fun extractRule(msg: ExtractRule)
    }


    interface IMillView: BaseView {
        fun getMill(msg: Mill)
        fun getEarningsList(msg: List<MillEarningsList>)
    }


    interface HomeView: BaseView {
        fun getHome(msg:Home)
    }

    interface IMinerRentView: BaseView {

        fun getInfo(item:MinerFILInfo)
        fun onSucceed(msg:String)
    }


    interface ChargeView: BaseView {

        fun getAddress(msg: Charge)

        fun chargeRecord(msg: ChargeRecord)
    }


    interface IAssetView: BaseView {
        fun myAsset(items: List<Asset>)
        fun getAssetCoinList(items: List<AssetCoin>)
    }

    interface IAgreementView: BaseView {
        fun agreement(msg: Agreement)
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

    interface IAccelerateView: BaseView {
        fun getAccelerateInfo(item:AccelerateInfo)
        fun onSuccess(msg: String)
        fun onFail(msg: String)
    }

    /**
     * 增值服务
     */
    interface IValueAddView: BaseView {
        fun getList(item : ValueAdd)
        fun onSuccess(msg: String)
    }

}