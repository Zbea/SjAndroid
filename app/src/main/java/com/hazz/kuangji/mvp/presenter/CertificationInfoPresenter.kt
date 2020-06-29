package com.hazz.kuangji.mvp.presenter


import android.text.TextUtils
import android.util.Pair
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Sms
import com.hazz.kuangji.mvp.model.bean.Certification
import com.hazz.kuangji.mvp.model.bean.ExchangeOrder
import com.hazz.kuangji.mvp.model.bean.UserInfo
import com.hazz.kuangji.net.*
import com.hazz.kuangji.utils.Utils
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class CertificationInfoPresenter(view: IContractView.ICertificationInfoView) : BasePresenter<IContractView.ICertificationInfoView>(view) {


    fun getCertification() {


        val login = RetrofitManager.service.getCertification()

        doRequest(login, object : Callback<Certification>(view) {
            override fun failed(tBaseResult: BaseResult<Certification>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<Certification>) {
                tBaseResult.data?.let {
                    view.getCertification(it)
                }
            }

        }, false)

    }



}