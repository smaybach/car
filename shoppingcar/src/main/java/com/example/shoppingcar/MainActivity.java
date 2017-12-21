package com.example.shoppingcar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoppingcar.bean.queryBean;
import com.example.shoppingcar.presenter.queryPresenter;
import com.example.shoppingcar.view.queryView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements queryView {

    @Bind(R.id.bianji)
    TextView bianji;
    @Bind(R.id.recycler_View)
    RecyclerView recyclerView;
    @Bind(R.id.quanxuan)
    CheckBox quanxuan;
    @Bind(R.id.total_price)
    TextView totalPrice;
    @Bind(R.id.total_num)
    TextView totalNum;
    @Bind(R.id.quzhifu)
    TextView quzhifu;
    @Bind(R.id.shanchu)
    TextView shanchu;
    @Bind(R.id.linear_shanchu)
    LinearLayout linearShanchu;
    @Bind(R.id.activity_main)
    LinearLayout activityMain;
    @Bind(R.id.back)
    TextView back;
    private RecyAdapter recyAdapter;
    private com.example.shoppingcar.presenter.queryPresenter queryPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        bianji.setTag(1);//编辑设置TAG=1
        quanxuan.setTag(1);//全选设置TAG=1   为未选中

        queryPresenter = new queryPresenter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyAdapter = new RecyAdapter(this);
        queryPresenter.queryFF();
        recyclerView.setAdapter(recyAdapter);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, XQActivity.class));
                finish();
            }
        });

        //调用RecyAdapter里面的接口，设置全选按钮   总价  总数量
        recyAdapter.setUpdateListener(new RecyAdapter.UpdateListener() {
            @Override
            public void setTotal(String total, String num, boolean allCheck) {
                //设置ui的改变
                totalNum.setText("共" + num + "件商品");//总数量
                totalPrice.setText("总价 :¥" + total + "元");//总价
                if (allCheck) {
                    quanxuan.setTag(2);
                    quanxuan.setBackgroundResource(R.drawable.shopcart_selected);
                } else {
                    quanxuan.setTag(1);
                    quanxuan.setBackgroundResource(R.drawable.shopcart_unselected);
                }
                quanxuan.setChecked(allCheck);
            }
        });

        //这里只做ui更改, 点击全选按钮,,调到adapter里面操作
        quanxuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用adapter里面的方法 ,,把当前quanxuan状态传递过去
                int tag = (int) quanxuan.getTag();
                if (tag == 1) {
                    quanxuan.setTag(2);
                    quanxuan.setBackgroundResource(R.drawable.shopcart_selected);
                } else {
                    quanxuan.setTag(1);
                    quanxuan.setBackgroundResource(R.drawable.shopcart_unselected);
                }

                recyAdapter.quanXuan(quanxuan.isChecked());
            }
        });

        //点击批量删除的按钮
        shanchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyAdapter.shanChu();
            }
        });

        //点击编辑按钮,
        bianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) bianji.getTag();
                if (tag == 1) {
                    bianji.setText("完成");
                    bianji.setTag(2);
                    quzhifu.setVisibility(View.GONE);
                    shanchu.setVisibility(View.VISIBLE);
                } else {
                    bianji.setText("编辑");
                    bianji.setTag(1);
                    quzhifu.setVisibility(View.VISIBLE);
                    shanchu.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    public void success(queryBean queryBean) {
        if (queryBean != null) {
            //将返回的数据添加到适配器中
            recyAdapter.add(queryBean);
        }
    }

    @Override
    public void failure() {
        System.out.println("网不好");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "网有点慢", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除绑定
        queryPresenter.detach();
    }
}
