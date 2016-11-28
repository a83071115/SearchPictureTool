package com.example.administrator.searchpicturetool.launch;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.searchpicturetool.main.MainActivity;
import com.jude.utils.JUtils;
import com.tbruyelle.rxpermissions.RxPermissions;

/**
 * Created by wenhuaijun on 2016/2/15 0015.
 */
public class LaunchActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RxPermissions.getInstance(this)
                .request(Manifest.permission.READ_PHONE_STATE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(LaunchActivity.this, MainActivity.class));
                                finish();
                            }
                        },200);
                    } else {
                        JUtils.Toast("请同意我们的权限，才能提供服务");
                    }
                });

    }
}
