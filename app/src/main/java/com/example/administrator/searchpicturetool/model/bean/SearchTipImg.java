package com.example.administrator.searchpicturetool.model.bean;

import com.jude.utils.JUtils;

/**
 * Created by WenHuaijun on 2016/9/17 0017.
 */
public class SearchTipImg extends NetImage{
    public String imgUrl;
    @Override
    public String getThumbImg() {
        return imgUrl;
    }

    @Override
    public String getLargeImg() {
        return imgUrl;
    }

    @Override
    public int getWidth() {
        return JUtils.getScreenWidth();
    }

    @Override
    public int getHeight() {
        return JUtils.dip2px(256);
    }
}
