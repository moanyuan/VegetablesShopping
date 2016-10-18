package com.lengfeng.vegetablesshopping.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.gson.Gson;
import com.lengfeng.vegetablesshopping.PanicBuyingActivity;
import com.lengfeng.vegetablesshopping.R;
import com.lengfeng.vegetablesshopping.SpecialPriceActivity;
import com.lengfeng.vegetablesshopping.bean.FigureInfo;
import com.lengfeng.vegetablesshopping.bean.FigureInfoBean;
import com.lengfeng.vegetablesshopping.bean.FigureResp;
import com.lengfeng.vegetablesshopping.bean.NewProductInfo;
import com.lengfeng.vegetablesshopping.bean.NewProductInfoBean;
import com.lengfeng.vegetablesshopping.bean.NewProductResp;
import com.lengfeng.vegetablesshopping.bean.PanicInfo;
import com.lengfeng.vegetablesshopping.bean.PanicInfoBean;
import com.lengfeng.vegetablesshopping.bean.PanicResp;
import com.lengfeng.vegetablesshopping.bean.SpecailInfoBean;
import com.lengfeng.vegetablesshopping.bean.SpecialInfo;
import com.lengfeng.vegetablesshopping.bean.SpecialResp;
import com.lengfeng.vegetablesshopping.common.Constant;
import com.lengfeng.vegetablesshopping.utils.MD5Utils;
import com.squareup.picasso.Picasso;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class HomeFragment extends Fragment implements OnClickListener {

	String secret = MD5Utils.MD5(Constant.APP_KEY)+Constant.VERSION_NO;
	private int recLen = 300;
	private ImageView iv_sm;
	private TextView tv_sousuo;
	private ImageView iv_ding;
	private RelativeLayout rl_sousuo;
	private LinearLayout ll_maodou;
	private LinearLayout ll_gua;
	private LinearLayout ll_huang;
	private LinearLayout ll_sheng;
	private LinearLayout ll_jiao;
	private LinearLayout ll_siji;
	private LinearLayout ll_fanqie;
	private LinearLayout ll_muer;
	private LinearLayout ll_xiaoqingcai;
	private LinearLayout ll_xianggu;
	private TextView tv_time;
	OkHttpClient client = new OkHttpClient();
	private List<PanicInfo> item;
	private int HANDLER_TEST = 2;
	private View view;
	private TextView tv_maodou;
	private TextView tv_gua;
	private TextView tv_huang;
	private TextView tv_sheng;
	private TextView tv_siji;
	private TextView tv_jiao;
	private TextView tv_fanqie;
	private TextView tv_muer;
	private TextView tv_xiaoqingcai;
	private TextView tv_xianggu;
	private ImageView iv_maodou;
	private ImageView iv_gua;
	private ImageView iv_huang;
	private ImageView iv_sheng;
	private ImageView iv_siji;
	private ImageView iv_jiao;
	private ImageView iv_fanqie;
	private ImageView iv_muer;
	private ImageView iv_xiaoqingcai;
	private ImageView iv_xianggu;
	private TextView tv_tj_01;
	private TextView tv_tj_02;
	private TextView tv_tj_03;
	private List<SpecialInfo> info;
	private TextView tv_yj_01;
	private TextView tv_yj_02;
	private TextView tv_yj_03;
	private List<NewProductInfo> newInfo;
	private TextView xp_price1;
	private TextView xp_price2;
	private TextView tv_c_price1;
	private TextView tv_c_price2;
	private String goods_id;
	private Intent maodouIntent;
	private SliderLayout home_arl;
	private List<FigureInfo> figureInfos;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_home,null);
		init();
		setOnListener();
		initImage();
		initData();
		initSale();
		initNew();
		initHeat();
		Message message = handler.obtainMessage(1);     // Message
		handler.sendMessageDelayed(message, 1000);

		return view;
	}

	//轮播图数据
	private void initImage() {

		final RequestBody formBody = new FormBody.Builder()
				.add("secret",secret)
				.build();

		final Request request = new Request.Builder()
				.url("http://rifulai.wujingen.com/api.php?act=get_banner")
				.post(formBody)
				.build();

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Response response = client.newCall(request).execute();
					String result = response.body().string();
					Log.i("WW","轮播图数据"+result);
					Gson gson = new Gson();
					FigureResp figureResp = gson.fromJson(result, FigureResp.class);
					FigureInfoBean figureInfoBean = figureResp.getInfo();
					figureInfos = figureInfoBean.getData_info();
					for (FigureInfo figureInfo : figureInfos) {
						figureInfo.getSrc();
					}
					handler.post(new Runnable() {
						@Override
						public void run() {
							Message msg = new Message();
							msg.what = 5;
							handler.sendMessage(msg);
						}
					});
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	//抢购商品
	private void initData() {
		final int pages = 1;
		final int counts = 10;
		final String sort = "DESC";
		final String orderby = "goods_id";

		final RequestBody formBody = new FormBody.Builder()
				.add("pages", String.valueOf(pages))
				.add("counts", String.valueOf(counts))
				.add("orderby",orderby)
				.add("sort", sort)
				.add("secret",secret)
				.build();

		final Request request = new Request.Builder()
				.url("http://ecshop.wujingen.com/api.php?act=get_rush_list")
				.post(formBody)
				.build();

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Response rp = client.newCall(request).execute();
					String code = rp.body().string();
					Gson gson = new Gson();
					PanicResp panicResp = gson.fromJson(code, PanicResp.class);
					PanicInfoBean panicInfoBean = panicResp.getInfo();
					item = panicInfoBean.getData_info();

					for (PanicInfo panicInfo : item) {
						String productName =  panicInfo.getGoods_name();
						goods_id = panicInfo.getGoods_id();
						Log.i("QQ","商品id"+ goods_id);
					}
					handler.post(new Runnable() {
						@Override
						public void run() {
							Bitmap bitmap = null;
							Message msg = new Message();
							msg.what = HANDLER_TEST;
							msg.obj = bitmap;
							handler.sendMessage(msg);
						}
					});

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	//特价商品
	private void initSale() {
		final int pages = 1;
		final int counts = 10;
		final String sort = "DESC";
		final String orderby = "goods_id";

		final RequestBody formBody = new FormBody.Builder()
				.add("pages", String.valueOf(pages))
				.add("counts", String.valueOf(counts))
				.add("orderby",orderby)
				.add("sort", sort)
				.add("secret",secret)
				.build();

		final Request request = new Request.Builder()
				.url("http://rifulai.wujingen.com/api.php?act=get_special_price_list")
				.post(formBody)
				.build();

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Response rpSale = client.newCall(request).execute();
					String saleCode = rpSale.body().string();

					Gson gson = new Gson();
					SpecialResp specialResp = gson.fromJson(saleCode,SpecialResp.class);
					SpecailInfoBean specailInfoBean = specialResp.getInfo();
					info = specailInfoBean.getData_info();
					for (SpecialInfo specialInfo : info) {
						specialInfo.getGoods_name();
						Log.i("QQ","商品特价"+ specialInfo.getFinal_price());
					}

					handler.post(new Runnable() {
						@Override
						public void run() {
							Message msg = new Message();
							msg.what = 3;
							handler.sendMessage(msg);
						}
					});
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	//新品
	private void initNew() {
		final int pages = 1;
		final int counts = 10;
		final String sort = "DESC";
		final String orderby = "goods_id";

		final RequestBody formBody = new FormBody.Builder()
				.add("pages", String.valueOf(pages))
				.add("counts", String.valueOf(counts))
				.add("orderby",orderby)
				.add("sort", sort)
				.add("secret",secret)
				.build();

		final Request request = new Request.Builder()
				.url("http://rifulai.wujingen.com/api.php?act=get_new_list")
				.post(formBody)
				.build();

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Response rpNew = client.newCall(request).execute();
					String newCode = rpNew.body().string();
					Log.i("QQ","新品"+ newCode);
					Gson gson = new Gson();
					NewProductResp newProductResp = gson.fromJson(newCode, NewProductResp.class);
					NewProductInfoBean newProductInfoBean = newProductResp.getInfo();
					newInfo = newProductInfoBean.getData_info();
					for (NewProductInfo newProductInfo : newInfo) {
						newProductInfo.getGoods_name();
					}

					handler.post(new Runnable() {
						@Override
						public void run() {
							Message msg = new Message();
							msg.what = 4;
							handler.sendMessage(msg);
						}
					});
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();

	}

	//热卖商品
	private void initHeat() {


	}

	Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 1:
					recLen--;
					tv_time.setText(""+recLen);
					if (recLen > 0) {
						Message message = handler.obtainMessage(1);
						handler.sendMessageDelayed(message, 1000);
					}else {
						tv_time.setVisibility(View.GONE);
					}
				case 2:
					if (item.size()>0){
						tv_maodou.setText(item.get(0).getGoods_name());
						Picasso.with(getActivity()).load(item.get(0).getImage_default()).into(iv_maodou);

						tv_gua.setText(item.get(1).getGoods_name());
						Picasso.with(getActivity()).load(item.get(1).getImage_default()).into(iv_gua);

						tv_huang.setText(item.get(2).getGoods_name());
						Picasso.with(getActivity()).load(item.get(2).getImage_default()).into(iv_huang);
					}else {
						return;
					}

					break;

				case 3:
					tv_sheng.setText(info.get(0).getGoods_name());
					tv_tj_01.setText("¥" + info.get(0).getFinal_price());
					tv_yj_01.setText("¥" + info.get(0).getMarket_price());
					Picasso.with(getActivity()).load(info.get(0).getImage_default()).into(iv_sheng);

					tv_siji.setText(info.get(1).getGoods_name());
					tv_tj_02.setText("¥"+info.get(1).getFinal_price());
					tv_yj_02.setText("¥" + info.get(1).getMarket_price());
					Picasso.with(getActivity()).load(info.get(1).getImage_default()).into(iv_siji);

					/*tv_jiao.setText(info.get(2).getGoods_name());
					tv_tj_03.setText("¥" + info.get(2).getFinal_price());
					Picasso.with(getActivity()).load(info.get(2).getImage_default()).into(iv_jiao);*/
					break;

				case 4:
					tv_fanqie.setText(newInfo.get(0).getGoods_name());
					xp_price1.setText("¥"+newInfo.get(0).getFinal_price());
					tv_c_price1.setText("菜场价"+"¥"+newInfo.get(0).getMarket_price());
					Picasso.with(getActivity()).load(newInfo.get(0).getImage_default()).into(iv_fanqie);


					tv_muer.setText(newInfo.get(1).getGoods_name());
					xp_price2.setText("¥"+newInfo.get(1).getFinal_price());
					tv_c_price2.setText("菜场价"+"¥"+newInfo.get(1).getMarket_price());
					Picasso.with(getActivity()).load(newInfo.get(1).getImage_default()).into(iv_muer);

					break;

				case 5:
					TextSliderView textSliderView1 = new TextSliderView(getActivity());
					TextSliderView textSliderView2 = new TextSliderView(getActivity());
					TextSliderView textSliderView3 = new TextSliderView(getActivity());
					textSliderView1.image(figureInfos.get(0).getSrc());
					textSliderView2.image(figureInfos.get(1).getSrc());

					home_arl.addSlider(textSliderView1);
					home_arl.addSlider(textSliderView2);

					break;
			}
			super.handleMessage(msg);
		};
	};

	private void setOnListener() {
		iv_sm.setOnClickListener(this);
		tv_sousuo.setOnClickListener(this);
		rl_sousuo.setOnClickListener(this);
		iv_ding.setOnClickListener(this);
		ll_maodou.setOnClickListener(this);
		ll_gua.setOnClickListener(this);
		ll_huang.setOnClickListener(this);
		ll_sheng.setOnClickListener(this);
		ll_jiao.setOnClickListener(this);
		ll_siji.setOnClickListener(this);
		ll_fanqie.setOnClickListener(this);
		ll_muer.setOnClickListener(this);
		ll_xiaoqingcai.setOnClickListener(this);
		ll_xianggu.setOnClickListener(this);
	}

	private void init() {
		home_arl = (SliderLayout) view.findViewById(R.id.home_arl);
		iv_sm = (ImageView) view.findViewById(R.id.iv_sm);
		tv_sousuo = (TextView) view.findViewById(R.id.tv_sousuo);
		rl_sousuo = (RelativeLayout) view.findViewById(R.id.rl_sousuo);
		iv_ding = (ImageView) view.findViewById(R.id.iv_ding);
		ll_maodou = (LinearLayout) view.findViewById(R.id.ll_maodou);
		ll_gua = (LinearLayout) view.findViewById(R.id.ll_gua);
		ll_huang = (LinearLayout) view.findViewById(R.id.ll_huang);
		ll_sheng = (LinearLayout) view.findViewById(R.id.ll_sheng);
		ll_jiao = (LinearLayout) view.findViewById(R.id.ll_jiao);
		ll_siji = (LinearLayout) view.findViewById(R.id.ll_siji);
		ll_fanqie = (LinearLayout) view.findViewById(R.id.ll_fanqie);
		ll_muer = (LinearLayout) view.findViewById(R.id.ll_muer);
		ll_xiaoqingcai = (LinearLayout) view.findViewById(R.id.ll_xiaoqingcai);
		ll_xianggu = (LinearLayout) view.findViewById(R.id.ll_xianggu);
		tv_time = (TextView) view.findViewById(R.id.tv_time);
		tv_maodou = (TextView) view.findViewById(R.id.tv_maodou);
		tv_gua = (TextView) view.findViewById(R.id.tv_gua);
		tv_huang = (TextView) view.findViewById(R.id.tv_huang);
		tv_sheng = (TextView) view.findViewById(R.id.tv_sheng);
		tv_siji = (TextView) view.findViewById(R.id.tv_siji);
		tv_jiao = (TextView) view.findViewById(R.id.tv_jiao);
		tv_fanqie = (TextView) view.findViewById(R.id.tv_fanqie);
		tv_muer = (TextView) view.findViewById(R.id.tv_muer);
		tv_xiaoqingcai = (TextView) view.findViewById(R.id.tv_xiaoqingcai);
		tv_xianggu = (TextView) view.findViewById(R.id.tv_xianggu);
		iv_maodou = (ImageView) view.findViewById(R.id.iv_maodou);
		iv_gua = (ImageView) view.findViewById(R.id.iv_gua);
		iv_huang = (ImageView) view.findViewById(R.id.iv_huang);
		iv_sheng = (ImageView) view.findViewById(R.id.iv_sheng);
		iv_siji = (ImageView) view.findViewById(R.id.iv_siji);
		iv_jiao = (ImageView) view.findViewById(R.id.iv_jiao);
		iv_fanqie = (ImageView) view.findViewById(R.id.iv_fanqie);
		iv_muer = (ImageView) view.findViewById(R.id.iv_muer);
		iv_xiaoqingcai = (ImageView) view.findViewById(R.id.iv_xiaoqingcai);
		iv_xianggu = (ImageView) view.findViewById(R.id.iv_xianggu);
		tv_tj_01 = (TextView) view.findViewById(R.id.tv_tj_01);
		tv_tj_02 = (TextView) view.findViewById(R.id.tv_tj_02);
		tv_tj_03 = (TextView) view.findViewById(R.id.tv_tj_03);
		tv_yj_01 = (TextView) view.findViewById(R.id.tv_yj_01);
		tv_yj_02 = (TextView) view.findViewById(R.id.tv_yj_02);
		tv_yj_03 = (TextView) view.findViewById(R.id.tv_yj_03);
		xp_price1 = (TextView) view.findViewById(R.id.xp_price1);
		xp_price2 = (TextView) view.findViewById(R.id.xp_price2);
		tv_c_price1 = (TextView) view.findViewById(R.id.tv_c_price1);
		tv_c_price2 = (TextView) view.findViewById(R.id.tv_c_price2);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.iv_sm:

				break;
			case R.id.tv_sousuo:

				break;
			case R.id.rl_sousuo:

				break;
			case R.id.iv_ding:

				break;
			case R.id.ll_maodou:
				maodouIntent = new Intent(getActivity(),PanicBuyingActivity.class);
				maodouIntent.putExtra("goods_id",goods_id);
				startActivity(maodouIntent);
				break;
			case R.id.ll_gua:
				Intent guaIntent = new Intent(getActivity(),PanicBuyingActivity.class);
				maodouIntent.putExtra("goods_id",goods_id + 1);
				startActivity(guaIntent);
				break;
			case R.id.ll_huang:
				Intent huangIntent = new Intent(getActivity(),PanicBuyingActivity.class);
				maodouIntent.putExtra("goods_id",goods_id + 2);
				startActivity(huangIntent);
				break;
			case R.id.ll_sheng:
				Intent shengIntent = new Intent(getActivity(),SpecialPriceActivity.class);
				startActivity(shengIntent);
				break;
			case R.id.ll_siji:
				Intent sijiIntent = new Intent(getActivity(),SpecialPriceActivity.class);
				startActivity(sijiIntent);
				break;
			case R.id.ll_jiao:
				Intent jiaoIntent = new Intent(getActivity(),SpecialPriceActivity.class);
				startActivity(jiaoIntent);
				break;
			case R.id.ll_fanqie:
				Intent fanqieIntent = new Intent(getActivity(),SpecialPriceActivity.class);
				startActivity(fanqieIntent);
				break;
			case R.id.ll_muer:
				Intent muerIntent = new Intent(getActivity(),SpecialPriceActivity.class);
				startActivity(muerIntent);
				break;
			case R.id.ll_xiaoqingcai:
				Intent xiaoqingcaiIntent = new Intent(getActivity(),SpecialPriceActivity.class);
				startActivity(xiaoqingcaiIntent);
				break;
			case R.id.ll_xianggu:
				Intent xiangguIntent = new Intent(getActivity(),SpecialPriceActivity.class);
				startActivity(xiangguIntent);
				break;
		}
	}
}