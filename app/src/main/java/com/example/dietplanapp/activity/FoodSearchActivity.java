package com.example.dietplanapp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.dietplanapp.R;
import com.example.dietplanapp.adapter.FoodListAdapter;
import com.example.dietplanapp.bean.FoodDetails;
import com.example.dietplanapp.utils.SPUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import io.reactivex.internal.operators.flowable.FlowableOnErrorReturn;


public class FoodSearchActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
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
    @BindView(R.id.ll_s)
    LinearLayout ll_s;
    @BindView(R.id.ll_history)
    LinearLayout ll_history;
    @BindView(R.id.tv_clear)
    TextView tv_clear;
    @BindView(R.id.listview)
    ListView listView;

    @BindView(R.id.search_input)
    EditText search_input;

    String[] sptypes={"main","meat","milk","fruit","fit","other"};
    String[] nametypes={"주식","고기","유류","과일과 야채","피트니스 식사","기타"};
    List<FoodDetails> allList=new ArrayList<>();

    @Override
    public void initRootView() {
        setContentView(R.layout.activity_foodsearch);
    }

    @Override
    public void initView() {
        for (int i=0;i<6;i++){
            allList.addAll( SPUtils.getDataList("foodlist_"+sptypes[i], FoodDetails.class));
        }
        type=getIntent().getIntExtra("type",0);
        tvTittle.setText(nametypes[type]+"");
        search_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchData(s.toString());
            }
        });
    }

    private void searchData(String toString) {
        if (toString.isEmpty()){
            ll_s.setVisibility(View.VISIBLE);
        }else{

            data_list = allList.stream().filter(o->o.name.equals(toString)).collect(Collectors.toList());

            mHandler.obtainMessage(0).sendToTarget();
        }
    }

    @Override
    public void initData() {
//        data_list = SPUtils.getDataList("foodlist_"+sptypes[type], FoodDetails.class);
//        mHandler.obtainMessage(0).sendToTarget();
    }

    @Override
    public void initListener() {
        ivBack.setOnClickListener(this);
        listView.setOnItemClickListener(this);
        tv_clear.setOnClickListener(this);
    }

    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (data_list.size()>0) {
                        adapter = new FoodListAdapter(FoodSearchActivity.this, data_list);
                        listView.setAdapter(adapter);
                        ll_s.setVisibility(View.GONE);
                    }else{
                        ll_s.setVisibility(View.VISIBLE);
                    }
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
            case R.id.tv_clear:
                ll_history.setVisibility(View.GONE);
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

