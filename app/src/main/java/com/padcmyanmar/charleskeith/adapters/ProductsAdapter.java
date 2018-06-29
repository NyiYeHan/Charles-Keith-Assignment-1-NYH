package com.padcmyanmar.charleskeith.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.padcmyanmar.charleskeith.R;
import com.padcmyanmar.charleskeith.data.vo.ProductVo;
import com.padcmyanmar.charleskeith.delegates.ProductsDelegate;
import com.padcmyanmar.charleskeith.viewholders.ProductsViewHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter <ProductsViewHolder>{

    private List<ProductVo> shoesList;
    private ProductsDelegate productsDelegate;

    public ProductsAdapter(ProductsDelegate productsDelegate) {
        shoesList = new ArrayList<>();
        this.productsDelegate = productsDelegate;
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_products_list, parent, false);

        return new ProductsViewHolder(view,productsDelegate);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder holder, int position) {
        holder.setProductData(shoesList.get(position));
    }

    @Override
    public int getItemCount() {
        return shoesList.size();
    }

    public void setShoesList(List<ProductVo> shoesList) {
        this.shoesList = shoesList;
        notifyDataSetChanged();
    }
}
