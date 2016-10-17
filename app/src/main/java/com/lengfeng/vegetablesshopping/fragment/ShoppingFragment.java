package com.lengfeng.vegetablesshopping.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lengfeng.vegetablesshopping.R;
import com.lengfeng.vegetablesshopping.bean.Product;
import com.lengfeng.vegetablesshopping.common.CommonViewHolder;
import com.lengfeng.vegetablesshopping.pay.PayDemoActivity;
import com.lengfeng.vegetablesshopping.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

public class ShoppingFragment extends Fragment implements OnClickListener, AdapterView.OnItemClickListener {

	private ListView lv_cart_shopping;
	private Button bt_checkout;
	private ImageView pro_add;
	private ImageView pro_reduce;
	private ImageView pro_delete;
	private TextView tv_total;
	private ImageView pro_image;
	private TextView pro_name;
	private TextView pro_shopPrice;
	private TextView pro_count;
	private View view;
	List<Product> datas = new ArrayList<Product>();
	private MyAdapter adapter;
	private CommonViewHolder cvh_gv;
	private LinearLayout ll_shopping_cart;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_shopping, null);
		lv_cart_shopping = (ListView) view.findViewById(R.id.lv_cart_shopping);
		initView();
		setOnClickListener();
		Product product = null;
		for (int i = 0; i < 2; i++) {
			product = new Product();
			product.setName("商品："+i);
			product.setNum(1);
			product.setPrice(i);
			datas.add(product);
		}
		adapter = new MyAdapter(datas,getActivity());
		lv_cart_shopping.setAdapter(adapter);
		adapter.setOnAddNum(this);
		adapter.onReduceNum(this);
		lv_cart_shopping.setOnItemClickListener(this);
		return view;
	}

	@Subscribe(threadMode = ThreadMode.MainThread)
	public void helloEventBus(String message){

	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

	}

	private void setOnClickListener() {
		bt_checkout.setOnClickListener(this);
	}

	private void initView() {
		lv_cart_shopping = (ListView) view.findViewById(R.id.lv_cart_shopping);
		bt_checkout = (Button) view.findViewById(R.id.bt_checkout);
	}

	@Override
	public void onClick(View view) {
		Object tag = view.getTag();
		switch (view.getId()){
			case R.id.bt_checkout:
				Intent ckIntent = new Intent(getActivity(), PayDemoActivity.class);
				startActivity(ckIntent);
				break;

			case R.id.pro_add:
				if (tag != null && tag instanceof Integer){
					int position = (Integer) tag;
					int num = datas.get(position).getNum();
					num++;
					datas.get(position).setNum(num);
					datas.get(position).setPrice(position*num);

					adapter.notifyDataSetChanged();
				}
				break;
			case R.id.pro_reduce:
				if (tag != null && tag instanceof Integer){
					int position = (Integer) tag;
					int num = datas.get(position).getNum();

					if (num>0) {
						num--;
						datas.get(position).setNum(num); //修改集合中商品数量
						datas.get(position).setPrice(position * num); //修改集合中该商品总价 数量*单价
						adapter.notifyDataSetChanged();
					}
				}
				break;
		}
	}



	int num = 0;
	class MyAdapter extends BaseAdapter  {

		private final List<Product> products;
		private Context context;
		private View.OnClickListener onAddNum;
		private View.OnClickListener onReduceNum;

		public MyAdapter(List<Product> products, Context context) {
			this.products = products;
			this.context = context;
		}

		@Override
		public int getCount() {
			int ret = 0;
			if (products != null) {
				ret = products.size();
			}
			return ret;
		}

		@Override
		public Object getItem(int position) {
			return products.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int i, View convertView, ViewGroup parent) {
			cvh_gv = CommonViewHolder.getCVH(convertView, getActivity(),
					R.layout.activity_shoppingcart_item);
			ll_shopping_cart = cvh_gv.getView(R.id.ll_shopping_cart, LinearLayout.class);
			pro_image = cvh_gv.getView(R.id.pro_image, ImageView.class);
			pro_name = cvh_gv.getView(R.id.pro_name, TextView.class);
			pro_shopPrice = cvh_gv.getView(R.id.pro_shopPrice, TextView.class);
			pro_count = cvh_gv.getView(R.id.pro_count, TextView.class);
			//pro_standard = cvh_gv.getView(R.id.pro_standard, TextView.class);
			pro_add = cvh_gv.getView(R.id.pro_add);
			pro_reduce = cvh_gv.getView(R.id.pro_reduce);
			pro_delete = cvh_gv.getView(R.id.pro_delete);
			pro_count.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
			pro_count.setText(String.valueOf(num));
			pro_count.setText(String.valueOf(products.get(i).getNum()));
			pro_name.setText(products.get(i).getName());
			pro_shopPrice.setText(String.valueOf(products.get(i).getPrice()));
			//tv_total.setText(products.get(position).getPrice());
			// pro_count.setText(products.get(position).getNum());
			pro_delete.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					pro_delete.setVisibility(View.GONE);
					products.remove(i);
					Toast.makeText(getActivity(),"删除成功",Toast.LENGTH_SHORT).show();
					adapter.notifyDataSetChanged();
				}
			});
			pro_add.setTag(i);
			pro_reduce.setTag(i);
			pro_add.setOnClickListener(onAddNum);
			pro_reduce.setOnClickListener(onReduceNum);
			return cvh_gv.convertView;
		}

		public void setOnAddNum(View.OnClickListener onAddNum) {
			this.onAddNum = onAddNum;
		}

		public void onReduceNum(View.OnClickListener onReduceNum) {
			this.onReduceNum = onReduceNum;
		}
	}
}
