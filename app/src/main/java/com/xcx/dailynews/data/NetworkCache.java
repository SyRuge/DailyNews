package com.xcx.dailynews.data;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by xcx on 2016/11/14.
 * 网络缓存
 */

public class NetworkCache<T> {
    public Observable<List<T>> get(String key){
        return Observable.create(new Observable.OnSubscribe<List<T>>() {
            @Override
            public void call(Subscriber<? super List<T>> subscriber) {
                //缓存到内存
            }
        });
    }
}
