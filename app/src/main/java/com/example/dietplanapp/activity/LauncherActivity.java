package com.example.dietplanapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.example.dietplanapp.AppConfig;
import com.example.dietplanapp.R;
import com.example.dietplanapp.utils.WordUtil;

public class LauncherActivity extends AbsActivity {

    private TextView mVersion;
    private Handler mHandler;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_launcher;
    }

    @Override
    protected void main() {
        mVersion= (TextView) findViewById(R.id.version);
        mVersion.setText(WordUtil.getString(R.string.app_name)+" V "+ AppConfig.getInstance().getVersion());
        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                forwardMainActivity();
            }
        }, 2000);
    }


    private void forwardMainActivity() {
        startActivity(new Intent(mContext, MainActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        super.onDestroy();
    }
}