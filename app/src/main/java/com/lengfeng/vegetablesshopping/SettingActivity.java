package com.lengfeng.vegetablesshopping;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lengfeng.vegetablesshopping.bean.UpdateUserResp;
import com.lengfeng.vegetablesshopping.common.Constant;
import com.lengfeng.vegetablesshopping.utils.BitMapUtil;
import com.lengfeng.vegetablesshopping.utils.MD5Utils;
import com.lengfeng.vegetablesshopping.utils.UIUtils;
import com.lengfeng.vegetablesshopping.wheelview.JudgeDate;
import com.lengfeng.vegetablesshopping.wheelview.ScreenInfo;
import com.lengfeng.vegetablesshopping.wheelview.WheelMain;
import com.lengfeng.vegetablesshopping.widgt.ActionSheetDialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by Administrator on 2016/7/29 0029.
 */
public class SettingActivity extends FragmentActivity implements View.OnClickListener {


    String secret= MD5Utils.MD5(Constant.APP_KEY)+Constant.VERSION_NO;
    final OkHttpClient client = new OkHttpClient();
    private ImageView iv_setting_back;
    private RelativeLayout rl_change_password;
    private RelativeLayout rl_setting_head;
    private RelativeLayout rl_setting_name;
    private RelativeLayout rl_setting_sex;
    private RelativeLayout rl_setting_age;
    private RelativeLayout rl_setting_birthday;
    private RelativeLayout rl_setting_address;
    private RelativeLayout rl_setting_marriage;
    private RelativeLayout rl_setting_hobby;
    private Button bt_exit_login;
    private ImageView iv_avatar;
    private TextView tv_setting_name;
    private TextView tv_setting_sex;
    private TextView tv_setting_age;
    private TextView tv_setting_birthday;
    private TextView tv_setting_address;
    private TextView tv_setting_marriage;
    private TextView tv_setting_hobby;
    private String oldPwd;
    private String sex;
    //WheelMain wheelMain;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private WheelMain wheelMain;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        init();
        setOnClickListener();
        SharedPreferences sharedPreferences = getSharedPreferences("test", Activity.MODE_PRIVATE);
        String user_id =sharedPreferences.getString("user", "");


        RequestBody formBody = new FormBody.Builder()
                .add("user_id",user_id)
                .add("secret",secret)
                .build();

        final Request request = new Request.Builder()
                .url("http://rifulai.wujingen.com/api.php?act=user_details")
                .post(formBody)
                .build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response rp = client.newCall(request).execute();
                    String result = rp.body().string();
                    Log.i("QQ","用户更新"+result);
                    Gson gson = new Gson();
                    /*UserResp userResp = gson.fromJson(result, UserResp.class);
                    UserInfoBean userInfoBean = userResp.getInfo();
                    UserInfo userInfo = userInfoBean.getData_info();*/
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void init() {
        iv_setting_back = (ImageView) findViewById(R.id.iv_setting_back);
        rl_change_password = (RelativeLayout) findViewById(R.id.rl_change_password);
        rl_setting_head = (RelativeLayout) findViewById(R.id.rl_setting_head);
        rl_setting_name = (RelativeLayout) findViewById(R.id.rl_setting_name);
        rl_setting_sex = (RelativeLayout) findViewById(R.id.rl_setting_sex);
        rl_setting_birthday = (RelativeLayout) findViewById(R.id.rl_setting_birthday);
        rl_setting_address = (RelativeLayout) findViewById(R.id.rl_setting_address);
        iv_avatar = (ImageView) findViewById(R.id.iv_avatar);
        tv_setting_name = (TextView) findViewById(R.id.tv_setting_name);
        tv_setting_sex = (TextView) findViewById(R.id.tv_setting_sex);
        tv_setting_birthday = (TextView) findViewById(R.id.tv_setting_birthday);
        tv_setting_address = (TextView) findViewById(R.id.tv_setting_address);
        bt_exit_login = (Button) findViewById(R.id.bt_exit_login);
    }

