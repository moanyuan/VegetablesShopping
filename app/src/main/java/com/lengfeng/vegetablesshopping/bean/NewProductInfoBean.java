package com.lengfeng.vegetablesshopping.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/9 0009.
 */
public class NewProductInfoBean {

        private NewProductInfo counts;

        private List<NewProductInfo> data_info;

        public NewProductInfo getCounts() {
            return counts;
        }

        public void setCounts(NewProductInfo counts) {
            this.counts = counts;
        }

        public List<NewProductInfo> getData_info() {
            return data_info;
        }

        public void setData_info(List<NewProductInfo> data_info) {
            this.data_info = data_info;
        }
}
