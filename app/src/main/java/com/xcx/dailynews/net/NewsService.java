package com.xcx.dailynews.net;

import com.xcx.dailynews.bean.TopicBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by xcx on 2016/11/1.
 */

public interface NewsService {
    @GET("{type}/{id}/0-20.html")
    Observable<String> getNewsServiceTest(@Path("type") String type, @Path("id") String id);

    @GET("{type}/{id}/0-20.html")
    Observable<TopicBean> getTopicNewsService(@Path("type") String type, @Path("id") String id);

    @GET("{type}/{id}/0-20.html")
    Observable<String> getNewsService(@Path("type") String type, @Path("id") String id);

    @GET("{type}/{id}/{size}-20.html")
    Observable<String> getPastNewsService(@Path("type") String type, @Path("id") String id, @Path
            ("size") String size);

    //http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1
    @GET
    Observable<String> getPhotoService(@Url String url);

    //http://3g.163.com/news/16/1123/07/C6HRUNE9000189FH.html
    @GET
    Observable<String> getNewsDetailService(@Url String url);

    @FormUrlEncoded
    @POST
    Observable<String> loginService(@Url String url, @Field("email") String userName, @Field
            ("password") String
            password);

    @FormUrlEncoded
    @POST
    Observable<String> signUpService(@Url String url, @Field("email") String userName, @Field
            ("password") String
            password);

    @FormUrlEncoded
    @POST
    Observable<String> insertCollectService(@Url String url, @Field("type") String type, @Field
            ("json")
            String json);

    @FormUrlEncoded
    @POST
    Observable<String> CheckVersionService(@Url String url, @Field("version") String version);

    @FormUrlEncoded
    @POST
    Observable<String> getServerCollectService(@Url String url, @Field("type") String type);

    @FormUrlEncoded
    @POST
    Observable<String> delServerCollectService(@Url String url, @Field("type") String type,
                                               @Field("url") String id);

}
