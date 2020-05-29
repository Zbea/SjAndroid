package com.hazz.kuangji.net

import io.reactivex.disposables.Disposable

/**
 * 感觉增加一个showLogin方法会不会好点，因为很多地方需要登陆，如果Token失效的话
 */
interface BaseView : IBaseView {
    fun addSubscription(d: Disposable)
    fun login()


    fun hideLoading()

    fun Loading()

    fun fail(msg:String)

}