package com.padcmyanmar.charleskeith.viewpods;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.padcmyanmar.charleskeith.R;


import butterknife.BindView;
import butterknife.ButterKnife;


public class EmptyViewPod extends RelativeLayout {

    @BindView(R.id.iv_empty)
    ImageView ivEmpty;

    @BindView(R.id.tv_empty)
    TextView tvEmpty;

    public EmptyViewPod(Context context) {
        super(context);
    }

    public EmptyViewPod(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EmptyViewPod(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this,this);
    }
    public void setEmptyData(String emptyImageUrl,String emptyMsg){
        tvEmpty.setText(emptyMsg);
        Glide.with(getContext())
                .load(emptyImageUrl)
                .into(ivEmpty);
    }
    public void setEmptyData(int emptyImage,String emptyMsg){
        tvEmpty.setText(emptyMsg);
        ivEmpty.setImageResource(emptyImage);
    }
}
