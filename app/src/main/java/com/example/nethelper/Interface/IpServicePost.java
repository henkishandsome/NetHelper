package com.example.nethelper.Interface;

import com.example.nethelper.Bean.NetCallBack;
import com.example.nethelper.Bean.User;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface IpServicePost {
    @FormUrlEncoded
    @POST("getUser")
    //Call<List<User>> getIdUser(@Field("id") int id);
    Call<NetCallBack> getIdUser(@Field("id") int id);
}
