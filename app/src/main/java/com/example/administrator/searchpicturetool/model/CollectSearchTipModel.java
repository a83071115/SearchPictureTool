package com.example.administrator.searchpicturetool.model;

import android.content.Context;

import com.example.administrator.searchpicturetool.model.db.DBManager;
import com.example.administrator.searchpicturetool.model.bean.CollectSearchTip;

import java.util.ArrayList;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by WenHuaijun on 2016/9/20 0020.
 */
public class CollectSearchTipModel {
    public static Observable<ArrayList<CollectSearchTip>> getSearchTipsFromDB(Context context) {
        return Observable.create((Observable.OnSubscribe<ArrayList<CollectSearchTip>>) subscriber -> {
            subscriber.onNext(DBManager.getInstance(context).queryHasCollectSearchTips());
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
