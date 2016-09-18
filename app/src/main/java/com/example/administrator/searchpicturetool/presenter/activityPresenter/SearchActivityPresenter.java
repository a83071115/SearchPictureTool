package com.example.administrator.searchpicturetool.presenter.activityPresenter;

import android.graphics.Bitmap;

import com.example.administrator.searchpicturetool.R;
import com.example.administrator.searchpicturetool.config.API;
import com.example.administrator.searchpicturetool.model.SaveBitmapModel;
import com.example.administrator.searchpicturetool.model.SqlModel;
import com.example.administrator.searchpicturetool.model.bean.NetImage;
import com.example.administrator.searchpicturetool.model.bean.SearchTipImg;
import com.example.administrator.searchpicturetool.util.Utils;
import com.example.administrator.searchpicturetool.view.activity.SearchActivity;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.jude.beam.bijection.Presenter;
import com.jude.utils.JUtils;

import java.util.Random;

import rx.functions.Action1;

/**
 * Created by wenhuaijun on 2015/11/3 0003.
 */
public class SearchActivityPresenter extends Presenter<SearchActivity> {
    private SearchTipImg mSearchTipImg;
    //保存图片后的观察者
    Action1<String> saveSubscriber = new Action1<String>() {
        @Override
        public void call(String path) {

            if (!path.equals(API.status.error + "")) {
                //保存到数据库
                SqlModel.addDownloadImg(getView(), mSearchTipImg, path);
                getView().toastMessage("下载封面图片成功");
            } else {
                JUtils.Toast("未获取到读写sd卡权限！无法保存图片");
            }
        }
    };
    private int[] bgImgs = {
            R.drawable.bg_1,
            R.drawable.bg_2,
            R.drawable.bg_3,
            R.drawable.bg_4,
            R.drawable.bg_5,
            R.drawable.bg_6,
            R.drawable.bg_7,
            R.drawable.bg_8,
            R.drawable.bg_9,
    };

    public int getBgImg() {
        return bgImgs[new Random().nextInt(bgImgs.length)];
    }

    public void gotoUpWithAnim(int position) {
        if (getView().getSearchFragment().getListView().getRecyclerView() != null) {
            getView().getSearchFragment().getListView().getRecyclerView().smoothScrollToPosition(position);
        }
    }

    public void gotoUp(int position) {
        if (getView().getSearchFragment().getListView().getRecyclerView() != null) {
            getView().getSearchFragment().getListView().getRecyclerView().scrollToPosition(position);
        }
    }

    public void collectHeaderImg(String imgUrl) {
        SearchTipImg mCollectImg = new SearchTipImg();
        mCollectImg.imgUrl = imgUrl;
        SqlModel.addCollectImg(getView(), mCollectImg);
    }

    public void collectSearchTip(String searchTip) {

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
}
