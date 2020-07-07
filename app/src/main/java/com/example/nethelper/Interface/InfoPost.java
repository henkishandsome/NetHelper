package com.example.nethelper.Interface;

import com.example.nethelper.Bean.InfoData;
import com.example.nethelper.Bean.InfoModel;
import com.example.nethelper.Bean.NetCallBack;

import java.util.List;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface InfoPost {
    @FormUrlEncoded
    @POST("selectByIMEI.do")
    Observable<InfoModel<InfoData>> getStationInfo(@Field("imei") String imei);
    @POST("selectAllStationinfo.do")
    Observable<InfoModel<List<InfoData>>> getAllStation();
}
