package com.example.dietplanapp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.dietplanapp.R;
import com.example.dietplanapp.adapter.FoodListAdapter;
import com.example.dietplanapp.bean.FoodDetails;
import com.example.dietplanapp.utils.SPUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;


public class FoodAnalyseActivity extends BaseActivity implements View.OnClickListener{

    Context mContext;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_tittle)
    TextView tvTittle;


    @Override
    public void initRootView() {
        setContentView(R.layout.activity_analyse);
    }

    @Override
    public void initView() {
        tvTittle.setText("식이 분석 결과");
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        ivBack.setOnClickListener(this);
    }


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

