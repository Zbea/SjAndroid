package com.hazz.kuangji.utils;

import android.util.Log;
import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;

import java.util.HashMap;

/**
 * RxBus
 * <p>
 * 参考 @Link https://blog.csdn.net/donkor_/article/details/79709366
 */
public class RxBus {
    private Relay<Object> bus = null;
    private final FlowableProcessor<Object> mBus;
    private HashMap<String, CompositeDisposable> mSubscriptionMap;

    //禁用构造方法
    private RxBus() {
        bus = PublishRelay.create().toSerialized();
        mBus = PublishProcessor.create().toSerialized();
    }

    public static RxBus get() {
        return Holder.instance;
    }

    private static class Holder {
        private static RxBus instance = new RxBus();
    }

    private static final String TAG = "RxBus";

    /**
     * 保存订阅后的subscription
     */
    public void addSubscription(Object o, Disposable subscription) {
        if (mSubscriptionMap == null) {
            mSubscriptionMap = new HashMap<>();
        }
        String key = o.getClass().getName();
        Log.v(TAG, "addSubscription: " + key);
        if (mSubscriptionMap.get(key) != null) {
            mSubscriptionMap.get(key).add(subscription);
        } else {
            CompositeDisposable compositeSubscription = new CompositeDisposable();
            compositeSubscription.add(subscription);
            mSubscriptionMap.put(key, compositeSubscription);
        }
    }

    /**
     * 取消订阅
     */
    public void unSubscribe(Object o) {
        if (mSubscriptionMap == null) {
            return;
        }
        String key = o.getClass().getName();
        Log.v(TAG, "unSubscribe: " + key);

        if (!mSubscriptionMap.containsKey(key)) {
            return;
        }
        if (mSubscriptionMap.get(key) != null) {
            mSubscriptionMap.get(key).clear();
        }
        mSubscriptionMap.remove(key);
    }


    public void send(Object event) {
        bus.accept(event);
    }

    public void sendFlowable(Object event) {
        mBus.onNext(event);
    }


    public <T> Observable<T> toObservable(Class<T> eventType) {
        return bus.ofType(eventType);
    }

    public <T> Flowable<T> toFlowable(Class<T> eventType) {
        return mBus.ofType(eventType);
    }

    public boolean hasObservers() {
        return bus.hasObservers();
    }

    public <T> Disposable observerOnMain(Object token, Class<T> eventType, Consumer<T> onNext) {
        Disposable d = toObservable(eventType).compose(subOnMain()).subscribe(onNext);
        addSubscription(token, d);
        return d;
    }


    public <T> Disposable observerOnMain(Object token, Class<T> eventType, Consumer<T> onNext, Consumer<? super Throwable> onError) {
        Disposable d = toObservable(eventType).compose(subOnMain()).subscribe(onNext, onError);
        addSubscription(token, d);
        return d;
    }


    public <T> Disposable observerOnIO(Object token, Class<T> eventType, Consumer<T> onNext) {
        Disposable d = toObservable(eventType).compose(subOnIO()).subscribe(onNext);
        addSubscription(token, d);
        return d;
    }

    public <T> Disposable observerOnIO(Object token, Class<T> eventType, Consumer<T> onNext, Consumer<? super Throwable> onError) {
        Disposable d = toObservable(eventType).compose(subOnIO()).subscribe(onNext, onError);
        addSubscription(token, d);
        return d;
    }


    public static <T> ObservableTransformer<T, T> subOnMain() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> ObservableTransformer<T, T> subOnIO() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }


}
