package com.example.administrator.searchpicturetool.recommend;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.searchpicturetool.R;
import com.example.administrator.searchpicturetool.model.bean.NewBanner;
import com.example.administrator.searchpicturetool.search.result.SearchResultActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by WenHuaijun on 2016/9/12 0012.
 */
public class BannerListViewHolder extends BaseViewHolder<NewBanner> implements View.OnClickListener {
    NewBanner recommendContent;
    private CardView cardView;
    private SimpleDraweeView draweeView;
    private TextView title;
    private TextView content;

    public BannerListViewHolder(ViewGroup parent) {
        super(parent, R.layout.itemview_more);
        draweeView = $(R.id.recomend_img);
        title = $(R.id.recommend_title);
        content = $(R.id.recommend_content);
        cardView = $(R.id.recommend_cardview);
        cardView.setOnClickListener(this);
    }

    @Override
    public void setData(NewBanner data) {
        super.setData(data);
        recommendContent = data;
        draweeView.setImageURI(Uri.parse(data.getImageUrl()));
        title.setText(data.getSearchTip());
        content.setText(data.getIntroduce());
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        bundle.putString("search", recommendContent.getSearchTip());
        bundle.putString("imagUrl", recommendContent.getImageUrl());
        Intent intent = new Intent();
        intent.putExtra("search", bundle);
        intent.setClass(getContext(), SearchResultActivity.class);
        getContext().startActivity(intent);
    }
}
