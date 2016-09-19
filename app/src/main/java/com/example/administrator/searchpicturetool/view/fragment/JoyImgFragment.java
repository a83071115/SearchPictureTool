package com.example.administrator.searchpicturetool.view.fragment;

import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.searchpicturetool.R;
import com.example.administrator.searchpicturetool.config.Constant;
import com.example.administrator.searchpicturetool.model.bean.ImageJoy;
import com.example.administrator.searchpicturetool.presenter.fragmentPresenter.JoyImgFragmentPresenter;
import com.example.administrator.searchpicturetool.view.viewHolder.JoyImageViewHolder;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.list.BeamListFragment;
import com.jude.beam.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by wenhuaijun on 2016/2/13 0013.
 */
@RequiresPresenter(JoyImgFragmentPresenter.class)
public class JoyImgFragment extends BeamListFragment<JoyImgFragmentPresenter,ImageJoy> implements View.OnClickListener{
    @Override
    protected ListConfig getConfig() {
        return Constant.getLoadMoreConfig();
    }
    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new JoyImageViewHolder(parent);
    }

    public void showRefreshing(boolean shouldShow){
        getListView().getSwipeToRefresh().post(() -> getListView().getSwipeToRefresh().setRefreshing(shouldShow));
    }

    @Override
    public void onClick(View view) {
        if(view.getId() ==R.id.view_net_btn){
            showRefreshing(true);
            getPresenter().onRefresh();
        }else if(view.getId() ==R.id.view_empty_btn){
            showRefreshing(true);
            getPresenter().onRefresh();
        }
    }
}
