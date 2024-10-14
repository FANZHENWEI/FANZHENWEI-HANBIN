package com.example.dietplanapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;

import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import butterknife.ButterKnife;


/**
 * ================================================
 *
 * ================================================
 *
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    public Context mContext;
    private MotionEvent mActionDownEvent;
    private VelocityTracker mVelocityTracker;
    private BaseActivity oContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //在setContentView之前添加,未添加的话home键监听无效，设置窗体属性
        //设置在activity启动的时候输入法默认不开启
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //无标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //保持屏幕常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        // 隐藏 ActionBasr放到setContentView之前
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        mContext = this;
        //当前用户
        //锁定纵向
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initRootView();
        //刀油
        ButterKnife.bind(this);

        initView();
        initData();
        initListener();

            oContext=this;

    }

    /**
     * 初始化根布局文件
     */

    public abstract void initRootView();

    /**
     * 初始化View
     */
    abstract public void initView();

    /**
     * 初始化数据
     */
    abstract public void initData();

    /**
     * 初始化监听
     */
    abstract public void initListener();


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        //设置为竖屏
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
        }
    }

    /**
     * 1点击空白位置 隐藏软键盘(并且增加滑动手势触发返回)
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(ev);
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }

        if (ev.getActionMasked() == MotionEvent.ACTION_DOWN) {
            if (mActionDownEvent != null) {
                mActionDownEvent.recycle();
            }
            // 记录按下时的事件
            mActionDownEvent = MotionEvent.obtain(ev);
        } else if (ev.getActionMasked() == MotionEvent.ACTION_UP) {
            // 右滑返回手势检测
            int pointerId = ev.getPointerId(0);
            int maximumFlingVelocity = ViewConfiguration.get(this).getScaledMaximumFlingVelocity();
            int minimumFlingVelocity = ViewConfiguration.get(this).getScaledMinimumFlingVelocity();
            mVelocityTracker.computeCurrentVelocity(1000, maximumFlingVelocity);
            final float velocityX = mVelocityTracker.getXVelocity(pointerId);
            // 左边缘检测，可根据需要调整，单位像素
            if (mActionDownEvent.getX() <= 50
                    // 有效触发距离，可根据需要调整，单位像素,默认300
                    && ev.getX() - mActionDownEvent.getX() >= 300
                    && Math.abs(velocityX) >= minimumFlingVelocity) {
                // finish当前Activity
                onBackPressed();
            }
        }
        // 分发控制还给Activity
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 2根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 3获取InputMethodManager，隐藏软键盘
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            Objects.requireNonNull(im).hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * [页面跳转]
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(BaseActivity.this, clz));
    }

    /**
     * [携带数据的页面跳转]
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * [含有Bundle通过Class打开编辑界面]
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }


    /**
     * Activity跳转
     */
    protected void openActivity(Class<?> pClass) {
        openActivity(pClass, null);
    }

    /**
     * Activity跳转
     */
    protected void openActivity(Class<?> pClass, Bundle pBundle) {
        openActivity(pClass, pBundle, null);
    }

    /**
     * Activity跳转
     */
    protected void openActivity(Class<?> pClass, Bundle pBundle, Uri uri) {
        Intent intent = new Intent(this, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        if (uri != null) {
            intent.setData(uri);
        }
        startActivity(intent);
    }

    /**
     * Activity跳转
     */
    protected void openActivity(String pAction) {
        openActivity(pAction, null);
    }

    /**
     * Activity跳转
     */
    protected void openActivity(String pAction, Bundle pBundle) {
        openActivity(pAction, pBundle, null);
    }

    /**
     * Activity跳转
     */
    protected void openActivity(String pAction, Bundle pBundle, Uri uri) {
        Intent intent = new Intent(pAction);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        if (uri != null) {
            intent.setData(uri);
        }
        startActivity(intent);
    }

}

