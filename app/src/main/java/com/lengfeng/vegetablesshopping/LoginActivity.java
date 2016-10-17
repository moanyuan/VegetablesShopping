package com.lengfeng.vegetablesshopping;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lengfeng.vegetablesshopping.bean.LoginInfo;
import com.lengfeng.vegetablesshopping.bean.LoginInfoBean;
import com.lengfeng.vegetablesshopping.bean.LoginResp;
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
 * Created by Administrator on 2016/8/3 0003.
 */
public class LoginActivity extends FragmentActivity implements View.OnClickListener {

    private ImageView iv_login_back;
    private EditText et_account_number;
    private EditText et_login_password;
    private Button bt_login_commit;
    private CheckBox cb_pwd;
    private SharedPreferences sp;
    private CheckBox cb_auto_login;
    private String passwordValue;
    private String userNameValue;
    OkHttpClient client = new OkHttpClient();

    String secret= MD5Utils.MD5(Constant.APP_KEY)+Constant.VERSION_NO;
    private String responseCode;
    private String id;
    private String word;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        setOnListener();
        sp = this.getSharedPreferences("useInfo", Activity.MODE_WORLD_READABLE);
        sp = getSharedPreferences("test", Activity.MODE_PRIVATE);
        id = sp.getString("name","");
        word = sp.getString("set","");
        Log.i("DL","传值账号"+ id);
        Log.i("DL","传值密码"+ word);
        //设置默认是记录密码状态
        if(sp.getBoolean("ISCHECK", false)){
            cb_pwd.setChecked(true);
            et_account_number.setText(sp.getString("USER_NAME", ""));
            et_login_password.setText(sp.getString("PASSWORD", ""));
            //判断自动登陆多选框状态
            if (sp.getBoolean("AUTO_ISCHECK", false)){
                //设置默认是自动登录状态
                cb_auto_login.setChecked(true);
                //Intent loginIntent = new Intent(LoginActivity.this,SettingActivity.class);
                //startActivity(loginIntent);
            }
        }

    }

    private void init() {
        iv_login_back = (ImageView) findViewById(R.id.iv_login_back);
        et_account_number = (EditText) findViewById(R.id.et_account_number);
        et_login_password = (EditText) findViewById(R.id.et_login_password);
        bt_login_commit = (Button) findViewById(R.id.bt_login_commit);
        cb_pwd = (CheckBox) findViewById(R.id.cb_pwd);
        cb_auto_login = (CheckBox) findViewById(R.id.cb_auto_login);
    }

    private void setOnListener() {
        iv_login_back.setOnClickListener(this);
        bt_login_commit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_login_back:
                finish();
                break;
            case R.id.bt_login_commit:
                Intent intent1 = getIntent();
                String userName = intent1.getStringExtra("name");
                String passWord = intent1.getStringExtra("pwd");
                String user_id = intent1.getStringExtra("user_name");
                Log.i("DL","登录账号"+userName);
                Log.i("DL","登录账号"+passWord);
                Log.i("DL","登录账号"+user_id);

                RequestBody formBody = new FormBody.Builder()
                        .add("user_name",id)
                        .add("password",word)
                        .add("secret",secret)
                        .build();

                final Request request = new Request.Builder()
                        .url("http://rifulai.wujingen.com/api.php?act=user_login")
                        .post(formBody)
                        .build();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Response response = null;
                        try {
                            response = client.newCall(request).execute();
                            if (response.isSuccessful()) {
                                responseCode = response.body().string();
                                Log.i("WY","打印POST响应的数据：" + response +"");
                                Gson gson = new Gson();
                                LoginResp loginResp = gson.fromJson(responseCode, LoginResp.class);
                                LoginInfoBean loginInfoBean = loginResp.getInfo();
                                LoginInfo loginInfo = loginInfoBean.getData_info();
                                String user_id = loginInfo.getUser_id();
                                Log.i("WY","打印POST响应的数据：" + user_id +"");

                            } else {
                                throw new IOException("Unexpected code " + response);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                userNameValue = et_account_number.getText().toString();
                passwordValue = et_login_password.getText().toString();


                if(userNameValue.equals(userName) && passwordValue.equals(passWord))
                {
                    Toast.makeText(LoginActivity.this,"登录成功", Toast.LENGTH_SHORT).show();
                    Intent intent2=new Intent();
                    intent2.putExtra("passWprd",passWord);
                    //intent2.setClass(LoginActivity.this,SettingActivity.class);
                    //startActivity(intent2);
                    //登录成功和记住密码框为选中状态才保存用户信息
                    if(cb_pwd.isChecked())
                    {
                        //记住用户名、密码、
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("USER_NAME", userNameValue);
                        editor.putString("PASSWORD",passwordValue);
                        editor.commit();
                    }
                    //跳转界面
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    LoginActivity.this.startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this,"用户名或密码错误，请重新登录", Toast.LENGTH_LONG).show();
                }

                break;
        }

        cb_pwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (cb_pwd.isChecked()) {
                    System.out.println("记住密码已选中");
                    sp.edit().putBoolean("ISCHECK", true).commit();

                }else {
                    System.out.println("记住密码没有选中");
                    sp.edit().putBoolean("ISCHECK", false).commit();
                }
            }
        });

        cb_auto_login.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (cb_auto_login.isChecked()) {
                    System.out.println("自动登录已选中");
                    sp.edit().putBoolean("AUTO_ISCHECK", true).commit();

                } else {
                    System.out.println("自动登录没有选中");
                    sp.edit().putBoolean("AUTO_ISCHECK", false).commit();
                }
            }
        });
    }
}
