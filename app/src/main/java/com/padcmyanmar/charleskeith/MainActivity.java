package com.padcmyanmar.charleskeith;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.padcmyanmar.charleskeith.activities.BaseActivity;
import com.padcmyanmar.charleskeith.activities.ProductsDetailsActivity;
import com.padcmyanmar.charleskeith.adapters.ProductsAdapter;
import com.padcmyanmar.charleskeith.data.model.ProductsModel;
import com.padcmyanmar.charleskeith.data.vo.ProductVo;
import com.padcmyanmar.charleskeith.delegates.ProductsDelegate;
import com.padcmyanmar.charleskeith.event.ApiErrorEvent;
import com.padcmyanmar.charleskeith.event.SuccessProductsEvent;
import com.padcmyanmar.charleskeith.viewpods.EmptyViewPod;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements ProductsDelegate {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_item)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshing)
    SwipeRefreshLayout swipeRefreshLayout;
    private ProductsAdapter productsAdapter;
    @BindView(R.id.vp_empty)
    EmptyViewPod vpEmpty;
    private static final String DUMMY_ACCESS_TOKEN = "b002c7e1a528b7cb460933fc2875e916";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this, this);
        setSupportActionBar(toolbar);
        productsAdapter = new ProductsAdapter(this);
        recyclerView.setAdapter(productsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        /*recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));*/
        ProductsModel.getObj().loadShoesList(1, DUMMY_ACCESS_TOKEN);

        ImageView ivGrid = findViewById(R.id.iv_grid);
        ivGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RelativeLayout grid = findViewById(R.id.in_grid);
                RelativeLayout list = findViewById(R.id.in_list);
                grid.setVisibility(View.VISIBLE);
                list.setVisibility(View.GONE);
                recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));

            }
        });

        ImageView ivList = findViewById(R.id.iv_list);
        ivList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RelativeLayout grid = findViewById(R.id.in_grid);
                RelativeLayout list = findViewById(R.id.in_list);
                grid.setVisibility(View.GONE);
                list.setVisibility(View.VISIBLE);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));


            }
        });
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ProductsModel.getObj().loadShoesList(1, DUMMY_ACCESS_TOKEN);
            }
        });
        vpEmpty.setEmptyData(R.drawable.internet_placeholder, getString(R.string.lost_internet));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessGetShoes(SuccessProductsEvent event) {
        swipeRefreshLayout.setRefreshing(false);
        productsAdapter.setShoesList(event.getProductVos());
        vpEmpty.setVisibility(View.INVISIBLE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onApiErrorEvent(ApiErrorEvent event) {
        swipeRefreshLayout.setRefreshing(false);
        Snackbar.make(swipeRefreshLayout, event.getErrorMsg(), Snackbar.LENGTH_INDEFINITE).show();
        vpEmpty.setVisibility(View.VISIBLE);


    }

    @Override
    public void onTapProducts(ProductVo productVo) {
        Intent intent = new Intent(getApplicationContext(), ProductsDetailsActivity.class);
        intent.putExtra("productId", productVo.getProduct_id());
        startActivity(intent);
    }


}
