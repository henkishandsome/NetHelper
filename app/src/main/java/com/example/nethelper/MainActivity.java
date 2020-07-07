package com.example.nethelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.nethelper.Bean.IpModel;
import com.example.nethelper.Bean.NetCallBack;
import com.example.nethelper.Bean.User;
import com.example.nethelper.Interface.IpService;
import com.example.nethelper.Interface.IpServicePost;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
private Button btn_get,btn_post,btn_second;
private TextView tv_show;
    private List<User> userlist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_get=findViewById(R.id.btn_get);
        btn_post=findViewById(R.id.btn_post);
        btn_second=findViewById(R.id.btn_second);
        tv_show=findViewById(R.id.tv_show);

        btn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url="http://192.168.43.187:8080/userLogin/";
                Retrofit retrofit=new Retrofit.Builder()
                        .baseUrl(url)
                        //增加返回值为Json的支持
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                IpService ipService=retrofit.create(IpService.class);
                Call<NetCallBack> call=ipService.getAllUser("user");
                call.enqueue(new Callback<NetCallBack>() {
                    @Override
                    public void onResponse(Call<NetCallBack> call, Response<NetCallBack> response) {
                        String res=response.body().toString();
                        tv_show.setText(res);
                    }

                    @Override
                    public void onFailure(Call<NetCallBack> call, Throwable t) {
                        tv_show.setText("访问失败");
                    }
                });
            }
        });
btn_post.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String url="http://192.168.43.187:8080/userLogin/user/";
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(url)
                //增加返回值为Json的支持
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IpServicePost ipServicePost=retrofit.create(IpServicePost.class);
        Call<NetCallBack>call=ipServicePost.getIdUser(1);
        call.enqueue(new Callback<NetCallBack>() {
            @Override
            public void onResponse(Call<NetCallBack> call, Response<NetCallBack> response) {
//                User res=response.body().getUser();
//                System.out.println(res);
//                tv_show.setText(res.getId()+"-"+res.getUsername());
                userlist=response.body().getUser();
                 System.out.println(userlist);

            }

            @Override
            public void onFailure(Call<NetCallBack> call, Throwable t) {
                tv_show.setText(t.getMessage());
                System.out.println(t.getMessage());
            }
        });
    }
});
btn_second.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(MainActivity.this,SecondActivity.class));
    }
});

    }

}
