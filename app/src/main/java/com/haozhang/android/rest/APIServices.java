package com.haozhang.android.rest;

import com.haozhang.android.modle.*;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author HaoZhang
 * @date 2016/7/24.
 */
public interface APIServices {

    @GET("test.ashx")
    Call<String[]> getArray();

    @GET("data/{type}/10/{index}")
    Call<BaseData<WelfareItemDatas>> loadWelfareDatas(@Path("type") String type, @Path("index") String index);
}
