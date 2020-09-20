package com.movies.utils.convertor;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.movies.data.model.movielist.CommonDetail;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CommonArrayConverter {
    @TypeConverter
    public static ArrayList<CommonDetail> fromString(String value) {
        Type listType = new TypeToken<ArrayList<CommonDetail>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<CommonDetail> list) {
        return new Gson().toJson(list);
    }
}