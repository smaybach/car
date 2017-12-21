package com.example.shoppingcar;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by Menglucywhh on 2017/12/19.
 */

public class CustomJiaJian extends LinearLayout{
    private Button reverse;
    private Button add;
    private EditText countEdit;
    private int mCount =1;
    public CustomJiaJian(Context context) {
        super(context);
    }

    public CustomJiaJian(final Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = View.inflate(context, R.layout.custom_jiajian,this);

        reverse = (Button) view.findViewById(R.id.reverse);
        add = (Button) view.findViewById(R.id.add);
        countEdit = (EditText) view.findViewById(R.id.count);

        reverse.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = countEdit.getText().toString().trim();
                int count = Integer.valueOf(content);

                if(count>1){
                    mCount = count-1;
                    countEdit.setText(mCount+"");
                    //回调给adapter里面
                    if(customListener!=null){
                        customListener.jiajian(mCount);
                    }
                }else{
                    Toast.makeText(context, "最小数量为1", Toast.LENGTH_SHORT).show();
                }
            }
        });

        add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = countEdit.getText().toString().trim();
                int count = Integer.valueOf(content)+1;
                mCount = count;

                countEdit.setText(mCount+"");

                //接口回调给adapter
                if(customListener!=null){
                    customListener.jiajian(mCount);
                }
            }
        });
    }

    public CustomJiaJian(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    CustomListener customListener;
    public void setCustomListener(CustomListener customListener){
        this.customListener = customListener;
    }

    //加减的接口
    public interface CustomListener{
        public void jiajian(int count);
        public void shuRuZhi(int count);
    }

    public void setEditText(int num) {
        if(countEdit!=null){
            countEdit.setText(num+"");
        }
    }
}
