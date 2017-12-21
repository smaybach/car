package com.example.shoppingcar.model;

import com.example.shoppingcar.bean.queryBean;

/**
 * date:2017/12/20
 * author:张伟(Administrator)
 * desc:
 */

public interface queryModelCallBack {
    void success(queryBean queryBean);
    void failure();
}
