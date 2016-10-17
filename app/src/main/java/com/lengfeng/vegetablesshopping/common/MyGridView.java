package com.lengfeng.vegetablesshopping.common;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class MyGridView extends GridView {

	public MyGridView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public MyGridView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public MyGridView(Context context) {
		this(context,null);
	}
	//重写该方法，解决GridView和ListView
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec
				(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
		
	}

}
