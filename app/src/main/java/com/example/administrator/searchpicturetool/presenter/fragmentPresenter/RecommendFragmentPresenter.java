package com.example.administrator.searchpicturetool.presenter.fragmentPresenter;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import com.example.administrator.searchpicturetool.app.APP;
import com.example.administrator.searchpicturetool.model.RecommendModel;
import com.example.administrator.searchpicturetool.presenter.adapter.RecommendAdapter;
import com.example.administrator.searchpicturetool.view.RollViewPagerItemView;
import com.example.administrator.searchpicturetool.view.fragment.RecommendFragment;
import com.jude.beam.bijection.Presenter;
import com.jude.beam.expansion.BeamBasePresenter;
import com.jude.utils.JFileManager;
import com.jude.utils.JUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * Created by wenhuaijun on 2016/2/5 0005.
 */
public class RecommendFragmentPresenter extends BeamBasePresenter<RecommendFragment> implements SwipeRefreshLayout.OnRefreshListener{
    private RecommendAdapter adapter;
    private boolean isInit =true;
    List<Object> objects;
    JFileManager.Folder folder;
    @Override
    protected void onCreateView(RecommendFragment view) {
        super.onCreateView(view);
        if(isInit){
            adapter.addHeader(new RollViewPagerItemView(getView().recyclerView.getSwipeToRefresh()));
            isInit =false;
        }
        getView().recyclerView.setLayoutManager(new LinearLayoutManager(getView().getContext()));
        getView().recyclerView.setAdapterWithProgress(adapter);
        getView().recyclerView.setRefreshListener(this);

    }

    @Override
    protected void onCreate(RecommendFragment view, Bundle savedState) {
        super.onCreate(view, savedState);
        folder = JFileManager.getInstance().getFolder(APP.Dir.Object);
        adapter = new RecommendAdapter(getView().getContext());
        //打开首先从缓存获取数据显示
        objects =(ArrayList<Object>)folder.readObjectFromFile("recommend");
        adapter.addAll(objects);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        //请求一次最新数据
        RecommendModel.getRecommends2(APP.instance)
                .subscribe(new Subscriber<List<Object>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        JUtils.Toast("网络不给力");
                        if(getView().recyclerView!=null){
                            if(adapter.getCount()==0){
                                getView().recyclerView.showError();
                            }
                            getView().recyclerView.getSwipeToRefresh().setRefreshing(false);
                        }
                    }

                    @Override
                    public void onNext(List<Object> objects) {
                        folder.writeObjectToFile(objects,"recommend");
                        adapter.clear();
                        adapter.addAll(objects);
                    }
                });

    }

}
