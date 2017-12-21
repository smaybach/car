package com.example.shoppingcar.model;

import com.example.shoppingcar.bean.deleteBean;

/**
 * date:2017/12/20
 * author:张伟(Administrator)
 * desc:
 */

public interface deleteModelCallBack {
    void success(deleteBean deleteBean);
    void failure();
}
