package com.lengfeng.vegetablesshopping.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/9 0009.
 */
public class SpecailInfoBean {

    private SpecialInfo counts;

    private List<SpecialInfo> data_info;

    public SpecialInfo getCounts() {
        return counts;
    }

    public void setCounts(SpecialInfo counts) {
        this.counts = counts;
    }

    public List<SpecialInfo> getData_info() {
        return data_info;
    }

    public void setData_info(List<SpecialInfo> data_info) {
        this.data_info = data_info;
    }
}
