package com.example.shoppingcar.presenter;

import com.example.shoppingcar.bean.CartBean;
import com.example.shoppingcar.model.ModelCallBack;
import com.example.shoppingcar.model.MyModel;
import com.example.shoppingcar.view.ViewCallBack;

/**
 * date:2017/12/20
 * author:张伟(Administrator)
 * desc:
 */

public class cartPresenter {
    ViewCallBack viewCallBack;
    MyModel myModel;

    public cartPresenter(ViewCallBack viewCallBack) {
        this.viewCallBack = viewCallBack;
        this.myModel = new MyModel();
    }

    public void cartFF() {
        myModel.cart(new ModelCallBack() {
            @Override
            public void success(CartBean cartBean) {
                viewCallBack.success(cartBean);
            }

            @Override
            public void failure(Exception e) {

            }
        });
    }

    public void detach() {
        this.viewCallBack = null;
    }

}
