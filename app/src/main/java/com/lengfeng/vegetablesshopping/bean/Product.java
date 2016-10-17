package com.lengfeng.vegetablesshopping.bean;

import android.widget.TextView;

/**
 * Created by Administrator on 2016/7/22 0022.
 */
public class Product {
    //商品名称
    private String name;
    // 商品数量
    private int num;
    // 该商品总价
    private int price;

    public Product() {
        this.num = num;
        this.name = name;
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getNum() {
        return num;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "product{" +
                "name='" + name + '\'' +
                ", num=" + num +
                ", price=" + price +
                '}';
    }
}
