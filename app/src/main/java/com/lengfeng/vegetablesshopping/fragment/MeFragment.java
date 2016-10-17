package com.lengfeng.vegetablesshopping.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lengfeng.vegetablesshopping.ChongPayActivity;
import com.lengfeng.vegetablesshopping.LoginActivity;
import com.lengfeng.vegetablesshopping.OrderActivity;
import com.lengfeng.vegetablesshopping.R;
import com.lengfeng.vegetablesshopping.RechargeActivity;
import com.lengfeng.vegetablesshopping.RegisterActivity;
import com.lengfeng.vegetablesshopping.SettingActivity;

public class MeFragment extends Fragment implements OnClickListener{

	private View view;
	private ImageView iv_person;
	private RelativeLayout rl_dingdan;
	private LinearLayout ll_pay;
	private LinearLayout ll_fahuo;
	private LinearLayout ll_shouhuo;
	private LinearLayout ll_wancheng;
	private LinearLayout ll_huiyuan;
	private LinearLayout ll_chongzhi;
	private LinearLayout ll_setting;
	private Button bt_login;
	private Button bt_register;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_me,null);
		initView();
		setOnListener();
        return view;
	}

	private void initView() {
		bt_login = (Button) view.findViewById(R.id.bt_login);
		bt_register = (Button) view.findViewById(R.id.bt_register);
		rl_dingdan = (RelativeLayout) view.findViewById(R.id.rl_dingdan);
		ll_pay = (LinearLayout) view.findViewById(R.id.ll_pay);
		ll_fahuo = (LinearLayout) view.findViewById(R.id.ll_fahuo);
		ll_shouhuo = (LinearLayout) view.findViewById(R.id.ll_shouhuo);
		ll_wancheng = (LinearLayout) view.findViewById(R.id.ll_wancheng);
		ll_huiyuan = (LinearLayout) view.findViewById(R.id.ll_huiyuan);
		ll_chongzhi = (LinearLayout) view.findViewById(R.id.ll_chongzhi);
		ll_setting = (LinearLayout) view.findViewById(R.id.ll_setting);
	}
	
	private void setOnListener() {
		bt_login.setOnClickListener(this);
		bt_register.setOnClickListener(this);
		rl_dingdan.setOnClickListener(this);
		ll_pay.setOnClickListener(this);
		ll_fahuo.setOnClickListener(this);
		ll_shouhuo.setOnClickListener(this);
		ll_wancheng.setOnClickListener(this);
		ll_huiyuan.setOnClickListener(this);
		ll_chongzhi.setOnClickListener(this);
		ll_setting.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.bt_login:
				Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
				startActivity(loginIntent);
				break;
			case R.id.bt_register:
				Intent registerIntent = new Intent(getActivity(), RegisterActivity.class);
				startActivity(registerIntent);
				break;
			case R.id.rl_dingdan:
				Intent orderIntent = new Intent(getActivity(),OrderActivity.class);
				startActivity(orderIntent);
				break;
			case R.id.ll_pay:

				break;
			case R.id.ll_fahuo:

				break;
			case R.id.ll_shouhuo:

				break;
			case R.id.ll_wancheng:

				break;
			case R.id.ll_huiyuan:
			Intent rechargeIntent = new Intent(getActivity(),RechargeActivity.class);
			startActivity(rechargeIntent );
				break;

			case R.id.ll_chongzhi:
				Intent payIntent = new Intent(getActivity(),ChongPayActivity.class);
				startActivity(payIntent );
				break;
			case R.id.ll_setting:
				Intent settingIntent = new Intent(getActivity(), SettingActivity.class);
				startActivity(settingIntent);
				break;

		}
		
	}
	
}
