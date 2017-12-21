package com.example.shoppingcar.model;

import com.example.shoppingcar.bean.CartBean;
import com.example.shoppingcar.utils.APIFactory;

import java.util.HashMap;
import java.util.Map;

import rx.Observer;

/**
 * date:2017/12/20
 * author:张伟(Administrator)
 * desc:
 */

public class MyModel {
    public void cart(final ModelCallBack modelCallBack) {

        Map<String, String> map = new HashMap<>();
        map.put("keywords", "手机");

        APIFactory.getInstance().cart(map, new Observer<CartBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(CartBean cartBean) {
                modelCallBack.success(cartBean);
            }
        });
    }
}
