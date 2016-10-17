package com.lengfeng.vegetablesshopping.bean;

/**
 * Created by Administrator on 2016/8/31 0031.
 */
public class RegisterResp {

    private String result;
    private String msg;
    private RegisterInfoBean info;

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

    public RegisterInfoBean getInfo() {
        return info;
    }

    public void setInfo(RegisterInfoBean info) {
        this.info = info;
    }
}
