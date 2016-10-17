package com.lengfeng.vegetablesshopping;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lengfeng.vegetablesshopping.bean.AddressInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/8/2 0002.
 */
public class AddressChooseActivity extends FragmentActivity implements View.OnClickListener {

    private AddressInfo myAddress;
    private ImageView iv_address_back;
    private EditText et_consignee;
    private EditText et_person_phone;
    private EditText et_person_address;
    private EditText et_person_street;
    private EditText et_person_content;
    private TextView tv_save_address;
    List<AddressInfo> addressInfo = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keep_address);
        init();

        myAddress = new AddressInfo();
        setOnListener();
    }

    private void setOnListener() {
        iv_address_back.setOnClickListener(this);
        tv_save_address.setOnClickListener(this);
    }

    private void init() {
        iv_address_back = (ImageView) findViewById(R.id.iv_address_back);
        tv_save_address = (TextView) findViewById(R.id.tv_save_address);
        et_consignee = (EditText) findViewById(R.id.et_consignee);
        et_person_phone = (EditText) findViewById(R.id.et_person_phone);
        et_person_address = (EditText) findViewById(R.id.et_person_address);
        et_person_street = (EditText) findViewById(R.id.et_person_street);
        et_person_content = (EditText) findViewById(R.id.et_person_content);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_address_back:
                finish();
                break;
            case R.id.tv_save_address:
                myAddress.setStreet(et_person_street.getText().toString().trim());
                myAddress.setName(et_consignee.getText().toString().trim());
                myAddress.setPhone(et_person_phone.getText().toString().trim());
                myAddress.setProvinces(et_person_address.getText().toString().trim());
                myAddress.setContent(et_person_content.getText().toString().trim());

                addressInfo.add(myAddress);
                Intent i = new Intent(AddressChooseActivity.this,AddressActivity.class);
                i.putExtra("addressInfo", (Serializable) addressInfo);
                startActivity(i);

                if (myAddress.getPhone().length() > 0) {
                    et_person_phone.setVisibility(View.GONE);
                }
                tv_save_address.requestFocus();
                tv_save_address.setFocusable(true);
                tv_save_address.setFocusableInTouchMode(true);
                break;
        }
    }
}
