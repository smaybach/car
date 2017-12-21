package com.example.shoppingcar;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoppingcar.bean.CartBean;
import com.example.shoppingcar.bean.addBean;
import com.example.shoppingcar.utils.APIFactory;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observer;

/**
 * date:2017/12/20
 * author:张伟(Administrator)
 * desc:
 */
public class XQAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<CartBean.DataBean> data;

    public XQAdapter(XQActivity xqActivity, List<CartBean.DataBean> data) {
        this.context = xqActivity;
        this.data = data;

        Fresco.initialize(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.xqitem, null);
        return new XQViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //拆分images字段
        String[] split = data.get(position).images.split("\\|");
        System.out.println("data.get(position).images = " + data.get(position).images);
        System.out.println("image"+split[0]);
        //设置商品的图片
        ((XQViewHolder) holder).img.setImageURI(Uri.parse(split[0]));
//        ((XQViewHolder) holder).img.setImageURI(split[0].toString());
//        ((XQViewHolder)holder).img.setImageURI(data.get(position).images);
//        Glide.with(context).load(split[0].toString()).into(((XQViewHolder) holder).img);
        ((XQViewHolder) holder).name.setText(data.get(position).title);
        ((XQViewHolder) holder).price.setText(data.get(position).price + "");
        ((XQViewHolder) holder).btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pid = data.get(position).pid;
                Map<String, String> map = new HashMap<String, String>();
                map.put("uid", "4421");
                map.put("pid", pid + "");
                APIFactory.getInstance().add(map, new Observer<addBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(addBean addBean) {
                        String code = addBean.code;
                        if (code.equals("0")) {
                            Toast.makeText(context, "加购成功", Toast.LENGTH_SHORT).show();
                        } else if (code.equals("1")) {
                            Toast.makeText(context, "天呢！用户未登录或用户id不能为空", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class XQViewHolder extends RecyclerView.ViewHolder {

        private final SimpleDraweeView img;
        private final TextView name;
        private final TextView price;
        private final Button btn;

        public XQViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.xq_fresco);
            name = itemView.findViewById(R.id.xq_name);
            price = itemView.findViewById(R.id.xq_price);
            btn = itemView.findViewById(R.id.xq_btn);
        }
    }
}
