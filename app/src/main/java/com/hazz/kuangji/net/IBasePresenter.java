package com.hazz.kuangji.net;


/**
 * Created by mfwn on 2016/11/12.
 */

public interface IBasePresenter<V extends IBaseView> {

    V getView();
}

/**
 * 还有类似下面的封装
 *
 * public interface IBasePresenter<V,E>
 */