    private void setOnClickListener() {
        iv_setting_back.setOnClickListener(this);
        rl_change_password.setOnClickListener(this);
        rl_setting_head.setOnClickListener(this);
        rl_setting_name.setOnClickListener(this);
        rl_setting_sex.setOnClickListener(this);
        rl_setting_birthday.setOnClickListener(this);
        rl_setting_address.setOnClickListener(this);
        bt_exit_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_setting_back:
                finish();
                break;
            case R.id.rl_change_password:
                int avatarId = iv_avatar.getId();
                break;
            case R.id.rl_setting_head:
                Intent headIntent = getIntent();
                oldPwd = headIntent.getStringExtra("passWord");

                //显示图像

                new ActionSheetDialog(SettingActivity.this)
                        .builder()
                        .setCancelable(false)
                        .setCanceledOnTouchOutside(false)

                        .addSheetItem("从相册选择", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        //打开系统选择图库程序，选择一张照片，作为头像返回
                                        Intent picture = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                        startActivityForResult(picture, PICTURE);
                                    }
                                }).show();
                break;
            case R.id.rl_setting_name:
                Intent nameIntent = new Intent(SettingActivity.this,InputNameActivity.class);
                startActivity(nameIntent);

                /*Bundle b=getIntent().getExtras();
                String inputName = b.getString("name");
                tv_setting_name.setText(inputName);*/

                Intent getIntent = getIntent();
                String inputName = getIntent.getStringExtra("inputName");
                tv_setting_name.setText(inputName);
                //SPUtils.get(SettingActivity.this,"inputName","");
                SharedPreferences sp = getSharedPreferences("inputName", Activity.MODE_PRIVATE);
                String name1 = sp.getString("inputName","");
                tv_setting_name.setText(name1);
                break;
            case R.id.rl_setting_sex:
                sex = tv_setting_sex.getText().toString().trim();
                if ("0".equals(sex)) {
                    tv_setting_sex.setText("保密");
                } else if ("1".equals(sex)) {
                    tv_setting_sex.setText("男");
                } else if ("2".equals(sex)) {
                    tv_setting_sex.setText("女");
                }

                new ActionSheetDialog(SettingActivity.this)
                        .builder()
                        .setTitle("性别")
                        .setCancelable(false)
                        .setCanceledOnTouchOutside(false)
                        .addSheetItem("男", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        tv_setting_sex.setText("男");
                                        sex = "2";
                                        showPickSex(sex);
                                        Toast.makeText(SettingActivity.this,
                                                "item" + which, Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                })
                        .addSheetItem("女", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        tv_setting_sex.setText("女");
                                        sex = "1";
                                        showPickSex(sex);
                                        Toast.makeText(SettingActivity.this,
                                                "item" + which, Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                })
                        .addSheetItem("保密", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        tv_setting_sex.setText("保密");
                                        sex = "0";
                                        showPickSex(sex);
                                        Toast.makeText(SettingActivity.this,
                                                "item" + which, Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                }).show();
                break;
            case R.id.rl_setting_birthday:
                String birthday = tv_setting_birthday.getText().toString().trim();
                LayoutInflater inflater = LayoutInflater.from(SettingActivity.this);
                final View timepickerview = inflater.inflate(R.layout.timepicker,null);
                ScreenInfo screenInfo = new ScreenInfo(SettingActivity.this);
                wheelMain = new WheelMain(timepickerview);
                wheelMain.screenheight = screenInfo.getHeight();
                String time = tv_setting_birthday.getText().toString();
                Calendar calendar = Calendar.getInstance();
                if (JudgeDate.isDate(time, "yyyy-MM-dd")) {
                    try {
                        calendar.setTime(dateFormat.parse(time));
                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                wheelMain.initDateTimePicker(year, month, day);
                new AlertDialog.Builder(SettingActivity.this)
                        .setTitle("选择时间")
                        .setView(timepickerview)
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        tv_setting_birthday.setText(wheelMain.getTime());
                                    }
                                })
                        .setNegativeButton("取消",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                    }
                                }).show();
                break;
            case R.id.rl_setting_address:
                String address = tv_setting_address.getText().toString().trim();
                Intent addressIntent = new Intent(SettingActivity.this,InputAddressActivity.class);
                startActivity(addressIntent);

                Intent intent2 = getIntent();
                String inputAdress = intent2.getStringExtra("address");
                tv_setting_name.setText(inputAdress);
                SharedPreferences sp1 = getSharedPreferences("address", Activity.MODE_PRIVATE);
                String name2= sp1.getString("address","");
                tv_setting_address.setText(name2);
                break;
            case R.id.bt_exit_login:
                Intent exitIntent = new Intent(SettingActivity.this,MainActivity.class);
                Toast.makeText(SettingActivity.this,"退出成功",Toast.LENGTH_SHORT).show();
                startActivity(exitIntent);
                break;
        }
    }

    private void showPickSex(final String sex){
        RequestBody formBody = new FormBody.Builder()
                .add("user_id", String.valueOf(iv_avatar.getId()))
                .add("age",sex)
                .add("secret",secret)
                .build();

        final Request request = new Request.Builder()
                .url("http://ecshop.wujingen.com/ecshop/api.php?act=user_upd")
                .post(formBody)
                .build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String responseCode = response.body().string();
                        Gson gson = new Gson();
                        UpdateUserResp updateUserResp = gson.fromJson(responseCode,UpdateUserResp.class);
                        if ("0".equals(updateUserResp.getStatus())) {
                            SharedPreferences sp = getSharedPreferences("user_info", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("Sex",sex);
                            editor.commit();
                        }
                        Log.i("WY","打印POST响应的数据：" + response +"");
                    } else {
                        throw new IOException("Unexpected code " + response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static final int CAMERA = 100;

    public static final int PICTURE = 200;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        File cacheDir = getCacheDir();
        if (requestCode == CAMERA && resultCode == RESULT_OK && data != null) {
            //相机
            try {
                Bundle bundle = data.getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");
                Bitmap zoom = BitMapUtil.zoom(bitmap, UIUtils.dp2px(32), UIUtils.dp2px(32));
                Bitmap circle = BitMapUtil.circle(zoom);
                iv_avatar.setImageBitmap(circle);
                FileOutputStream fos = new FileOutputStream(new File(cacheDir, "/tx.png"));
                circle.compress(Bitmap.CompressFormat.PNG, 0, fos);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (requestCode == PICTURE && resultCode == RESULT_OK && data != null) {
            //图库
            try {
                Uri uri = data.getData();
                String txPath = getPath(uri);
                Log.e("zoubo", "txPath:" + txPath);
                Bitmap bitmap = BitmapFactory.decodeFile(txPath);
                Bitmap zoom = BitMapUtil.zoom(bitmap, UIUtils.dp2px(32), UIUtils.dp2px(32));
                Bitmap circleImage = BitMapUtil.circle(zoom);
                iv_avatar.setImageBitmap(circleImage);
                FileOutputStream fos = new FileOutputStream(new File(cacheDir, "/tx.png"));
                circleImage.compress(Bitmap.CompressFormat.PNG, 0, fos);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @SuppressLint("NewApi")
    private String getPath(Uri uri) {
        int sdkVersion = Build.VERSION.SDK_INT;
        //高于4.4.2的版本
        if (sdkVersion >= 19) {
            if (isExternalStorageDocument(uri)) {
                String docId = DocumentsContract.getDocumentId(uri);
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));
                return getDataColumn(SettingActivity.this, contentUri, null, null);
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(this, contentUri, selection, selectionArgs);
            } else if (isMedia(uri)) {
                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor actualimagecursor = this.managedQuery(uri, proj, null, null, null);
                int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                actualimagecursor.moveToFirst();
                return actualimagecursor.getString(actual_image_column_index);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();
            return getDataColumn(this, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    private boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isMedia(Uri uri) {
        return "media".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

}
