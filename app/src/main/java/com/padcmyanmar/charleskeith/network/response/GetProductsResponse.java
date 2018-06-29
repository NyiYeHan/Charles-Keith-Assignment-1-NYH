package com.padcmyanmar.charleskeith.network.response;

import com.google.gson.annotations.SerializedName;
import com.padcmyanmar.charleskeith.data.vo.ProductVo;

import java.util.List;

public class GetProductsResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("apiVersion")
    private String apiVersion;

    @SerializedName("page")
    private String page;

    @SerializedName("newProducts")
    private List<ProductVo> productVos;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public String getPage() {
        return page;
    }

    public List<ProductVo> getProductVos() {
        return productVos;
    }

    public boolean responseIsOk() {
        return (code == 200 && productVos != null);
    }
}
