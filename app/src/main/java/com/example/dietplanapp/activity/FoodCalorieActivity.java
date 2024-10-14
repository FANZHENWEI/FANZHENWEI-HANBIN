package com.example.dietplanapp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.dietplanapp.R;
import com.example.dietplanapp.adapter.FoodCalorieListAdapter;
import com.example.dietplanapp.bean.Food;
import com.example.dietplanapp.utils.SPUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;


public class FoodCalorieActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private HashMap<String, Object> mHashMap;
    private SimpleAdapter mSimpleAdapter;
    private List<Food> data_list;
    FoodCalorieListAdapter adapter;
    FloatingActionButton fabAdd;
    int type=0;
    Context mContext;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_tittle)
    TextView tvTittle;
    @BindView(R.id.tv_type)
    TextView tv_type;
    @BindView(R.id.listview)
    ListView listView;
    String[] types={"아침","점심","저녁"};
    String[] sptypes={"zao","wu","wan"};

    @Override
    public void initRootView() {
        setContentView(R.layout.activity_runcalorie);
    }

    @Override
    public void initView() {
        type=getIntent().getIntExtra("type",0);
        tvTittle.setText("칼로리 조회");
        tv_type.setText(getIntent().getStringExtra("name")+"추천");
    }

    @Override
    public void initData() {
        data_list = SPUtils.getDataList("food_"+sptypes[type], Food.class);
        mHandler.obtainMessage(0).sendToTarget();
    }

    @Override
    public void initListener() {
        ivBack.setOnClickListener(this);
        listView.setOnItemClickListener(this);
    }



//    private void loadData() {
//        BmobQuery<Food> bmobQuery = new BmobQuery<FoodCalorie>();
//        //查询数据
//        bmobQuery.addWhereEqualTo( "userid", currentUser.getObjectId());
//
//        //返回50条数据，如果不加上这条语句，默认返回10条数据
//        bmobQuery.setLimit( 100 );
//        //final int finalRandomImage = randomImage;
//        bmobQuery.findObjects(new FindListener<FoodCalorie>() {
//            @Override
//            public void done(List<FoodCalorie> list, BmobException e) {
//                if (e == null) {
//                    data_list=list;
//                    SPUtils.setDataList("DetectList", list);
//
//                    mHandler.obtainMessage(0).sendToTarget();
//                } else {
//                    Toast.makeText(getBaseContext(), "刷新失败" + e.getMessage(), Toast.LENGTH_LONG).show();
//                }
//
//            }
//        });
//    }
    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    adapter = new FoodCalorieListAdapter(FoodCalorieActivity.this,data_list);
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
//    private void deleteObject(String objectId) {
//        FoodCalorie p = new FoodCalorie();
//        p.setObjectId(objectId);
//        p.delete(new UpdateListener() {
//            @Override
//            public void done(BmobException e) {
//                if (e == null) {
////                    Toast.show(mContext, "删除成功");
//                    //  Log.e(Debug.filename(new Exception()), Debug.funAndLine(new Exception()) + "===删除成功===");
//                } else {
//                    Log.e(LogUtils.filename(new Exception()), LogUtils.funAndLine(new Exception()) + "删除失败：" + e.getMessage() + "," + e.getErrorCode());
//                }
//            }
//        });
//    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Food food=(Food) parent.getAdapter().getItem(position);
        showAddDialog(food);

//        Intent intent = new Intent(mContext, AddFoodActivity.class);
//        intent.putExtra("meal", f);
//        intent.setClass(mContext, AddFoodActivity.class);
//        startActivity(intent);

//       final FoodCalorie f=(FoodCalorie) parent.getAdapter().getItem(position);
//        new AlertDialog.Builder(this).setTitle("是否修改这次监测记录")
//                .setNegativeButton("取消", null)
//                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Intent intent = new Intent(mContext, AddFoodActivity.class);
//                        intent.putExtra("meal", f);
//                        intent.setClass(mContext, AddFoodActivity.class);
//                        startActivity(intent);
//                    }
//                })
//                .show();
    }

    private void showAddDialog(Food food) {
        new AlertDialog.Builder(this).setTitle("레코드에 추가할 지 여부")
                .setMessage(food.name+" | "+ food.kcal+"Kcal/"+food.num+"g")
                .setNegativeButton("취소", null)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       checkAdd(food);
                    }
                })
                .show();

    }

    private void checkAdd(Food food) {
        List<Food> foodList = SPUtils.getDataList("food_"+type,Food.class);
        boolean isexist=false;
        for (Food data:foodList){
            if (data.name.equals(food.name)){
                Toast.makeText(this,"기록이 이미 존재합니다. 갱신하십시오",Toast.LENGTH_SHORT).show();
                isexist=true;
                break;
            }
        }
        if(!isexist){
            foodList.add(food);
            SPUtils.setDataList("food_"+type,foodList);
// 在某个方法中设置返回的信息，并关闭 Activity B
            Intent returnIntent = new Intent();
            returnIntent.putExtra("type", type);  // 将要传递的信息放入 Intent 中
            setResult(Activity.RESULT_OK, returnIntent);
            finish();        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

        return true;
    }
}

