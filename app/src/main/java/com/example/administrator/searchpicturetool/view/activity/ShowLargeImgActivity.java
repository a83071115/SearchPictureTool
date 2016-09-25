package com.example.administrator.searchpicturetool.view.activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.example.administrator.searchpicturetool.R;
import com.example.administrator.searchpicturetool.presenter.activityPresenter.ShowLargeImgActivityPresenter;
import com.example.administrator.searchpicturetool.util.Utils;
import com.example.administrator.searchpicturetool.widght.PinchImageViewPager;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.utils.JUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by wenhuaijun on 2015/11/4 0004.
 */
@RequiresPresenter(ShowLargeImgActivityPresenter.class)
public class ShowLargeImgActivity extends BeamBaseActivity<ShowLargeImgActivityPresenter> implements View.OnClickListener {
   /* @BindView(R.id.toolbar)
    Toolbar toolbar;*/
    @BindView(R.id.large_page)
    TextView pg_tv;
    @BindView(R.id.large_viewPager)
    PinchImageViewPager viewPager;
    @BindView(R.id.large_star)
    ImageView star;
    FragmentManager fragmentManager;
    @BindView(R.id.linearlayout_img)
    RelativeLayout bottomLayout;
    private boolean hasCollected =false;
    private MaterialDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_large_img);
        ButterKnife.bind(this);
        //onSetToolbar(toolbar);
        hasCollected = getIntent().getBooleanExtra("hasCollected", false);
        if(hasCollected){
            star.setImageResource(R.drawable.ic_large_delete_selector);
        }
        fragmentManager = getSupportFragmentManager();
        viewPager.setOnClickListener(this);
        marginNavigationBar(bottomLayout);
        //hideNavigationBar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.large_img_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cut:
                JUtils.Toast("该功能在下一个版本中开发，敬请期待");
                return true;
            case R.id.action_wrapper:
                getPresenter().setWallWrapper();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
            setResult(200,new Intent().putExtra("position",getPresenter().getPosition()));
            super.onBackPressed();
    }

    public PinchImageViewPager getViewPager() {
        return viewPager;
    }
    public TextView getPg_tv(){
        return pg_tv;
    }
    public void setHasCollected(boolean hasCollected) {
        this.hasCollected = hasCollected;
    }

    @Override
    public void onClick(View v) {
        this.finish();
    }
    @OnClick(R.id.large_share)
    public void share(){
        JUtils.Toast("正在分享...");
        getPresenter().sharePicture();
    }
    @OnClick(R.id.large_download)
    public void download(){
        JUtils.Toast("正在下载...");
        getPresenter().savePicture();
    }
    @OnClick(R.id.large_star)
    public void collect(){
        if(hasCollected){
            getPresenter().requestCollectPicture();

        }else{
            getPresenter().collectPicture();
        }

    }

    @OnClick(R.id.large_more)
    public void showMoreDialog(){
        new MaterialDialog.Builder(this)
                .theme(Theme.DARK)
                .title("更多选项")
                .items(R.array.arrays_more)
                .itemsCallback((dialog, itemView, position, text) -> {
                    switch (position){
                        case 0:
                            showDialog("设置壁纸","请稍等片刻");
                            getPresenter().setWallWrapper();
                            break;
                        case 1:
                            JUtils.Toast("该功能在下一个版本中开发，敬请期待");
                            break;
                        case 2:
                            showDialog("举报该图片","请稍等片刻");
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    dismissDialog();
                                    JUtils.Toast("已举报");
                                }
                            },2000);
                        default:
                            break;
                    }
                }).show();
    }
    public void hideNavigationBar(){

        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.KITKAT){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    }
    public MaterialDialog showDialog(String title, String content){
            mProgressDialog = new MaterialDialog.Builder(this)
                    .theme(Theme.DARK)
                    .content(content)
                    .title(title)
                    .progress(true, 0)
                    .show();
        return mProgressDialog;
    }
    public void dismissDialog(){
        if(mProgressDialog!=null){
            mProgressDialog.dismiss();
        }
    }

    public void showSnackBar(View view,String message, String action, View.OnClickListener listener){
        if (view==null){
            view =pg_tv;
        }
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
                .setActionTextColor(ContextCompat.getColor(this,R.color.colorPrimaryDark))
                .setAction(action,listener).show();
    }

    public void marginNavigationBar(RelativeLayout view){
        if(!Utils.checkDeviceHasNavigationBar(this)){
            return;
        }
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)view.getLayoutParams();
        layoutParams.setMargins(JUtils.dip2px(16),JUtils.dip2px(8),JUtils.dip2px(16),Utils.getNavigationBarHeight(this));
        view.setLayoutParams(layoutParams);
    }

   /* @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode){
            case 200:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    getPresenter().savePicture();
                } else {
                    // Permission Denied
                    JUtils.Toast("未获取到读写sd卡权限，无法保存图片");
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }*/
}
