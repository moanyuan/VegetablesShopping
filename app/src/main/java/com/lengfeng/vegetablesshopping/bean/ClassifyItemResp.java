package com.lengfeng.vegetablesshopping.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/9 0009.
 */
public class ClassifyItemResp {


    private String result;
    private String msg;
    private List<ClassIfyInfoBean> info;

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

    public List<ClassIfyInfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<ClassIfyInfoBean> info) {
        this.info = info;
    }

}
