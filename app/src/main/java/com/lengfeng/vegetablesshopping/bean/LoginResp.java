package com.lengfeng.vegetablesshopping.bean;

/**
 * Created by Administrator on 2016/9/5 0005.
 */
public class LoginResp {

    private String result;
    private String msg;
    private LoginInfoBean info;

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

    public LoginInfoBean getInfo() {
        return info;
    }

    public void setInfo(LoginInfoBean info) {
        this.info = info;
    }
}
