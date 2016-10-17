package com.lengfeng.vegetablesshopping;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;

public class SpecialPriceActivity extends Activity implements OnClickListener, TextWatcher{
	
	private ImageView detail_back;
	private Button bt_sp_jia;
	private Button bt_sp_qiang;
	private PopupWindow window;
	private Button bt_add;
	private Button bt_subbt;
	private EditText edt_shuzi;
	private View view;
	private SliderLayout home_slider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sale_price);
		initView();
		setOnListener();		
		init();
	}

	private void initView() {
		detail_back = (ImageView) findViewById(R.id.detail_back);
		bt_sp_jia = (Button) findViewById(R.id.bt_sp_jia);
		bt_sp_qiang = (Button) findViewById(R.id.bt_sp_qiang);
		home_slider = (SliderLayout) findViewById(R.id.home_slider);
	}
	
	private void setOnListener() {
		detail_back.setOnClickListener(this);
		bt_sp_jia.setOnClickListener(this);
		bt_sp_qiang.setOnClickListener(this);	
	}
	
	private void init() {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.detail_back:
			finish();
			break;
		case R.id.bt_sp_jia:
			showPopwindow();
			break;
		case R.id.bt_sp_qiang:
			Intent intent = new Intent(SpecialPriceActivity.this,ConfirmActivity.class);
			startActivity(intent);
			break;
		case R.id.bt_add:
			
			break;
		case R.id.bt_subbt:
			
			break;
		}	
	}
	
	int num = 1;
	private void showPopwindow() {
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.purchase_window, null);
		bt_subbt = (Button) view.findViewById(R.id.bt_subbt);
		bt_add = (Button) view.findViewById(R.id.bt_add);
		edt_shuzi = (EditText) view.findViewById(R.id.edt_shuzi);
		bt_subbt.setOnClickListener(new OnButtonClickListener());
		bt_add.setOnClickListener(new OnButtonClickListener());
		edt_shuzi.addTextChangedListener(this);
		edt_shuzi.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
		edt_shuzi.setText(String.valueOf(num));
		bt_add.setTag("+");
		bt_subbt.setTag("-");
		
		window = new PopupWindow(view,
				WindowManager.LayoutParams.MATCH_PARENT,
				WindowManager.LayoutParams.WRAP_CONTENT);
		
		window.setFocusable(true);
		
		ColorDrawable dw = new ColorDrawable(Color.WHITE);
	    window.setBackgroundDrawable(dw);
	    
	    window.showAtLocation(SpecialPriceActivity.this.findViewById(R.id.bt_sp_jia),
	    		Gravity.BOTTOM,0 , 0);
	    
	    window.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
	}
	
	
	class OnButtonClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {			
			String numString = edt_shuzi.getText().toString();
			if (numString == null || numString.equals("")) {
				num = 1;
				edt_shuzi.setText("1");
			} else {
				if (v.getTag().equals("+")) {
					if (++num < 1) // 先加，再判断
					{
						num--;
						Toast.makeText(SpecialPriceActivity.this, "请输入一个大于0的数字", Toast.LENGTH_SHORT).show();
					} else {
						edt_shuzi.setText(String.valueOf(num));
						//ttt.setText(edt_shuzi.getText());
					}
				} else if (v.getTag().equals("-")) {
					if (--num < 1) // 先减，再判断
					{
						num++;
						Toast.makeText(SpecialPriceActivity.this, "请输入一个大于0的数字", Toast.LENGTH_SHORT).show();
					} else {
						edt_shuzi.setText(String.valueOf(num));
						//ttt.setText(edt.getText());
					}
				}
			}
		}
	}

	@Override
	public void afterTextChanged(Editable s) {
		String numString = s.toString();
		if (numString == null || numString.equals("")) {
			num = 0;
			//ttt.setText(edt.getText());
		} else {
			int numInt = Integer.parseInt(numString);
			if (numInt < 0) {
				Toast.makeText(SpecialPriceActivity.this, "请输入一个大于0的数字", Toast.LENGTH_SHORT).show();
			} else {
				// 设置EditText光标位置 为文本末端
				edt_shuzi.setSelection(edt_shuzi.getText().toString().length());
				num = numInt;
				//ttt.setText(edt.getText());
			}
		}
	}
	
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {

		
	}

}
