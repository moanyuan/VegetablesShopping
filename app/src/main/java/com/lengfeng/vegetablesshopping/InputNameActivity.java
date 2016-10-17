package com.lengfeng.vegetablesshopping;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by Administrator on 2016/8/5 0005.
 */
public class InputNameActivity extends FragmentActivity implements View.OnClickListener {

    private ImageView iv_input_back;
    private TextView tv_save_name;
    private EditText et_input_name;
    private String inputName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_name);
        init();
        setListener();
    }

    private void setListener() {
        iv_input_back.setOnClickListener(this);
        tv_save_name.setOnClickListener(this);
    }

    private void init() {
        iv_input_back = (ImageView) findViewById(R.id.iv_input_back);
        tv_save_name = (TextView) findViewById(R.id.tv_save_name);
        et_input_name = (EditText) findViewById(R.id.et_input_name);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_input_back:
                finish();
                break;
            case R.id.tv_save_name:
                inputName = et_input_name.getText().toString().trim();

                Intent intent = new Intent(InputNameActivity.this,SettingActivity.class);
                /*Bundle inputBundle = new Bundle();
                inputBundle.putString("name",inputName);
                intent.putExtras(inputBundle);*/


                if (inputName !=null){
                    //SPUtils.put(InputNameActivity.this,"inputName",inputName);
                    SharedPreferences sp = getSharedPreferences("inputName", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("inputName",inputName);
                    editor.commit();
                    intent.putExtra("inputName", inputName);
                    startActivityForResult(intent,2);
                }else{
                    Toast.makeText(InputNameActivity.this,"您输入的昵称不能为空哦",Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==2&resultCode==1) {
            String stringExtra = data.getStringExtra("inputName");
            tv_save_name.setText(stringExtra);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
