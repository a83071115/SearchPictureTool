package com.example.administrator.searchpicturetool.joy;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.example.administrator.searchpicturetool.model.ImageJoyModel;
import com.example.administrator.searchpicturetool.model.bean.ImageJoy;
import com.example.administrator.searchpicturetool.base.BaseListFragmentPresenter;

/**
 * Created by wenhuaijun on 2016/2/13 0013.
 */
public class JoyImgFragmentPresenter extends BaseListFragmentPresenter<JoyImgFragment,ImageJoy> {
    @Override
    protected void onCreate(@NonNull JoyImgFragment view, Bundle savedState) {
        super.onCreate(view, savedState);
        onRefresh();
    }

    @Override
    protected void onCreateView(JoyImgFragment view) {
        super.onCreateView(view);

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
