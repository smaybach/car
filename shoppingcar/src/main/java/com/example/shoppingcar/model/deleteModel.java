package com.example.shoppingcar.model;

import com.example.shoppingcar.bean.deleteBean;
import com.example.shoppingcar.utils.APIFactory;

import java.util.HashMap;
import java.util.Map;

import rx.Observer;

/**
 * date:2017/12/20
 * author:张伟(Administrator)
 * desc:
 */

public class deleteModel {
    public void delete(String pid, final deleteModelCallBack deleteBack) {
        Map<String, String> map = new HashMap<>();
        map.put("uid", "uid");
        map.put("pid", pid);

        APIFactory.getInstance().delete(map, new Observer<deleteBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(deleteBean deleteBean) {
                deleteBack.success(deleteBean);
            }
        });

    }
}
