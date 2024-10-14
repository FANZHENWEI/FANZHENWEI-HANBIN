package com.example.dietplanapp.db;


import com.example.dietplanapp.R;
import com.example.dietplanapp.bean.ProductInfo;

import java.util.ArrayList;
import java.util.List;

public class DataService {

    /**
     * 模拟提供数据
     * @param position
     * @return
     */
    public static List<ProductInfo> getListData(int position ){
        List<ProductInfo> list = new ArrayList<>();
        if (position==0){
            list.add(new ProductInfo(0, R.drawable.ic_h0,"바벨  프레스",
                    "자세한 내용은 동영상을 보고 운동을 배우세요.",12));
            list.add(new ProductInfo(1, R.drawable.ic_h1,"인클라인 벤치 프레스",
                    "자세한 내용은 동영상을 보고 운동을 배우세요.",12));
            list.add(new ProductInfo(2, R.drawable.ic_h0_1,"哑铃上斜卧推",
                    "자세한 내용은 동영상을 보고 운동을 배우세요.",12));
            list.add(new ProductInfo(3, R.drawable.ic_h1_1,"哑铃上斜锤式卧推",
                    "자세한 내용은 동영상을 보고 운동을 배우세요.",12));

        } else if (position==1) {
            list.add(new ProductInfo(4, R.drawable.ic_h2,"펜들레이 로우",
                    "자세한 내용은 동영상을 보고 운동을 배우세요.",12));
            list.add(new ProductInfo(5, R.drawable.ic_h3,"인클라인 로우",
                    "자세한 내용은 동영상을 보고 운동을 배우세요.",12));
            list.add(new ProductInfo(6, R.drawable.ic_h2_1,"器械划船1",
                    "자세한 내용은 동영상을 보고 운동을 배우세요.",12));
            list.add(new ProductInfo(7, R.drawable.ic_h3_1,"器械划船2",
                    "자세한 내용은 동영상을 보고 운동을 배우세요.",12));
        } else if (position==2) {
            list.add(new ProductInfo(8, R.drawable.ic_h4,"오버헤드 프에스",
                    "자세한 내용은 동영상을 보고 운동을 배우세요.",12));
            list.add(new ProductInfo(9, R.drawable.ic_h5,"숄더 프레스 머신",
                    "자세한 내용은 동영상을 보고 운동을 배우세요.",12));
            list.add(new ProductInfo(10, R.drawable.ic_h4_1,"哑铃肩推",
                    "자세한 내용은 동영상을 보고 운동을 배우세요.",12));
            list.add(new ProductInfo(11, R.drawable.ic_h5_1,"阿诺德肩推",
                    "자세한 내용은 동영상을 보고 운동을 배우세요.",12));
       } else if (position==3) {
            list.add(new ProductInfo(12, R.drawable.ic_h6,"바벨 바이셉 컬",
                    "자세한 내용은 동영상을 보고 운동을 배우세요.",12));
            list.add(new ProductInfo(13, R.drawable.ic_h7,"바벨 프리쳐 컬",
                    "자세한 내용은 동영상을 보고 운동을 배우세요.",12));
            list.add(new ProductInfo(14, R.drawable.ic_h6_1,"EZ杆弯举",
                    "자세한 내용은 동영상을 보고 운동을 배우세요.",12));
            list.add(new ProductInfo(15, R.drawable.ic_h7_1,"EZ杆牧师凳弯举",
                    "자세한 내용은 동영상을 보고 운동을 배우세요.",12));
        } else if (position==4) {
            list.add(new ProductInfo(16, R.drawable.ic_h8,"크런치",
                    "자세한 내용은 동영상을 보고 운동을 배우세요.",12));
            list.add(new ProductInfo(17, R.drawable.ic_h9,"싯 업",
                    "자세한 내용은 동영상을 보고 운동을 배우세요.",12));
            list.add(new ProductInfo(18, R.drawable.ic_h8_1,"卷腹(直腿)",
                    "자세한 내용은 동영상을 보고 운동을 배우세요.",12));
            list.add(new ProductInfo(19, R.drawable.ic_h9_1,"蛙式卷腹",
                    "자세한 내용은 동영상을 보고 운동을 배우세요.",12));
        } else if (position==5) {
            list.add(new ProductInfo(20, R.drawable.ic_h10,"바벨 스쿼트",
                    "자세한 내용은 동영상을 보고 운동을 배우세요.",12));
            list.add(new ProductInfo(21, R.drawable.ic_h11,"프론트 스쿼트",
                    "자세한 내용은 동영상을 보고 운동을 배우세요.",12));
            list.add(new ProductInfo(22, R.drawable.ic_h10_1,"杠铃前蹲(交叉手)",
                    "자세한 내용은 동영상을 보고 운동을 배우세요.",12));
            list.add(new ProductInfo(23, R.drawable.ic_h11_1,"杠铃半蹲",
                    "자세한 내용은 동영상을 보고 운동을 배우세요.",12));
        }else if (position==6) {
            list.add(new ProductInfo(24, R.drawable.ic_h12,"퍼즈 데드리프트",
                    "자세한 내용은 동영상을 보고 운동을 배우세요.",12));
            list.add(new ProductInfo(25, R.drawable.ic_h13,"바벨 스티프 레그 데드리프트",
                    "자세한 내용은 동영상을 보고 운동을 배우세요.",12));
            list.add(new ProductInfo(26, R.drawable.ic_h12_1,"杠铃直腿硬拉",
                    "자세한 내용은 동영상을 보고 운동을 배우세요.",12));
            list.add(new ProductInfo(27, R.drawable.ic_h13_1,"杠铃相扑硬拉",
                    "자세한 내용은 동영상을 보고 운동을 배우세요.",12));
        }else if (position==7) {
            list.add(new ProductInfo(28, R.drawable.ic_h14,"카프 레이즈",
                    "자세한 내용은 동영상을 보고 운동을 배우세요.",12));
            list.add(new ProductInfo(29, R.drawable.ic_h15,"한쪽 카프 레이즈",
                    "자세한 내용은 동영상을 보고 운동을 배우세요.",12));
            list.add(new ProductInfo(30, R.drawable.ic_h14_1,"哑铃提蹱",
                    "자세한 내용은 동영상을 보고 운동을 배우세요.",12));
            list.add(new ProductInfo(31, R.drawable.ic_h15_1,"器械提蹱1",
                    "자세한 내용은 동영상을 보고 운동을 배우세요.",12));
        }

        return list;
    }


}
