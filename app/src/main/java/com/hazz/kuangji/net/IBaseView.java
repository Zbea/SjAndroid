package com.hazz.kuangji.net;



public interface IBaseView {
    /**
     * 开始加载
     */
    void onStartRequest();

    /**
     * 加载出现错误
     * @param responeThrowable
     */
    void onFailer(ExceptionHandle.ResponeThrowable responeThrowable);

    /**
     * 加载完成
     */
    void onComplete();
}
