package com.lengfeng.vegetablesshopping.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/9 0009.
 */
public class NewProductResp {

    private String result;
    private String msg;
    private NewProductInfoBean info;
    private String count;

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

    public NewProductInfoBean getInfo() {
        return info;
    }

    public void setInfo(NewProductInfoBean info) {
        this.info = info;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

}
