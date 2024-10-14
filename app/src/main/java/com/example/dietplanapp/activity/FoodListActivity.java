package com.example.dietplanapp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dietplanapp.R;
import com.example.dietplanapp.adapter.FoodCalorieListAdapter;
import com.example.dietplanapp.adapter.FoodListAdapter;
import com.example.dietplanapp.adapter.FoodSelectAdapter;
import com.example.dietplanapp.bean.Food;
import com.example.dietplanapp.bean.FoodDetails;
import com.example.dietplanapp.utils.SPUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;


public class FoodListActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    static final int REQUEST_CODE = 1;  // 请求码

    private HashMap<String, Object> mHashMap;
    private SimpleAdapter mSimpleAdapter;
    private List<FoodDetails> data_list;
    FoodListAdapter adapter;
    FloatingActionButton fabAdd;
    int type=0;
    Context mContext;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_tittle)
    TextView tvTittle;

    @BindView(R.id.listview)
    ListView listView;
    String[] sptypes={"main","meat","milk","fruit","fit","other"};
    String[] nametypes={"주식","고기","유류","과일과 야채","피트니스 식사","다른 음식"};

    @Override
    public void initRootView() {
        setContentView(R.layout.activity_foodlist);
    }

    @Override
    public void initView() {
        type=getIntent().getIntExtra("type",0);
        tvTittle.setText(nametypes[type]+"");
    }

    @Override
    public void initData() {
        data_list = SPUtils.getDataList("foodlist_"+sptypes[type], FoodDetails.class);
        mHandler.obtainMessage(0).sendToTarget();
    }

    @Override
    public void initListener() {
        ivBack.setOnClickListener(this);
        listView.setOnItemClickListener(this);
    }

    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    adapter = new FoodListAdapter(FoodListActivity.this,data_list);
                    listView.setAdapter(adapter);
                    break;

                default:
                    break;
            }
        }

    };
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //关闭
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FoodDetails food=(FoodDetails) parent.getAdapter().getItem(position);
        Intent intent = new Intent(this, FoodDetailsActivity.class);
        intent.putExtra("food", food);
        intent.putExtra("type", type);

        startActivityForResult(intent,REQUEST_CODE);
    }



    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
               initData();
            }
        }
    }
}

