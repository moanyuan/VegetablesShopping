package com.lengfeng.vegetablesshopping.common;



import java.util.HashMap;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CommonViewHolder {
	public final View convertView;
	
	public static CommonViewHolder getCVH(View convertView,Context context, int layoutRes) {
		if(convertView == null){
			View.inflate(context, layoutRes, null);
			return new CommonViewHolder(View.inflate(context, layoutRes, null));
		}else {
			return  (CommonViewHolder) convertView.getTag();
		}
	}
	
	public CommonViewHolder(View convertView){
		this.convertView = convertView;
		
		convertView.setTag(this);
	}
	
	HashMap<Integer, View> views = new HashMap<Integer, View>();
	
	//类型推导 通过参数来确定返回类型
	public <T extends View> T getView(int id){
		if(views.get(id)==null){
			views.put(id, convertView.findViewById(id));
		}
		return (T) views.get(id);
	}
	
	//类型推导  通过返回值确定返回类型
	public <T extends View> T getView(int id ,Class<T>klass){
		return (T)getView(id);
	}
	
	public TextView getTextView(int id){
		return getView(id,TextView.class);
	}
	
	public ImageView getImageView(int id){
		return getView(id, ImageView.class);
	}
}
