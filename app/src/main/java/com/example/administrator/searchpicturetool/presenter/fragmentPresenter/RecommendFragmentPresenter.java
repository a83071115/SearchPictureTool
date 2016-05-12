package com.example.administrator.searchpicturetool.presenter.fragmentPresenter;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;

import com.example.administrator.searchpicturetool.app.APP;
import com.example.administrator.searchpicturetool.db.DBManager;
import com.example.administrator.searchpicturetool.model.RecommendModel;
import com.example.administrator.searchpicturetool.model.bean.NewRecommendContent;
import com.example.administrator.searchpicturetool.presenter.adapter.RecommendAdapter;
import com.example.administrator.searchpicturetool.util.RecommendComparator;
import com.example.administrator.searchpicturetool.view.viewHolder.RollViewPagerItemView;
import com.example.administrator.searchpicturetool.view.fragment.RecommendFragment;
import com.jude.beam.expansion.BeamBasePresenter;
import com.jude.utils.JFileManager;
import com.jude.utils.JUtils;

import java.util.Collections;
import java.util.List;

import rx.Subscriber;

/**
 * Created by wenhuaijun on 2016/2/5 0005.
 */
public class RecommendFragmentPresenter extends BeamBasePresenter<RecommendFragment> implements SwipeRefreshLayout.OnRefreshListener{
    private RecommendAdapter adapter;
    private boolean isInit =true;
    private GridLayoutManager girdLayoutManager;
    List<NewRecommendContent> newRecommendContents;




    @Override
    protected void onCreate(RecommendFragment view, Bundle savedState) {
        super.onCreate(view, savedState);
        adapter = new RecommendAdapter(getView().getContext());
        girdLayoutManager =new GridLayoutManager(getView().getContext(),2);
        //打开首先从缓存获取数据显示
        getDataFromCache();
       /*
        newRecommendContents = DBManager.getInstance(getView().getContext()).getRecomendContentfromDB();
        if(newRecommendContents!=null&&newRecommendContents.size()!=0){
          //  Collections.sort(newRecommendContents, new RecommendComparator());
            adapter.addAll(newRecommendContents);
            girdLayoutManager.setSpanSizeLookup(adapter.obtainTipSpanSizeLookUp());
        }
        getView().recyclerView.getSwipeToRefresh().setRefreshing(true);*/
        onRefresh();
    }
    @Override
    protected void onCreateView(RecommendFragment view) {
        super.onCreateView(view);
        if(isInit){
            adapter.addHeader(new RollViewPagerItemView(getView().recyclerView.getSwipeToRefresh()));
            isInit =false;


        }else{
            girdLayoutManager =new GridLayoutManager(getView().getContext(),2);
            girdLayoutManager.setSpanSizeLookup(adapter.obtainTipSpanSizeLookUp());
        }
        // getView().recyclerView.setLayoutManager(new LinearLayoutManager(getView().getContext()));
        //根据不同position设置item的宽度，tip宽度为铺满整个屏幕
        getView().recyclerView.setLayoutManager(girdLayoutManager);
        getView().recyclerView.setAdapterWithProgress(adapter);
        getView().recyclerView.setRefreshListener(this);

    }

    @Override
    public void onRefresh() {
        //请求一次最新数据
        RecommendModel.getRecommendsFromNet(APP.instance)
                .subscribe(new Subscriber<List<NewRecommendContent>>() {
                    @Override
                    public void onCompleted() {
                        if (getView().recyclerView != null) {
                            getView().recyclerView.getSwipeToRefresh().setRefreshing(false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        JUtils.Log("subscriber--onError");
                        JUtils.Toast("网络不给力");
                        if (getView().recyclerView != null) {
                            if (adapter.getCount() == 0) {
                                getView().recyclerView.showError();
                            }
                            //   getView().recyclerView.getSwipeToRefresh().setRefreshing(false);
                        }
                    }

                    @Override
                    public void onNext(List<NewRecommendContent> objects) {
                        JUtils.Log("subscriber--onNext");
                        adapter.clear();
                        adapter.addAll(objects);
                        girdLayoutManager.setSpanSizeLookup(adapter.obtainTipSpanSizeLookUp());
                    }
                });

    }
    public void getDataFromCache(){
        //打开首先从缓存获取数据显示
        newRecommendContents = DBManager.getInstance(getView().getContext()).getRecomendContentfromDB();
        if(newRecommendContents!=null&&newRecommendContents.size()!=0){
            //  Collections.sort(newRecommendContents, new RecommendComparator());
            adapter.addAll(newRecommendContents);
            girdLayoutManager.setSpanSizeLookup(adapter.obtainTipSpanSizeLookUp());
        }
    }

}
