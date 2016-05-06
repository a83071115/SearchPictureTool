package com.example.administrator.searchpicturetool.presenter.fragmentPresenter;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import com.example.administrator.searchpicturetool.model.RecommendModel;
import com.example.administrator.searchpicturetool.presenter.adapter.RecommendAdapter;
import com.example.administrator.searchpicturetool.view.RollViewPagerItemView;
import com.example.administrator.searchpicturetool.view.fragment.MainFragment;
import com.jude.beam.bijection.Presenter;
import com.jude.utils.JUtils;

import java.util.List;

import rx.Subscriber;

/**
 * Created by wenhuaijun on 2016/2/5 0005.
 */
public class MainFragmentPresenter extends Presenter<MainFragment> implements SwipeRefreshLayout.OnRefreshListener{
    private RecommendAdapter adapter;
    @Override
    protected void onCreateView(MainFragment view) {
        super.onCreateView(view);
        adapter = new RecommendAdapter(getView().getContext());
        getView().recyclerView.setLayoutManager(new LinearLayoutManager(getView().getContext()));
        getView().recyclerView.setAdapterWithProgress(adapter);
        adapter.addHeader(new RollViewPagerItemView(getView().recyclerView.getSwipeToRefresh()));
        getView().recyclerView.setRefreshListener(this);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        RecommendModel.getRecommends2(getView().getContext())
                .subscribe(new Subscriber<List<Object>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        JUtils.Log(e.getMessage());
                        JUtils.Toast("网络不给力");
                        getView().recyclerView.showError();
                        getView().recyclerView.getSwipeToRefresh().setRefreshing(false);
                    }

                    @Override
                    public void onNext(List<Object> objects) {
                        adapter.clear();
                        adapter.addAll(objects);
                    }
                });

    }

}
