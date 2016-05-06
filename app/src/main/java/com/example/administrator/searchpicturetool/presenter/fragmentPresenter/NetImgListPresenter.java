package com.example.administrator.searchpicturetool.presenter.fragmentPresenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.administrator.searchpicturetool.R;
import com.example.administrator.searchpicturetool.model.GetImagelistModel;
import com.example.administrator.searchpicturetool.model.bean.NetImage;
import com.example.administrator.searchpicturetool.view.activity.ShowLargeImgActivity;
import com.example.administrator.searchpicturetool.view.fragment.NetImgFragment;
import com.jude.beam.expansion.list.BeamListFragmentPresenter;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.Arrays;

/**
 * Created by wenhuaijun on 2015/11/2 0002.
 */

public class NetImgListPresenter extends BeamListFragmentPresenter<NetImgFragment,NetImage> implements RecyclerArrayAdapter.OnItemClickListener{
    private String tab;

    @Override
    protected void onCreate(@NonNull NetImgFragment view, Bundle savedState) {
        super.onCreate(view, savedState);
        tab =view.getResources().getStringArray(R.array.search_tab)[view.getArguments().getInt("tab")];
        onRefresh();
        getAdapter().setOnItemClickListener(NetImgListPresenter.this);
    }

    @Override
    protected void onCreateView(NetImgFragment view) {
        super.onCreateView(view);
        view.getListView().setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        GetImagelistModel.getImageList(tab,0)
                .map(Arrays::asList)
                .unsafeSubscribe(getRefreshSubscriber());
    }
    @Override
    public void onLoadMore() {
        super.onLoadMore();
        GetImagelistModel.getImageList(tab, getCurPage())
                .map(Arrays::asList)
                .unsafeSubscribe(getRefreshSubscriber());
    }
    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent();
        intent.putExtra("position", position);
        intent.putExtra("netImages", getAdapter().getItem(position));
        intent.setClass(getView().getContext(), ShowLargeImgActivity.class);
        getView().getActivity().startActivityForResult(intent, 100);
    }

}
