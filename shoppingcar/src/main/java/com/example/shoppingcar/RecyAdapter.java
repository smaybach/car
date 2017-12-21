package com.example.shoppingcar;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoppingcar.bean.deleteBean;
import com.example.shoppingcar.bean.queryBean;
import com.example.shoppingcar.presenter.deletePresenter;
import com.example.shoppingcar.presenter.queryPresenter;
import com.example.shoppingcar.utils.APIFactory;
import com.example.shoppingcar.view.deleteView;
import com.example.shoppingcar.view.queryView;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observer;

/**
 * date:2017/12/20
 * author:张伟(Administrator)
 * desc:
 */
public class RecyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements queryView, deleteView {

    queryPresenter queryPresenter;
    deletePresenter deletePresenter;
    Context context;
    //创建大的集合
    private List<queryBean.DataBean.ListBean> list;
    //存放商家的id和商家的名称的map集合
    private Map<String, String> map = new HashMap<>();

    public RecyAdapter(MainActivity mainActivity) {
        this.context = mainActivity;
        queryPresenter = new queryPresenter(this);
        deletePresenter = new deletePresenter(this);
        //初始化Fresco
        Fresco.initialize(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.recy_cart_item, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
/**
 * 设置商铺的 shop_checkbox和商铺的名字 显示或隐藏
 * */
        if (list.get(position).getIsFirst() == 1) {
            //显示商家
            ((MyViewHolder) holder).shop_checkbox.setVisibility(View.VISIBLE);
            ((MyViewHolder) holder).shop_name.setVisibility(View.VISIBLE);
            //设置shop_checkbox的选中状态
            ((MyViewHolder) holder).shop_checkbox.setChecked(list.get(position).isShop_check());
            ((MyViewHolder) holder).shop_name.setText(map.get(String.valueOf(list.get(position).getSellerid())) + " ＞");
        } else {//2
            //隐藏商家
            ((MyViewHolder) holder).shop_name.setVisibility(View.GONE);
            ((MyViewHolder) holder).shop_checkbox.setVisibility(View.GONE);
        }

        //拆分images字段
        String[] split = list.get(position).getImages().split("\\|");
        //设置商品的图片
        ((MyViewHolder) holder).item_face.setImageURI(Uri.parse(split[0]));
        // ImageLoader.getInstance().displayImage(split[0],holder.item_face);
        //控制商品的item_checkbox,,根据字段改变
        ((MyViewHolder) holder).item_checkbox.setChecked(list.get(position).isItem_check());
        ((MyViewHolder) holder).item_name.setText(list.get(position).getTitle());
        ((MyViewHolder) holder).item_price.setText(list.get(position).getPrice() + "");
        //调用customjiajian里面的方法设置 加减号中间的数字
        ((MyViewHolder) holder).customJiaJian.setEditText(list.get(position).getNum());

        ((MyViewHolder) holder).item_bianji.setTag(1);
        //点击item编辑才显示 自定义加减框
        ((MyViewHolder) holder).item_bianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) ((MyViewHolder) holder).item_bianji.getTag();

                if (tag == 1) {
                    //加减号显示
                    ((MyViewHolder) holder).item_bianji.setText("完成");
                    ((MyViewHolder) holder).customJiaJian.setVisibility(View.VISIBLE);
                    //商品的名称隐藏
                    ((MyViewHolder) holder).item_name.setVisibility(View.GONE);
                    ((MyViewHolder) holder).item_yansechima.setVisibility(View.VISIBLE);
                    ((MyViewHolder) holder).item_price.setVisibility(View.GONE);
                    ((MyViewHolder) holder).item_delete.setVisibility(View.VISIBLE);
                    ((MyViewHolder) holder).item_bianji.setTag(2);
                } else {
                    //相反的 隐藏的显示,显示的隐藏
                    //加减号显示
                    ((MyViewHolder) holder).item_bianji.setText("编辑");
                    ((MyViewHolder) holder).customJiaJian.setVisibility(View.GONE);
                    //商品的名称隐藏
                    ((MyViewHolder) holder).item_name.setVisibility(View.VISIBLE);
                    ((MyViewHolder) holder).item_yansechima.setVisibility(View.GONE);
                    ((MyViewHolder) holder).item_price.setVisibility(View.VISIBLE);
                    ((MyViewHolder) holder).item_delete.setVisibility(View.GONE);
                    ((MyViewHolder) holder).item_bianji.setTag(1);
                }
            }
        });
        //设置点击多选框
        //商铺的shop_checkbox点击事件 ,控制商品的item_checkbox
        ((MyViewHolder) holder).shop_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //先改变数据源中的shop_check
                list.get(position).setShop_check(((MyViewHolder) holder).shop_checkbox.isChecked());

                for (int i = 0; i < list.size(); i++) {
                    //如果是同一家商铺的 都给成相同状态
                    if (list.get(position).getSellerid() == list.get(i).getSellerid()) {
                        //当前条目的选中状态 设置成 当前商铺的选中状态
                        list.get(i).setItem_check(((MyViewHolder) holder).shop_checkbox.isChecked());
                    }
                }
                //刷新适配器
                notifyDataSetChanged();
                //调用求和的方法
                sum(list);
            }
        });

        //商品的item_checkbox点击事件,控制商铺的shop_checkbox
        ((MyViewHolder) holder).item_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //先改变数据源中的item_checkbox
                list.get(position).setItem_check(((MyViewHolder) holder).item_checkbox.isChecked());

                //反向控制商铺的shop_checkbox
                for (int i = 0; i < list.size(); i++) {
                    for (int j = 0; j < list.size(); j++) {
                        //如果两个商品是同一家店铺的 并且 这两个商品的item_checkbox选中状态不一样
                        if (list.get(i).getSellerid() == list.get(j).getSellerid() && !list.get(j).isItem_check()) {
                            //就把商铺的shop_checkbox改成false
                            list.get(i).setShop_check(false);
                            break;
                        } else {
                            //同一家商铺的商品 选中状态都一样,就把商铺shop_checkbox状态改成true
                            list.get(i).setShop_check(true);
                        }
                    }
                }

                //更新适配器
                notifyDataSetChanged();
                //调用求和的方法
                sum(list);
            }
        });

        //删除条目的点击事件
        ((MyViewHolder) holder).item_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                list.remove(position);//移除集合中的当前数据
                //删除完当前的条目 重新判断商铺的显示隐藏
                int pid = list.get(position).getPid();
                Map<String, String> mapDel = new HashMap<String, String>();
                mapDel.put("uid", "4421");
                mapDel.put("pid", pid + "");
                APIFactory.getInstance().delete(mapDel, new Observer<deleteBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(deleteBean deleteBean) {
                        String code = deleteBean.code;
                        if (code.equals("0")) {
                            Toast.makeText(context, "删除购物车成功", Toast.LENGTH_SHORT).show();
                            list.remove(position);
                            setFirst(list);
                            //调用重新求和
                            sum(list);
                            notifyDataSetChanged();
                        } else if (code.equals("1")) {
                            Toast.makeText(context, "天呢！商品id不能为空", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        //加减号的监听,
        ((MyViewHolder) holder).customJiaJian.setCustomListener(new CustomJiaJian.CustomListener() {
            @Override
            public void jiajian(int count) {
                //改变数据源中的数量
                list.get(position).setNum(count);
                notifyDataSetChanged();
                sum(list);
            }

            @Override
            //输入值 求总价
            public void shuRuZhi(int count) {
                list.get(position).setNum(count);
                notifyDataSetChanged();
                sum(list);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private final CheckBox shop_checkbox;
        private final TextView shop_name;
        private final CheckBox item_checkbox;
        private final TextView item_name;
        private final TextView item_price;
        private final CustomJiaJian customJiaJian;
        //private final ImageView item_delete;
        private final TextView item_delete;
        private final ImageView item_face;
        private final TextView item_bianji;
        private final TextView item_yansechima;

        public MyViewHolder(View itemView) {
            super(itemView);

            //拿到控件
            shop_checkbox = (CheckBox) itemView.findViewById(R.id.shop_checkbox);
            shop_name = (TextView) itemView.findViewById(R.id.shop_name);
            item_checkbox = (CheckBox) itemView.findViewById(R.id.item_checkbox);
            item_name = (TextView) itemView.findViewById(R.id.item_name);
            item_price = (TextView) itemView.findViewById(R.id.item_price);
            customJiaJian = (CustomJiaJian) itemView.findViewById(R.id.custom_jiajian);
            //item_delete = (ImageView) itemView.findViewById(R.id.item_delete);
            item_delete = (TextView) itemView.findViewById(R.id.item_delete);

            item_face = (ImageView) itemView.findViewById(R.id.item_face);
            item_bianji = itemView.findViewById(R.id.item_bianji);
            item_yansechima = itemView.findViewById(R.id.item_yansechima);
        }
    }


    UpdateListener updateListener;

    public void setUpdateListener(UpdateListener updateListener) {
        this.updateListener = updateListener;
    }

    //view层调用这个方法, 点击quanxuan按钮的操作
    public void quanXuan(boolean checked) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setShop_check(checked);
            list.get(i).setItem_check(checked);

        }
        notifyDataSetChanged();
        sum(list);
    }

    private void sum(List<queryBean.DataBean.ListBean> list) {
        int totalNum = 0;
        float totalMoney = 0.0f;
        boolean allCheck = true;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isItem_check()) {
                totalNum += list.get(i).getNum();
                totalMoney += list.get(i).getNum() * list.get(i).getPrice();
            } else {
                //如果有个未选中,就标记为false
                allCheck = false;
            }
        }
        //接口回调出去 把总价 总数量 和allcheck 传给view层
        updateListener.setTotal(totalMoney + "", totalNum + "", allCheck);
    }

    //批量删除的按钮
    public void shanChu() {
        //存储删除的id
        final List<Integer> delete_listid = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isItem_check()) {
                //将要删除的pid添加到这个集合里
                delete_listid.add(list.get(i).getPid());
            }
        }
        if (delete_listid.size() == 0) {
            //如果没有要删除的,就吐司提示
            Toast.makeText(context, "请选中至少一个商品后再删除", Toast.LENGTH_SHORT).show();
            return;
        }
        //弹框
        final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("操作提示");
        dialog.setMessage("你确定要删除这" + delete_listid.size() + "个商品?");
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //使用接口删除
                String a = "";
                for (int j = 0; j < delete_listid.size(); j++) {
                    // a+=delete_listid.get(j)+"";
                    Integer integer = delete_listid.get(j);
                    String pid = String.valueOf(integer);
                    deletePresenter.deleteFF(pid);
                    // list.remove(j);
                }
                //  Toast.makeText(context, a,Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        }).create().show();
    }

    public void add(queryBean queryBean) {
        if (list == null) {
            list = new ArrayList<>();
        }
        if (queryBean != null) {
            for (queryBean.DataBean shop : queryBean.getData()) {
                map.put(shop.getSellerid(), shop.getSellerName());
                //第二层遍历里面的商品
                for (int i = 0; i < shop.getList().size(); i++) {
                    //添加到list集合里
                    list.add(shop.getList().get(i));
                }
            }
            //调用方法 设置显示或隐藏 商铺名
            setFirst(list);

        }
        notifyDataSetChanged();
    }

    /**
     * 设置数据源,控制是否显示商家
     */
    private void setFirst(List<queryBean.DataBean.ListBean> list) {
        if (list.size() > 0) {
            list.get(0).setIsFirst(1);
            //从第二条开始遍历
            for (int i = 1; i < list.size(); i++) {
                //如果和前一个商品是同一家商店的
                if (list.get(i).getSellerid() == list.get(i - 1).getSellerid()) {
                    //设置成2不显示商铺
                    list.get(i).setIsFirst(2);
                } else {//设置成1显示商铺
                    list.get(i).setIsFirst(1);
                    //如果当前条目选中,把当前的商铺也选中
                    if (list.get(i).isItem_check() == true) {
                        list.get(i).setShop_check(list.get(i).isItem_check());
                    }
                }
            }
        }
    }

    @Override
    public void success(queryBean queryBean) {
        list.clear();
        add(queryBean);
    }

    @Override
    public void success(deleteBean deleteBean) {
        queryPresenter.queryFF();
    }

    @Override
    public void failure() {
        System.out.println("网不好");
        Toast.makeText(context, "adapter网有点慢", Toast.LENGTH_SHORT).show();
    }

    //接口
    public interface UpdateListener {
        public void setTotal(String total, String num, boolean allCheck);
    }

}
