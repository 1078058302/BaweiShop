package com.umeng.soexample.net;

import com.umeng.soexample.services.BaseService;
import com.umeng.soexample.services.HttpListener;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class HttpHelper {
    private BaseService baseService;

    public HttpHelper() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new NetWorkInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://www.zhaoapi.cn/")
                .client(okHttpClient)
                .build();
        baseService = retrofit.create(BaseService.class);
    }

    public HttpHelper get(String url, Map<String, String> map) {
        if (map == null) {
            map = new HashMap<>();
        }

        baseService.get(url, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return this;
    }

    public HttpHelper post(String url, Map<String, String> map) {
        if (map == null) {
            map = new HashMap<>();
        }

        baseService.get(url, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return this;
    }

    //上传头像
    public HttpHelper uploadPic(String filePath) {
        File file = new File(filePath);
        MediaType mediaType = MediaType.parse("multipart/form-data; charset=utf-8");
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(mediaType)
                .addFormDataPart("file", "fan.png",
                        RequestBody.create(MediaType.parse("image/png"), file))
                .addFormDataPart("uid", "21014");
        RequestBody requestBody = builder.build();
        MultipartBody.Part part = MultipartBody.Part.create(requestBody);

        baseService.upload("/file/upload", part)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

        return this;
    }


    private Observer observer = new Observer<ResponseBody>() {

        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(ResponseBody responseBody) {
            try {
                String data = responseBody.string();
                listener.success(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(Throwable e) {
            String error = e.getMessage();
            listener.fail(error);
        }

        @Override
        public void onComplete() {

        }
    };

    private HttpListener listener;

    public void result(HttpListener listener) {
        this.listener = listener;
    }

}
