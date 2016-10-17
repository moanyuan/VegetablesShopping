package com.lengfeng.vegetablesshopping;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lengfeng.vegetablesshopping.fragment.ClassifyFragment;
import com.lengfeng.vegetablesshopping.fragment.HomeFragment;
import com.lengfeng.vegetablesshopping.fragment.MeFragment;
import com.lengfeng.vegetablesshopping.fragment.ShoppingFragment;


public class MainActivity extends FragmentActivity implements OnClickListener{

	private FrameLayout content;
	private ImageView iv_Home;
	private TextView tv_Home;
	private LinearLayout ll_Home;
	private ImageView iv_classify;
	private TextView tv_classify;
	private LinearLayout ll_classify;
	private ImageView iv_Me;
	private TextView tv_Me;
	private LinearLayout ll_Me;
	private ImageView iv_shopping;
	private TextView tv_shopping;
	private LinearLayout ll_shopping;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		setOnListener();
		initData();
	}

	private void initView() {
		content = (FrameLayout) findViewById(R.id.content);
		iv_Home = (ImageView) findViewById(R.id.iv_home);
		tv_Home = (TextView) findViewById(R.id.tv_home);
		ll_Home = (LinearLayout) findViewById(R.id.ll_home);
		iv_classify = (ImageView) findViewById(R.id.iv_community);
		tv_classify = (TextView) findViewById(R.id.tv_community);
		ll_classify = (LinearLayout) findViewById(R.id.ll_community);
		iv_Me = (ImageView) findViewById(R.id.iv_me);
		tv_Me = (TextView) findViewById(R.id.tv_me);
		ll_Me = (LinearLayout) findViewById(R.id.ll_me);
		iv_shopping = (ImageView) findViewById(R.id.iv_shopping);
		tv_shopping = (TextView) findViewById(R.id.tv_shopping);
		ll_shopping = (LinearLayout) findViewById(R.id.ll_shopping);
	}
	
	private void setOnListener() {
		ll_Home.setOnClickListener(this);
		ll_classify.setOnClickListener(this);
		ll_shopping.setOnClickListener(this);
		ll_Me.setOnClickListener(this);
	}

	private void initData() {
		setSelect(0);
	}

	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_home:
			setSelect(0);
			break;
		case R.id.ll_community:
             setSelect(1);
             break;
        case R.id.ll_shopping:
             setSelect(2);
             break;
        case R.id.ll_me:
             setSelect(3);
             break;
			
		}
	}
	
	HomeFragment homeFragment = null;
    ClassifyFragment classifyFragment = null;
    MeFragment meFragment = null;
    ShoppingFragment shoppingFragment = null;
	private FragmentTransaction ft;
	
	private void setSelect(int i) {
		FragmentManager fm = getSupportFragmentManager();
	    ft = fm.beginTransaction();
		hideFragments();
		resetImgs();
		switch (i) {
		case 0:
			if (homeFragment == null) {
                homeFragment = new HomeFragment();
                ft.add(R.id.content, homeFragment);
            }
            iv_Home.setImageResource(R.drawable.cai_05);
            tv_Home.setTextColor(getResources().getColor(R.color.home_back_selected));
            ft.show(homeFragment);
			break;
		case 1:
            if (classifyFragment == null) {
            	classifyFragment = new ClassifyFragment();
                ft.add(R.id.content, classifyFragment);
            }
            iv_classify.setImageResource(R.drawable.cai_06);
            tv_classify.setTextColor(getResources().getColor(R.color.home_back_selected));
            ft.show(classifyFragment);
            break;
		case 2:
            if (shoppingFragment == null) {
            	shoppingFragment = new ShoppingFragment();
                ft.add(R.id.content, shoppingFragment);
            }
            iv_shopping.setImageResource(R.drawable.cai_07);
            tv_shopping.setTextColor(getResources().getColor(R.color.home_back_selected));
            ft.show(shoppingFragment);
            break;
        case 3:
            if (meFragment == null) {
                meFragment = new MeFragment();
                ft.add(R.id.content, meFragment);
            }
            iv_Me.setImageResource(R.drawable.cai_08);
            tv_Me.setTextColor(getResources().getColor(R.color.home_back_selected));
            ft.show(meFragment);
            break;
		}
		ft.commit();
	}

	private void resetImgs() {
		iv_Home.setImageResource(R.drawable.cai_01);
		iv_classify.setImageResource(R.drawable.cai_02);
        iv_shopping.setImageResource(R.drawable.cai_03);
        iv_Me.setImageResource(R.drawable.cai_04);
        tv_Home.setTextColor(getResources().getColor(R.color.home_back_unselected));
        tv_classify.setTextColor(getResources().getColor(R.color.home_back_unselected));
        tv_shopping.setTextColor(getResources().getColor(R.color.home_back_unselected));
        tv_Me.setTextColor(getResources().getColor(R.color.home_back_unselected));
	}

	private void hideFragments() {
		if (homeFragment != null) {
            ft.hide(homeFragment);
        }
		if (classifyFragment != null) {
	        ft.hide(classifyFragment);
	    }
        if (shoppingFragment != null) {
            ft.hide(shoppingFragment);
        }
        if (meFragment != null) {
            ft.hide(meFragment);
        }
	}
}
