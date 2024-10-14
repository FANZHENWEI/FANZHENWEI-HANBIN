package com.example.dietplanapp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.dietplanapp.R;
import com.example.dietplanapp.adapter.FoodListAdapter;
import com.example.dietplanapp.bean.Food;
import com.example.dietplanapp.bean.FoodDetails;
import com.example.dietplanapp.utils.LogUtils;
import com.example.dietplanapp.utils.SPUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;


public class FoodDetailsActivity extends BaseActivity implements View.OnClickListener {

    int type=0;
    Context mContext;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_tittle)
    TextView tvTittle;

    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_type)
    TextView tv_type;
    @BindView(R.id.tv_kcal)
    TextView tv_kcal;
    @BindView(R.id.tv_db)
    TextView tv_db;
    @BindView(R.id.tv_ts)
    TextView tv_ts;
    @BindView(R.id.tv_zf)
    TextView tv_zf;
    @BindView(R.id.iv_save)
    ImageView iv_save;
    FoodDetails data;
    String[] sptypes={"main","meat","milk","fruit","fit","other"};

    @Override
    public void initRootView() {
        setContentView(R.layout.activity_fooddetails);
    }

    @Override
    public void initView() {
        type=getIntent().getIntExtra("type",0);
        tvTittle.setText("음식 상세 정보");
    }

    @Override
    public void initData() {
        data=(FoodDetails) getIntent().getSerializableExtra("food");
        if (data!=null){
            tv_name.setText(data.name+"");
            tv_type.setText(data.advise+"");
            tv_kcal.setText(data.kcal+"Kcal");
            tv_db.setText(data.danbai+"g");
            tv_ts.setText(data.tanshui+"g");
            tv_zf.setText(data.zhifang+"g");
            Log.e(LogUtils.funAndLine(new Exception()),"SAVE:"+data.save);
            if (data.save){
                iv_save.setBackground(getResources().getDrawable(R.drawable.ic_saved));
            }else{
                iv_save.setBackground(getResources().getDrawable(R.drawable.ic_unsave));

            }
        }
    }

    @Override
    public void initListener() {
        ivBack.setOnClickListener(this);
        iv_save.setOnClickListener(this);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //关闭
            case R.id.iv_back:
                Intent returnIntent = new Intent();
                returnIntent.putExtra("type", type);  // 将要传递的信息放入 Intent 中
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
                break;
            case R.id.iv_save:
                if (data==null) return;
                if ( data.save){
                    iv_save.setBackground(getResources().getDrawable(R.drawable.ic_unsave));
                    data.save=false;
                }else{
                    iv_save.setBackground(getResources().getDrawable(R.drawable.ic_saved));
                    data.save=true;
                }
                List<FoodDetails> detailsList=SPUtils.getDataList("foodlist_"+sptypes[type],FoodDetails.class);
                List <FoodDetails> delFoodList = new ArrayList<>();
                Log.e(LogUtils.funAndLine(new Exception()),"delFoodList size:"+delFoodList.size());

                for (FoodDetails details:detailsList) {
                    if (details.name.equals(data.name)) {
                        delFoodList.add(data);
                    }else{
                        delFoodList.add(details);
                    }
                }
                SPUtils.setDataList("foodlist_" + sptypes[type], delFoodList);
                break;
        }
    }


}

