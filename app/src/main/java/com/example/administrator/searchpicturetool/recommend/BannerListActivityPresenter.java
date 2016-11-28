package com.example.administrator.searchpicturetool.recommend;

import android.support.annotation.NonNull;

import com.example.administrator.searchpicturetool.app.APP;
import com.example.administrator.searchpicturetool.model.MoreRecommendModel;
import com.example.administrator.searchpicturetool.model.bean.NewBanner;
import com.example.administrator.searchpicturetool.base.BaseListActivityPresenter;

/**
 * Created by WenHuaijun on 2016/9/12 0012.
 */
public class BannerListActivityPresenter extends BaseListActivityPresenter<BannerListActivity,NewBanner> {
    @Override
    protected void onCreateView(@NonNull BannerListActivity view) {
        super.onCreateView(view);
        onRefresh();
    }
    @Override
    public void onRefresh() {
        super.onRefresh();
        MoreRecommendModel.getRecommendBanners(APP.getInstance())
                .subscribe(getRefreshSubscriber());
    }
}
