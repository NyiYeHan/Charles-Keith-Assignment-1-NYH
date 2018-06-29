package com.padcmyanmar.charleskeith.activities;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.bumptech.glide.Glide;
import com.padcmyanmar.charleskeith.MainActivity;

import com.padcmyanmar.charleskeith.R;
import com.padcmyanmar.charleskeith.data.model.ProductsModel;
import com.padcmyanmar.charleskeith.data.vo.ProductVo;
import com.padcmyanmar.charleskeith.viewpods.EmptyViewPod;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ProductsDetailsActivity extends MainActivity {

    ImageView ivDetails;


    EmptyViewPod vpDetails;


    RelativeLayout relativeLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*ButterKnife.bind(this,this);*/

        setContentView(R.layout.activity_proucts_details);
        ivDetails = findViewById(R.id.iv_details);
        vpDetails = findViewById(R.id.vp_details_empty);
        relativeLayout = findViewById(R.id.relativeDetails);

        String productId = getIntent().getStringExtra("productId");
        ProductVo productVo = ProductsModel.getObj().getProductById(productId);
        if (productVo != null){
            bind(productVo);
        }
        else {
            vpDetails.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.GONE);
            vpDetails.setEmptyData(getString(R.string.empty_img_url), getString(R.string.empty_msg));

        }





    }
    public void bind(ProductVo productVo){
        Glide.with(ivDetails.getContext())
                .load(productVo.getProduct_image())
                .into(ivDetails);
    }
}
