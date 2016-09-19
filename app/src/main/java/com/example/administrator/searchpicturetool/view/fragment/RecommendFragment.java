package com.example.administrator.searchpicturetool.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.searchpicturetool.R;
import com.example.administrator.searchpicturetool.presenter.fragmentPresenter.RecommendFragmentPresenter;
import com.jude.beam.bijection.BeamFragment;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.easyrecyclerview.EasyRecyclerView;


/**
 * Created by wenhuaijun on 2016/2/5 0005.
 */

@RequiresPresenter(RecommendFragmentPresenter.class)
public class RecommendFragment extends BeamFragment<RecommendFragmentPresenter> implements View.OnClickListener {
    public EasyRecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommend, container, false);
        recyclerView = (EasyRecyclerView) view.findViewById(R.id.easy_recyclerview);
        initStatusView(inflater,container,view);
        return view;
    }

    private void initStatusView(LayoutInflater inflater,ViewGroup container,View view) {
       /* View errorView = inflater.inflate((R.layout.view_net_error), container, false);
        View emptyView = inflater.inflate((R.layout.view_empty), container, false);*/
        recyclerView.getErrorView().findViewById(R.id.view_net_btn).setOnClickListener(this);
        recyclerView.getEmptyView().findViewById(R.id.view_empty_btn).setOnClickListener(this);
       /* recyclerView.setErrorView(errorView);
        recyclerView.setEmptyView(emptyView);*/
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.view_net_btn) {
            recyclerView.setRefreshing(true);
            getPresenter().onRefresh();
        } else if (view.getId() == R.id.view_empty_btn) {
            recyclerView.setRefreshing(true);
            getPresenter().onRefresh();
        }
    }
}
