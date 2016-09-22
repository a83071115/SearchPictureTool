package com.example.administrator.searchpicturetool.presenter;

import android.support.annotation.NonNull;
import android.view.View;

import com.example.administrator.searchpicturetool.R;
import com.example.administrator.searchpicturetool.view.BaseListFragment;
import com.jude.beam.expansion.list.BeamListFragmentPresenter;
import com.jude.utils.JUtils;

/**
 * Created by WenHuaijun on 2016/9/19 0019.
 */
public class BaseListFragmentPresenter<T extends BaseListFragment,M> extends BeamListFragmentPresenter<T,M>{
    @Override
    protected void onCreateView(@NonNull T view) {
        super.onCreateView(view);
        initStatusView(view);
    }
    public void initStatusView(T view) {
        if(view.getListView().getErrorView()!=null){
            View errorView =view.getListView().getErrorView().findViewById(R.id.view_net_btn);
            if(errorView!=null){
                errorView.setOnClickListener(getView());
            }
        }
        if(view.getListView().getEmptyView()!=null){
            View emptyView =view.getListView().getEmptyView().findViewById(R.id.view_empty_btn);
            if(emptyView!=null){
                emptyView.setOnClickListener(getView());
            }
        }
       // view.getListView().getErrorView().findViewById(R.id.view_net_btn).setOnClickListener(getView());
        //view.getListView().getEmptyView().findViewById(R.id.view_empty_btn).setOnClickListener(getView());
        if (view.getListView().getErrorView()!=null&&getAdapter() != null && getAdapter().getCount() == 0 && !JUtils.isNetWorkAvilable()) {
            view.getListView().showError();
        }
    }
}
