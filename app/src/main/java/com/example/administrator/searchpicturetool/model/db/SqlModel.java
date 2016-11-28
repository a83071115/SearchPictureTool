package com.example.administrator.searchpicturetool.model.db;

import android.content.Context;

import com.example.administrator.searchpicturetool.config.API;
import com.example.administrator.searchpicturetool.model.bean.CollectSearchTip;
import com.example.administrator.searchpicturetool.model.bean.DownloadImg;
import com.example.administrator.searchpicturetool.model.bean.NetImage;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by wenhuaijun on 2016/1/31 0031.
 */
public class SqlModel {
    //存下载图片信息到数据库
    public static void addDownloadImg(Context context,NetImage netImage,String fileName){
       // DBManager manager = new DBManager(context);
        DownloadImg hasDownloadImg= new DownloadImg(fileName,netImage.getLargeImg(),netImage.getHeight(),netImage.getWidth());
        DBManager.getInstance(context).addHasDownload(hasDownloadImg);
    }
    //从download数据库中删除一个图片信息
    public static void deleteDownloadImgByname(Context context,String name){
       // DBManager manager = new DBManager(context);
        DBManager.getInstance(context).deleteHasDownload(name);
    }
    //批量删除选中的已下载图片
    public static Observable<String> deleteDownloadImgs(final Context context, final List<DownloadImg> imgs){
        Observable<String> observable = Observable.just("")
                .map(s -> {
                    DBManager.getInstance(context).deleteDownloadPictures(imgs,context);
                    return API.status.success+"";
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    //批量删除选中的收藏的标签
    public static Observable<String> deleteSeachTips(final Context context, final List<CollectSearchTip> imgs){
        Observable<String> observable = Observable.just("")
                .map(s -> {
                    DBManager.getInstance(context).deleteSearchTips(imgs);
                    return API.status.success+"";
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    //存收藏图片信息到数据库
    public static void addCollectImg(Context context,NetImage netImage){
        DBManager.getInstance(context).addHasCollect(netImage);
    }
    public static Observable<String> addSearchTip(Context context,String tip,String uriType,String uri){
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                DBManager.getInstance(context).addSearchTip(tip,uriType,uri);
                subscriber.onNext("收藏搜索标签成功");

            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
    //从collect数据库删除一张图片信息
    public static void deleteCollectImgByUrl(Context context,String largeUrl){
        DBManager.getInstance(context).deleteHasCollect(largeUrl);
    }
    //批量删除选中的已下载图片
    public static Observable<String> deleteCollectImgs(final Context context, final ArrayList<NetImage> imgs){
        Observable<String> observable = Observable.just("")
                .map(s -> {
                    DBManager.getInstance(context).deleteCollectPictures(imgs);
                    return API.status.success+"";
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    //从数据库获取所有收藏图片的信息
    public static Observable<ArrayList<NetImage>> getCollectImgs(final Context context){
        Observable<ArrayList<NetImage>> observable = Observable.just("").map(new Func1<String, ArrayList<NetImage>>() {
            @Override
            public ArrayList<NetImage> call(String s) {
                return DBManager.getInstance(context).queryHasCollectImgs();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        return observable;
    }
    //从数据库获取所有下载图片的信息
    public static  Observable<ArrayList<DownloadImg>> getDownloadImgs(final Context context){
        Observable<ArrayList<DownloadImg>> observable = Observable.just("").map(new Func1<String, ArrayList<DownloadImg>>() {
            @Override
            public ArrayList<DownloadImg> call(String s) {
                return DBManager.getInstance(context).queryHasDownloadImgs();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        return  observable;
    }
}
