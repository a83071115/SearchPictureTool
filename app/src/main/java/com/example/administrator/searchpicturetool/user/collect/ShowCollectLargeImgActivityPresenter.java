package com.example.administrator.searchpicturetool.user.collect;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.administrator.searchpicturetool.config.API;
import com.example.administrator.searchpicturetool.user.UserActivity;
import com.example.administrator.searchpicturetool.widght.imageLoader.EasyImageLoader;
import com.example.administrator.searchpicturetool.model.SaveBitmapModel;
import com.example.administrator.searchpicturetool.model.db.SqlModel;
import com.example.administrator.searchpicturetool.model.WrapperModel;
import com.example.administrator.searchpicturetool.model.bean.NetImage;
import com.example.administrator.searchpicturetool.detail.ShowLargeImgAdapter;
import com.example.administrator.searchpicturetool.util.Utils;
import com.example.administrator.searchpicturetool.widght.PinchImageViewPager;
import com.jude.beam.bijection.Presenter;
import com.jude.utils.JUtils;

import java.util.ArrayList;

import rx.Subscriber;

/**
 * Created by wenhuaijun on 2015/11/4 0004.
 */
public class ShowCollectLargeImgActivityPresenter extends Presenter<ShowCollectLargeImgActivity> implements PinchImageViewPager.OnPageChangeListener {
    public static ArrayList<NetImage> netImages;
    int currentPosition = 0;
    ShowLargeImgAdapter adapter;
    //设置事件发生后的消费该事件的观察者
    Subscriber<Integer> callbackSubscriber = new Subscriber<Integer>() {
       /* @Override
        public void call(Integer integer) {
            if (integer.intValue() == API.status.success) {
                JUtils.Toast("设置成功！");
                getView().dismissDialog();
            } else {
                JUtils.Toast("设置失败...");
                getView().dismissDialog();
            }
        }*/

        @Override
        public void onNext(Integer integer) {
            if (integer.intValue() == API.status.success) {
                JUtils.Toast("设置成功！");
                getView().dismissDialog();
            } else {
                JUtils.Toast("设置失败...");
                getView().dismissDialog();
            }
        }

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }
    };
    private NetImage netImage;
    /**
     * -1 初始化
     * 0 下载图片
     * 1 分享图片
     * 3 设为壁纸
     * 4 设为锁屏
     */
    private int state = -1;
    //保存图片后的观察者
    Subscriber<String> saveSubscriber = new Subscriber<String>() {
        @Override
        public void onNext(String path) {

            if (!path.equals(API.status.error + "")) {
                if (state == 0) {
                 //   JUtils.ToastLong("图片已保存至：" + path);
                    showSnackBar("图片已保存至：" + path,"download");
                    //保存到数据库
                    if(netImages==null||netImages.get(currentPosition)==null){
                        return;
                    }
                    SqlModel.addDownloadImg(getView(), netImages.get(currentPosition), path);
                }
                if (state == 1) {
                    // startShareImg(path);
                    Utils.startShareImg(path, getView());
                }
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

    @Override
    protected void onCreateView(ShowCollectLargeImgActivity view) {
        super.onCreateView(view);
        //netImages = (ArrayList<NetImage>) view.getIntent().getSerializableExtra("netImages");
        currentPosition = view.getIntent().getIntExtra("position", 0);
        adapter = new ShowLargeImgAdapter(netImages, getView());
        getView().getViewPager().setAdapter(adapter);
        adapter.setPinchImageViewPager(getView().getViewPager());
        getView().getViewPager().setCurrentItem(currentPosition);
        getView().getViewPager().setOnPageChangeListener(this);
        if(netImages!=null&&netImages.size()!=0){
            getView().getPg_tv().setText((currentPosition + 1) + "/" + netImages.size());
        }
    }

    @Override
    protected void onDestroyView() {
        super.onDestroyView();
        if(netImages!=null){
            netImages.clear();
            netImages =null;
        }
        EasyImageLoader.getInstance(getView()).clearMemoryCache();
    }

    public void savePicture() {
        state = 0;
        if(netImages==null||netImages.size()==0){
            return;
        }
        downloadBitmapToSdCard(getView(), netImages.get(currentPosition).getLargeImg(), netImages.get(currentPosition).getThumbImg());
    }

    public void collectPicture() {
        if(netImages==null||netImages.size()==0){
            return;
        }
        SqlModel.addCollectImg(getView(), netImages.get(currentPosition));
        showSnackBar("已收藏","collect");

    }

    public void requestCollectPicture() {
        if(netImages==null||netImages.size()==0){
            return;
        }
        SqlModel.deleteCollectImgByUrl(getView(), netImages.get(currentPosition).getLargeImg());
        getView().setResult(100);
        getView().finish();
        JUtils.Toast("已取消收藏");

    }

    public void sharePicture() {
        if(netImages==null||netImages.size()==0){
            return;
        }
        state = 1;
        downloadBitmapEvent(getView(), netImages.get(currentPosition).getLargeImg());
    }

    public void setWallWrapper() {
        if(netImages==null||netImages.size()==0){
            return;
        }
        state = 3;
        downloadBitmapEvent(getView(), netImages.get(currentPosition).getLargeImg());
    }

    public void setLockWrapper() {
        if(netImages==null||netImages.size()==0){
            return;
        }
        state = 4;
        downloadBitmapEvent(getView(), netImages.get(currentPosition).getLargeImg());
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if(netImages!=null&&netImages.size()!=0){
            getView().getPg_tv().setText((position + 1) + "/" + netImages.size());
        }

        currentPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public int getPosition() {
        return currentPosition;
    }

    public void downloadBitmapToSdCard(final Context context, String url, String smallUrl) {
        EasyImageLoader.getInstance(context).getBitmap(url, bitmap -> {
            if (bitmap != null) {
                SaveBitmapModel.getSaveBitmapObservable(bitmap, getView()).subscribe(saveSubscriber);
            } else {
                if (!url.equals(smallUrl)) {
                    downloadBitmapToSdCard(context, smallUrl, smallUrl);
                }
            }
        });
    }

    public void downloadBitmapEvent(final Context context, String url) {
        EasyImageLoader.getInstance(context).getBitmap(url, bitmap -> {
            if (bitmap != null) {
                if (state == 1) {
                    //share
                    SaveBitmapModel.getSaveBitmapObservable(bitmap, getView()).subscribe(saveSubscriber);
                } else if (state == 3) {
                    //设置桌面壁纸
                    WrapperModel.getSetWallWrapperObservable(bitmap, context).subscribe(callbackSubscriber);

                } else if (state == 4) {
                    //设置锁屏壁纸
                    WrapperModel.getSetLockWrapperObservable(bitmap, context).subscribe(callbackSubscriber);
                }
            } else {
                JUtils.Toast("下载图片失败");
                getView().dismissDialog();
            }
        });

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

  /*  private void startShareImg(String path){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image*//*");
        File file = new File(path);
        Uri uri = Uri.fromFile(file);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        getView().startActivity(Intent.createChooser(shareIntent, "请选择"));
    }*/
 /*//判断是否是Android 6.0以上，需动态获取权限
                    if(Build.VERSION.SDK_INT >=23){
                        //查看是否获取了写权限
                        int hasWritePermission = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        if(hasWritePermission != PackageManager.PERMISSION_GRANTED){
                            //未获取到权限，申请权限
                            ActivityCompat.requestPermissions(getView(),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},200);
                        }else{
                            //已获取到权限
                            SaveBitmapModel.getSaveBitmapObservable(bitmap).subscribe(saveSubscriber);
                        }
                    }else{*/
}
