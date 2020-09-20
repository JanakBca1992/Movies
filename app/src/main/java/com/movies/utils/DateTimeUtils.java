package com.movies.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;

@SuppressLint("ALL")
public class DateTimeUtils {
    private static final String SERVER_FORMAT = "yyyy-MM-dd";
    private static final String LOCAL_FORMAT = "MMM dd, yyyy";


    public static String getReleaseData(String date) {
        if (date == null) {
            return "";
        }
        try {
            return new SimpleDateFormat(LOCAL_FORMAT).format(Objects.requireNonNull(new SimpleDateFormat(SERVER_FORMAT).parse(date)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

}
