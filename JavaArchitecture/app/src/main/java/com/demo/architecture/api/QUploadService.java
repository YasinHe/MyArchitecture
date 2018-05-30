package com.demo.architecture.api;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Url;

/**
 * Created by HeYingXin on 2017/7/8.
 */
public interface QUploadService {

    //上传文件
    /**
     *示例：
     // 创建 RequestBody，用于封装构建RequestBody
     RequestBody requestFile =
     RequestBody.create(MediaType.parse("multipart/form-data"), file);
     // MultipartBody.Part  和后端约定好Key，一般就是文件的类型
     MultipartBody.Part body =
     MultipartBody.Part.createFormData("image", file.getName(), requestFile);
     // 添加描述
     String descriptionString = "hello, 这是文件描述";
     RequestBody description =
     RequestBody.create(
     MediaType.parse("multipart/form-data"), descriptionString);
     * @param httpUrl
     * @param description
     * @param file
     * @return
     */
    @Multipart
    @POST
    Observable<ResponseBody> uploadFile(@Url String httpUrl, @Part("description") RequestBody description,
                                        @Part MultipartBody.Part file);

    //上传图片
    @Multipart
    @POST
    Observable<ResponseBody> uploadImage(@Url String url, @Part() MultipartBody.Part file);

    //上传一组图片
    @Multipart
    @POST
    Observable<ResponseBody> uploaduploadImages(@Url String url, @PartMap() Map<String, RequestBody> maps);
}