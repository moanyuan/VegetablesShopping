package com.lengfeng.vegetablesshopping.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lengfeng.vegetablesshopping.R;
import com.lengfeng.vegetablesshopping.bean.ClassIfyInfoBean;
import com.lengfeng.vegetablesshopping.bean.ClassifyInfo;
import com.lengfeng.vegetablesshopping.bean.ClassifyItemResp;
import com.lengfeng.vegetablesshopping.bean.Model;
import com.lengfeng.vegetablesshopping.common.Constant;
import com.lengfeng.vegetablesshopping.utils.MD5Utils;

import java.io.IOException;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class ClassifyFragment extends Fragment {

	private ScrollView scrollView;
	private LayoutInflater root;
	private String[] menuslists;
	private View view;
	private TextView[] tvList;
	private View[] views;
	private int currentItem = 0;
	private String typename;
	//private ArrayList<Classify> list;
	private ShopAdapter shopAdapter;
	private ViewPager viewpager;
	String secret = MD5Utils.MD5(Constant.APP_KEY)+Constant.VERSION_NO;
	OkHttpClient client = new OkHttpClient();
	private List<ClassIfyInfoBean> classIfyInfoBeen;
	private TextView textView;
	private LinearLayout toolsLayout;
	private int i;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_classify,null);
		scrollView = (ScrollView) view.findViewById(R.id.tools_scrlllview);
		shopAdapter = new ShopAdapter(getFragmentManager());
		root = LayoutInflater.from(getActivity());	
		showToolsView();
		initView();
		initPager();
		return view;
	}

	private void initView() {
		final String orderby = "sort_order";
		final String sort = "ASC";

		final RequestBody formBody = new FormBody.Builder()
				.add("orderby",orderby)
				.add("sort", sort)
				.add("secret",secret)
				.build();

		final Request request = new Request.Builder()
				.url("http://rifulai.wujingen.com/api.php?act=goods_category")
				.post(formBody)
				.build();

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Response response = client.newCall(request).execute();
					String classifyCode = response.body().string();
					Log.i("EE","分类id" + classifyCode);
					Gson gson = new Gson();
					ClassifyItemResp classifyItemResp = gson.fromJson(classifyCode, ClassifyItemResp.class);
					classIfyInfoBeen = classifyItemResp.getInfo();
					for (i = 0; i < classIfyInfoBeen.size(); i++) {
						Log.i("EE","分类名称" + classIfyInfoBeen.get(i).getCat_name());
					}

				} catch (IOException e) {

				}
			}
		}).start();

		handler.post(new Runnable() {
			@Override
			public void run() {
				Message msg = new Message();
				msg.what = 1;
				handler.sendMessage(msg);
			}
		});
	}

	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what){
				case 1:


					break;
			}
			super.handleMessage(msg);
		}
	};

	private void showToolsView() {
		menuslists = Model.menusList;
		toolsLayout = (LinearLayout) view.findViewById(R.id.tools);
		tvList = new TextView[menuslists.length];
		views = new View[menuslists.length];

		for (int i = 0; i < menuslists.length; i++) {
			View view = root.inflate(R.layout.item_text_layout, null);
			view.setId(i);
			view.setOnClickListener(toolsItemListener);
			textView = (TextView) view.findViewById(R.id.text);
			textView.setText(menuslists[i]);
			toolsLayout.addView(view);
			tvList[i] = textView;
			views[i] = view;
		}
		changeTextColor(0);
	}

	private View.OnClickListener toolsItemListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			viewpager.setCurrentItem(v.getId());
		}
	};

	/**
	 * initPager<br/>
	 * 初始化ViewPager控件相关内容
	 */
	private void initPager() {
		viewpager = (ViewPager)view.findViewById(R.id.goods_pager);
		viewpager.setAdapter(shopAdapter);
		viewpager.addOnPageChangeListener(onPageChangeListener);
	}

	/**
	 * OnPageChangeListener<br/>
	 * 监听ViewPager选项卡变化事的事件
	 */
	private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
		@Override
		public void onPageSelected(int arg0) {
			if (viewpager.getCurrentItem() != arg0)
				viewpager.setCurrentItem(arg0);
			if (currentItem != arg0) {
				changeTextColor(arg0);
				changeTextLocation(arg0);
			}
			currentItem = arg0;
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}
	};

	/**
	 * 改变栏目位置
	 *
	 * @param clickPosition
	 */
	private void changeTextLocation(int clickPosition) {
		int x = (views[clickPosition].getTop());
		scrollView.smoothScrollTo(0, x);
	}

	private void changeTextColor(int id) {
		for (int i = 0; i < tvList.length; i++) {
			if (i != id) {
				tvList[i].setBackgroundColor(0x00000000);
				tvList[i].setTextColor(0xFF000000);
			}
		}
		tvList[id].setBackgroundColor(0xFFFFFFFF);
		tvList[id].setTextColor(0xFFFF5D5E);
	}


	private class ShopAdapter extends FragmentPagerAdapter {

		public ShopAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public android.support.v4.app.Fragment getItem(int index) {
			ProTypeFragment fragment = new ProTypeFragment();
			Bundle bundle = new Bundle();
			bundle.putInt("index", index);
			fragment.setArguments(bundle);
			return fragment;
		}

		@Override
		public int getCount() {
			return menuslists.length;
		}
	}
}
