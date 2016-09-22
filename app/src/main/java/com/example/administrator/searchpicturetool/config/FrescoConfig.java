package com.example.administrator.searchpicturetool.config;

import android.app.ActivityManager;
import android.content.Context;

import com.facebook.imagepipeline.core.ImagePipelineConfig;

/**
 * Created by WenHuaijun on 2016/9/22 0022.
 */
public class FrescoConfig {

    public static ImagePipelineConfig getConfig(Context context){
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(context)
              //  .setBitmapMemoryCacheParamsSupplier(new MemoryCacheSupplier((ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE)))
                .setDownsampleEnabled(true)
                .build();
        return config;
    }
}
