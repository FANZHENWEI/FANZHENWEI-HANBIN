package com.example.dietplanapp.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dietplanapp.R;
import com.example.dietplanapp.activity.FoodAnalyseActivity;
import com.example.dietplanapp.activity.FoodCalorieActivity;
import com.example.dietplanapp.activity.FoodSearchActivity;
import com.example.dietplanapp.activity.SetActivity;
import com.example.dietplanapp.adapter.FoodSelectAdapter;
import com.example.dietplanapp.bean.Food;
import com.example.dietplanapp.callback.ICallback;
import com.example.dietplanapp.utils.LogUtils;
import com.example.dietplanapp.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DietPlanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DietPlanFragment extends Fragment implements View.OnClickListener, ICallback {
    static final int REQUEST_CODE = 1;  // 请求码

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RelativeLayout btn_snack,btn_breakfast,btn_launch,btn_dinner;
    LinearLayout ll_add;
    ListView listview_zao;
    List<Food> foodList=new ArrayList<>();
    FoodSelectAdapter adapter;

    LinearLayout ll_add2;
    ListView listview_wu;
    List<Food> foodList2=new ArrayList<>();
    FoodSelectAdapter adapter2;
    EditText search_input;
    LinearLayout ll_add3;
    ListView listview_wan;
    List<Food> foodList3=new ArrayList<>();
    FoodSelectAdapter adapter3;
    TextView tv_analyse;

    public DietPlanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DietPlanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DietPlanFragment newInstance(String param1, String param2) {
        DietPlanFragment fragment = new DietPlanFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view =  inflater.inflate(R.layout.fragment_diet_plan, container, false);
        btn_snack =view.findViewById(R.id.btn_snack);
        btn_snack.setOnClickListener(this);
        btn_breakfast=view.findViewById(R.id.btn_breakfast);
        btn_breakfast.setOnClickListener(this);
        btn_launch=view.findViewById(R.id.btn_launch);
        btn_launch.setOnClickListener(this);
        btn_dinner=view.findViewById(R.id.btn_dinner);
        btn_dinner.setOnClickListener(this);
        search_input=view.findViewById(R.id.search_input);
        search_input.setOnClickListener(this);
        ll_add=view.findViewById(R.id.ll_add);
        ll_add.setOnClickListener(this);
        ll_add2=view.findViewById(R.id.ll_add2);
        ll_add2.setOnClickListener(this);
        ll_add3=view.findViewById(R.id.ll_add3);
        ll_add3.setOnClickListener(this);
        listview_zao=view.findViewById(R.id.listview_zao);
        listview_wu=view.findViewById(R.id.listview_wu);
        listview_wan=view.findViewById(R.id.listview_wan);
        tv_analyse=view.findViewById(R.id.tv_analyse);
        tv_analyse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), FoodAnalyseActivity.class));
            }
        });
        TextView tvcal=view.findViewById(R.id.tv_recal);
        tvcal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SetActivity.class));
            }
        });
        loadFood(0);
        return view;
    }

    private void loadFood(int type) {
        foodList= SPUtils.getDataList("food_"+type,Food.class);
        adapter = new FoodSelectAdapter(getActivity(),foodList,type,this);
//        listview_zao.setAdapter(adapter);

        switch (type){
            case 0:
                listview_zao.setAdapter(adapter);
                break;
            case 1:
                listview_wu.setAdapter(adapter);
                break;
            case 2:
                listview_wan.setAdapter(adapter);
                break;
            default:
                break;
        }
    }

    public void showAddFoodDialog() {
        String [] arrs=new String[]{"아침 식사 추가", "점심 식사 추가","저녁 식사 추가", "취소"};
      new AlertDialog.Builder(getActivity()).setItems(arrs,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                case 1:
                                case 2:
                                    Intent intent = new Intent(getActivity(), FoodCalorieActivity.class);
                                    intent.putExtra("type",which);
                                    intent.putExtra("name",arrs[which]);
                                    startActivityForResult(intent, REQUEST_CODE);
                                    break;
                                default:
                                    break;
                            }
                        }
                    }).create().show();

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), FoodCalorieActivity.class);

        switch (v.getId()){
            case R.id.btn_snack:
                showAddFoodDialog();
                break;
            case R.id.btn_breakfast:
                if (ll_add.getVisibility()==View.VISIBLE){
                    ll_add.setVisibility(View.GONE);
                }else {
                    ll_add.setVisibility(View.VISIBLE);
                    loadFood(0);
                }
                break;
            case R.id.btn_launch:
                if (ll_add2.getVisibility()==View.VISIBLE){
                    ll_add2.setVisibility(View.GONE);
                }else {
                    ll_add2.setVisibility(View.VISIBLE);
                    loadFood(1);
                }
                break;
            case R.id.btn_dinner:
                if (ll_add3.getVisibility()==View.VISIBLE){
                    ll_add3.setVisibility(View.GONE);
                }else {
                    ll_add3.setVisibility(View.VISIBLE);
                    loadFood(2);
                }
                break;
            case R.id.ll_add:
                intent.putExtra("type",0);
                intent.putExtra("name","아침");

                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.ll_add2:
                intent.putExtra("type",1);
                intent.putExtra("name","점심");

                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.ll_add3:
                intent.putExtra("type",2);
                intent.putExtra("name","저녁");
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.search_input:
                startActivity( new Intent(getActivity(), FoodSearchActivity.class));
                break;
        }
    }

    @Override
    public void callbackNetStatus(int type, boolean status) {
        Log.e(LogUtils.funAndLine(new Exception()),"type:"+type);
        if (status){
            loadFood(type);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                int type = data.getIntExtra("result",0);  // 获取返回的信息
                // 处理返回的信息
                loadFood(type);
            }
        }
    }

}