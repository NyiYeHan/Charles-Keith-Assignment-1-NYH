package com.padcmyanmar.charleskeith.event;

import com.padcmyanmar.charleskeith.data.vo.ProductVo;

import java.util.List;

public class SuccessProductsEvent {
    private List<ProductVo> productVos;

    public List<ProductVo> getProductVos() {
        return productVos;
    }

    public SuccessProductsEvent(List<ProductVo> productVos) {

        this.productVos = productVos;
    }
}
