package com.example.administrator.searchpicturetool.recommend;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.searchpicturetool.R;
import com.example.administrator.searchpicturetool.model.bean.NewBanner;
import com.example.administrator.searchpicturetool.base.BaseListActivity;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by WenHuaijun on 2016/9/12 0012.
 */
@RequiresPresenter(BannerListActivityPresenter.class)
public class BannerListActivity extends BaseListActivity<BannerListActivityPresenter,NewBanner> implements View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        //  setTitle(getString(R.string.menu_recommend));
    }

    @Override
    public int getLayout() {
        return R.layout.activity_more;
    }

    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new BannerListViewHolder(parent);
    }

    @Override
    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    public void onClick(View view) {
       super.onClick(view);
    }
}