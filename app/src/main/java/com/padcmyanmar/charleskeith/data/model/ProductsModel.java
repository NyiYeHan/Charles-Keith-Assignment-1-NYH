package com.padcmyanmar.charleskeith.data.model;

import com.padcmyanmar.charleskeith.data.vo.ProductVo;
import com.padcmyanmar.charleskeith.event.SuccessProductsEvent;
import com.padcmyanmar.charleskeith.network.DataAgent;
import com.padcmyanmar.charleskeith.network.RetrofitDataAgentImpl;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductsModel {
    private static ProductsModel obj;
    private DataAgent dataAgent;
    public Map<String, ProductVo> mMap;

    private ProductsModel() {
        dataAgent = RetrofitDataAgentImpl.getObj();
        mMap = new HashMap<>();
        EventBus.getDefault().register(this);
    }

    public static ProductsModel getObj() {
        if (obj == null) {
            obj = new ProductsModel();
        }
        return obj;
    }

    public void loadShoesList(int page, String accessToken) {
        dataAgent.loadShoesList(page, accessToken);
    }

    public ProductVo getProductById(String id) {
        return mMap.get(id);
        //return null;
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onSuccessGetProducts(SuccessProductsEvent event) {
        for (ProductVo productVo : event.getProductVos()) {
            mMap.put(productVo.getProduct_id(), productVo);
        }

    }

    public List<ProductVo> getProductVoList() {
        return new ArrayList<>(mMap.values());
    }
}
