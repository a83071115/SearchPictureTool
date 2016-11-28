package com.example.administrator.searchpicturetool.recommend;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import com.example.administrator.searchpicturetool.R;
import com.example.administrator.searchpicturetool.config.Constant;
import com.example.administrator.searchpicturetool.model.bean.NewRecommendContent;
import com.example.administrator.searchpicturetool.base.BaseListActivity;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/10 0010.
 */
@RequiresPresenter(MoreAcitivityPresenter.class)
public class MoreRecommendActivity extends BaseListActivity<MoreAcitivityPresenter,NewRecommendContent> implements View.OnClickListener{
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_more;
    }

    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new MoreViewHolder(parent);
    }
    @Override
    protected ListConfig getConfig() {
        return Constant.getBaseConfig();
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
