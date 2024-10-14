package com.example.dietplanapp.activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.example.dietplanapp.R;
import com.example.dietplanapp.utils.DateUtils;
import com.example.dietplanapp.utils.SPUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.net.ssl.SSLPeerUnverifiedException;

import butterknife.BindView;

/**

 */
public class SetActivity extends BaseActivity {

    protected OptionsPickerView pvCustomOptions;
    protected OptionsPickerView pvTargetOptions;

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_tittle)
    TextView tvTittle;
    @BindView(R.id.tv_base_some)
    TextView tvBaseSome;
    @BindView(R.id.iv_white_search)
    ImageButton ivWhiteSearch;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    @BindView(R.id.iv_male)
    ImageView iv_male;
    @BindView(R.id.iv_female)
    ImageView iv_female;

    @BindView(R.id.et_height)
    EditText et_height;
    @BindView(R.id.et_weight)
    EditText et_weight;
    @BindView(R.id.et_age)
    EditText et_age;
    @BindView(R.id.et_run)
    EditText et_run;
    @BindView(R.id.et_target)
    EditText et_target;
    @BindView(R.id.btn_save)
    Button btn_save;


    protected List<String> runItem=new ArrayList<>();

    protected List<String> targetItem=new ArrayList<>();

    int selectsex=0;
    int selectrun=0;
    int selectTarget=0;

    @Override
    public void initRootView() {
        setContentView(R.layout.activity_set);
    }

    @Override
    public void initView() {
        iv_male.setBackground(getResources().getDrawable(R.drawable.ic_male_select));
        tvTittle.setText("신체 데이터");
    }

    @Override
    public void initData() {
        runItem.add("가벼운 활동량(오래 앉아있음, 움직이지 않음)");
        runItem.add("가벼운 활동량(주 1-3일 운동)");
        runItem.add("중간 활동량(중강도 운동, 주 6-7일 운동)");
        runItem.add("적극적 활동량(고강도 운동, 주 6-7일 운동)");
        runItem.add("매우 활동적 (매일 고강도 훈련운동과 스포츠 근로자)");
        targetItem.add("근육을 늘이다");
        targetItem.add("지방을 줄이다");
        targetItem.add("체형을 유지하다");
        int sex=SPUtils.getInt("sex",0);
        if (sex==0){
            iv_male.setBackground(getResources().getDrawable(R.drawable.ic_male_select));
            iv_female.setBackground(getResources().getDrawable(R.drawable.ic_female_unselect));
        }else{
            iv_male.setBackground(getResources().getDrawable(R.drawable.ic_male_unselect));
            iv_female.setBackground(getResources().getDrawable(R.drawable.ic_female_select));
        }
        et_height.setText(SPUtils.getInt("height")+"");
        et_weight.setText(SPUtils.getInt("weight")+"");
        et_age.setText(SPUtils.getString("age")+"");
        et_run.setText(runItem.get(SPUtils.getInt("run"))+"");
        et_target.setText(targetItem.get(SPUtils.getInt("target"))+"");


    }

    @Override
    public void initListener() {
        ivBack.setOnClickListener(this);
        iv_female.setOnClickListener(this);
        iv_male.setOnClickListener(this);
        et_run.setOnClickListener(this);
        et_age.setOnClickListener(this);
        et_target.setOnClickListener(this);
        btn_save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //关闭
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_male:
                iv_male.setBackground(getResources().getDrawable(R.drawable.ic_male_select));
                iv_female.setBackground(getResources().getDrawable(R.drawable.ic_female_unselect));
                selectsex=0;
                break;
            case R.id.iv_female:
                iv_male.setBackground(getResources().getDrawable(R.drawable.ic_male_unselect));
                iv_female.setBackground(getResources().getDrawable(R.drawable.ic_female_select));
                selectsex=1;
                break;
            case R.id.et_age:
                showDateDialog();
                break;
            case R.id.et_run:
                showMoveSelector();
                break;
            case R.id.et_target:
                showTargetSelector();
                break;
            case R.id.btn_save:
                saveData();
                break;
            default:
                break;
        }
    }

    private void saveData() {
        SPUtils.putInt("sex",selectsex);
        SPUtils.putInt("height",Integer.parseInt(et_height.getText().toString()));
        SPUtils.putInt("weight",Integer.parseInt(et_weight.getText().toString()));
        SPUtils.putString("age",et_age.getText().toString());
        SPUtils.putInt("run",selectrun);
        SPUtils.putInt("target",selectTarget);
        Toast.makeText(this,"제출 성공",Toast.LENGTH_SHORT).show();
        finish();
    }

    /**
     * 显示运动习惯选择器
     */
    public void showMoveSelector() {
        pvCustomOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                selectrun = options1;
                et_run.setText(runItem.get(options1));
            }
        })
                .build();
        pvCustomOptions.setPicker(runItem);
        pvCustomOptions.show();
    }
    /**
     * 显示运动目标选择器
     */
    public void showTargetSelector() {
        pvTargetOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                selectTarget = options1;
                et_target.setText(targetItem.get(options1));
            }
        })
                .build();
        pvTargetOptions.setPicker(targetItem);
        pvTargetOptions.show();
    }

    private void showDateDialog() {
        new TimePickerView.Builder(mContext, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                et_age.setText(DateUtils.date2Str(date, "yyyy-MM-dd"));
            }
        }).setRangDate(null, Calendar.getInstance())
                .setType(new boolean[]{true, true, true, false, false, false})
                .build()
                .show();
    }
}
