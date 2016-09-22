package com.example.administrator.searchpicturetool.view.viewHolder;
import android.net.Uri;
import android.text.TextUtils;
import android.view.ViewGroup;
import com.example.administrator.searchpicturetool.R;
import com.example.administrator.searchpicturetool.model.bean.NetImage;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.utils.JUtils;

/**
 * Created by wenhuaijun on 2015/11/3 0003.
 */
public class NetImageListViewHolder extends BaseViewHolder<NetImage>{
    SimpleDraweeView image;
    float width;
    float height;
    float screenWidth;
    ViewGroup.LayoutParams layoutParams;
    public NetImageListViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_netimage);
        image =(SimpleDraweeView)itemView.findViewById(R.id.net_img);
        screenWidth = JUtils.getScreenWidth()/2;
    }

    public static void loadImg(Uri uri, SimpleDraweeView draweeView,int width,int height){
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(width/2, height/2))
                .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(draweeView.getController())
                .setControllerListener(new BaseControllerListener<ImageInfo>())
                .build();
        draweeView.setController(controller);
    }

    @Override
    public void setData(NetImage data) {
        super.setData(data);
        height =data.getHeight();
        width = data.getWidth();
        int widthPx =(int) screenWidth /2;
        int heightPx =(int)((height/width)* screenWidth);
        if(!TextUtils.isEmpty(data.getThumbImg())){
            layoutParams= image.getLayoutParams();
            layoutParams.height= heightPx;
            image.setLayoutParams(layoutParams);
        }

        //image.setImageURI(Uri.parse(data.getThumbImg()));
        loadImg(Uri.parse(data.getThumbImg()),image,widthPx,heightPx);
    }


}
