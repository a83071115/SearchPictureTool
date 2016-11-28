package com.example.administrator.searchpicturetool.user.tip;

import android.view.ViewGroup;

import com.example.administrator.searchpicturetool.R;
import com.example.administrator.searchpicturetool.config.Constant;
import com.example.administrator.searchpicturetool.model.bean.CollectSearchTip;
import com.example.administrator.searchpicturetool.base.BaseListFragment;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by WenHuaijun on 2016/9/20 0020.
 */
@RequiresPresenter(CollectTipFragmentPresenter.class)
public class CollectTipFragment extends BaseListFragment<CollectTipFragmentPresenter,CollectSearchTip>{
    @Override
    protected ListConfig getConfig() {
        return Constant.getBaseConfig().setContainerErrorAble(false).setContainerEmptyRes(R.layout.view_empty_user);
    }
    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new CollectTipViewHolder(parent);
    }
}
