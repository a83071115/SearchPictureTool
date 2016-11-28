package com.example.administrator.searchpicturetool.user;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.administrator.searchpicturetool.R;
import com.example.administrator.searchpicturetool.util.Utils;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.utils.JUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wenhuaijun on 2015/11/12 0012.
 */
@RequiresPresenter(UserActivityPresenter.class)
public class UserActivity extends BeamBaseActivity<UserActivityPresenter> implements ViewPager.OnPageChangeListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.white));
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        initAppBarSetting();
        fab.hide();
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_pictures_menu, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_delete:
                if(!fab.isShown()){
                    fab.show();
                    showSnackbar();
                    getPresenter().beginSelectImgs(true);
                }else {
                    fab.hide();
                    getPresenter().doDelete();
                }

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void initAppBarSetting() {
        appBarLayout.addOnOffsetChangedListener((appBarLayout1, i) -> UserActivity.this.getPresenter().stopRefresh(i));
    }

    @OnClick(R.id.fab)
    public void beginTransaction() {
        fab.hide();
        getPresenter().doDelete();
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    public TabLayout getTabLayout() {
        return tabLayout;
    }

    @Override
    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
       getPresenter().page=position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void showSnackbar() {
        String text = "请选择要删除的图片";
        if(!Utils.checkDeviceHasNavigationBar(this)){
            Snackbar.make(fab, text, Snackbar.LENGTH_SHORT)
                   .show();
        }else {
            JUtils.Toast(text);
        }

    }

    @Override
    public void onBackPressed() {
        if (getPresenter().isTransactioning) {
            if(fab.isShown()){
                fab.hide();
            }
            getPresenter().transactionEnd();
            return;
        }
        super.onBackPressed();
    }
}
