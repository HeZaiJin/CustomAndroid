package com.haozhang.android.rest;

import com.haozhang.android.modle.*;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * REST retrofit 无线模块
 * @author HaoZhang
 * @date 2016/7/24.
 */
public class RESTClient {

    private static final Retrofit sRetrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Config.API)
            .build();

    private static final Retrofit sLoaderRetrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Config.API_WELF)
            .build();

    private static final APIServices sApiService = sRetrofit.create(APIServices.class);
    private static final APIServices sLoaderService = sLoaderRetrofit.create(APIServices.class);


    public static void getArray(Callback<String[]> callback) {
        Call<String[]> array = sApiService.getArray();
        array.enqueue(callback);
    }

    public static void getWelfares(int index, Callback<BaseData<WelfareItemDatas>> call) {
        String type = "福利";
        String ins = String.valueOf(index);
        if (null != ins) {
            Call<BaseData<WelfareItemDatas>> ca = sLoaderService.loadWelfareDatas(type, ins);
            ca.enqueue(call);
        }
    }


}
