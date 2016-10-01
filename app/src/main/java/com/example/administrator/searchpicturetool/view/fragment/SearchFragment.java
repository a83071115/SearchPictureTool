package com.example.administrator.searchpicturetool.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.administrator.searchpicturetool.R;
import com.example.administrator.searchpicturetool.config.Constant;
import com.example.administrator.searchpicturetool.model.bean.NetImage;
import com.example.administrator.searchpicturetool.presenter.fragmentPresenter.SerachFragmentListPresenter;
import com.example.administrator.searchpicturetool.view.BaseListFragment;
import com.example.administrator.searchpicturetool.view.viewHolder.NetImageListViewHolder;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.list.BeamListFragment;
import com.jude.beam.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by wenhuaijun on 2015/11/3 0003.
 */
@RequiresPresenter(SerachFragmentListPresenter.class)
public class SearchFragment extends BaseListFragment<SerachFragmentListPresenter,NetImage> implements View.OnClickListener {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected ListConfig getConfig() {
        return Constant.getLoadMoreConfig().setContainerEmptyAble(false);
    }
    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new NetImageListViewHolder(parent);
    }
    @Override
    public void onClick(View view) {
        super.onClick(view);

    }
}
