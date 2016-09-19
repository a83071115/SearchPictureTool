package com.example.administrator.searchpicturetool.presenter.fragmentPresenter;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.example.administrator.searchpicturetool.R;
import com.example.administrator.searchpicturetool.model.ImageJoyModel;
import com.example.administrator.searchpicturetool.model.bean.ImageJoy;
import com.example.administrator.searchpicturetool.view.fragment.JoyImgFragment;
import com.example.administrator.searchpicturetool.view.fragment.NetImgFragment;
import com.jude.beam.expansion.list.BeamListFragmentPresenter;
import com.jude.utils.JUtils;

import java.util.ArrayList;

import rx.Subscriber;

/**
 * Created by wenhuaijun on 2016/2/13 0013.
 */
public class JoyImgFragmentPresenter extends BeamListFragmentPresenter<JoyImgFragment,ImageJoy>{
    @Override
    protected void onCreate(@NonNull JoyImgFragment view, Bundle savedState) {
        super.onCreate(view, savedState);
        onRefresh();
    }

    @Override
    protected void onCreateView(JoyImgFragment view) {
        super.onCreateView(view);
        initStatusView(view);

    }
    public void initStatusView(JoyImgFragment view) {
        view.getListView().getErrorView().findViewById(R.id.view_net_btn).setOnClickListener(getView());
        view.getListView().getEmptyView().findViewById(R.id.view_empty_btn).setOnClickListener(getView());
        if (getAdapter() != null && getAdapter().getCount() == 0 && !JUtils.isNetWorkAvilable()) {
            view.getListView().showError();
        }
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        ImageJoyModel.getImageJoys(). unsafeSubscribe(getRefreshSubscriber());/*subscribe(new Subscriber<ArrayList<ImageJoy>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                getView().stopRefresh();
                getView().showError(e);
            }

            @Override
            public void onNext(ArrayList<ImageJoy> imageJoys) {
                    getAdapter().clear();
                    getAdapter().addAll(imageJoys);
            }
        });*/
    }
}
