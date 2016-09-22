package com.example.administrator.searchpicturetool.model.bean;

import java.io.Serializable;

/**
 * Created by wenhuaijun on 2015/11/2 0002.
 */
public abstract class NetImage implements Serializable {
    private boolean isSelected;
    private boolean beginTransaction;
    public abstract String getThumbImg();

    public abstract String getLargeImg();

    public abstract int getWidth();

    public abstract int getHeight();

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isBeginTransaction() {
        return beginTransaction;
    }

    public void setBeginTransaction(boolean beginTransaction) {
        this.beginTransaction = beginTransaction;
    }
}
