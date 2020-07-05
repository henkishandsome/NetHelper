package com.example.nethelper.Interface;

import com.example.nethelper.Bean.NetCallBack;
import com.example.nethelper.Bean.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IpService {
    @GET("{path}/findAllUser")
    Call<NetCallBack> getAllUser(@Path("path") String path);
}
