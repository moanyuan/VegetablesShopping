package com.lengfeng.vegetablesshopping;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.lengfeng.vegetablesshopping.bean.AddressInfo;
import com.lengfeng.vegetablesshopping.common.CommonViewHolder;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/1 0001.
 */
public class AddressActivity extends FragmentActivity implements View.OnClickListener {

    private ImageView iv_address_back;
    private ListView lv_add_address;
    private Button bt_add_address;
    private ListView lv_add_address1;
    private List<AddressInfo> address = new ArrayList<AddressInfo>();
    private MyAdapter adapter;
    private TextView tv_delete;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        init();
        setOnListener();
        adapter = new MyAdapter(getBaseContext());
        lv_add_address.setAdapter(adapter);

    }

    private void setOnListener() {
        iv_address_back.setOnClickListener(this);
        //lv_add_address.setOnClickListener(this);
        bt_add_address.setOnClickListener(this);
    }

    private void init() {
        iv_address_back = (ImageView) findViewById(R.id.iv_address_back);
        lv_add_address = (ListView) findViewById(R.id.lv_add_address);
        bt_add_address = (Button) findViewById(R.id.bt_add_address);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_address_back:
                finish();
                break;
            case R.id.lv_add_address:

                break;
            case R.id.bt_add_address:
                Intent AddressChooseIntent = new Intent(AddressActivity.this,AddressChooseActivity.class);
                startActivity(AddressChooseIntent);

                break;
        }
    }

    class MyAdapter extends BaseAdapter {

        private Context context;
        private LayoutInflater inflater;

        public MyAdapter(Context context){
            this.context=context;
            this.inflater=LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public Object getItem(int position) {
            return address.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            CommonViewHolder cvh_gv = CommonViewHolder.getCVH(convertView, AddressActivity.this,
                    R.layout.list_item_address);

            TextView tv_list_consignee = cvh_gv.getView(R.id.tv_list_consignee);
            TextView tv_list_phone = cvh_gv.getView(R.id.tv_list_phone);
            TextView tv_list_address = cvh_gv.getView(R.id.tv_list_address);
            CheckBox cb_default = cvh_gv.getView(R.id.cb_default);
            LinearLayout ll_list_compile = cvh_gv.getView(R.id.ll_list_compile);
            LinearLayout ll_list_delete = cvh_gv.getView(R.id.ll_list_delete);
            tv_delete = cvh_gv.getView(R.id.tv_delete);

            ll_list_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_delete.setVisibility(View.GONE);
                    address.remove(position);
                    //AddressInfo a = address.get(position);
                    /*AlertDialog dialog = new AlertDialog.Builder(AddressActivity.this)
                            .setTitle("删除收货地址")
                            .setMessage("确定删除这个收货地址吗？")
                            .setPositiveButton("确定", new AlertDialog.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    address.remove(position);
                                    adapter.notifyDataSetChanged();
                                }
                            }).setPositiveButton("取消", new AlertDialog.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).create();*/
                    //dialog.show();
                }
            });
            /*tv_list_consignee.setText(address.get(position).getName());
            tv_list_phone.setText(address.get(position).getPhone());
            tv_list_address.setText(address.get(position).getProvinces());

            if(address.get(position).isStatus()){
                cb_default.setChecked(true);
            }else{
                cb_default.setChecked(false);
            }*/
            return cvh_gv.convertView;
        }
    }
}
