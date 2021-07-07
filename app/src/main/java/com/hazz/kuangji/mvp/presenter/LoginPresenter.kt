package com.hazz.kuangji.mvp.presenter


import android.util.Pair
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.UserInfo
import com.hazz.kuangji.net.*
import com.hazz.kuangji.utils.Utils


class LoginPresenter(view: IContractView.LoginView) : BasePresenter<IContractView.LoginView>(view) {

    fun login(credential: String, password: String
    ) {


        val body = RequestUtils.getBody(

                Pair.create<Any, Any>("username", credential),
                Pair.create<Any, Any>("password", Utils.encryptMD5(password))

        )


        val login = RetrofitManager.service.login(body)

        doRequest(login, object : Callback<UserInfo>(view) {
            override fun failed(tBaseResult: BaseResult<UserInfo>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<UserInfo>) {
                view.loginSuccess(tBaseResult.data!!)
            }

        }, true)

    }

    fun resetPwd(old_password: String, password: String
    ) {


        val body = RequestUtils.getBody(

                Pair.create<Any, Any>("old_password", Utils.encryptMD5(old_password)),
                Pair.create<Any, Any>("password", Utils.encryptMD5(password))

        )

        val login = RetrofitManager.service.resetPwd(body)

        doRequest(login, object : Callback<Any>(view) {
            override fun failed(tBaseResult: BaseResult<Any>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<Any>) {
                view.registerSucceed(tBaseResult.msg)
            }

        }, true)

    }

    fun sendSMs(mobile: String,type: String) {

        val sms = RetrofitManager.service.sendCode(mobile,type)
        doRequest(sms, object : Callback<Any>(view) {
            override fun failed(tBaseResult: BaseResult<Any>): Boolean {
                view.sendSms("发送失败")
                return false
            }

            override fun success(tBaseResult: BaseResult<Any>) {
                view.sendSms("发送成功")
            }

        }, true)
    }

    fun regist(username: String, invitation_code: String, mobile: String,
               captcha: String, password: String, trade_password: String) {


        val body = RequestUtils.getBody(
                Pair.create<Any, Any>("username", username),
                Pair.create<Any, Any>("invitation_code", invitation_code),
                Pair.create<Any, Any>("mobile", mobile),
                Pair.create<Any, Any>("captcha", captcha),
                Pair.create<Any, Any>("password", Utils.encryptMD5(password)),
                Pair.create<Any, Any>("trade_password", Utils.encryptMD5(trade_password))


        )

        val login = RetrofitManager.service.regist(body)

        doRequest(login, object : Callback<Any>(view) {
            override fun failed(tBaseResult: BaseResult<Any>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<Any>) {
                view.registerSucceed(tBaseResult.msg)
            }

        }, true)

    }


    fun forgetPwd(username: String, mobile: String, captcha: String, password: String, type: Int) {

        val body = RequestUtils.getBody(
                Pair.create<Any, Any>("username", username),
                Pair.create<Any, Any>("mobile", mobile),
                Pair.create<Any, Any>("captcha", captcha),
                Pair.create<Any, Any>("password", Utils.encryptMD5(password)))

        val login =if (type==1)
        {
             RetrofitManager.service.findLoginPwd(body)//找回登录密码
        }
        else{
            RetrofitManager.service.findTradePwd(body)//找回资金密码
        }

        doRequest(login, object : Callback<Any>(view) {
            override fun failed(tBaseResult: BaseResult<Any>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<Any>) {
                view.registerSucceed(tBaseResult.msg)
            }

        }, true)

    }


}