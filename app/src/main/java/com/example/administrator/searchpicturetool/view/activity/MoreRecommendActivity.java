package com.example.administrator.searchpicturetool.view.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.example.administrator.searchpicturetool.R;
import com.example.administrator.searchpicturetool.presenter.activitPresenter.MoreAcitivityPresenter;
import com.example.administrator.searchpicturetool.view.fragment.MoreFragment;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/10 0010.
 */
@RequiresPresenter(MoreAcitivityPresenter.class)
public class MoreRecommendActivity extends BeamBaseActivity<MoreAcitivityPresenter>{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        ButterKnife.bind(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.container,new MoreFragment());
    }

}
