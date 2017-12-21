package com.example.shoppingcar.utils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * date:2017/12/19
 * author:张伟(Administrator)
 * desc:
 */

public class OkhttpUtils {
    private static OkHttpClient client = null;

    public static OkHttpClient getInstance() {
        if (client == null) {
            synchronized (OkhttpUtils.class) {
                if (client == null) {
                    client = new OkHttpClient.Builder()
                            .writeTimeout(20000, TimeUnit.SECONDS)
                            .readTimeout(20000, TimeUnit.SECONDS)
                            .connectTimeout(20000, TimeUnit.SECONDS)
                            .build();
                }
            }
        }
        return client;
    }
}
