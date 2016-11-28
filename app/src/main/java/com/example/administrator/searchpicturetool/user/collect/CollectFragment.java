package com.example.administrator.searchpicturetool.user.collect;

import android.view.ViewGroup;

import com.example.administrator.searchpicturetool.R;
import com.example.administrator.searchpicturetool.config.Constant;
import com.example.administrator.searchpicturetool.model.bean.NetImage;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.list.BeamListFragment;
import com.jude.beam.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by wenhuaijun on 2015/11/12 0012.
 */
@RequiresPresenter(CollectListPresenter.class)
public class CollectFragment extends BeamListFragment<CollectListPresenter,NetImage> {
    @Override
    protected ListConfig getConfig() {
        return Constant.getBaseConfig().setContainerEmptyRes(R.layout.view_empty_user);
    }

    @Override
    public void showError(Throwable e) {
        super.showError(e);
    }

    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new CollectImageListViewHolder(parent);
    }
}
