package com.example.administrator.searchpicturetool.user.collect;

import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.searchpicturetool.R;
import com.example.administrator.searchpicturetool.util.CollectImgSelected;
import com.example.administrator.searchpicturetool.model.bean.NetImage;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.utils.JUtils;

/**
 * Created by wenhuaijun on 2016/2/2 0002.
 */
public class CollectImageListViewHolder extends BaseViewHolder<NetImage> {
    SimpleDraweeView image;
    View view_bg;
    ImageView img_selected;
    float width;
    float height;
    float sccrenWidth;
    ViewGroup.LayoutParams layoutParams;
    public CollectImageListViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_view_user_collect_img);
        image =(SimpleDraweeView)itemView.findViewById(R.id.net_img);
        view_bg =itemView.findViewById(R.id.bg_layout);
        img_selected =(ImageView)itemView.findViewById(R.id.img_selected);
        sccrenWidth = JUtils.getScreenWidth()/2;
    }

    @Override
    public void setData(final NetImage data) {
        super.setData(data);
        height =data.getHeight();
        width = data.getWidth();
        layoutParams= image.getLayoutParams();
        layoutParams.height= (int)((height/width)*sccrenWidth);
        image.setLayoutParams(layoutParams);
        image.setImageURI(Uri.parse(data.getThumbImg()));

        if(data.isBeginTransaction()){
            view_bg.setLayoutParams(layoutParams);
            view_bg.setVisibility(View.VISIBLE);
            img_selected.setVisibility(View.VISIBLE);
            if(data.isSelected()){
                img_selected.setImageResource(R.drawable.ic_selected);
            }else {
                img_selected.setImageResource(R.drawable.ic_not_selected);
            }
            img_selected.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!data.isSelected()){
                        img_selected.setImageResource(R.drawable.ic_selected);
                        data.setSelected(true);
                    }else{
                        img_selected.setImageResource(R.drawable.ic_not_selected);
                        data.setSelected(true);
                    }

                }
            });
        }else{
            view_bg.setVisibility(View.GONE);
            img_selected.setVisibility(View.GONE);
        }
    }

}
