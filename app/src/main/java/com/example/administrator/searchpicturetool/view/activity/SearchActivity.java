package com.example.administrator.searchpicturetool.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.searchpicturetool.R;
import com.example.administrator.searchpicturetool.presenter.activitPresenter.SearchActivityPresenter;
import com.example.administrator.searchpicturetool.view.fragment.SearchFragment;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wenhuaijun on 2015/11/3 0003.
 */
    @RequiresPresenter(SearchActivityPresenter.class)
public class SearchActivity extends BeamBaseActivity<SearchActivityPresenter>{
    @BindView(R.id.bg_img)
    ImageView imageView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.search_fab)
    FloatingActionButton fab;
    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;
    FragmentManager manager;
    private SearchFragment searchFragment;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
         ButterKnife.bind(this);
        onSetToolbar(toolbar);
        collapsingToolbarLayout.setTitle(getIntent().getBundleExtra("search").getString("search"));
        imageView.setImageResource(getPresenter().getBgImg());
        manager =getSupportFragmentManager();
       searchFragment = new SearchFragment();
        searchFragment.setArguments(getIntent().getBundleExtra("search"));
        manager.beginTransaction().replace(R.id.search_container,searchFragment).commit();
        initAppBarSetting();

    }
    public void initAppBarSetting(){
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                if (i == 0) {
                    fab.hide();
                } else {
                    fab.show();
                }
            }
        });
    }
    @OnClick(R.id.search_fab)
    public void clickFab(View view){
        getPresenter().gotoUp(0);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){
            if(data!=null){
                getPresenter().gotoUp(data.getIntExtra("position",0));
            }

        }
    }

    public SearchFragment getSearchFragment() {
        return searchFragment;
    }
}
