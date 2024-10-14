package com.example.dietplanapp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.RequiresApi;

import com.example.dietplanapp.R;
import com.example.dietplanapp.bean.FoodDetails;
import com.example.dietplanapp.bean.ProductInfo;
import com.example.dietplanapp.utils.LogUtils;
import com.example.dietplanapp.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class ProductDetailsActivity extends BaseActivity implements View.OnClickListener {

    Context mContext;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_tittle)
    TextView tvTittle;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_feel)
    TextView tv_feel;
    @BindView(R.id.tv_huxi)
    TextView tv_huxi;
    @BindView(R.id.tv_step)
    TextView tv_step;
    @BindView(R.id.video_local)
    VideoView mVideoLocal;


    ProductInfo data;
    @Override
    public void initRootView() {
        setContentView(R.layout.activity_productdetails);
    }

    @Override
    public void initView() {
        tvTittle.setText("훈련 상세 정보");
    }

    @Override
    public void initData() {
        data=(ProductInfo) getIntent().getSerializableExtra("productInfo");
        if (data!=null){
            initLocalVideo();
        }
    }

    //播放本地视频
    private void initLocalVideo() {
        tv_name.setText(data.getProduct_title()+"");
        int[] arr={ R.raw.v1, R.raw.v2,R.raw.v1, R.raw.v2,
                R.raw.v3, R.raw.v4, R.raw.v3, R.raw.v4,
                R.raw.v5, R.raw.v6, R.raw.v5, R.raw.v6,
                R.raw.v7, R.raw.v8, R.raw.v7, R.raw.v8,
                R.raw.v9, R.raw.v10,  R.raw.v9, R.raw.v10,
                R.raw.v11, R.raw.v12,  R.raw.v11, R.raw.v12,
                R.raw.v13, R.raw.v14, R.raw.v13, R.raw.v14,
                R.raw.v15, R.raw.v16, R.raw.v15, R.raw.v16};

        int[] steps={R.string.step0,R.string.step1,R.string.step0,R.string.step0,
                R.string.step0,R.string.step0,R.string.step0,R.string.step0,
                R.string.step0,R.string.step0,R.string.step0,R.string.step0,
                R.string.step0,R.string.step0,R.string.step0,R.string.step0,
                R.string.step0,R.string.step0,R.string.step0,R.string.step0,
                R.string.step0,R.string.step0,R.string.step0,R.string.step0,
                R.string.step0,R.string.step0,R.string.step0,R.string.step0,
                R.string.step0,R.string.step0,R.string.step0,R.string.step0};

        int[] huxis={R.string.huxi0,R.string.huxi1,R.string.huxi0,R.string.huxi0,
                R.string.huxi0,R.string.huxi0,R.string.huxi0,R.string.huxi0,
                R.string.huxi0,R.string.huxi0,R.string.huxi0,R.string.huxi0,
                R.string.huxi0,R.string.huxi0,R.string.huxi0,R.string.huxi0,
                R.string.huxi0,R.string.huxi0,R.string.huxi0,R.string.huxi0,
                R.string.huxi0,R.string.huxi0,R.string.huxi0,R.string.huxi0,
                R.string.huxi0,R.string.huxi0,R.string.huxi0,R.string.huxi0,
                R.string.huxi0,R.string.huxi0,R.string.huxi0,R.string.huxi0};

        int[] feels={R.string.feel0,R.string.feel1,R.string.feel0,R.string.feel0,
                R.string.feel0,R.string.feel0,R.string.feel0,R.string.feel0,
                R.string.feel0,R.string.feel0,R.string.feel0,R.string.feel0,
                R.string.feel0,R.string.feel0,R.string.feel0,R.string.feel0,
                R.string.feel0,R.string.feel0,R.string.feel0,R.string.feel0,
                R.string.feel0,R.string.feel0,R.string.feel0,R.string.feel0,
                R.string.feel0,R.string.feel0,R.string.feel0,R.string.feel0,
                R.string.feel0,R.string.feel0,R.string.feel0,R.string.feel0};
        int id= data.getProduct_id();
        tv_feel.setText(getResources().getString(feels[id]));
        tv_huxi.setText(getResources().getString(huxis[id]));
        tv_step.setText(getResources().getString(steps[id]));
        MediaController localMediaController = new MediaController(this);
        mVideoLocal.setMediaController(localMediaController);
        String uri = ("android.resource://" + getPackageName() + "/" + arr[id]);
        mVideoLocal.setVideoURI(Uri.parse(uri));
        mVideoLocal.start();
    }


    @Override
    public void initListener() {
        ivBack.setOnClickListener(this);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //关闭
            case R.id.iv_back:

                finish();
                break;
        }
    }


}

