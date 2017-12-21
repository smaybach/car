package com.example.shoppingcar.presenter;

import com.example.shoppingcar.bean.queryBean;
import com.example.shoppingcar.model.queryModel;
import com.example.shoppingcar.model.queryModelCallBack;
import com.example.shoppingcar.view.queryView;

/**
 * date:2017/12/20
 * author:张伟(Administrator)
 * desc:
 */

public class queryPresenter {
    queryView queryView;
    queryModel queryModel;

    public queryPresenter(queryView queryView) {
        this.queryView = queryView;
        this.queryModel = new queryModel();
    }

    public void queryFF() {
        queryModel.query(new queryModelCallBack() {
            @Override
            public void success(queryBean queryBean) {
                queryView.success(queryBean);
            }

            @Override
            public void failure() {

            }
        });
    }

    public void detach() {
        this.queryView = null;
    }

}
