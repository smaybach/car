package com.example.shoppingcar.bean;

import java.util.List;

/**
 * date:2017/12/20
 * author:张伟(Administrator)
 * desc:
 */

public class CartBean {

    public String msg;
    public String code;
    public String page;
    public List<DataBean> data;

    public static class DataBean {

        public double bargainPrice;
        public String createtime;
        public String detailUrl;
        public String images;
        public int itemtype;
        public int pid;
        public double price;
        public int pscid;
        public int salenum;
        public int sellerid;
        public String subhead;
        public String title;
    }
}
