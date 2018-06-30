package com.padcmyanmar.charleskeith.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.padcmyanmar.charleskeith.R;
import com.padcmyanmar.charleskeith.data.vo.ProductVo;
import com.padcmyanmar.charleskeith.delegates.ProductsDelegate;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductsViewHolder extends RecyclerView.ViewHolder {
    private ProductVo productVo;
    private ProductsDelegate mproductsDelegate;
   /* @BindView(R.id.iv_grid_new)
    ImageView ivGridNew;*/
    @BindView(R.id.iv_list_new)
    ImageView ivListNew;
    @BindView(R.id.tv_list)
    TextView tvList;
    public ProductsViewHolder(View itemView,ProductsDelegate productsDelegate) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        this.mproductsDelegate = productsDelegate;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mproductsDelegate.onTapProducts(productVo);
            }
        });

    }

    public void setProductData(ProductVo productVo) {
        this.productVo = productVo;

        /*Glide.with(ivGridNew.getContext())
                .load(productVo.getProduct_image())
                .into(ivGridNew);*/

        Glide.with(ivListNew.getContext())
                .load(productVo.getProduct_image())
                .into(ivListNew);
        tvList.setText(productVo.getProduct_title());

    }
}
