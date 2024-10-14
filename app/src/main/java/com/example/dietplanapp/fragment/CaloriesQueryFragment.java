package com.example.dietplanapp.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dietplanapp.R;
import com.example.dietplanapp.activity.FoodListActivity;
import com.example.dietplanapp.adapter.CatalogListAdapter;
import com.example.dietplanapp.bean.Option;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CaloriesQueryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CaloriesQueryFragment extends Fragment implements AdapterView.OnItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CaloriesQueryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CaloriesQueryFragment.
     */


    // 图片封装为一个数组
    private int[] icon = { R.drawable.goods_1, R.drawable.goods_2,
            R.drawable.goods_3, R.drawable.goods_4, R.drawable.goods_5,
            R.drawable.goods_6};

    private String[] iconName = { "주식\nStaple Food", "고기\nMeat", "유류\nDairy", "과일과 야채\nFruits and Vegetables", "피트니스 식사\nFitness Food", "기타\nOther Food" };


    CatalogListAdapter adapter;
    private GridView gview;
    private List<Option> data_list;

    // TODO: Rename and change types and number of parameters
    public static CaloriesQueryFragment newInstance(String param1, String param2) {
        CaloriesQueryFragment fragment = new CaloriesQueryFragment();
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
        View view = inflater.inflate(R.layout.fragment_calories_query, container, false);
        gview = (GridView)view.findViewById(R.id.gview);
        gview.setOnItemClickListener(this);
        load();
        return view;
    }

    private void load() {

        //新建List
        data_list = new ArrayList<Option>();
        //获取数据
        getData();
        adapter = new CatalogListAdapter(data_list);
        gview.setAdapter(adapter);
    }
    public List<Option> getData() {

        for (int i = 0; i < iconName.length; i++) {
            Option puke = new Option(iconName[i],icon[i]);

            data_list.add(puke);
        }

        return data_list;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView tv_note_id = (TextView) view.findViewById(R.id.text);
        String item_id = tv_note_id.getText().toString();
        Toast.makeText(getActivity(),"选择了："+item_id,Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getActivity(), FoodListActivity.class);
        intent.putExtra("name", item_id);
        intent.putExtra("type",position);
        startActivity(intent);

    }

   /* private Handler mHandler = new Handler(){

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Log.v(LogUtils.filename(new Exception()), LogUtils.funAndLine(new Exception())+data_list.size());
                    CatalogListAdapter adapter = new CatalogListAdapter(data_list);
                    gview.setAdapter(adapter);
                    break;
                default:
                    break;
            }
        }

    };
*/

}