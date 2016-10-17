package com.lengfeng.vegetablesshopping;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.lengfeng.vegetablesshopping.bean.PhoneCodeInfo;
import com.lengfeng.vegetablesshopping.bean.PhoneCodeResp;
import com.lengfeng.vegetablesshopping.bean.RegisterInfo;
import com.lengfeng.vegetablesshopping.bean.RegisterInfoBean;
import com.lengfeng.vegetablesshopping.bean.RegisterResp;
import com.lengfeng.vegetablesshopping.common.Constant;
import com.lengfeng.vegetablesshopping.utils.MD5Utils;
import com.lengfeng.vegetablesshopping.utils.MobileUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by Administrator on 2016/7/12 0012.
 */
public class RegisterActivity extends FragmentActivity implements View.OnClickListener {

    boolean isValid = false;
    public boolean isChange = false;
    private boolean tag = true;
    Thread thread = null;
    private int i = 60;
    //客户端输入的验证码
    private String valicationCode;
    private EditText inputPhoneEt;
    private EditText inputCodeEt;
    private Button requestCodeBtn;
    private Button commitBtn;
    private String userName;
    final OkHttpClient client = new OkHttpClient();
    String secret= MD5Utils.MD5(Constant.APP_KEY)+Constant.VERSION_NO;
    private ImageView iv_register_back;
    private String name;
    private String code;
    private String responseCode;
    private String code1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init() {
        inputPhoneEt = (EditText) findViewById(R.id.et_input_phone);
        inputCodeEt = (EditText) findViewById(R.id.et_input_code);
        requestCodeBtn = (Button) findViewById(R.id.bt_code);
        iv_register_back = (ImageView) findViewById(R.id.iv_register_back);
        commitBtn = (Button) findViewById(R.id.bt_commit);
        requestCodeBtn.setOnClickListener(this);
        commitBtn.setOnClickListener(this);
        iv_register_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        name = inputPhoneEt.getText().toString().trim();
        code = inputCodeEt.getText().toString().trim();
        switch (v.getId()){
            case R.id.bt_code:
                if(MobileUtils.isMobileNO(name)){
                    if(!isvalidate());
                    requestCodeBtn.setText("获取验证码");
                    requestCodeBtn.setClickable(true);
                    isChange = true;
                    changeBtnGetCode();
                    getValidateCode();

                    RequestBody formBody = new FormBody.Builder()
                            .add("mobile",inputPhoneEt.getText().toString().trim())
                            .add("secret",secret)
                            .build();

                    final Request request = new Request.Builder()
                            .url("http://ecshop.wujingen.com/api.php?act=mobile_code")
                            .post(formBody)
                            .build();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Response response = client.newCall(request).execute();
                                if (response.isSuccessful()) {
                                    responseCode = response.body().string();
                                    Gson gson = new Gson();
                                    PhoneCodeResp phoneCodeResp = gson.fromJson(responseCode, PhoneCodeResp.class);
                                    PhoneCodeInfo phoneCodeInfo = phoneCodeResp.getInfo();
                                    code1 = phoneCodeInfo.getCode();
                                } else {
                                    throw new IOException("Unexpected code " + response);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }else {
                    Toast.makeText(RegisterActivity.this,"您输入的手机号码有误",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_commit:

                RequestBody formBody = new FormBody.Builder()
                        .add("mobile",inputPhoneEt.getText().toString().trim())
                        .add("code",code)
                        .add("secret",secret)
                        .build();

                final Request request = new Request.Builder()
                        .url("http://ecshop.wujingen.com/api.php?act=mobile_code")
                        .post(formBody)
                        .build();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Response rpCode = client.newCall(request).execute();
                            if (rpCode.isSuccessful()){
                                String phoneCode = rpCode.body().string();
                                Log.i("QQ","手机验证码的请求" + phoneCode);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                register();
                break;
            case R.id.iv_register_back:
                finish();
                break;
        }
    }

    private boolean getValidateCode() {
        name = inputPhoneEt.getText().toString().trim();
        code = inputCodeEt.getText().toString().trim();
        if (name.equals("")) {
            Toast.makeText(this, "请输入用户名或手机号!", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            userName = name;
            valicationCode = code;
            Thread codeThread = new Thread(new Runnable() {
                @Override

                public void run() {

                }
            });
            codeThread.start();
        }
        return true;
    }

    private void changeBtnGetCode() {
        Thread thread =  new Thread(){
            public void run(){
                if (tag) {
                    while (i > 0) {
                        i--;
                        if (RegisterActivity.this == null) {
                            break;
                        }
                        RegisterActivity.this
                                .runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        requestCodeBtn.setText("获取验证码("
                                                + i + ")");
                                        requestCodeBtn
                                                .setClickable(false);
                                    }
                                });
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    tag = false;
                }
                i = 60;
                tag = true;
                if (RegisterActivity.this != null) {
                    RegisterActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            requestCodeBtn.setText("获取验证码");
                            requestCodeBtn.setClickable(true);
                        }
                    });
                }
            }
        };
        thread.start();
    }

    private boolean isvalidate() {
        String userName = inputPhoneEt.getText().toString().trim();
        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void register() {
        try {
            JSONObject jobj = new JSONObject(responseCode);

            String result = jobj.getString("result");
            if ("success".equals(result))
            {
                String code = jobj.getJSONObject("info").getString("code");
                String inputCode = inputCodeEt.getText().toString().trim();
                if(inputCode != null && code.equals(inputCode)){
                    Intent pwdIntent = new Intent(RegisterActivity.this,SettingPwdActivity.class);
                    pwdIntent.putExtra("name",name);
                    startActivity(pwdIntent);
                }else {
                    Toast.makeText(RegisterActivity.this,"您输入的验证码有错误"+code,Toast.LENGTH_SHORT).show();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:

                    break;
            }
            super.handleMessage(msg);
        }
    };
}
