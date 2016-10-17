package com.lengfeng.vegetablesshopping.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/8 0008.
 */
public class PanicInfoBean {

    private PanicInfo counts;
    private List<PanicInfo> data_info;

    public PanicInfo getCounts() {
        return counts;
    }

    public void setCounts(PanicInfo counts) {
        this.counts = counts;
    }

    public List<PanicInfo> getData_info() {
        return data_info;
    }

    public void setData_info(List<PanicInfo> data_info) {
        this.data_info = data_info;
    }
}
