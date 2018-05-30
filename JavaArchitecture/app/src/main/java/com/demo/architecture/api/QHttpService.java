package com.demo.architecture.api;

import com.demo.architecture.model.BaseModel;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;


/**
 * Created by HeYingXin on 2017/2/16.
 */
public interface QHttpService {

    /**
     * 请求
     */
    @FormUrlEncoded
    @POST
    Observable<BaseModel> request(@Url String httpUrl, @FieldMap Map<String, String> params);

    /**
     * 上传错误日志
     * @param content
     * @param device
     * @return
     */
    @FormUrlEncoded
    @POST
    Observable<BaseModel> postCrash(@Url String httpUrl, @Field("content") String content, @Field("device") String device);

}
