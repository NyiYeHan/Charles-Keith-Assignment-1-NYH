package com.padcmyanmar.charleskeith.network;

import com.padcmyanmar.charleskeith.network.response.GetProductsResponse;
import com.padcmyanmar.charleskeith.utils.ProductsConstant;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ProductsApi {
    @FormUrlEncoded
    @POST(ProductsConstant.GET_SHOES_PRODUCT)
    Call<GetProductsResponse> loadShoesList(
           @Field(ProductsConstant.PARAM_PAGE) int page,
           @Field(ProductsConstant.PARAM_ACCESS_TOKEN) String accessToken
    );



}
