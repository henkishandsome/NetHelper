package com.example.nethelper.Utils;




import com.alibaba.fastjson.JSONObject;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpUtil {

    public static String url="http://10.132.212.75:8082/videomonitor/controls/";
    public static void sendOkHttpRequest(String address, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }

    public static void sendOkHttpRequestJson(String address, JSONObject json, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        String JSON = json.toString();
        MediaType mediaType = MediaType.Companion.parse("application/json;charset=UTF-8");
         RequestBody requestBody = RequestBody.Companion.create(JSON,mediaType);
        Request request = new Request
                .Builder()
                .url(address)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }


    public static void sendOkHttpRequestText(String address, Map<String, String> parms, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        MultipartBody.Builder builder = new MultipartBody.Builder();
        if (parms!=null)
        {
            for (Map.Entry<String, String> entry:parms.entrySet())
            {
                builder.addFormDataPart(entry.getKey(),entry.getValue());
            }
        }
        RequestBody requestBody = builder
                .setType(MultipartBody.FORM)
                .build();

        Request request = new Request
                .Builder()
                .url(address)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public static void retrofitRequest(String url){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(url)
                //增加返回值为Json的支持
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

    }

}
