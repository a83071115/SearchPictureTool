package com.example.administrator.searchpicturetool.joy;

import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.searchpicturetool.R;
import com.example.administrator.searchpicturetool.config.Constant;
import com.example.administrator.searchpicturetool.model.bean.ImageJoy;
import com.example.administrator.searchpicturetool.base.BaseListFragment;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by wenhuaijun on 2016/2/13 0013.
 */
@RequiresPresenter(JoyImgFragmentPresenter.class)
public class JoyImgFragment extends BaseListFragment<JoyImgFragmentPresenter,ImageJoy> implements View.OnClickListener{
    @Override
    protected ListConfig getConfig() {
        return Constant.getUnloadMoreConfig().setNoMoreAble(true).setNoMoreRes(R.layout.itemview_joy_no_more);
    }
    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new JoyImageViewHolder(parent);
    }



    @Override
    public void onClick(View view) {
        super.onClick(view);
    }
}
