package com.lengfeng.vegetablesshopping.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/8 0008.
 */
public class PanicResp {

    private String result;
    private String msg;
    private PanicInfoBean info;

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

    public PanicInfoBean getInfo() {
        return info;
    }

    public void setInfo(PanicInfoBean info) {
        this.info = info;
    }

}
