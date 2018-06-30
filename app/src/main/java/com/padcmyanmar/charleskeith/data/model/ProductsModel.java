package com.padcmyanmar.charleskeith.data.model;

import com.padcmyanmar.charleskeith.data.vo.ProductVo;
import com.padcmyanmar.charleskeith.event.SuccessForceRefreshGetProductsEvent;
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
    private static final String DUMMY_ACCESS_TOKEN = "b002c7e1a528b7cb460933fc2875e916";
    private int mpage;
    private ProductsModel() {
        dataAgent = RetrofitDataAgentImpl.getObj();
        mMap = new HashMap<>();
        EventBus.getDefault().register(this);
        mpage = 1;
    }

    public static ProductsModel getObj() {
        if (obj == null) {
            obj = new ProductsModel();
        }
        return obj;
    }

    public void loadProductsList() {
        dataAgent.loadProductsList(mpage, DUMMY_ACCESS_TOKEN,false);
    }
    public void forceRefreshProductsList(){
        mpage = 1;
        dataAgent.loadProductsList(1,DUMMY_ACCESS_TOKEN,true);
    }

    public ProductVo getProductById(String id) {
        return mMap.get(id);
        //return null;
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onSuccessGetProducts(SuccessProductsEvent event) {
       setDataIntoRepo(event.getProductVos());
        mpage ++;
    }
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onSuccessForceRefreshGetProducts(SuccessForceRefreshGetProductsEvent event) {
        setDataIntoRepo(event.getProductVos());

    }
    private void setDataIntoRepo(List<ProductVo> productList){
        for (ProductVo productVo : productList) {
            mMap.put(productVo.getProduct_id(), productVo);
        }
    }

    public List<ProductVo> getProductVoList() {
        return new ArrayList<>(mMap.values());
    }
}
