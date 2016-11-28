package com.example.administrator.searchpicturetool.user.download;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.example.administrator.searchpicturetool.model.db.SqlModel;
import com.example.administrator.searchpicturetool.model.bean.DownloadImg;
import com.example.administrator.searchpicturetool.widght.PinchImageViewPager;
import com.jude.beam.bijection.Presenter;
import com.jude.utils.JUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by wenhuaijun on 2015/11/13 0013.
 */
public class ShowDownloadlargeImgPresenter extends Presenter<ShowDownloadImgActivity> implements PinchImageViewPager.OnPageChangeListener{
    int currentPosition=0;
    ArrayList<DownloadImg> downloadImgs;
    ShowDownloadLargeImgAdapter adapter;

    @Override
    protected void onCreateView(ShowDownloadImgActivity view) {
        super.onCreateView(view);
        currentPosition = getView().getIntent().getIntExtra("position",0);
        downloadImgs = (ArrayList<DownloadImg>) getView().getIntent().getSerializableExtra("largeImgs");
        adapter =new ShowDownloadLargeImgAdapter(downloadImgs,getView());
        getView().getViewPager().setAdapter(adapter);
        getView().getViewPager().setCurrentItem(currentPosition);
        getView().getViewPager().setOnPageChangeListener(this);
        getView().getPg_tv().setText((currentPosition + 1) + "/" + downloadImgs.size());
    }
    public void sharePicture(){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        Uri uri = Uri.fromFile(new File(downloadImgs.get(currentPosition).getName()));
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        getView().startActivity(Intent.createChooser(shareIntent, "请选择"));
    }
    public void setWallWrapper(){
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getView());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        wallpaperManager.setBitmap(BitmapFactory.decodeFile((downloadImgs.get(currentPosition)).getName()));
                        getView().runOnUiThread(new Thread(new Runnable() {
                            @Override
                            public void run() {
                                JUtils.Toast("设置桌面壁纸成功");
                            }
                        }));
                    } catch (IOException e) {
                        e.printStackTrace();
                        JUtils.Toast("设置桌面壁纸失败");
                    }
                }
            }).start();


        }

    public void setLockWrapper(){
        WallpaperManager mWallManager =WallpaperManager.getInstance(getView());
        Class class1 =mWallManager.getClass();
        Method setWallPaperMethod = null;
        try {
            setWallPaperMethod = class1.getMethod("setBitmapToLockWallpaper",Bitmap.class);
            setWallPaperMethod.invoke(mWallManager, BitmapFactory.decodeFile((downloadImgs.get(currentPosition)).getName()));
            JUtils.Toast("设置锁屏壁纸成功");
        } catch (NoSuchMethodException e) {
            JUtils.Toast("设置锁屏壁纸失败");
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            JUtils.Toast("设置锁屏壁纸失败");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            JUtils.Toast("设置锁屏壁纸失败");
            e.printStackTrace();
        }

    }
    public void deletePicture(){
        final File file = new File(downloadImgs.get(currentPosition).getName());
        if(file.exists()){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    file.delete();
                    //发送广播，让相册更新图片
                    getView().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getPath())));
                }
            }).start();
        }
        SqlModel.deleteDownloadImgByname(getView(), downloadImgs.get(currentPosition).getName());
        getView().setResult(100);
        getView().finish();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        getView().getPg_tv().setText((position + 1) + "/" + downloadImgs.size());
        currentPosition=position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
