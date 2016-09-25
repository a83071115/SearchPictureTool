package com.example.administrator.searchpicturetool.presenter.activityPresenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.example.administrator.searchpicturetool.R;
import com.example.administrator.searchpicturetool.config.API;
import com.example.administrator.searchpicturetool.model.SaveBitmapModel;
import com.example.administrator.searchpicturetool.model.SqlModel;
import com.example.administrator.searchpicturetool.model.bean.NetImage;
import com.example.administrator.searchpicturetool.model.bean.SearchTipImg;
import com.example.administrator.searchpicturetool.util.Utils;
import com.example.administrator.searchpicturetool.view.activity.SearchActivity;
import com.example.administrator.searchpicturetool.view.activity.UserActivity;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.jude.beam.bijection.Presenter;
import com.jude.utils.JUtils;

import java.util.Random;

import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by wenhuaijun on 2015/11/3 0003.
 */
public class SearchActivityPresenter extends Presenter<SearchActivity> {
    Subscriber<String> tipSubScriber = new Subscriber<String>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            getView().toastMessage("收藏搜索标签失败");
        }

        @Override
        public void onNext(String s) {
            showSnackBar(s,"tip");
        }
    };
    private SearchTipImg mSearchTipImg;
    //保存图片后的观察者
    Subscriber<String> saveSubscriber = new Subscriber<String>() {
        @Override
        public void onNext(String path) {

            if (!path.equals(API.status.error + "")) {
                //保存到数据库
                SqlModel.addDownloadImg(getView(), mSearchTipImg, path);
                showSnackBar("下载封面图片成功","download");
                getView().toastMessage("下载封面图片成功");
            } else {
                JUtils.Toast("未获取到读写sd卡权限！无法保存图片");
            }
        }
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }


    };
    private int[] bgImgs = {
            R.drawable.bg_1, R.drawable.bg_2, R.drawable.bg_3, R.drawable.bg_4, R.drawable.bg_5, R.drawable.bg_6, R.drawable.bg_7,
            R.drawable.bg_8, R.drawable.bg_9, R.drawable.bg_10, R.drawable.bg_11, R.drawable.bg_12, R.drawable.bg_13, R.drawable.bg_14,
            R.drawable.bg_15, R.drawable.bg_16, R.drawable.bg_17, R.drawable.bg_18, R.drawable.bg_19, R.drawable.bg_20,
    };



    public int getBgImg() {
        return bgImgs[new Random().nextInt(bgImgs.length)];
    }

    public void gotoUpWithAnim(int position) {
        if (getView().getSearchFragment().getListView()!=null&&getView().getSearchFragment().getListView().getRecyclerView() != null) {
            getView().getSearchFragment().getListView().getRecyclerView().smoothScrollToPosition(position);
        }
    }

    public void gotoUp(int position) {
        if (getView().getSearchFragment().getListView()!=null&&getView().getSearchFragment().getListView().getRecyclerView() != null) {
            getView().getSearchFragment().getListView().getRecyclerView().scrollToPosition(position);
        }
    }

    public void collectHeaderImg(String imgUrl) {
        SearchTipImg mCollectImg = new SearchTipImg();
        mCollectImg.imgUrl = imgUrl;
        SqlModel.addCollectImg(getView(), mCollectImg);
        showSnackBar("收藏封面图片成功","collect");
    }

    public void collectSearchTip(String searchTip,String uriType,String uri) {
        SqlModel.addSearchTip(getView(),searchTip,uriType,uri).subscribe(tipSubScriber);
    }

    public void downloadHeaderImg(String imgUrl) {
        mSearchTipImg = new SearchTipImg();
        mSearchTipImg.imgUrl = imgUrl;
        SaveBitmapModel.getFrescoDownloadBitmap(getView(), imgUrl)
                .subscribe(new BaseBitmapDataSubscriber() {
                    @Override
                    protected void onNewResultImpl(Bitmap bitmap) {
                        if (bitmap != null) {
                            SaveBitmapModel.getSaveBitmapObservable(bitmap, getView()).subscribe(saveSubscriber);
                        } else {
                            getView().toastMessage("下载封面图片失败");
                        }
                    }

                    @Override
                    protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
                        getView().toastMessage("下载封面图片失败");
                    }
                }, CallerThreadExecutor.getInstance());
    }

    public void showSnackBar(String s,String action){
        if(!Utils.checkDeviceHasNavigationBar(getView())){
            getView().showSnackBar(null, s, "查看", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setAction(action);
                    intent.setClass(getView(),UserActivity.class);
                    getView().startActivity(intent);
                }
            });
        }else {
            JUtils.Toast(s);
        }

    }

}
