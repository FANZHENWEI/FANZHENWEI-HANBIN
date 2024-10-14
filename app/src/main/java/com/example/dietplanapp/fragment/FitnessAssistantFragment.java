package com.example.dietplanapp.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dietplanapp.R;
import com.example.dietplanapp.activity.ProductDetailsActivity;
import com.example.dietplanapp.adapter.LeftListAdapter;
import com.example.dietplanapp.adapter.RightListAdapter;
import com.example.dietplanapp.bean.ProductInfo;
import com.example.dietplanapp.db.DataService;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FitnessAssistantFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FitnessAssistantFragment extends Fragment {
    private View rootView;

    private RecyclerView leftRecyclerView;
    private RecyclerView rightRecyclerView;
    private LeftListAdapter mLeftListAdapter;
    private RightListAdapter mRightListAdapter;
    private List<String> leftDataList =new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FitnessAssistantFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FitnessAssistantFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FitnessAssistantFragment newInstance(String param1, String param2) {
        FitnessAssistantFragment fragment = new FitnessAssistantFragment();
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
        rootView = inflater.inflate(R.layout.fragment_fitness_assistant,container,false);
//        初始化控件
        leftRecyclerView = rootView.findViewById(R.id.leftRecyclerView);
        rightRecyclerView = rootView.findViewById(R.id.rightRecyclerView);
        return  rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        leftDataList.add("가슴");
        leftDataList.add("등");
        leftDataList.add("어깨");
        leftDataList.add("팔뚝");
        leftDataList.add("복부");
        leftDataList.add("다리");
        leftDataList.add("엉덩");
        leftDataList.add("종아리");

        mLeftListAdapter =new LeftListAdapter(leftDataList);
        leftRecyclerView.setAdapter(mLeftListAdapter);


        mRightListAdapter =new RightListAdapter();
        rightRecyclerView.setAdapter(mRightListAdapter);
//        默认加载食品数据
        mRightListAdapter.setListData(DataService.getListData(0));
//        点击加载食品详细事件
        mRightListAdapter.setOnItemClickListener(new RightListAdapter.onItemClickListener() {
            @Override
            public void onItemClick(ProductInfo productInfo, int position) {
//                跳转传值
                Intent intent = new Intent(getActivity(), ProductDetailsActivity.class);
//               intent 传递对象的时候，实体类一定要implements Serializable
                intent.putExtra("productInfo", productInfo);
                startActivity(intent);
            }
        });


//        recyclerView点击事件
        mLeftListAdapter.setmLeftListOnClickItemListener(new LeftListAdapter.LeftListOnClickItemListener() {
            @Override
            public void onItemClick(int position) {
                mLeftListAdapter.setCurrentIndex(position);
//                点击左侧切换对应列表数据
                mRightListAdapter.setListData(DataService.getListData(position));
            }
        });
    }
}