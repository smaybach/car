package com.example.shoppingcar.model;

import com.example.shoppingcar.bean.queryBean;
import com.example.shoppingcar.utils.APIFactory;

import java.util.HashMap;
import java.util.Map;

import rx.Observer;

/**
 * date:2017/12/20
 * author:张伟(Administrator)
 * desc:
 */

public class queryModel {
    public void query(final queryModelCallBack queryBack) {
        Map<String, String> map = new HashMap<>();
        map.put("uid", "4421");

        APIFactory.getInstance().query(map, new Observer<queryBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(queryBean queryBean) {
                queryBack.success(queryBean);
            }
        });

    }
}
