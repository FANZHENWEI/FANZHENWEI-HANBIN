package com.example.dietplanapp.utils;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressUtils {

    private static ProgressDialog dialog = null;

    public static void show(Context context){
        show(context, null);
    }

    public static void show(Context context, String msg){
        dialog = new ProgressDialog(context);
        dialog.setMessage(msg == null ? "loading..." : msg);
        dialog.setCancelable(false);
        dialog.show();
    }

    public static void dismiss(){
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
    }
}
