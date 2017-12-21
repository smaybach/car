package com.example.shoppingcar.utils;

import com.example.shoppingcar.IGetHttpUrl;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * date:2017/12/19
 * author:张伟(Administrator)
 * desc:
 */

public class RetrofitUtils {
    private static IGetHttpUrl service = null;

    public static IGetHttpUrl getInstance() {
        if (service == null) {
            synchronized (RetrofitUtils.class) {
                if (service == null){
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://120.27.23.105/product/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .client(OkhttpUtils.getInstance())
                            .build();

                    service = retrofit.create(IGetHttpUrl.class);
                }
            }
        }
        return service;
    }
}
