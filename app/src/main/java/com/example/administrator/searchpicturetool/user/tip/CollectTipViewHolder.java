package com.example.administrator.searchpicturetool.user.tip;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.searchpicturetool.R;
import com.example.administrator.searchpicturetool.config.Constant;
import com.example.administrator.searchpicturetool.model.bean.CollectSearchTip;
import com.example.administrator.searchpicturetool.search.result.SearchResultActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.utils.JUtils;

/**
 * Created by WenHuaijun on 2016/9/20 0020.
 */
public class CollectTipViewHolder extends BaseViewHolder<CollectSearchTip> implements View.OnClickListener{
    ImageView img_selected;
    private SimpleDraweeView simpleDraweeView;
    private TextView searchTip;
    private CollectSearchTip data;
    private RelativeLayout relativeLayout;
    private ViewGroup.LayoutParams layoutParams;
    private float sccrenWidth;
    public CollectTipViewHolder(ViewGroup parent) {
        super(parent, R.layout.itemview_collect_search_tip);
        relativeLayout = $(R.id.seach_tip_layout);
        simpleDraweeView = $(R.id.recomend_img);
        img_selected =$(R.id.img_selected);
        searchTip =$(R.id.collect_tip_title);
        simpleDraweeView.setOnClickListener(this);
        layoutParams = relativeLayout.getLayoutParams();
        sccrenWidth = JUtils.getScreenWidth()/2;
        layoutParams.height =(int)(sccrenWidth/5*3);
        relativeLayout.setLayoutParams(layoutParams);

    }

    @Override
    public void setData(CollectSearchTip data) {
        super.setData(data);
        this.data =data;
        searchTip.setText(data.getTip());
        if(data.getUriType().equals(Constant.URI_TYPE_NET)){
            simpleDraweeView.setImageURI(data.getUri());
        }else if(data.getUriType().equals(Constant.URI_TYPE_NATIVE)){
            simpleDraweeView.setImageResource(Integer.parseInt(data.getUri()));
        }

        if(data.isTranscation()){
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
            img_selected.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        if(data!=null){
            Bundle bundle = new Bundle();
            bundle.putString("search",data.getTip());
            bundle.putString("uriType",data.getUriType());
            bundle.putString("imagUrl",data.getUri());
            Intent intent = new Intent();
            intent.putExtra("search", bundle);
            intent.setClass(getContext(), SearchResultActivity.class);
            getContext().startActivity(intent);
        }

    }
}
