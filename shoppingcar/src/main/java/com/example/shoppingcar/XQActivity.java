package com.example.shoppingcar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.shoppingcar.bean.CartBean;
import com.example.shoppingcar.presenter.cartPresenter;
import com.example.shoppingcar.view.ViewCallBack;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class XQActivity extends AppCompatActivity implements ViewCallBack {

    @Bind(R.id.GWC)
    TextView GWC;
    @Bind(R.id.XQ_Recy)
    RecyclerView XQRecy;
    private com.example.shoppingcar.presenter.cartPresenter cartPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xq);
        ButterKnife.bind(this);
        cartPresenter = new cartPresenter(this);
        cartPresenter.cartFF();
    }

    @OnClick(R.id.GWC)
    public void onViewClicked() {
        startActivity(new Intent(XQActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void success(CartBean cartBean) {
        List<CartBean.DataBean> data = cartBean.data;
        XQRecy.setLayoutManager(new LinearLayoutManager(this));
        XQRecy.setAdapter(new XQAdapter(this, data));
    }

    @Override
    public void failure(Exception e) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cartPresenter.detach();
    }
}
