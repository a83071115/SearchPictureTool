package com.example.administrator.searchpicturetool.model.bean;

import java.io.Serializable;

/**
 * Created by WenHuaijun on 2016/9/20 0020.
 */
public class CollectSearchTip implements Serializable{
    private String tip;
    private String uriType;
    private String uri;
    private int id;
    private boolean isTranscation;
    private boolean isSelected;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getUriType() {
        return uriType;
    }

    public void setUriType(String uriType) {
        this.uriType = uriType;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public boolean isTranscation() {
        return isTranscation;
    }

    public void setTranscation(boolean transcation) {
        isTranscation = transcation;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
