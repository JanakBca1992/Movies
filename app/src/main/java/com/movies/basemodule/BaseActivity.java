package com.movies.basemodule;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.google.android.material.snackbar.Snackbar;
import com.movies.R;
import com.movies.utils.KeyboardUtils;

import java.util.Objects;

import dagger.android.AndroidInjection;


public abstract class BaseActivity<T extends ViewDataBinding, V extends BaseViewModel> extends AppCompatActivity implements BaseNavigator {

    private AlertDialog mProgressDialog;
    private T mViewDataBinding;
    private V mViewModel;

    public abstract int getBindingVariable();

    public abstract
    @LayoutRes
    int getResourceLayout();

    public abstract void onInitialize();

    public abstract void onShow();

    public abstract void onHide();

    public abstract void onTerminate();

    public abstract V getViewModel();

    @LayoutRes
    public int getLayoutIdLoading() {
        return -1;
    }

    public int getThemResId() {
        return -1;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        performDependencyInjection();
        super.onCreate(savedInstanceState);
        performDataBinding();
        initDialog();
        onInitialize();
    }

    public void performDependencyInjection() {
        AndroidInjection.inject(this);
    }

    private void performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getResourceLayout());
        this.mViewModel = mViewModel == null ? getViewModel() : mViewModel;
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        mViewDataBinding.executePendingBindings();
    }

    @Override
    protected void onDestroy() {
        onTerminate();
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        onShow();
    }

    @Override
    protected void onStop() {
        super.onStop();
        onHide();
    }

    public T getViewBinding() {
        return mViewDataBinding;
    }

    private void initDialog() {
        AlertDialog.Builder builder = getThemResId() != -1 ? new AlertDialog.Builder(this, getThemResId()) : new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setView(LayoutInflater.from(this).inflate(getLayoutIdLoading() == -1 ? R.layout.view_dialog_loadin_default : getLayoutIdLoading(), null));
        mProgressDialog = builder.create();
        Objects.requireNonNull(mProgressDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }


    @Override
    public AppCompatActivity getBaseActivity() {
        return this;
    }

    @Override
    public void showLoadingDialog() {
        if (mProgressDialog != null && !mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    @Override
    public void hideLoadingDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void hideKeyboard() {
        KeyboardUtils.hideKeyboard(this);
    }

    @Override
    public void showSnackBar(CharSequence message, boolean isError) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        if (isError) {
            sbView.setBackgroundColor(Color.RED);
        }
        TextView textView = sbView.findViewById(R.id.snackbar_text);
        textView.setMaxLines(3);
        textView.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        snackbar.show();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
