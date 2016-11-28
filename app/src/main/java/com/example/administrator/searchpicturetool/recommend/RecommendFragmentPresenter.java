package com.example.administrator.searchpicturetool.recommend;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;

import com.example.administrator.searchpicturetool.app.APP;
import com.example.administrator.searchpicturetool.model.RecommendModel;
import com.example.administrator.searchpicturetool.model.bean.NewRecommendContent;
import com.jude.beam.expansion.BeamBasePresenter;
import com.jude.utils.JUtils;

import java.util.List;

import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by wenhuaijun on 2016/2/5 0005.
 */
public class RecommendFragmentPresenter extends BeamBasePresenter<RecommendFragment> implements SwipeRefreshLayout.OnRefreshListener{
    private RecommendAdapter adapter;
    private boolean isInit =true;
    private GridLayoutManager girdLayoutManager;




    @Override
    protected void onCreate(RecommendFragment view, Bundle savedState) {
        super.onCreate(view, savedState);
        adapter = new RecommendAdapter(getView().getContext());
        girdLayoutManager =new GridLayoutManager(getView().getContext(),2);
        //打开首先从缓存获取数据显示
        getDataFromCache();
        //延迟加载，和缓存加载间隔一段时间，避免卡顿
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onRefresh();
            }
        },2000);

    }
    @Override
    protected void onCreateView(RecommendFragment view) {
        super.onCreateView(view);
        if(isInit){
            adapter.addHeader(new RollViewPagerItemView(getView().recyclerView.getSwipeToRefresh()));
            isInit =false;
            getView().recyclerView.setAdapterWithProgress(adapter);
        }else{
            girdLayoutManager =new GridLayoutManager(getView().getContext(),2);
            girdLayoutManager.setSpanSizeLookup(adapter.obtainTipSpanSizeLookUp());
            getView().recyclerView.setAdapterWithProgress(adapter);
            if (adapter != null && adapter.getCount() == 0) {
                if(JUtils.isNetWorkAvilable()){
                    getView().recyclerView.showEmpty();
                }else {
                    getView().recyclerView.showError();
                }
            }
        }
        // getView().recyclerView.setLayoutManager(new LinearLayoutManager(getView().getContext()));
        //根据不同position设置item的宽度，tip宽度为铺满整个屏幕
        getView().recyclerView.setLayoutManager(girdLayoutManager);
        getView().recyclerView.setRefreshListener(this);
          /*  if (adapter != null && adapter.getCount() == 0 && !JUtils.isNetWorkAvilable()) {
            getView().recyclerView.showError();
        }*/
    }

    @Override
    public void onRefresh() {
        RecommendModel.getRecommendsFromNet(APP.getInstance(), new Subscriber<List<NewRecommendContent>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                JUtils.Toast("网络不给力");
                if (getView().recyclerView != null) {
                    if (adapter.getCount() == 0) {
                        getView().recyclerView.showError();
                    }
                    getView().recyclerView.setRefreshing(false);
                }
            }

            @Override
            public void onNext(List<NewRecommendContent> list) {
                adapter.clear();
                adapter.addAll(list);
                girdLayoutManager.setSpanSizeLookup(adapter.obtainTipSpanSizeLookUp());
            }
        });

    }
    public void getDataFromCache(){
        //打开首先从缓存获取数据显示
        RecommendModel.getRecommendsFromDB(getView().getContext()).subscribe(new Action1<List<NewRecommendContent>>() {
            @Override
            public void call(List<NewRecommendContent> list) {
                if(list!=null&&list.size()!=0){
                    adapter.addAll(list);
                    girdLayoutManager.setSpanSizeLookup(adapter.obtainTipSpanSizeLookUp());
                    if(getView().recyclerView!=null){
                        getView().recyclerView.setRefreshing(true);
                    }
                }
            }
        });

    }

}
