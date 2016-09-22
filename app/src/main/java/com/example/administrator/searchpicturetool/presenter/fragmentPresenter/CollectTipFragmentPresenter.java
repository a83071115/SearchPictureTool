package com.example.administrator.searchpicturetool.presenter.fragmentPresenter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;

import com.example.administrator.searchpicturetool.model.CollectSearchTipModel;
import com.example.administrator.searchpicturetool.model.bean.CollectSearchTip;
import com.example.administrator.searchpicturetool.model.bean.NetImage;
import com.example.administrator.searchpicturetool.presenter.BaseListFragmentPresenter;
import com.example.administrator.searchpicturetool.view.fragment.CollectTipFragment;

import java.util.ArrayList;

import rx.functions.Action1;

/**
 * Created by WenHuaijun on 2016/9/20 0020.
 */
public class CollectTipFragmentPresenter extends BaseListFragmentPresenter<CollectTipFragment,CollectSearchTip> {

    private ArrayList<CollectSearchTip> collectSearchTips;
    private boolean isSelection;

    @Override
    protected void onCreate(@NonNull CollectTipFragment view, Bundle savedState) {
        super.onCreate(view, savedState);
        onRefresh();
    }

    @Override
    protected void onCreateView(@NonNull CollectTipFragment view) {
        super.onCreateView(view);
      GridLayoutManager gridLayoutManager =new GridLayoutManager(getView().getContext(),2);
        view.getListView().getRecyclerView().setHasFixedSize(true);
        getView().getListView().setLayoutManager(gridLayoutManager);


    }

    public void beginSelectiong(boolean begin){
        if(isSelection==begin){
            return;
        }
        isSelection =begin;
        for(CollectSearchTip collectSearchTip: collectSearchTips){
            collectSearchTip.setTranscation(begin);
        }
        getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        CollectSearchTipModel.getSearchTipsFromDB(getView()
                .getContext())
                .doOnNext(new Action1<ArrayList<CollectSearchTip>>() {
                    @Override
                    public void call(ArrayList<CollectSearchTip> collectSearchTips) {
                        CollectTipFragmentPresenter.this.collectSearchTips =collectSearchTips;
                    }
                })
                .subscribe(getRefreshSubscriber());
    }

    public ArrayList<CollectSearchTip> getCollectSearchTips() {
        return collectSearchTips;
    }

    public void setSelection(boolean selection) {
        isSelection = selection;
    }
}
