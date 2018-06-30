package com.padcmyanmar.charleskeith.event;

import com.padcmyanmar.charleskeith.data.vo.ProductVo;

import java.util.List;

public class SuccessForceRefreshGetProductsEvent extends SuccessProductsEvent {


    public SuccessForceRefreshGetProductsEvent(List<ProductVo> productVos) {
        super(productVos);
    }
}
