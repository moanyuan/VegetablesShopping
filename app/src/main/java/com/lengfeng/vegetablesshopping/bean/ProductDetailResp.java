package com.lengfeng.vegetablesshopping.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/12 0012.
 */
public class ProductDetailResp {

    private String result;
    private String msg;
    private ProductDetailInfoBean info;

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

    public ProductDetailInfoBean getInfo() {
        return info;
    }

    public void setInfo(ProductDetailInfoBean info) {
        this.info = info;
    }
}
