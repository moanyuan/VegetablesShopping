package com.lengfeng.vegetablesshopping.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/13 0013.
 */
public class FigureResp {

    private String result;
    private String msg;
    private FigureInfoBean info;

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

    public FigureInfoBean getInfo() {
        return info;
    }

    public void setInfo(FigureInfoBean info) {
        this.info = info;
    }
}
