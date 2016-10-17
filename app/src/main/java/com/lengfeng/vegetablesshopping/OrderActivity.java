package com.lengfeng.vegetablesshopping;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import com.lengfeng.vegetablesshopping.fragment.CompletedFragment;
import com.lengfeng.vegetablesshopping.fragment.ObligationFragment;
import com.lengfeng.vegetablesshopping.fragment.ReceivingFragment;
import com.lengfeng.vegetablesshopping.fragment.SendOutFragment;
import com.viewpagerindicator.TabPageIndicator;
import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends FragmentActivity implements View.OnClickListener {
	
	Boolean hasInit = false;

	List<Fragment> fragmentList = new ArrayList<Fragment>();
	private ImageView order_back;
	private TabPageIndicator order_tpi;
	private ViewPager order_vp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!hasInit) {
			setContentView(R.layout.activity_dingdan);
			order_back = (ImageView) findViewById(R.id.order_back);
			order_tpi = (TabPageIndicator) findViewById(R.id.order_tpi);
			order_vp = (ViewPager) findViewById(R.id.order_vp);
		}
		order_back.setOnClickListener(this);
		initData();
	}

	private void initData() {
		ObligationFragment obligationFragment = new ObligationFragment();
		ReceivingFragment receivingFragment = new ReceivingFragment();
		SendOutFragment sendOutFragment = new SendOutFragment();
		CompletedFragment completedFragment = new CompletedFragment();
		fragmentList.add(obligationFragment);
		fragmentList.add(receivingFragment);
		fragmentList.add(sendOutFragment);
		fragmentList.add(completedFragment);
		order_vp.setAdapter(new MyAdapter(getSupportFragmentManager()));
		order_tpi.setViewPager(order_vp);
		order_tpi.setVisibility(View.VISIBLE);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.order_back:
				finish();
				break;
		}
	}

	String[] pageNames = new String[]{"待付款","待发货","待收货","已完成"};
	class MyAdapter extends FragmentPagerAdapter{

		public MyAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return fragmentList.get(position);
		}

		@Override
		public int getCount() {
			return fragmentList.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return pageNames[position];
		}
	}
}
