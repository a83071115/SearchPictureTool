package com.example.administrator.searchpicturetool.view.fragment;

import android.view.ViewGroup;

import com.example.administrator.searchpicturetool.R;
import com.example.administrator.searchpicturetool.view.viewHolder.MoreViewHolder;
import com.jude.beam.expansion.list.BeamListFragment;
import com.jude.beam.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by Administrator on 2016/5/10 0010.
 */
public class MoreFragment extends BeamListFragment{
    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new MoreViewHolder(parent);
    }
    @Override
    protected ListConfig getConfig() {
        return super.getConfig()
                .setRefreshAble(true)
                .setNoMoreAble(true)
                .setLoadmoreAble(true)
                .setErrorAble(true)
                .setContainerErrorAble(true)
                .setContainerErrorRes(R.layout.view_net_error)
                .setContainerProgressRes(R.layout.page_progress)
                .setLoadMoreRes(R.layout.page_loadmore);
    }
}
