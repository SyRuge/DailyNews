package com.xcx.dailynews.mvp.model;

import android.util.Log;

import com.xcx.dailynews.bean.TopicBean;
import com.xcx.dailynews.mvp.ui.view.OnGetDataListener;
import com.xcx.dailynews.net.NewsService;

import retrofit2.Retrofit;
import rx.Subscriber;

/**
 * Created by xcx on 2016/11/2.
 */

public class NewsTopicModel {
    public void setOnGetDataListener(OnGetDataListener listener,Retrofit retrofit) {
        mListener = listener;
        mRetrofit = retrofit;
    }

    private OnGetDataListener mListener;
    private Retrofit mRetrofit;

    public void getDataFromServer(){
        NewsService newsService = mRetrofit.create(NewsService.class);
       /* newsService.getTopicNewsService("headline","T1348647909107")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubscriber());*/
    }

    class MySubscriber extends Subscriber<TopicBean>{

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(TopicBean topicBean) {
         //   mListener.onDataSuccess(topicBean);
            Log.e("TAG", "onNext: "+topicBean.toString());
        }
    }
}
