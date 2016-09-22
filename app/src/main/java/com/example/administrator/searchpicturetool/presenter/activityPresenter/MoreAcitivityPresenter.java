package com.example.administrator.searchpicturetool.presenter.activityPresenter;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.example.administrator.searchpicturetool.R;
import com.example.administrator.searchpicturetool.app.APP;
import com.example.administrator.searchpicturetool.model.MoreRecommendModel;
import com.example.administrator.searchpicturetool.model.bean.NewRecommendContent;
import com.example.administrator.searchpicturetool.presenter.BaseListActivityPresenter;
import com.example.administrator.searchpicturetool.view.activity.MoreRecommendActivity;
import com.example.administrator.searchpicturetool.view.fragment.NetImgFragment;
import com.jude.beam.expansion.list.BeamListActivityPresenter;
import com.jude.utils.JUtils;


/**
 * Created by Administrator on 2016/5/10 0010.
 */

public class MoreAcitivityPresenter extends BaseListActivityPresenter<MoreRecommendActivity, NewRecommendContent> {
    private String tip;
    private float type;


    @Override
    protected void onCreate(@NonNull MoreRecommendActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        tip = getView().getIntent().getStringExtra("tip");
        type = getView().getIntent().getFloatExtra("type", 0);
        onRefresh();
    }

    @Override
    protected void onCreateView(MoreRecommendActivity view) {
        super.onCreateView(view);
        getView().getToolbar().setTitle(tip);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }



    @Override
    public void onRefresh() {
        super.onRefresh();
            MoreRecommendModel.getMoreRecommend(APP.getInstance(), tip, type)
                    .subscribe(getRefreshSubscriber());
    }
}
