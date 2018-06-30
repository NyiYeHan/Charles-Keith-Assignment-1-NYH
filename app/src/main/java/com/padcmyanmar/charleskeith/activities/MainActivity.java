package com.padcmyanmar.charleskeith.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.padcmyanmar.charleskeith.R;
import com.padcmyanmar.charleskeith.activities.BaseActivity;
import com.padcmyanmar.charleskeith.activities.ProductsDetailsActivity;
import com.padcmyanmar.charleskeith.adapters.ProductsAdapter;
import com.padcmyanmar.charleskeith.data.model.ProductsModel;
import com.padcmyanmar.charleskeith.data.vo.ProductVo;
import com.padcmyanmar.charleskeith.delegates.ProductsDelegate;
import com.padcmyanmar.charleskeith.event.ApiErrorEvent;
import com.padcmyanmar.charleskeith.event.SuccessForceRefreshGetProductsEvent;
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
        ProductsModel.getObj().loadProductsList();

       ImageView ivGrid = findViewById(R.id.iv_grid);


        ivGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RelativeLayout relativeGrid = findViewById(R.id.rl_shoes);
                relativeGrid.getLayoutParams().width = 180;
                relativeGrid.getLayoutParams().height = 200;

                ImageView ivListNew = findViewById(R.id.iv_list_new);
                ivListNew.getLayoutParams().width =180;
                ivListNew.getLayoutParams().height = 170;

                Button btnNews = findViewById(R.id.btn_list);
                btnNews.getLayoutParams().width=10;
                btnNews.getLayoutParams().height=20;

                recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));

            }
        });

        ImageView ivList = findViewById(R.id.iv_list);
        ivList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));


            }
        });
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ProductsModel.getObj().forceRefreshProductsList();
            }
        });
        vpEmpty.setEmptyData(R.drawable.internet_placeholder, getString(R.string.lost_internet));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private boolean isListItemRead = true;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE &&
                        ((LinearLayoutManager) recyclerView.getLayoutManager())
                                .findLastCompletelyVisibleItemPosition() ==
                                recyclerView.getAdapter().getItemCount() - 1
                        && !isListItemRead) {
                    ProductsModel.getObj().loadProductsList();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
                int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                int pastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager())
                        .findFirstVisibleItemPosition();
                if (visibleItemCount + pastVisibleItem < totalItemCount) {
                    isListItemRead = false;
                }

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
    public void onSuccessGetProducts(SuccessProductsEvent event) {
        swipeRefreshLayout.setRefreshing(false);
        if (event.getProductVos() != null) {
            productsAdapter.appendProductsList(event.getProductVos());

        }

    }

    public void onSuccessForceRefreshGetProducts(SuccessForceRefreshGetProductsEvent event) {
        swipeRefreshLayout.setRefreshing(false);
        productsAdapter.setProductsList(event.getProductVos());

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onApiErrorEvent(ApiErrorEvent event) {
        if (!event.getErrorMsg().contains("success")) {
            swipeRefreshLayout.setRefreshing(false);
            Snackbar.make(swipeRefreshLayout, event.getErrorMsg(), Snackbar.LENGTH_INDEFINITE).show();
            vpEmpty.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void onTapProducts(ProductVo productVo) {
        Intent intent = new Intent(getApplicationContext(), ProductsDetailsActivity.class);
        intent.putExtra("productId", productVo.getProduct_id());
        startActivity(intent);
    }


}
