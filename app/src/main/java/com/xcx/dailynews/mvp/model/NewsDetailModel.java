package com.xcx.dailynews.mvp.model;

import android.text.TextUtils;

import com.xcx.dailynews.Constants;
import com.xcx.dailynews.net.NewsService;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by xcx on 2016/11/24.
 */

public class NewsDetailModel extends BaseModel {

    private Subscription mSubscribe;

    @Override
    public void removeMemeoryCache(String channelId) {

    }

    @Override
    public void unSubscribe() {
        if (mSubscribe!=null){
            if (!mSubscribe.isUnsubscribed()){
                mSubscribe.unsubscribe();
            }
        }
    }

    @Override
    protected void getDataFromModel(String url, final String channelId, int loadType, final int position) {
        Observable<String> diskCache = mCacheLoader.mDiskCache.getNewsDetail(channelId +
                "_detail_" + position);
        NewsService service = mRetrofit.create(NewsService.class);
        Observable<String> networkCache = service.getNewsDetailService(url)
                .subscribeOn(Schedulers.io())
                .doOnNext(new Action1<String>() {
                    @Override
                    public void call(final String s) {
                        Schedulers.io().createWorker().schedule(new Action0() {
                            @Override
                            public void call() {
                            //    Log.e("TAG", "html=: "+s);
                                mCacheLoader.mDiskCache.put(channelId + "_detail_" + position,s);
                            }
                        });
                    }
                });
        mSubscribe = Observable.concat(diskCache, networkCache)
                .first(new CacheFun1())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HtmlSubscriber());
    }

    class CacheFun1 implements Func1<String,Boolean>{

        @Override
        public Boolean call(String s) {
            return !TextUtils.isEmpty(s);
        }
    }

    class HtmlFun1 implements Func1<String,String>{

        @Override
        public String call(String s) {
            Document doc = Jsoup.parse(s);
            Elements es = doc.select("div.content");
            String html = es.html();
            return html;
        }
    }

    class HtmlSubscriber extends Subscriber<String>{

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            mListener.onDataFailure(e.getMessage());
        }

        @Override
        public void onNext(String s) {
            mListener.onDataSuccess(s, Constants.NEWS_DETAIL);
        }
    }
}
