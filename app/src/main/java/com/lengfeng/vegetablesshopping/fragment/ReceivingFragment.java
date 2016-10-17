package com.lengfeng.vegetablesshopping.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lengfeng.vegetablesshopping.R;

/**
 * Created by Administrator on 2016/7/28 0028.
 */
public class ReceivingFragment extends Fragment {

    private Button bt_look_over;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_receiving,null);
        bt_look_over = (Button) view.findViewById(R.id.bt_look_over);
        return view;
    }
}
