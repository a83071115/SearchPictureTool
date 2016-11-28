package com.example.administrator.searchpicturetool.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.searchpicturetool.config.Constant;
import com.example.administrator.searchpicturetool.model.bean.NetImage;
import com.example.administrator.searchpicturetool.base.NetImgListPresenter;
import com.example.administrator.searchpicturetool.base.BaseListFragment;
import com.example.administrator.searchpicturetool.base.NetImageListViewHolder;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

@RequiresPresenter(NetImgListPresenter.class)
public class NetImgFragment extends BaseListFragment<NetImgListPresenter, NetImage> implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    @Override
    protected ListConfig getConfig() {
        return Constant.getLoadMoreConfig();
    }

    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {

        return new NetImageListViewHolder(parent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

    }
}
