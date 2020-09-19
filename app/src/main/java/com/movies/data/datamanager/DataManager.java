package com.movies.data.datamanager;

import com.movies.data.datamanager.local.database.DbHelper;
import com.movies.data.datamanager.local.preference.PreferencesHelper;
import com.movies.data.datamanager.remote.ApiHelper;

public interface DataManager extends DbHelper, PreferencesHelper, ApiHelper {

}
