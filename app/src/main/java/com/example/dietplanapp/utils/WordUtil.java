package com.example.dietplanapp.utils;

import android.content.res.Resources;

import com.example.dietplanapp.AppContext;


public class WordUtil {

    private static Resources sResources;

    static {
        sResources = AppContext.sInstance.getResources();
    }

    public static String getString(int res) {
        return sResources.getString(res);
    }
}
