package com.example.administrator.searchpicturetool.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.administrator.searchpicturetool.R;
import com.example.administrator.searchpicturetool.config.ShareConfig;
import com.example.administrator.searchpicturetool.util.Utils;
import com.example.administrator.searchpicturetool.recommend.BannerListActivity;
import com.example.administrator.searchpicturetool.search.result.SearchResultActivity;
import com.example.administrator.searchpicturetool.setting.SettingActivity;
import com.example.administrator.searchpicturetool.user.UserActivity;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.utils.JUtils;
import com.search.material.library.MaterialSearchView;
import com.umeng.fb.FeedbackAgent;
import com.umeng.message.PushAgent;
import com.umeng.update.UmengUpdateAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@RequiresPresenter(MainActivityPresenter.class)
public class MainActivity extends BeamBaseActivity<MainActivityPresenter> implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.search_view)
    MaterialSearchView searchView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.main_fab_layout)
    RelativeLayout fabLayout;
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setDrawerLayout();
        initSearchView();
        initAppBarSetting();
        initPush();
        UmengUpdateAgent.forceUpdate(this);
        marginNavigationBar(fab);


    }

    public void initPush() {
        FeedbackAgent agent = new FeedbackAgent(this);
        agent.sync();
        if (JUtils.getSharedPreference().getBoolean("shouldPush", true)) {
            PushAgent mPushAgent = PushAgent.getInstance(this);
            mPushAgent.enable();
        }
    }

    public void setDrawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    public void initSearchView() {
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                navigationView.setCheckedItem(R.id.nav_main);
                Bundle bundle = new Bundle();
                bundle.putString("search", query);
                Intent intent = new Intent();
                intent.putExtra("search", bundle);
                intent.setClass(MainActivity.this, SearchResultActivity.class);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    public void initAppBarSetting() {
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                MainActivity.this.getPresenter().stopRefresh(i);
                if (i == 0 && fab.isShown()) {
                    fab.hide();
                } else if (i != 0 && !fab.isShown()) {
                    fab.show();
                }
            }
        });
    }

    @OnClick(R.id.fab)
    public void clickFab(View view) {
        getPresenter().goToUp(0);
    }

    public void openShare() {
        ShareConfig config = new ShareConfig();
        config.init(this, this).openShare(this, false);

    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            //super.onBackPressed();
            exit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_search){
            searchView.showSearch();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_main) {

        } else if (id == R.id.nav_banner) {
            startActivity(new Intent(this, BannerListActivity.class));
        } else if (id == R.id.nav_search) {
            searchView.showSearch();
        } else if (id == R.id.nav_user) {
            startActivity(new Intent(this, UserActivity.class));
        } else if (id == R.id.nav_setting) {
            startActivity(new Intent(this, SettingActivity.class));
        }/* else if (id == R.id.nav_share) {
            openShare();
        }*/ else if (id == R.id.nav_rate) {
            try {
                openToRate();
            } catch (Throwable e) {
                JUtils.Toast("未发现任何应用市场");
            }

        } else if (id == R.id.nav_send) {
            FeedbackAgent agent = new FeedbackAgent(this);
            agent.startFeedbackActivity();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (data != null) {
                getPresenter().goToUp(data.getIntExtra("position", 0));
            }

        }
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    public TabLayout getTabLayout() {
        return tabLayout;
    }

    public void exit() {
        boolean hasRated = JUtils.getSharedPreference().getBoolean("hasRated",false);
        int count = JUtils.getSharedPreference().getInt("app_exit_count", 1);
        if (!hasRated&&(count==5||count%10==0)) {
            count++;
            JUtils.getSharedPreference().edit().putInt("app_exit_count", count).commit();
            showRatingDialog();
            return;
        }
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            JUtils.Toast("再按一次退出");
            exitTime = System.currentTimeMillis();
        } else {
            count++;
            JUtils.getSharedPreference().edit().putInt("app_exit_count", count).commit();
            finish();
            //System.exit(0);
        }
    }

    public void showRatingDialog() {
        new MaterialDialog.Builder(this)
                .title("给个好评")
                .content("陛下，如果您喜欢这个应用，请给个好评！您的鼓励是我们做得更好的动力！")
                .negativeText("继续退出")
                .positiveText("去评分")
                .cancelable(false)
                .canceledOnTouchOutside(false)
                .onPositive((dialog, which) -> {
                    JUtils.getSharedPreference().edit().putBoolean("hasRated",true).commit();
                    openToRate();

                })
                .onNegative((dialog, which) -> finish()).show();
    }

    public void openToRate() {
        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void marginNavigationBar(View view){
        if(!Utils.checkDeviceHasNavigationBar(this)){
            return;
        }
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)view.getLayoutParams();
        layoutParams.setMargins(JUtils.dip2px(16),JUtils.dip2px(16),JUtils.dip2px(16),Utils.getNavigationBarHeight(this)/2);
        view.setLayoutParams(layoutParams);
    }
}
