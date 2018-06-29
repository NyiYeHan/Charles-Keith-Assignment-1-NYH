package com.padcmyanmar.charleskeith.network;

import com.padcmyanmar.charleskeith.event.ApiErrorEvent;
import com.padcmyanmar.charleskeith.event.SuccessProductsEvent;
import com.padcmyanmar.charleskeith.network.response.GetProductsResponse;
import com.padcmyanmar.charleskeith.utils.ProductsConstant;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitDataAgentImpl implements DataAgent {
    private ProductsApi mProductsApi;

    private static RetrofitDataAgentImpl obj;

    private RetrofitDataAgentImpl() {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ProductsConstant.API_BASE_ROOT)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        mProductsApi = retrofit.create(ProductsApi.class);


    }

    public static RetrofitDataAgentImpl getObj() {
        if (obj == null) {
            obj = new RetrofitDataAgentImpl();
        }
        return obj;
    }

    @Override
    public void loadShoesList(int page, String accessToken) {

        Call<GetProductsResponse> getShoesResponseCall = mProductsApi.loadShoesList(page, accessToken);
        getShoesResponseCall.enqueue(new Callback<GetProductsResponse>() {
            @Override
            public void onResponse(Call<GetProductsResponse> call, Response<GetProductsResponse> response) {
                GetProductsResponse responseObj = response.body();
                if (responseObj != null && responseObj.responseIsOk()) {
                    SuccessProductsEvent event = new SuccessProductsEvent(responseObj.getProductVos());
                    EventBus.getDefault().post(event);

                } else {
                    if (responseObj == null) {
                        ApiErrorEvent event = new ApiErrorEvent("empty Response occur");
                        EventBus.getDefault().post(event);
                    } else {
                        ApiErrorEvent event = new ApiErrorEvent(responseObj.getMessage());
                        EventBus.getDefault().post(event);
                    }
                }

            }

            @Override
            public void onFailure(Call<GetProductsResponse> call, Throwable t) {
                ApiErrorEvent event = new ApiErrorEvent(t.getMessage());
                EventBus.getDefault().post(event);
            }
        });
    }
}
