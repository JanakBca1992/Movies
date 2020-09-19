package com.movies.data.datamanager.local.preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.movies.di.PreferenceInfo;

import javax.inject.Inject;

public class AppPreferencesHelper implements PreferencesHelper {

    private static final String PREF_KEY_TOTAL_PAGE = "pref_key_total_page";
    private static final String PREF_KEY_CURRENT_PAGE = "pref_key_current_page";

    private final SharedPreferences mPrefs;

    @Inject
    AppPreferencesHelper(Context context, @PreferenceInfo String prefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    @Override
    public Long getCurrentPage() {
        return mPrefs.getLong(PREF_KEY_CURRENT_PAGE, 0);
    }

    @Override
    public void setCurrentPage(Long currentPage) {
        mPrefs.edit().putLong(PREF_KEY_CURRENT_PAGE, currentPage).apply();
    }

    @Override
    public Long getTotalPage() {
        return mPrefs.getLong(PREF_KEY_TOTAL_PAGE, 1);
    }

    @Override
    public void setTotalPage(Long totalPage) {
        mPrefs.edit().putLong(PREF_KEY_TOTAL_PAGE, totalPage).apply();
    }
}
