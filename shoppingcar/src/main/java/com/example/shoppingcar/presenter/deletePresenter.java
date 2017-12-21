package com.example.shoppingcar.presenter;

import com.example.shoppingcar.bean.deleteBean;
import com.example.shoppingcar.model.deleteModel;
import com.example.shoppingcar.model.deleteModelCallBack;
import com.example.shoppingcar.view.deleteView;

/**
 * date:2017/12/20
 * author:张伟(Administrator)
 * desc:
 */

public class deletePresenter {
    deleteModel deleteModel;
    deleteView deleteView;

    public deletePresenter(com.example.shoppingcar.view.deleteView deleteView) {
        this.deleteView = deleteView;
        this.deleteModel = new deleteModel();
    }

    public void deleteFF(String pid) {
        deleteModel.delete(pid, new deleteModelCallBack() {
            @Override
            public void success(deleteBean deleteBean) {
                deleteView.success(deleteBean);
            }

            @Override
            public void failure() {

            }
        });
    }

}
