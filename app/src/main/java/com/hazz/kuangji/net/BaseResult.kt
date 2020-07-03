package com.hazz.kuangji.net


class BaseResult<T>{
    var msg: String=""
    var error: String=""
    var code = 0
    var data: T? = null
}