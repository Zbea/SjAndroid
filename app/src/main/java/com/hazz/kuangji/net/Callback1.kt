package com.hazz.kuangji.net


import com.hazz.kuangji.net.exception.ExceptionHandle
import com.hazz.kuangji.utils.SToast
import io.reactivex.Observer
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable

abstract class Callback1<T> : Observer<T> {

    private var baseView: BaseView

    constructor(baseView: BaseView) {
        this.baseView = baseView
    }


    override fun onSubscribe(@NonNull d: Disposable) {
        baseView?.addSubscription(d)
    }

    override fun onNext(@NonNull iss: T) {
        if (iss!=null)
        {
            success(iss)
        }
        else{
            failed(iss)
        }
    }

    override fun onComplete() {
        baseView?.hideLoading()
    }

    override fun onError(@NonNull e: Throwable) {
        e.printStackTrace()
//        SToast.showToast(ExceptionHandle.handleException(e))
        baseView.hideLoading()
    }

    abstract fun failed(tBaseResult: T): Boolean

    abstract fun success(iss: T)
}