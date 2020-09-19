package com.movies.data.datamanager.local.preference;

public interface PreferencesHelper {

    Long getCurrentPage();

    void setCurrentPage(Long currentPage);

    Long getTotalPage();

    void setTotalPage(Long totalPage);
}
