package com.padcmyanmar.charleskeith.data.vo;

import com.google.gson.annotations.SerializedName;

public class ProductVo {
    @SerializedName("product-id")
    private String product_id;
    @SerializedName("product-image")
    private String product_image;
    @SerializedName("product-title")
    private String product_title;

    public String getProduct_image() {
        return product_image;
    }

    public String getProduct_title() {
        return product_title;
    }

    public String getProduct_id() {

        return product_id;
    }
}
