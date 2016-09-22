package com.example.administrator.searchpicturetool.presenter.fragmentPresenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.TextView;

import com.example.administrator.searchpicturetool.R;
import com.example.administrator.searchpicturetool.model.DownloadImgModel;
import com.example.administrator.searchpicturetool.model.SqlModel;
import com.example.administrator.searchpicturetool.model.bean.DownloadImg;
import com.example.administrator.searchpicturetool.view.activity.ShowDownloadImgActivity;
import com.example.administrator.searchpicturetool.view.fragment.DownloadFragment;
import com.jude.beam.expansion.list.BeamListFragmentPresenter;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.utils.JUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

/**
 * Created by wenhuaijun on 2015/11/12 0012.
 */

public class DownloadListPresenter extends BeamListFragmentPresenter<DownloadFragment,DownloadImg> implements RecyclerArrayAdapter.OnItemClickListener{
    private List<DownloadImg> downloadImgs;
    private boolean isSelection;

    @Override
    protected void onCreate(@NonNull DownloadFragment view, Bundle savedState) {
        super.onCreate(view, savedState);
        onRefresh();
    }

    @Override
    protected void onCreateView(DownloadFragment view) {
        super.onCreateView(view);
       // view.getListView().getRecyclerView().setHasFixedSize(false);
        view.getListView().setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    }
    public void beginSelectiong(boolean begin){
        if(isSelection==begin){
            return;
        }
        isSelection =begin;
        for(DownloadImg downloadImg:downloadImgs){
            downloadImg.setBeginTransaction(begin);
            getAdapter().notifyDataSetChanged();
        }
    }
    @Override
    public void onRefresh() {
        super.onRefresh();
        SqlModel.getDownloadImgs(getView().getContext()).subscribe(new Action1<ArrayList<DownloadImg>>() {
            @Override
            public void call(ArrayList<DownloadImg> imgs) {
                downloadImgs = imgs;
                if (downloadImgs.size() == 0||downloadImgs==null){
                    getView().getListView().showEmpty();
                }
                getRefreshSubscriber().onNext(downloadImgs);
                getAdapter().setOnItemClickListener(DownloadListPresenter.this);
            }
        });
    }

    @Override
    public void onItemClick(int position) {
      //  ((UserActivity)(getView().getActivity())).getExpansion().showProgressDialog("请选择");
        Intent intent = new Intent();
        intent.putExtra("largeImgs", new ArrayList<DownloadImg>(downloadImgs));
        intent.putExtra("position", position);
        intent.setClass(getView().getContext(), ShowDownloadImgActivity.class);
   //     getView().startActivity(intent);
        getView().startActivityForResult(intent,100);
    }

    @Override
    protected void onResult(int requestCode, int resultCode, Intent data) {
        super.onResult(requestCode, resultCode, data);
        if(requestCode==100&&resultCode==100){
            onRefresh();
        }
    }

    public void setSelection(boolean selection) {
        isSelection = selection;
    }

    public List<DownloadImg> getDownloadImgs() {
        return downloadImgs;
    }
}
