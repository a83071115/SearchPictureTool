package com.example.administrator.searchpicturetool.presenter.activityPresenter;

import com.example.administrator.searchpicturetool.config.API;
import com.example.administrator.searchpicturetool.model.SqlModel;
import com.example.administrator.searchpicturetool.model.bean.CollectSearchTip;
import com.example.administrator.searchpicturetool.model.bean.DownloadImg;
import com.example.administrator.searchpicturetool.model.bean.NetImage;
import com.example.administrator.searchpicturetool.presenter.adapter.UserPagerAdapter;
import com.example.administrator.searchpicturetool.view.activity.UserActivity;
import com.jude.beam.bijection.Presenter;


import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

/**
 * Created by wenhuaijun on 2015/11/12 0012.
 */
public class UserActivityPresenter extends Presenter<UserActivity> {
    public UserPagerAdapter userPagerAdapter;
    public boolean isTransactioning;
    public int page = 0;

    @Override
    protected void onCreateView(UserActivity view) {
        super.onCreateView(view);
        userPagerAdapter = new UserPagerAdapter(getView().getSupportFragmentManager());
        getView().getViewPager().setAdapter(userPagerAdapter);
        getView().getTabLayout().setupWithViewPager(getView().getViewPager());
        if (getView().getIntent().getAction() != null) {
            if (getView().getIntent().getAction().equals("download")) {
                getView().getViewPager().setCurrentItem(0);
            } else if (getView().getIntent().getAction().equals("collect")) {
                getView().getViewPager().setCurrentItem(1);
            } else if (getView().getIntent().getAction().equals("tip")) {
                getView().getViewPager().setCurrentItem(2);
            }

        }
    }

    @Override
    protected void onDestroyView() {
        super.onDestroyView();
    }

    public void beginSelectImgs(boolean begin) {
        isTransactioning = begin;
        if (begin) {
            if (page == 0){
                userPagerAdapter.getDownloadFragment().getPresenter().beginSelectiong(begin);
                userPagerAdapter.getCollectFragment().getPresenter().beginSelectiong(!begin);
                if(userPagerAdapter.getCollectTipFragment()!=null){
                    userPagerAdapter.getCollectTipFragment().getPresenter().beginSelectiong(!begin);
                }
            }

            if (page == 1){
                userPagerAdapter.getDownloadFragment().getPresenter().beginSelectiong(!begin);
                userPagerAdapter.getCollectFragment().getPresenter().beginSelectiong(begin);
                if(userPagerAdapter.getCollectTipFragment()!=null){
                    userPagerAdapter.getCollectTipFragment().getPresenter().beginSelectiong(!begin);
                }
            }

            if (page == 2) {
                userPagerAdapter.getDownloadFragment().getPresenter().beginSelectiong(!begin);
                userPagerAdapter.getCollectFragment().getPresenter().beginSelectiong(!begin);
                if(userPagerAdapter.getCollectTipFragment()!=null){
                    userPagerAdapter.getCollectTipFragment().getPresenter().beginSelectiong(begin);
                }
            }
        } else {
            userPagerAdapter.getDownloadFragment().getPresenter().beginSelectiong(begin);
            userPagerAdapter.getCollectFragment().getPresenter().beginSelectiong(begin);
            if(userPagerAdapter.getCollectTipFragment()!=null){
                userPagerAdapter.getCollectTipFragment().getPresenter().beginSelectiong(begin);
            }
        }


    }

    public void doDelete() {
        if (page == 0) deleteDownloadImgs();
        if (page == 1) deleteCollectImgs();
        if (page == 2) deleteSearchTips();
    }

    public void deleteDownloadImgs() {
        userPagerAdapter.getDownloadFragment().getPresenter().setSelection(false);
        List<DownloadImg> downloadImgList = userPagerAdapter.getDownloadFragment().getPresenter().getDownloadImgs();
        if (downloadImgList == null || downloadImgList.size() == 0) {
            return;
        }
        SqlModel.deleteDownloadImgs(getView(), userPagerAdapter.getDownloadFragment().getPresenter().getDownloadImgs()).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                if (s.equals(API.status.success + "")) {
                    userPagerAdapter.getDownloadFragment().getPresenter().onRefresh();
                }
            }
        });
    }

    public void deleteCollectImgs() {
        userPagerAdapter.getCollectFragment().getPresenter().setSelection(false);
        ArrayList<NetImage> netImages = userPagerAdapter.getCollectFragment().getPresenter().getNetImages();
        if (netImages == null || netImages.size() == 0) {
            return;
        }
        SqlModel.deleteCollectImgs(getView(), userPagerAdapter.getCollectFragment().getPresenter().getNetImages()).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                if (s.equals(API.status.success + "")) {
                    userPagerAdapter.getCollectFragment().getPresenter().onRefresh();

                }
            }
        });
    }

    public void deleteSearchTips() {
        userPagerAdapter.getCollectTipFragment().getPresenter().setSelection(false);
        ArrayList<CollectSearchTip> collectSearchTips = userPagerAdapter.getCollectTipFragment().getPresenter().getCollectSearchTips();
        if (collectSearchTips == null || collectSearchTips.size() == 0) {
            return;
        }
        SqlModel.deleteSeachTips(getView(), collectSearchTips)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        userPagerAdapter.getCollectTipFragment().getPresenter().onRefresh();
                    }
                });
    }

    public void transactionEnd() {
        isTransactioning = false;
        beginSelectImgs(false);
    }

    public void stopRefresh(int i) {
        switch (getView().getViewPager().getCurrentItem()) {
            case 0:
                userPagerAdapter.getDownloadFragment().getListView().getSwipeToRefresh().setEnabled(i == 0);
                break;
            case 1:
                userPagerAdapter.getCollectFragment().getListView().getSwipeToRefresh().setEnabled(i == 0);
                break;
        }
    }
}
