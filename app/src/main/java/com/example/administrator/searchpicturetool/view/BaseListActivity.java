package com.example.administrator.searchpicturetool.view;

import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.example.administrator.searchpicturetool.R;
import com.example.administrator.searchpicturetool.config.Constant;
import com.example.administrator.searchpicturetool.presenter.BaseListActivityPresenter;
import com.jude.beam.expansion.list.BeamListActivity;
import com.jude.beam.expansion.list.ListConfig;

/**
 * Created by WenHuaijun on 2016/9/19 0019.
 */
public abstract class BaseListActivity<T extends BaseListActivityPresenter, M> extends BeamListActivity<T, M> implements View.OnClickListener {
    private MaterialDialog mProgressDialog;

    @Override
    protected ListConfig getConfig() {
        return Constant.getBaseConfig();
    }

    public void showRefreshing(boolean shouldShow) {
        getListView().getSwipeToRefresh().post(() -> getListView().getSwipeToRefresh().setRefreshing(shouldShow));
    }

    public MaterialDialog showDialog(String title, String content) {
        mProgressDialog = new MaterialDialog.Builder(this)
                .theme(Theme.DARK)
                .content(content)
                .title(title)
                .progress(true, 0)
                .show();
        return mProgressDialog;
    }

    public void dismissDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    public void showSnackBar(View fab, String message, String action, View.OnClickListener listener) {
        Snackbar.make(fab, message, Snackbar.LENGTH_SHORT)
                .setActionTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
                .setAction(action, listener).show();
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.view_net_btn) {
            showRefreshing(true);
            getPresenter().onRefresh();
        } else if (view.getId() == R.id.view_empty_btn) {
            showRefreshing(true);
            getPresenter().onRefresh();
        }
    }
}
