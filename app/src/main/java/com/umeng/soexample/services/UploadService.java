package com.umeng.soexample.services;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * author:AbnerMing
 * date:2018/11/20
 */
public interface UploadService {

    @Multipart
    @POST("/file/upload")
    Observable<ResponseBody> upload(@Query("uid") String uid, @Part MultipartBody.Part part);
}
