package com.example.administrator.searchpicturetool.presenter.fragmentPresenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.administrator.searchpicturetool.model.SqlModel;
import com.example.administrator.searchpicturetool.model.bean.NetImage;
import com.example.administrator.searchpicturetool.view.activity.ShowLargeImgActivity;
import com.example.administrator.searchpicturetool.view.fragment.CollectFragment;
import com.jude.beam.expansion.list.BeamListFragmentPresenter;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.utils.JUtils;

/**
 * Created by wenhuaijun on 2015/11/12 0012.
 */
public class CollectListPresenter extends BeamListFragmentPresenter<CollectFragment,NetImage> implements RecyclerArrayAdapter.OnItemClickListener{

    @Override
    protected void onCreate(@NonNull CollectFragment view, Bundle savedState) {
        super.onCreate(view, savedState);
        onRefresh();
        getAdapter().setOnItemClickListener(CollectListPresenter.this);
    }

    @Override
    protected void onCreateView(CollectFragment view) {
        super.onCreateView(view);
        view.getListView().getRecyclerView().setHasFixedSize(false);
        view.getListView().setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        SqlModel.getCollectImgs(getView().getContext())
                .doOnError(e-> JUtils.Log(e.getMessage()))
                .unsafeSubscribe(getRefreshSubscriber());
    }


    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent();
        intent.putExtra("position", position);
        intent.putExtra("netImages", getAdapter().getItem(position));
        intent.putExtra("hasCollected",true);
        intent.setClass(getView().getContext(), ShowLargeImgActivity.class);
        getView().startActivityForResult(intent,100);
    }

    @Override
    protected void onResult(int requestCode, int resultCode, Intent data) {
        super.onResult(requestCode, resultCode, data);
        if(requestCode==100&&resultCode==100){
            onRefresh();
        }
    }
}
