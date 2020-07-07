package com.example.nethelper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.nethelper.Bean.InfoData;
import com.example.nethelper.Bean.InfoModel;
import com.example.nethelper.Interface.InfoPost;
import com.example.nethelper.Utils.HttpMethods;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SecondActivity extends AppCompatActivity {
private Button btn_get2,btn_post2,btn_postarray;
private TextView tv_show2;
private ProgressBar pbar_test;
//public static Subscription subscription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        btn_get2=findViewById(R.id.btn_get2);
        btn_post2=findViewById(R.id.btn_post2);
        btn_postarray=findViewById(R.id.btn_postarray);
        tv_show2=findViewById(R.id.tv_show2);
        pbar_test=findViewById(R.id.pBar_test);
        pbar_test.setVisibility(View.GONE);
        btn_post2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStationByImei();
//                String url="http://10.132.212.75:8082/videomonitor/controls/";
//                Retrofit retrofit=new Retrofit.Builder()
//                        .baseUrl(url)
//                        //增加返回值为Json的支持
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .build();
//                InfoPost infoPost=retrofit.create(InfoPost.class);
//                Call<InfoModel<InfoData>> call=infoPost.getStationInfo("867071048139516");
//                call.enqueue(new Callback<InfoModel<InfoData>>() {
//                    @Override
//                    public void onResponse(Call<InfoModel<InfoData>> call, Response<InfoModel<InfoData>> response) {
//                        String msg=response.body().getMsg();
//                       InfoData data =response.body().getData();
//                       tv_show2.setText(msg+data.getBuilding()+data.getStation());
//                    }
//
//                    @Override
//                    public void onFailure(Call<InfoModel<InfoData>> call, Throwable t) {
//                        tv_show2.setText(t.getMessage());
//                        System.out.println(t.getMessage());
//                    }
//                });
            }
        });

        btn_postarray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url="http://10.132.212.75:8082/videomonitor/controls/";
                Retrofit retrofit=new Retrofit.Builder()
                        .baseUrl(url)
                        //增加返回值为Json的支持
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .build();

                InfoPost infoPost=retrofit.create(InfoPost.class);
                HttpMethods.subscription=infoPost.getAllStation()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<InfoModel<List<InfoData>>>() {
                            @Override
                            public void onCompleted() {
                                System.out.println("onCompleted");
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(InfoModel<List<InfoData>> listInfoModel) {
                                String msg=listInfoModel.getMsg();
                                List<InfoData> infoData=listInfoModel.getData();
                                tv_show2.setText(msg);
                                for (int i=0;i<infoData.size();i++){
                                InfoData info=infoData.get(i);
                                System.out.println(info.getStation());
                        }
                            }
                        });
//                Call<InfoModel<List<InfoData>>> call=infoPost.getAllStation();
//                call.enqueue(new Callback<InfoModel<List<InfoData>>>() {
//                    @Override
//                    public void onResponse(Call<InfoModel<List<InfoData>>> call, Response<InfoModel<List<InfoData>>> response) {
//                        String msg=response.body().getMsg();
//                        List<InfoData> infoData=response.body().getData();
//                        tv_show2.setText(msg);
//                        for (int i=0;i<infoData.size();i++){
//                            InfoData info=infoData.get(i);
//                            System.out.println(info.getStation());
//                        }
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<InfoModel<List<InfoData>>> call, Throwable t) {
//                        tv_show2.setText(t.getMessage());
//                        System.out.println(t.getMessage());
//                    }
//                });
            }
        });
    }
    private void getStationByImei(){

//        SubscriberOnNextListenter subscriberOnNextListenter = new SubscriberOnNextListenter() {
//            @Override
//            public void next(Object o) {
//
//            }
//        };

        HttpMethods.getInstance().getStationInfo("867071048139516", new Subscriber<InfoModel<InfoData>>() {
            @Override
            public void onStart() {
             if (pbar_test.getVisibility()==View.GONE){
                 pbar_test.setVisibility(View.VISIBLE);
             }
            }
            @Override
            public void onCompleted() {
             if (pbar_test.getVisibility()==View.VISIBLE){
                 pbar_test.setVisibility(View.GONE);
             }
            }

            @Override
            public void onError(Throwable e) {
                if (pbar_test.getVisibility()==View.VISIBLE){
                    pbar_test.setVisibility(View.GONE);
                    tv_show2.setText(e.getMessage());
                }
            }

            @Override
            public void onNext(InfoModel<InfoData> infoModel) {
                String msg=infoModel.getMsg();
                       InfoData data =infoModel.getData();
                       tv_show2.setText(msg+data.getBuilding()+data.getStation());
            }
        });
    }

    @Override
    public void onStop(){
        super.onStop();
        if (HttpMethods.subscription!=null&&HttpMethods.subscription.isUnsubscribed()){
            HttpMethods.subscription.unsubscribe();
        }
    }
}
