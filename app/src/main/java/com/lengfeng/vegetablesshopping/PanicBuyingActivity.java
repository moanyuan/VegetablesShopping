package com.lengfeng.vegetablesshopping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.gson.Gson;
import com.lengfeng.vegetablesshopping.bean.ProductDetailInfo;
import com.lengfeng.vegetablesshopping.bean.ProductDetailInfoBean;
import com.lengfeng.vegetablesshopping.bean.ProductDetailResp;
import com.lengfeng.vegetablesshopping.common.Constant;
import com.lengfeng.vegetablesshopping.utils.MD5Utils;

import de.greenrobot.event.EventBus;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PanicBuyingActivity extends Activity implements OnClickListener, TextWatcher{

	String secret = MD5Utils.MD5(Constant.APP_KEY)+Constant.VERSION_NO;
	private ImageView detail_back;
	private ImageView detail_cart;
	private Button bt_panic_jia;
	private Button bt_panic_qiang;
	private Button bt_add;
	private Button bt_subbt;
	private EditText edt_shuzi;
	private View view;
	private PopupWindow window;
	OkHttpClient client = new OkHttpClient();
	private TextView tv_qg;
	private TextView tv_market_price;
	private TextView tv_end_date;
	private ProductDetailInfo productDetailInfo;
	private ProductDetailInfoBean productDetailInfoBean;
	private SliderLayout home_slider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_panic_buy);
		initView();
		setOnListener();
		init();
		//initPanic();
		Message message = handler.obtainMessage(1);     // Message
		handler.sendMessageDelayed(message, 1000);

	}


	/*private void initPanic() {
		Intent intent =getIntent();
		String goods_id = intent.getStringExtra("goods_id");
		Log.i("QQ","接收的id"+goods_id);

		final RequestBody formBody = new FormBody.Builder()
				.add("goods_id",goods_id)
				.add("secret",secret)
				.build();

		final Request request = new Request.Builder()
				.url("http://rifulai.wujingen.com/api.php?act=get_goods_detail")
				.post(formBody)
				.build();

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Response rp = client.newCall(request).execute();
					Log.i("RR","商品详情"+ rp);
					String result = rp.body().string();
					Log.i("RR","商品post数据"+ result);
					Gson gson = new Gson();
					ProductDetailResp productDetailResp = gson.fromJson(result, ProductDetailResp.class);
					productDetailInfoBean = productDetailResp.getInfo();
					productDetailInfo = productDetailInfoBean.getData_info();
					Log.i("QQ","商品价格"+productDetailInfo.getFinal_price());

					handler.post(new Runnable() {
						@Override
						public void run() {
							Message msg = new Message();
							msg.what = 1;
							handler.sendMessage(msg);
						}
					});
				} catch (IOException e) {

				}
			}
		}).start();
	}*/

	private void initView() {
		detail_back = (ImageView) findViewById(R.id.detail_back);
		detail_cart = (ImageView) findViewById(R.id.detail_cart);
		bt_panic_jia = (Button) findViewById(R.id.bt_panic_jia);
		bt_panic_qiang = (Button) findViewById(R.id.bt_panic_qiang);
		tv_qg = (TextView) findViewById(R.id.tv_qg);
		tv_market_price = (TextView) findViewById(R.id.tv_market_price);
		tv_end_date = (TextView) findViewById(R.id.tv_end_date);
		home_slider = (SliderLayout) findViewById(R.id.home_slider);
	}
	
	private void setOnListener() {
		detail_back.setOnClickListener(this);
		detail_cart.setOnClickListener(this);
		bt_panic_jia.setOnClickListener(this);
		bt_panic_qiang.setOnClickListener(this);
	}
	
	private void init() {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.detail_back:
			finish();
			break;
		case R.id.bt_panic_jia:
			showPopwindow();
			break;
		case R.id.bt_panic_qiang:
			Intent confirmIntent = new Intent(PanicBuyingActivity.this,ConfirmActivity.class);
			startActivity(confirmIntent);
			break;
		}
	}

	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what){
				case 1:
					/*tv_qg.setText(productDetailInfoBean.getData_info().getFinal_price());
					tv_market_price.setText(productDetailInfo.getMarket_price());
					TextSliderView textSliderView1 = new TextSliderView(PanicBuyingActivity.this);
					textSliderView1.image(productDetailInfo.getImage_default());*/
					break;
			}
			super.handleMessage(msg);
		}
	};

	int num = 0;
	private Button bt_dibu;
	private void showPopwindow() {
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.purchase_window, null);
		bt_dibu = (Button) view.findViewById(R.id.bt_dibu);
		bt_subbt = (Button) view.findViewById(R.id.bt_subbt);
		bt_add = (Button) view.findViewById(R.id.bt_add);
		bt_dibu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				view.setVisibility(View.GONE);

			}
		});
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
	    
	    window.showAtLocation(PanicBuyingActivity.this.findViewById(R.id.bt_panic_jia),
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
						Toast.makeText(PanicBuyingActivity.this, "请输入一个大于0的数字", Toast.LENGTH_SHORT).show();
					} else {
						edt_shuzi.setText(String.valueOf(num));
						//ttt.setText(edt_shuzi.getText());
					}
				} else if (v.getTag().equals("-")) {
					if (--num < 1) // 先减，再判断
					{
						num++;
						Toast.makeText(PanicBuyingActivity.this, "请输入一个大于0的数字", Toast.LENGTH_SHORT).show();
					} else {
						edt_shuzi.setText(String.valueOf(num));
						//ttt.setText(edt.getText());
					}
				}
			}
		}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		
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
				Toast.makeText(PanicBuyingActivity.this, "请输入一个大于0的数字", Toast.LENGTH_SHORT).show();
			} else {
				// 设置EditText光标位置 为文本末端
				edt_shuzi.setSelection(edt_shuzi.getText().toString().length());
				num = numInt;
				//ttt.setText(edt.getText());
			}
		}
	}
}
