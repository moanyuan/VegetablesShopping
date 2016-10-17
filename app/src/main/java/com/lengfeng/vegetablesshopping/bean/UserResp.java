package com.lengfeng.vegetablesshopping.bean;

/**
 * Created by Administrator on 2016/9/8 0008.
 */
public class UserResp {

    private String result;
    private String msg;
    private UserInfoBean info;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public UserInfoBean getInfo() {
        return info;
    }

    public void setInfo(UserInfoBean info) {
        this.info = info;
    }

}
