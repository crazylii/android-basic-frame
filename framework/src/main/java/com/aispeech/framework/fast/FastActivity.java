package com.aispeech.framework.fast;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;

import com.aispeech.framework.bean.ToastBean;
import com.aispeech.framework.widget.LoadingDialog;
import com.yollpoll.arch.base.BaseActivity;
import com.yollpoll.arch.util.ToastUtil;


/**
 * Created by spq on 2021/2/13
 */
public abstract class FastActivity<BIND extends ViewDataBinding, VM extends FastViewModel> extends BaseActivity<BIND, VM> {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLD();
    }

    private void initLD() {
        mViewModel.getLoadingLD().observe(this, show -> {
            if (show) {
                showLoading();
            } else {
                hideLoading();
            }
        });
        mViewModel.getFinishLD().observe(this, finish -> {
            if (finish) {
                getContext().finish();
            }
        });
    }

    LoadingDialog loadingDialog;

    public void showLoading() {
        loadingDialog = LoadingDialog.Companion.showLoading(this);
    }

    public void hideLoading() {
        if (null != loadingDialog) {
            loadingDialog.hide();
        }
    }

}
