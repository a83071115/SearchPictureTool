package com.example.administrator.searchpicturetool.presenter.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.searchpicturetool.R;
import com.example.administrator.searchpicturetool.library.imageLoader.EasyImageLoader;
import com.example.administrator.searchpicturetool.model.SaveBitmapModel;
import com.example.administrator.searchpicturetool.model.bean.NetImage;
import com.example.administrator.searchpicturetool.widght.PinchImageView;
import com.example.administrator.searchpicturetool.widght.PinchImageViewPager;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.jude.utils.JUtils;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;

/**
 * Created by wenhuaijun on 2015/11/8 0008.
 */
public class ShowLargeImgAdapter extends PagerAdapter implements View.OnClickListener {
    ViewGroup.LayoutParams mLayoutParams;
    private ArrayList<NetImage> netImages;
    private Activity context;
    private LayoutInflater inflater;
    private int screenHeight;
    private int screenWidth;
    private View view;
    private int currentPosition = -1;
    private PinchImageViewPager mPinchImageViewPager;

    public ShowLargeImgAdapter(ArrayList<NetImage> netImages, Activity context) {
        this.netImages = netImages;
        this.context = context;
        screenHeight = JUtils.getScreenHeight();
        screenWidth = JUtils.getScreenWidth();
        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return netImages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //  super.destroyItem(container, position, object);
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        view = inflater.inflate(R.layout.item_large_img, null);

        PinchImageView pinchImageView = (PinchImageView) view.findViewById(R.id.photoView);
        ProgressWheel progressWheel = (ProgressWheel)view.findViewById(R.id.progress_wheel);
        pinchImageView.setOnClickListener(this);
        //加载低分辨率图片

        DataSource<CloseableReference<CloseableImage>> dataSource =SaveBitmapModel.
                getFrescoCacheBitmap(context, netImages.get(position).getThumbImg());
       if(dataSource!=null){
           //fresco含有缓存时直接现在缩略图
           progressWheel.setVisibility(View.GONE);
           dataSource.subscribe(new BaseBitmapDataSubscriber() {
                       @Override
                       protected void onNewResultImpl(Bitmap bitmap) {

                           if (pinchImageView != null) {
                               if(bitmap!=null){
                                   pinchImageView.post(new Runnable() {
                                       @Override
                                       public void run() {
                                           pinchImageView.setImageBitmap(bitmap);
                                       }
                                   });
                               }
                           }
                       }

                       @Override
                       protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
                           JUtils.Log("请求缩略图bitmap失败");
                       }
                   }, CallerThreadExecutor.getInstance());
       }else {
           progressWheel.setVisibility(View.VISIBLE);
       }
        container.addView(view);

        return view;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        if (currentPosition != position) {
            PinchImageView pinchImageView = (PinchImageView) (((View) object).findViewById(R.id.photoView));
            ProgressWheel progressWheel = (ProgressWheel) (((View) object).findViewById(R.id.progress_wheel));
            if (pinchImageView.getTag() == null || !pinchImageView.getTag().equals(netImages.get(position).getLargeImg())) {
                pinchImageView.setTag(netImages.get(position).getLargeImg());
                EasyImageLoader.getInstance(context).getBitmap(netImages.get(position).getLargeImg(), new EasyImageLoader.BitmapCallback() {
                    @Override
                    public void onResponse(Bitmap bitmap) {

                        if(bitmap!=null){
                            if(progressWheel.isShown()){
                                progressWheel.setVisibility(View.GONE);
                            }
                            pinchImageView.setImageBitmap(bitmap);
                        }else {
                            if(progressWheel.isShown()){
                                progressWheel.setVisibility(View.GONE);
                                EasyImageLoader.getInstance(context).bindBitmap(netImages.get(position).getThumbImg(), pinchImageView);
                            }
                            JUtils.Toast("该图没有高清图...");
                        }
                    }
                });
            }

            if (getPinchImageViewPager() != null && pinchImageView != null) {
                getPinchImageViewPager().setMainPinchImageView(pinchImageView);
            }
            currentPosition = position;
        }

    }

    @Override
    public void onClick(View v) {
        context.finish();
    }

    public PinchImageViewPager getPinchImageViewPager() {
        return mPinchImageViewPager;
    }

    public void setPinchImageViewPager(PinchImageViewPager mPinchImageViewPager) {
        this.mPinchImageViewPager = mPinchImageViewPager;
    }
}
