package com.movies.basemodule;

import androidx.appcompat.app.AppCompatActivity;

public interface BaseNavigator {
    AppCompatActivity getBaseActivity();

    void showLoadingDialog();

    void hideLoadingDialog();

    void hideKeyboard();

    void showSnackBar(CharSequence message, boolean isError);

    void showToast(String message);
}
