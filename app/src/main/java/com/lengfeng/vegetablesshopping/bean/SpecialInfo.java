package com.lengfeng.vegetablesshopping.bean;

/**
 * Created by Administrator on 2016/9/9 0009.
 */
public class SpecialInfo {

    private String goods_id;
    private String market_price;
    private String goods_name;
    private String final_price;
    private String image_default;
    private String goods_store;
    private String end_time;
    private String end_date;
    private String count;

    public SpecialInfo(String goods_id) {
        this.goods_id = goods_id;
    }

    public SpecialInfo(String goods_id, String market_price, String goods_name, String final_price, String image_default, String goods_store, String end_time, String end_date, String count) {
        this.goods_id = goods_id;
        this.market_price = market_price;
        this.goods_name = goods_name;
        this.final_price = final_price;
        this.image_default = image_default;
        this.goods_store = goods_store;
        this.end_time = end_time;
        this.end_date = end_date;
        this.count = count;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public String getMarket_price() {
        return market_price;
    }

    public String getFinal_price() {
        return final_price;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public String getImage_default() {
        return image_default;
    }

    public String getGoods_store() {
        return goods_store;
    }

    public String getEnd_time() {
        return end_time;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getCount() {
        return count;
    }
}
