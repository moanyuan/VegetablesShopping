package com.lengfeng.vegetablesshopping;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class RechargeActivity extends Activity implements View.OnClickListener {

	private ImageView rc_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recharge);
		init();
		setOnListener();
	}

	private void setOnListener() {
		rc_back.setOnClickListener(this);
	}

	private void init() {
		rc_back = (ImageView) findViewById(R.id.rc_back);
	}


	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.rc_back:
				finish();
				break;
		}
	}
}
