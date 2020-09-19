package com.movies.basemodule;

public interface BaseNavigator {
    void showLoadingDialog();

    void hideLoadingDialog();

    void hideKeyboard();

    void showSnackBar(CharSequence message, boolean isError);

    void showToast(String message);
}
