package com.wjc.p2p.bean;

/**
 * Created by ${万嘉诚} on 2016/11/12.
 * WeChat：wjc398556712
 * Function：
 */

public class Product {
    public String id;
    public String memberNum;
    public String minTouMoney;
    public String money;
    public String name;
    public String progress;
    public String suodingDays;
    public String yearRate;

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", memberNum='" + memberNum + '\'' +
                ", minTouMoney='" + minTouMoney + '\'' +
                ", money='" + money + '\'' +
                ", name='" + name + '\'' +
                ", progress='" + progress + '\'' +
                ", suodingDays='" + suodingDays + '\'' +
                ", yearRate='" + yearRate + '\'' +
                '}';
    }
}
