package com.xcx.dailynews.mvp.model;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.xcx.dailynews.Constants;
import com.xcx.dailynews.bean.NewsDetailBean;
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
        if (mSubscribe != null) {
            if (!mSubscribe.isUnsubscribed()) {
                mSubscribe.unsubscribe();
            }
        }
    }

    @Override
    protected void getDataFromModel(String url, final String channelId, int loadType, final int
            position) {
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
                              //    Log.e("createWorker", "html=: " + s4);
                                mCacheLoader.mDiskCache.put(channelId + "_detail_" + position, s);
                            }
                        });
                    }
                });
        mSubscribe = Observable.concat(diskCache, networkCache)
                .first(new CacheFun1())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new NewsDetailResultFun1())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HtmlSubscriber());
    }

    class NewsDetailResultFun1 implements Func1<String, NewsDetailBean> {

        /**
         * {
         * "newsdetail": {
         * "body": "pzx",
         * "title": "林郑月娥：感谢中央信任支持 将充分落实宪制责任",
         * "source": "海外网",
         * "ptime": "2017-04-11 17:36:16"
         *      }
         * }
         */

        @Override
        public NewsDetailBean call(String s) {

            String s1 = s.substring(s.indexOf("\"") + 1, s.length() - 1);
            String s2 = s1.substring(s1.indexOf("\"") + 1, s1.length());

            String s4 = "{\"newsdetail\"" + s2;

            //解析Json
            //解析json对象
            NewsDetailBean bean = JSONObject.parseObject(s4, NewsDetailBean.class);

            Document d = Jsoup.parseBodyFragment(bean.newsdetail.body);
            String html = d.html();
            bean.newsdetail.html=html;

            return bean;

        }
    }

    class CacheFun1 implements Func1<String, Boolean> {

        @Override
        public Boolean call(String s) {
            return !TextUtils.isEmpty(s);
        }
    }

    class HtmlFun1 implements Func1<String, String> {

        @Override
        public String call(String s) {
            Document doc = Jsoup.parse(s);
            Elements es = doc.select("div.content");
            String html = es.html();
            return html;
        }
    }

    class HtmlSubscriber extends Subscriber<NewsDetailBean> {

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            mListener.onDataFailure(e.getMessage());
        }

        @Override
        public void onNext(NewsDetailBean bean) {
            Log.e("TAG","onNext NewsDetailModel");
            mListener.onDataSuccess(bean, Constants.NEWS_DETAIL);
        }
    }
}
