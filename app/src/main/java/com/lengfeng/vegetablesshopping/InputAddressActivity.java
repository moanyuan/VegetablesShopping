package com.lengfeng.vegetablesshopping;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/9/14 0014.
 */
public class InputAddressActivity extends FragmentActivity implements View.OnClickListener {

    private ImageView iv_input_back2;
    private TextView tv_save_name;
    private EditText et_input_name;
    private String inputName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_address);
        init();
        setListener();
    }

    private void init() {
        iv_input_back2 = (ImageView) findViewById(R.id.iv_input_back2);
        tv_save_name = (TextView) findViewById(R.id.tv_save_name);
        et_input_name = (EditText) findViewById(R.id.et_input_name);
    }

    private void setListener() {
        iv_input_back2.setOnClickListener(this);
        tv_save_name.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_input_back2:
                finish();
                break;
            case R.id.tv_save_name:
                inputName = et_input_name.getText().toString().trim();

                Intent intent = new Intent(this, SettingActivity.class);

                if (inputName != null) {
                    //SPUtils.put(InputNameActivity.this,"inputName",inputName);
                    SharedPreferences sp = getSharedPreferences("address", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("address", inputName);
                    editor.commit();
                    intent.putExtra("address", inputName);
                    startActivityForResult(intent, 3);
                } else {
                    Toast.makeText(this, "您输入的地址不能为空哦", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
