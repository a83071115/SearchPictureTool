package com.example.administrator.searchpicturetool.presenter.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import com.example.administrator.searchpicturetool.view.fragment.CollectFragment;
import com.example.administrator.searchpicturetool.view.fragment.CollectTipFragment;
import com.example.administrator.searchpicturetool.view.fragment.DownloadFragment;
import com.example.administrator.searchpicturetool.view.fragment.NetImgFragment;
import com.jude.utils.JUtils;

import java.util.HashMap;

/**
 * Created by wenhuaijun on 2015/11/12 0012.
 */
public class UserPagerAdapter extends FragmentPagerAdapter {
    private String[] pageTitles = {"下载图片","收藏图片","收藏标签"};
    private DownloadFragment downloadFragment;
    private CollectFragment collectFragment;
    private CollectTipFragment collectTipFragment;
    public UserPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                collectFragment = new CollectFragment();
                return collectFragment;
            case 0:
                downloadFragment = new DownloadFragment();
                return downloadFragment;
            case 2:
                collectTipFragment = new CollectTipFragment();
                return collectTipFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return pageTitles.length;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pageTitles[position];
    }

    public DownloadFragment getDownloadFragment() {
        return downloadFragment;
    }

    public CollectFragment getCollectFragment() {
        return collectFragment;
    }

    public CollectTipFragment getCollectTipFragment() {
        return collectTipFragment;
    }
}
