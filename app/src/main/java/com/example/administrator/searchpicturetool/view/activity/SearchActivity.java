package com.example.administrator.searchpicturetool.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.administrator.searchpicturetool.R;
import com.example.administrator.searchpicturetool.config.Constant;
import com.example.administrator.searchpicturetool.presenter.activityPresenter.SearchActivityPresenter;
import com.example.administrator.searchpicturetool.util.Utils;
import com.example.administrator.searchpicturetool.view.fragment.SearchFragment;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.utils.JUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wenhuaijun on 2015/11/3 0003.
 */
@RequiresPresenter(SearchActivityPresenter.class)
public class SearchActivity extends BeamBaseActivity<SearchActivityPresenter> {
    @BindView(R.id.searchView)
    SearchView searchView;
    @BindView(R.id.bg_img)
    SimpleDraweeView imageView;
    @BindView(R.id.id_search_coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.search_fab)
    FloatingActionButton fab;
    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;
    FragmentManager manager;
    MenuItem item;
    private int maxScrollHeight = 0;
    private SearchFragment searchFragment;
    private String imagUrl;
    private String searchWord;
    private String uriType;
    private String uri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        onSetToolbar(toolbar);
        maxScrollHeight = JUtils.dip2px(200);
        searchWord = getIntent().getBundleExtra("search").getString("search");
        collapsingToolbarLayout.setTitle(searchWord);
        marginTopStatusHeight();
        marginNavigationBar(fab);
        imagUrl = getIntent().getBundleExtra("search").getString("imagUrl");
        uriType =getIntent().getBundleExtra("search").getString("uriType");
        if(TextUtils.isEmpty(imagUrl)){
            uri =getPresenter().getBgImg()+"";
            imageView.setBackgroundResource(Integer.parseInt(uri));
            uriType =Constant.URI_TYPE_NATIVE;
        }else if(TextUtils.isEmpty(uriType)||uriType.equals(Constant.URI_TYPE_NET)){
            imageView.setImageURI(Uri.parse(imagUrl));
            uriType =Constant.URI_TYPE_NET;
            uri =imagUrl;
        } else  if(uriType.equals(Constant.URI_TYPE_NATIVE)){
            imageView.setBackgroundResource(Integer.parseInt(imagUrl));
            uri=imagUrl;
        }
        manager = getSupportFragmentManager();
        searchFragment = new SearchFragment();
        searchFragment.setArguments(getIntent().getBundleExtra("search"));
        manager.beginTransaction().replace(R.id.search_container, searchFragment).commit();
        initAppBarSetting();
        initSearchView();

    }

    /**
     * fixed: fitsystemWindowLayout在CollapsingToolbarLayout上存在的marginbug
     */
    public void marginTopStatusHeight() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            FrameLayout.LayoutParams frameLayoutParams = (FrameLayout.LayoutParams)toolbar.getLayoutParams();
            RelativeLayout.LayoutParams relativeLayoutParams = (RelativeLayout.LayoutParams)mCoordinatorLayout.getLayoutParams();
            relativeLayoutParams.topMargin=-JUtils.getStatusBarHeight();
            frameLayoutParams.topMargin=JUtils.getStatusBarHeight()*2;
            mCoordinatorLayout.setLayoutParams(relativeLayoutParams);
            toolbar.setLayoutParams(frameLayoutParams);
        }
    }

    public void marginNavigationBar(View view){
        if(!Utils.checkDeviceHasNavigationBar(this)){
            return;
        }
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)view.getLayoutParams();
        layoutParams.setMargins(JUtils.dip2px(16),JUtils.dip2px(16),JUtils.dip2px(16),Utils.getNavigationBarHeight(this)/2);
        view.setLayoutParams(layoutParams);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_activity_menu, menu);
        item = menu.findItem(R.id.action_search);
        if(TextUtils.isEmpty(imagUrl)){
            menu.findItem(R.id.action_collect_header_img).setVisible(false);
            menu.findItem(R.id.action_download_header_img).setVisible(false);
            menu.findItem(R.id.action_collect_tip).setVisible(false);
        }
        item.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       /* int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }*/
        switch (item.getItemId()) {
            case R.id.action_search:
                if (!searchView.isShown()) {
                    searchView.setVisibility(View.VISIBLE);
                    searchView.onActionViewExpanded();
                    searchView.requestFocus();
                    searchView.setQuery(searchWord, false);
                } else {

                    if (searchView.getQuery().toString().equals("")) {
                        searchView.setVisibility(View.GONE);
                        searchView.onActionViewCollapsed();
                        // collapsingToolbarLayout.requestFocus();
                    } else {
                        searchView.setQuery(searchView.getQuery(), true);
                    }
                }

                break;
            case R.id.action_collect_header_img:
                getPresenter().collectHeaderImg(imagUrl);

                break;
            case R.id.action_download_header_img:
                getPresenter().downloadHeaderImg(imagUrl);
                break;
            case R.id.action_collect_tip:
                getPresenter().collectSearchTip(searchWord,uriType,uri);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void initAppBarSetting() {
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                stopRefresh(i);
                if (i > (-maxScrollHeight/2) && fab.isShown()) {
                    fab.hide();
                    if (item != null) {
                        item.setVisible(false);
                    }
                } else if (i <= (-maxScrollHeight/2) && !fab.isShown()) {
                    fab.show();
                    item.setVisible(true);
                }
            }
        });
    }

    private void initSearchView() {
        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.onActionViewCollapsed();
                searchWord = query;
                searchPicture(searchWord);
                searchView.setVisibility(View.GONE);
                collapsingToolbarLayout.setTitle(searchWord);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @OnClick(R.id.search_fab)
    public void clickFab(View view) {
        getPresenter().gotoUpWithAnim(0);
    }@OnClick(R.id.floating_action_button)
    public void collectSearchTip(View view) {
        getPresenter().collectSearchTip(searchWord,uriType,uri);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (data != null) {
                getPresenter().gotoUp(data.getIntExtra("position", 0));
            }

        }
    }

    private void searchPicture(String query) {
        searchFragment = new SearchFragment();
        Bundle bundle = new Bundle();
        bundle.putString("search", query);
        searchFragment.setArguments(bundle);
        manager.beginTransaction().replace(R.id.search_container, searchFragment).commit();
    }

    public SearchFragment getSearchFragment() {
        return searchFragment;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (searchView.isShown()) {
                    searchView.setVisibility(View.GONE);
                    searchView.onActionViewCollapsed();
                    return true;
                } else {
                    return super.onKeyDown(keyCode, event);
                }
            default:
                return super.onKeyDown(keyCode, event);
        }

    }
    public void toastMessage(String text){
        JUtils.Toast(text);
    }

    public void showSnackBar(View view,String message, String action, View.OnClickListener listener){
        Snackbar.make(fab, message, Snackbar.LENGTH_SHORT)
                .setActionTextColor(ContextCompat.getColor(this,R.color.colorPrimaryDark))
                .setAction(action,listener).show();
    }
    public void stopRefresh(int i){
           if(searchFragment!=null&&searchFragment.getListView()!=null&&searchFragment.getListView().getSwipeToRefresh()!=null){
               searchFragment.getListView().getSwipeToRefresh().setEnabled(i==0);
           }
    }
}
