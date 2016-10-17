package com.lengfeng.vegetablesshopping;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lengfeng.vegetablesshopping.bean.RegisterInfo;
import com.lengfeng.vegetablesshopping.bean.RegisterInfoBean;
import com.lengfeng.vegetablesshopping.bean.RegisterResp;
import com.lengfeng.vegetablesshopping.common.Constant;
import com.lengfeng.vegetablesshopping.utils.MD5Utils;

import java.io.IOException;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/7/29 0029.
 */
public class SettingPwdActivity  extends FragmentActivity {

    private ImageView iv_set_pwd_back;
    private EditText et_password;
    private EditText et_confirm_password;
    private Button bt_confirm_commit;
    OkHttpClient client = new OkHttpClient();
    String secret= MD5Utils.MD5(Constant.APP_KEY)+Constant.VERSION_NO;
    private String setPwd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pwd);
        iv_set_pwd_back = (ImageView) findViewById(R.id.iv_set_pwd_back);
        et_password = (EditText) findViewById(R.id.et_password);
        et_confirm_password = (EditText) findViewById(R.id.et_confirm_password);
        bt_confirm_commit = (Button) findViewById(R.id.bt_confirm_commit);
        iv_set_pwd_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        bt_confirm_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPwd = et_password.getText().toString().trim();
                final String confirmPwd = et_confirm_password.getText().toString().trim();
                Intent getIntent = getIntent();
                String name = getIntent.getStringExtra("name");

                RequestBody formBody = new FormBody.Builder()
                        .add("mobile",name)
                        .add("password", setPwd)
                        .add("repassword",confirmPwd)
                        .add("secret",secret)
                        .build();

                final Request request = new Request.Builder()
                        .url("http://rifulai.wujingen.com/api.php?act=user_register")
                        .post(formBody)
                        .build();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Response rp = client.newCall(request).execute();
                            Log.i("QQ","post数据" + rp);
                            String result = rp.body().string();
                            Log.i("QQ","post打印结果" + result);
                            Gson gson  = new Gson();
                            RegisterResp registerResp = gson.fromJson(result, RegisterResp.class);
                            RegisterInfoBean registerInfoBean = registerResp.getInfo();
                            RegisterInfo registerInfo = registerInfoBean.getData_info();
                            registerInfo.getUser_id();
                            String user_name = registerInfo.getUser_name();
                            Log.i("QQ","打印结果获取的ID" + registerInfo.getUser_id());
                            Log.i("QQ","打印结果获取的名字" + user_name);
                            String user_id = registerInfo.getUser_id();
                            SharedPreferences mySharedPreferences= getSharedPreferences("test", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = mySharedPreferences.edit();
                            editor.putString("name", user_name);
                            editor.putString("set", setPwd);
                            editor.putString("user", user_id);
                            editor.commit();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                if(setPwd != null && setPwd.equals(confirmPwd)){
                    Toast.makeText(SettingPwdActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                    Intent loginIntent = new Intent(SettingPwdActivity.this,MainActivity.class);
                    loginIntent.putExtra("pwd", setPwd);
                    loginIntent.putExtra("name",name);
                    startActivity(loginIntent);
                }else {
                    Toast.makeText(SettingPwdActivity.this,"您输入的密码不一样亲",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
