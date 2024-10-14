package com.example.dietplanapp.fragment;



import android.util.SparseArray;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.dietplanapp.R;
import com.example.dietplanapp.custom.DrawableRadioButton;


public class MainFragment extends AbsFragment implements View.OnClickListener{

    private static final int DIETPLAN = 0;
    private static final int CALORIES_QUERY = 1;
    private static final int USERINFO = 2;
    private static final int FITNESS_ASSISTANT = 3;


    private DrawableRadioButton mBtnDietplan;
    private DrawableRadioButton mBtnCaloriesQuery;
    private DrawableRadioButton mBtnUserInfo;
    private DrawableRadioButton mBtnFitnessAssistant;
    private DrawableRadioButton mBtnAnalyseAssistant;

    private int mCurKey;//当前选中的fragment的key
    private SparseArray<Fragment> mSparseArray;
    private FragmentManager mFragmentManager;
    private DietPlanFragment mDietPlanFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void main() {
        mBtnDietplan = mRootView.findViewById(R.id.btn_dietplan);
        mBtnCaloriesQuery = mRootView.findViewById(R.id.btn_calories_query);
        mBtnUserInfo = mRootView.findViewById(R.id.btn_userinfo);
        mBtnFitnessAssistant = mRootView.findViewById(R.id.btn_fitness_assistant);
        mBtnAnalyseAssistant = mRootView.findViewById(R.id.btn_analyse);

        mBtnDietplan.setOnClickListener(this);
        mBtnCaloriesQuery.setOnClickListener(this);
        mBtnUserInfo.setOnClickListener(this);
        mBtnFitnessAssistant.setOnClickListener(this);
        mBtnAnalyseAssistant.setOnClickListener(this);

        mSparseArray = new SparseArray<>();
        mDietPlanFragment = new DietPlanFragment();
        mSparseArray.put(DIETPLAN,mDietPlanFragment);
        mSparseArray.put(CALORIES_QUERY,new CaloriesQueryFragment());
        mSparseArray.put(USERINFO,new UserFragment());
        mSparseArray.put(FITNESS_ASSISTANT,new AssistantFragment());
        mSparseArray.put(FITNESS_ASSISTANT,new AnalyseFragment());

        mCurKey = DIETPLAN;
        mFragmentManager = getChildFragmentManager();
        FragmentTransaction tx = mFragmentManager.beginTransaction();
        for (int i = 0, size = mSparseArray.size(); i < size; i++) {
            Fragment fragment = mSparseArray.valueAt(i);
            tx.add(R.id.replaced, fragment);
            if (mSparseArray.keyAt(i) == mCurKey) {
                tx.show(fragment);
            } else {
                tx.hide(fragment);
            }
        }
        tx.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_dietplan:
                toggleDietplan();
                break;
            case R.id.btn_calories_query:
                toggleCaloriesQuery();
                break;
            case R.id.btn_userinfo:
                toggleUserInfo();
                break;
            case R.id.btn_fitness_assistant:
                toggleFitnessAssistant();
                break;
            case R.id.btn_analyse:
                toggleAnalyseAssistant();
                break;
        }
    }

    /**
     * 切换到饮食计划页面
     */
    private void toggleDietplan() {
        toggle(DIETPLAN);
        if (mBtnDietplan != null) {
            mBtnDietplan.doToggle();
        }
    }

    /**
     * 切换到热量查询页面
     */
    private void toggleCaloriesQuery() {
        toggle(CALORIES_QUERY);
        if (mBtnCaloriesQuery != null) {
            mBtnCaloriesQuery.doToggle();
        }
    }

    /**
     * 切换到个人中心页面
     */
    private void toggleUserInfo() {
        toggle(USERINFO);
        if (mBtnUserInfo != null) {
            mBtnUserInfo.doToggle();
        }
    }

    /**
     * 切换到健康助手页面
     */
    private void toggleFitnessAssistant() {
        toggle(FITNESS_ASSISTANT);
        if (mBtnFitnessAssistant!= null) {
            mBtnFitnessAssistant.doToggle();
        }
    }

    private void toggleAnalyseAssistant() {
        toggle(FITNESS_ASSISTANT);
        if (mBtnAnalyseAssistant!= null) {
            mBtnAnalyseAssistant.doToggle();
        }
    }


    private void toggle(int key) {
        if (key == mCurKey) {
            return;
        }
        mCurKey = key;
        FragmentTransaction tx = mFragmentManager.beginTransaction();
        for (int i = 0, size = mSparseArray.size(); i < size; i++) {
            Fragment fragment = mSparseArray.valueAt(i);
            if (mSparseArray.keyAt(i) == mCurKey) {
                tx.show(fragment);
            } else {
                tx.hide(fragment);
            }
        }
        tx.commitAllowingStateLoss();
    }
}