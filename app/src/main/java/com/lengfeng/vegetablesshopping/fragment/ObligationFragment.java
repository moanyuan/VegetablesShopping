package com.lengfeng.vegetablesshopping.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.lengfeng.vegetablesshopping.R;


/**
 * Created by Administrator on 2016/7/28 0028.
 */
public class ObligationFragment extends Fragment implements View.OnClickListener {

    private Button bt_cancel_order;
    private Button bt_payment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_obligation,null);
        bt_cancel_order = (Button) view.findViewById(R.id.bt_cancel_order);
        bt_payment = (Button) view.findViewById(R.id.bt_payment);
        bt_cancel_order.setOnClickListener(this);
        bt_payment.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_cancel_order:
                Toast.makeText(getActivity(),"取消成功",Toast.LENGTH_SHORT).show();
                break;
            case R.id.bt_payment:
                Toast.makeText(getActivity(),"付款找零",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
