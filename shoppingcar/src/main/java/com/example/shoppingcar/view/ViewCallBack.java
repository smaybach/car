package com.example.shoppingcar.view;

import com.example.shoppingcar.bean.CartBean;

/**
 * date:2017/12/20
 * author:张伟(Administrator)
 * desc:
 */

public interface ViewCallBack {
    void success(CartBean cartBean);
    void failure(Exception e);
}
