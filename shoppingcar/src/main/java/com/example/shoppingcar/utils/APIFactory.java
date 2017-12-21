package com.example.shoppingcar.utils;

import com.example.shoppingcar.bean.CartBean;
import com.example.shoppingcar.bean.addBean;
import com.example.shoppingcar.bean.deleteBean;
import com.example.shoppingcar.bean.queryBean;

import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * date:2017/12/20
 * author:张伟(Administrator)
 * desc:
 */

public class APIFactory {
    private static APIFactory factory = null;

    public static APIFactory getInstance() {
        if (factory == null) {
            synchronized (APIFactory.class) {
                if (factory == null) {
                    factory = new APIFactory();
                }
            }
        }
        return factory;
    }

    public void query(Map<String, String> map, Observer<queryBean> observer) {
        RetrofitUtils.getInstance().queryUrl(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


    public void delete(Map<String, String> map, Observer<deleteBean> observer) {
        RetrofitUtils.getInstance().deleteUrl(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void cart(Map<String, String> map, Observer<CartBean> observer) {
        RetrofitUtils.getInstance().cartUrl(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void add(Map<String, String> map, Observer<addBean> observer) {
        RetrofitUtils.getInstance().addUrl(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


}
