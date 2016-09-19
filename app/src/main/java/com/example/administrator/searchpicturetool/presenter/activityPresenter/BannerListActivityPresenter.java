package com.example.administrator.searchpicturetool.presenter.activityPresenter;

import android.support.annotation.NonNull;

import com.example.administrator.searchpicturetool.R;
import com.example.administrator.searchpicturetool.app.APP;
import com.example.administrator.searchpicturetool.model.MoreRecommendModel;
import com.example.administrator.searchpicturetool.model.bean.NewBanner;
import com.example.administrator.searchpicturetool.view.activity.BannerListActivity;
import com.jude.beam.expansion.list.BeamListActivityPresenter;

/**
 * Created by WenHuaijun on 2016/9/12 0012.
 */
public class BannerListActivityPresenter extends BeamListActivityPresenter<BannerListActivity,NewBanner> {
    @Override
    protected void onCreateView(@NonNull BannerListActivity view) {
        super.onCreateView(view);
        onRefresh();
        initStatusView();
    }

    public void initStatusView() {
        getView().getListView().getErrorView().findViewById(R.id.view_net_btn).setOnClickListener(getView());
        getView().getListView().getEmptyView().findViewById(R.id.view_empty_btn).setOnClickListener(getView());
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        MoreRecommendModel.getRecommendBanners(APP.getInstance())
                .subscribe(getRefreshSubscriber());
    }
}
