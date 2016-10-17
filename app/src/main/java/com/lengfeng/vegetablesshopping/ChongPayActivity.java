package com.lengfeng.vegetablesshopping;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.lengfeng.vegetablesshopping.pay.PayDemoActivity;

public class ChongPayActivity extends Activity implements View.OnClickListener
{

	private ImageView iv_return;
	private Button bt_pay_money;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay_chong);
		init();
		setOnListener();
	}

	private void setOnListener() {
		iv_return.setOnClickListener(this);
		bt_pay_money.setOnClickListener(this);
	}

	private void init() {
		iv_return = (ImageView) findViewById(R.id.iv_return);
		bt_pay_money = (Button) findViewById(R.id.bt_pay_money);
	}


	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.iv_return:
				finish();
				break;

			case R.id.bt_pay_money:
				Intent intent = new Intent(this,PayDemoActivity.class);
				startActivity(intent);
				break;
		}
	}
}
