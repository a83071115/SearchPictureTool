package com.example.administrator.searchpicturetool.presenter.adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.searchpicturetool.R;
import com.example.administrator.searchpicturetool.library.imageLoader.EasyImageLoader;
import com.example.administrator.searchpicturetool.model.bean.NetImage;
import com.example.administrator.searchpicturetool.widght.PinchImageView;
import com.example.administrator.searchpicturetool.widght.PinchImageViewPager;
import com.jude.utils.JUtils;

import java.util.ArrayList;

/**
 * Created by wenhuaijun on 2015/11/8 0008.
 */
public class ShowLargeImgAdapter extends PagerAdapter implements View.OnClickListener {
    private PinchImageView pinchImageView;
    private ArrayList<NetImage> netImages;
    private Activity context;
    private LayoutInflater inflater;
    private int screenHeight;
    private int screenWidth;
    private View view;
    private int currentPosition =-1;
    ViewGroup.LayoutParams mLayoutParams;
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

        pinchImageView = (PinchImageView) view.findViewById(R.id.photoView);
        pinchImageView.setOnClickListener(this);
        //加载低分辨率图片
        EasyImageLoader.getInstance(context).bindBitmap(netImages.get(position).getThumbImg(), pinchImageView,100,100);

        /*EasyImageLoader.getInstance(context).bindBitmap(netImages.get(position).getLargeImg(), pinchImageView, new EasyImageLoader.BindBitmapErrorCallBack() {
            @Override
            public void onError(ImageView imgView) {
                JUtils.Log("bindBitmap onError");
                EasyImageLoader.getInstance(context).bindBitmap(netImages.get(position).getThumbImg(), imgView);
            }
        });*/
        container.addView(view);

        return view;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        if(currentPosition!=position){
            pinchImageView = (PinchImageView) (((View)object).findViewById(R.id.photoView));
            if(!pinchImageView.getTag().equals(netImages.get(position).getLargeImg())){
                EasyImageLoader.getInstance(context).bindBitmap(netImages.get(position).getLargeImg(), pinchImageView, imgView -> {
                   JUtils.Toast("该图没有高清图...");
                    EasyImageLoader.getInstance(context).bindBitmap(netImages.get(position).getThumbImg(), imgView);
                });
            }
            pinchImageView.setTag(netImages.get(position).getLargeImg());
            if(getPinchImageViewPager()!=null&&pinchImageView!=null){
                getPinchImageViewPager().setMainPinchImageView(pinchImageView);
            }
            currentPosition =position;
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
