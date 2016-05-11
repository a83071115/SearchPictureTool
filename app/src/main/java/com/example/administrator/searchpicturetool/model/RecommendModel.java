package com.example.administrator.searchpicturetool.model;

import android.content.Context;

import com.example.administrator.searchpicturetool.db.DBManager;
import com.example.administrator.searchpicturetool.model.bean.NewRecommendContent;
import com.example.administrator.searchpicturetool.model.bean.RecommendContent;
import com.example.administrator.searchpicturetool.model.bean.RecommendTip;
import com.example.administrator.searchpicturetool.util.RecommendComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by wenhuaijun on 2016/2/12 0012.
 * 目前给推荐列表提供测试数据
 */
public class RecommendModel {

    public static Observable<List<NewRecommendContent>> getRecommendsFromNet(final Context context){
        return Observable.create(new Observable.OnSubscribe<List<NewRecommendContent>>() {
            @Override
            public void call(Subscriber<? super List<NewRecommendContent>> subscriber) {
                BmobQuery<NewRecommendContent> queryContent = new BmobQuery<>();
                queryContent.findObjects(context, new FindListener<NewRecommendContent>() {
                    @Override
                    public void onSuccess(List<NewRecommendContent> list) {
                        subscriber.onNext(list);
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(int i, String s) {
                        subscriber.onError(new Throwable(i + " " + s));
                    }
                });
            }
        }).doOnNext(newRecommendContents -> {
            Collections.sort(newRecommendContents, new RecommendComparator());
            DBManager.getInstance(context).deleteAllRecommendContents();
            DBManager.getInstance(context).addAllRecomendContents(newRecommendContents);
        });
    }
    /*public static Observable<List<Object>> getRecommends2(final Context context){
        return Observable.create(subscriber1 -> {
            BmobQuery<RecommendTip> queryTip = new BmobQuery<>();
            queryTip.findObjects(context, new FindListener<RecommendTip>() {
                        @Override
                        public void onSuccess(List<RecommendTip> list) {
                            for (RecommendTip recommendTip : list) {
                                subscriber1.onNext(recommendTip);
                            }
                            subscriber1.onCompleted();
                        }

                        @Override
                        public void onError(int i, String s) {
                            subscriber1.onError(new Throwable(i+" "+s));
                        }
                    });
                })
                .mergeWith(Observable.create(subscriber1 -> {
                    BmobQuery<RecommendContent> queryContent = new BmobQuery<>();
                    queryContent.findObjects(context, new FindListener<RecommendContent>() {
                        @Override
                        public void onSuccess(List<RecommendContent> list) {
                            for (RecommendTip recommendContent : list) {
                                subscriber1.onNext(recommendContent);
                            }
                            subscriber1.onCompleted();
                        }

                        @Override
                        public void onError(int i, String s) {
                            subscriber1.onError(new Throwable(i+" "+s));
                        }
                    });
                }))
                .toList()
                .doOnNext(list -> Collections.sort(list, new RecommendComparator()));
    }*/
}
