package com.example.administrator.searchpicturetool.search.result;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.administrator.searchpicturetool.model.GetImagelistModel;
import com.example.administrator.searchpicturetool.model.bean.NetImage;
import com.example.administrator.searchpicturetool.base.BaseListFragmentPresenter;
import com.example.administrator.searchpicturetool.detail.ShowLargeImgActivityPresenter;
import com.example.administrator.searchpicturetool.detail.ShowLargeImgActivity;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.functions.Action1;

/**
 * Created by wenhuaijun on 2015/11/3 0003.
 */
public class SerachResultFragmentListPresenter extends BaseListFragmentPresenter<SearchResultFragment,NetImage> implements RecyclerArrayAdapter.OnItemClickListener{
    private String tab;
    private ArrayList<NetImage> netImages;

    @Override
    protected void onCreate(SearchResultFragment view, Bundle savedState) {
        super.onCreate(view, savedState);
        getAdapter().setOnItemClickListener(SerachResultFragmentListPresenter.this);
    }

    @Override
    protected void onCreateView(SearchResultFragment view) {
        super.onCreateView(view);
        view.getListView().getRecyclerView().setHasFixedSize(false);
        view.getListView().setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
      //  view.getListView().getErrorView().findViewById(R.id.view_net_btn).setOnClickListener(getView());
        tab =view.getArguments().getString("search");
        netImages = new ArrayList<NetImage>();
        if(tab.equals("search")){
            getRefreshSubscriber().onNext(netImages);
            return;
        }
        onRefresh();

    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        GetImagelistModel.getImageList(tab,0)
                .map(Arrays::asList)
                .doOnNext(new Action1<List<NetImage>>() {
                    @Override
                    public void call(List<NetImage> netImages) {
                        SerachResultFragmentListPresenter.this.netImages = new ArrayList<NetImage>(netImages);
                    }
                })
                .unsafeSubscribe(getRefreshSubscriber());
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        GetImagelistModel.getImageList(tab, getCurPage())
                .map(Arrays::asList)
                .doOnNext(new Action1<List<NetImage>>() {
                    @Override
                    public void call(List<NetImage> netImages) {
                        SerachResultFragmentListPresenter.this.netImages.addAll(netImages);
                    }
                })
                .unsafeSubscribe(getMoreSubscriber());
    }

    @Override
    protected void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onItemClick(int position) {
        //清空fresco 内存缓存
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        imagePipeline.clearMemoryCaches();
        Intent intent = new Intent();
        intent.putExtra("position", position);
       // intent.putExtra("clickFrom","search");
      //  intent.putExtra("netImages", netImages);
        ShowLargeImgActivityPresenter.netImages = (ArrayList<NetImage>)netImages.clone();
        intent.setClass(getView().getContext(), ShowLargeImgActivity.class);
        getView().getActivity().startActivityForResult(intent,100);
    }
}
