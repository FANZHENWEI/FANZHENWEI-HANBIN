package com.example.dietplanapp;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.example.dietplanapp.bean.FoodDetails;

import java.util.List;

public class AppConfig {

    private static final String TAG = "AppConfig";
    private static AppConfig sInstance;
    private String mVersion;

    private AppConfig() {

    }

    public static AppConfig getInstance() {
        if (sInstance == null) {
            synchronized (AppConfig.class) {
                if (sInstance == null) {
                    sInstance = new AppConfig();
                }
            }
        }
        return sInstance;
    }

    /**
     * Get version number
     */
    public String getVersion() {
        if (TextUtils.isEmpty(mVersion)) {
            try {
                PackageManager manager = AppContext.sInstance.getPackageManager();
                PackageInfo info = manager.getPackageInfo(AppContext.sInstance.getPackageName(), 0);
                mVersion = info.versionName;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mVersion;
    }

}
