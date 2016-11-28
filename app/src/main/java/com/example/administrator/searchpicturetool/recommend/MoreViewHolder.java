package com.example.administrator.searchpicturetool.recommend;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.searchpicturetool.R;
import com.example.administrator.searchpicturetool.model.bean.NewRecommendContent;
import com.example.administrator.searchpicturetool.search.result.SearchResultActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by Administrator on 2016/5/10 0010.
 */
public class MoreViewHolder extends BaseViewHolder<NewRecommendContent> implements View.OnClickListener{
    NewRecommendContent recommendContent;
    private CardView cardView;
    private SimpleDraweeView draweeView;
    private TextView title;
    private TextView content;
    public MoreViewHolder(ViewGroup parent) {
        super(parent, R.layout.itemview_more);
        draweeView =$(R.id.recomend_img);
        title =$(R.id.recommend_title);
        content =$(R.id.recommend_content);
        cardView =$(R.id.recommend_cardview);
        cardView.setOnClickListener(this);
    }

    @Override
    public void setData(NewRecommendContent data) {
        super.setData(data);
        recommendContent =data;
        draweeView.setImageURI(Uri.parse(data.getImageUrl()));
        title.setText(data.getTitle());
        content.setText(data.getContent());
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        bundle.putString("search",recommendContent.getTitle());
        bundle.putString("imagUrl",recommendContent.getImageUrl());
        Intent intent = new Intent();
        intent.putExtra("search", bundle);
        intent.setClass(getContext(), SearchResultActivity.class);
        getContext().startActivity(intent);
    }
}
