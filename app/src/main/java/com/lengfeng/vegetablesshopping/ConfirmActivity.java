package com.lengfeng.vegetablesshopping;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lengfeng.vegetablesshopping.bean.Product;
import com.lengfeng.vegetablesshopping.pay.PayDemoActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/6 0006.
 */
public class ConfirmActivity extends FragmentActivity implements View.OnClickListener {

    private RelativeLayout rl_address;
    private Button iv_add;
    private Button iv_produce;
    private RelativeLayout rl_delivery;
    private Button bt_add_order;
    private Button bt_total_price;
    private TextView tv_amount;
    private TextView tv_price_01;
    private TextView tv_product_01;
    private TextView tv_ride;
    private Button bt_total_price1;
    private ImageView iv_confirm_back;
    List<Product> datas = new ArrayList<Product>();
    int num = 0;
    private CheckBox weChat;
    private CheckBox alipay;
    private Button bt_affirm_pay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        initView();
        setListenter();
    }

    private void initView() {
        iv_confirm_back = (ImageView) findViewById(R.id.iv_confirm_back);
        rl_address = (RelativeLayout) findViewById(R.id.rl_address);
        iv_add = (Button) findViewById(R.id.iv_add);
        tv_amount = (TextView) findViewById(R.id.tv_amount);
        iv_produce = (Button) findViewById(R.id.iv_produce);
        rl_delivery = (RelativeLayout) findViewById(R.id.rl_delivery);
        bt_add_order = (Button) findViewById(R.id.bt_add_order);
        bt_total_price = (Button) findViewById(R.id.bt_total_price);
        tv_price_01 = (TextView) findViewById(R.id.tv_price_01);
        tv_product_01 = (TextView) findViewById(R.id.tv_product_01);
        tv_ride = (TextView) findViewById(R.id.tv_ride);
        bt_total_price1 = (Button) findViewById(R.id.bt_total_price);
        tv_amount.setText(String.valueOf(num));
        iv_add.setTag("+");
        iv_produce.setTag("-");
    }

    private void setListenter() {
        rl_address.setOnClickListener(this);
        iv_add.setOnClickListener(new OnButtonClickListener());
        iv_produce.setOnClickListener(new OnButtonClickListener());
        rl_delivery.setOnClickListener(this);
        bt_add_order.setOnClickListener(this);
        iv_confirm_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_confirm_back:
                finish();
                break;
            case R.id.rl_address:
                Intent addressIntent = new Intent(ConfirmActivity.this,AddressActivity.class);
                startActivity(addressIntent);
                break;
            case R.id.bt_add_order:
                showWindow();
                break;
        }
    }

    private void showWindow() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View roots = inflater.inflate(R.layout.pay_window, null);
        weChat = (CheckBox) roots.findViewById(R.id.WeChat);
        alipay = (CheckBox) roots.findViewById(R.id.alipay);
        bt_affirm_pay = (Button) roots.findViewById(R.id.bt_affirm_pay);

        alipay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    bt_affirm_pay.setOnClickListener(new OnButtonClickListener(){
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ConfirmActivity.this, PayDemoActivity.class);
                            startActivity(intent);
                        }
                    });
                }else{

                }
            }
        }) ;

        PopupWindow window = new PopupWindow(roots,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

        window.setFocusable(true);

        ColorDrawable dw = new ColorDrawable(Color.WHITE);
        window.setBackgroundDrawable(dw);

        window.showAtLocation(ConfirmActivity.this.findViewById(R.id.bt_add_order),
                Gravity.BOTTOM,0 , 0);

        window.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    class OnButtonClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Object tag = v.getTag();
            String numString = tv_amount.getText().toString();
            if (numString == null || numString.equals("")) {
                num = 1;
                tv_amount.setText("1");
            } else {
                if (tag.equals("+")) {
                    if (++num < 1) // 先加，再判断
                    {
                        num--;

                    } else {
                        tv_amount.setText(String.valueOf(num));
                    }
                } else if (tag.equals("-")) {
                    if (--num < 1) // 先减，再判断
                    {
                        num++;

                    } else {
                        tv_amount.setText(String.valueOf(num));
                    }
                }
            }
        }
    }
}
