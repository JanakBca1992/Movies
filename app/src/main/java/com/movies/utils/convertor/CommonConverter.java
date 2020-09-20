package com.movies.utils.convertor;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.movies.data.model.movielist.CommonDetail;

public class CommonConverter {
    @TypeConverter
    public static CommonDetail fromString(String value) {
        return new Gson().fromJson(value, CommonDetail.class);
    }

    @TypeConverter
    public static String fromModel(CommonDetail commonDetail) {
        return new Gson().toJson(commonDetail);
    }
}