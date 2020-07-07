package com.example.nethelper.Utils;

import com.example.nethelper.Bean.InfoData;
import com.example.nethelper.Bean.InfoModel;
import com.example.nethelper.Interface.InfoPost;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HttpMethods {
    public static final String BASE_URL = "http://10.132.212.75:8082/videomonitor/controls/";
    private static final int DEFAULT_TIMEOUT = 5;

    private Retrofit retrofit;

    private InfoPost infoPost;

    public static Subscription subscription;

    //先构造私有的构造方法
    private HttpMethods() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        infoPost = retrofit.create(InfoPost.class);
    }

    //创建单例
    public static class SingleonHolder {
        private static final HttpMethods instance = new HttpMethods();
    }

    //获取单例
    public static HttpMethods getInstance() {
        return SingleonHolder.instance;
    }

    /**
     *
     * @param subscriber 传递过来的观察者对象
     */
    public void getStationInfo(String imei, Subscriber<InfoModel<InfoData>> subscriber) {
        subscription=infoPost.getStationInfo(imei)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
