package com.hazz.kuangji.net


import android.util.Log
import com.hazz.kuangji.utils.SToast
import io.reactivex.Observer
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable

abstract class Callback<T> : Observer<BaseResult<T>> {

    private var baseView: BaseView

    constructor(baseView: BaseView) {
        this.baseView = baseView
    }


    override fun onSubscribe(@NonNull d: Disposable) {
        baseView?.addSubscription(d)
    }

    override fun onNext(@NonNull tBaseResult: BaseResult<T>) {
        if (!tBaseResult.error.isNullOrEmpty())
        {
            baseView?.fail(tBaseResult.error)
            return
        }

        if (tBaseResult.code == 200) {
            success(tBaseResult)
        } else {
            when {
                tBaseResult.code==401 -> {
                    baseView?.login()
                }
                else -> {
                    baseView?.fail(tBaseResult.msg)
                    failed(tBaseResult)

                }
            }
        }
    }

    override fun onComplete() {
        baseView?.hideLoading()
    }

    override fun onError(@NonNull e: Throwable) {
        e.printStackTrace()
        SToast.showToast(ExceptionHandle.handleException(e))
        baseView.hideLoading()
    }

    abstract fun failed(tBaseResult: BaseResult<T>): Boolean

    abstract fun success(tBaseResult: BaseResult<T>)
}