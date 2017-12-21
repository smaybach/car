package com.example.shoppingcar;

import com.example.shoppingcar.bean.CartBean;
import com.example.shoppingcar.bean.addBean;
import com.example.shoppingcar.bean.deleteBean;
import com.example.shoppingcar.bean.queryBean;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * date:2017/12/19
 * author:张伟(Administrator)
 * desc:
 */

public interface IGetHttpUrl {

    @GET("searchProducts?page=1&source=android")
    Observable<CartBean> cartUrl(@QueryMap Map<String, String> map);
//    http://120.27.23.105/product/searchProducts?keywords=手机&page=1&source=android

    @GET("addCart?token=0DA2B4A9F80689086B333CADEB7F306B&source=android")
    Observable<addBean> addUrl(@QueryMap Map<String, String> map);
//    Observable<addBean> addUrl(@Query("uid") String uid, @Query("pid") String pid);


    @GET("getCarts?token=0DA2B4A9F80689086B333CADEB7F306B&source=android")
    Observable<queryBean> queryUrl(@QueryMap Map<String, String> map);
//    Observable<queryBean> queryUrl(@Query("uid") String uid);


    @GET("deleteCart?token=0DA2B4A9F80689086B333CADEB7F306B&source=android")
    Observable<deleteBean> deleteUrl(@QueryMap Map<String, String> map);
//    Observable<deleteBean> deleteUrl(@Query("uid") String uid, @Query("pid") String pid);


}
