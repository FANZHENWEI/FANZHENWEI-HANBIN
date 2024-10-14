package com.example.dietplanapp;

import android.app.Activity;
import android.app.Application;

import com.example.dietplanapp.bean.Food;
import com.example.dietplanapp.bean.FoodDetails;
import com.example.dietplanapp.utils.SPUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppContext extends Application {
    public static AppContext sInstance;
    private List<Activity> mActivitieArray;

    public static Map<String,FoodDetails> foodMap  = new HashMap<>();
    @Override
    public void onCreate() {
        super.onCreate();
        mActivitieArray=new ArrayList<>();
        SPUtils.init(this);
       List<Food> foodList= SPUtils.getDataList("food_zao", Food.class);
       if (foodList.size()==0){
           Food food=new Food("구기자",258.00f,100,"아침 추천");
           foodList.add(food);
           food=new Food("떡",56.00f,100,"아침 추천");
           foodList.add(food);
           food=new Food("우유",490.00f,100,"아침 추천");
           foodList.add(food);
           food=new Food("량피",258.00f,100,"아침 추천");
           foodList.add(food);
       }
       SPUtils.setDataList("food_zao",foodList);

       foodList= SPUtils.getDataList("food_wu", Food.class);
        if (foodList.size()==0){
            Food food=new Food("파우더(유송)",251.00f,100,"점심 추천");
            foodList.add(food);
            food=new Food("국화(마른)",242.00f,100,"점심 추천");
            foodList.add(food);
            food=new Food("왕새우",85.00f,100,"점심 추천");
            foodList.add(food);
            food=new Food("농어",105.00f,100,"점심 추천");
            foodList.add(food);
        }
        SPUtils.setDataList("food_wu",foodList);

        foodList= SPUtils.getDataList("food_wan", Food.class);
        if (foodList.size()==0){
            Food food=new Food("번데기",230.00f,100,"저녁 추천");
            foodList.add(food);
            food=new Food("고영양단백질분말",197.00f,100,"저녁 추천");
            foodList.add(food);
            food=new Food("쌀국수",376.00f,100,"저녁 추천");
            foodList.add(food);
            food=new Food("계피나무",199.00f,100,"저녁 추천");
            foodList.add(food);
        }
        SPUtils.setDataList("food_wan",foodList);


        List<FoodDetails> dataList= SPUtils.getDataList("foodlist_main", FoodDetails.class);
        if (dataList.size()==0){
            FoodDetails food=new FoodDetails("밥","추천하다",116.00f,25.90f,2.60f,0.30f,"0");
            dataList.add(food);
            food=new FoodDetails("만두","적당히 먹다",221.00f,47.00f,7.00f,1.10f,"0");
            dataList.add(food);
        }
        SPUtils.setDataList("foodlist_main",dataList);

        dataList= SPUtils.getDataList("foodlist_meat", FoodDetails.class);
        if (dataList.size()==0){
            FoodDetails food=new FoodDetails("달걀","추천하다",144.00f,2.80f,13.30f,8.80f,"1");
            dataList.add(food);
            food=new FoodDetails("돼지고기","적당히 먹다",143.00f,1.500f,20.30f,6.20f,"1");
            dataList.add(food);
        }
        SPUtils.setDataList("foodlist_meat",dataList);

        dataList= SPUtils.getDataList("foodlist_milk", FoodDetails.class);
        if (dataList.size()==0){
            FoodDetails food=new FoodDetails("우유","추천하다",54.00f,3.40f,3.00f,3.20f,"2");
            dataList.add(food);
            food=new FoodDetails("요구르트","추천하다",64.00f,9.00f,2.70f,1.90f,"2");
            dataList.add(food);
        }
        SPUtils.setDataList("foodlist_milk",dataList);

        dataList= SPUtils.getDataList("foodlist_fruit", FoodDetails.class);
        if (dataList.size()==0){
            FoodDetails food=new FoodDetails("사과","추천하다",52.00f,13.50f,0.20f,0.20f,"3");
            dataList.add(food);
            food=new FoodDetails("바나나","추천하다",91.00f,22,1.40f,0.20f,"3");
            dataList.add(food);
        }
        SPUtils.setDataList("foodlist_fruit",dataList);

        dataList= SPUtils.getDataList("foodlist_fit", FoodDetails.class);
        if (dataList.size()==0){
            FoodDetails food=new FoodDetails("훈툰","추천하다",646.00f,225.90f,82.60f,20.30f,"4");
            dataList.add(food);
            food=new FoodDetails("완두","적당히 먹다",702.00f,147.00f,37.00f,10.10f,"4");
            dataList.add(food);
        }
        SPUtils.setDataList("foodlist_fit",dataList);

        dataList= SPUtils.getDataList("foodlist_other", FoodDetails.class);
        if (dataList.size()==0){
            FoodDetails food=new FoodDetails("구기자","추천하다",258.00f,64.10f,13.90f,1.50f,"5");
            dataList.add(food);
            food=new FoodDetails("글루텐","적당히 먹다",490.00f,40.40f,26.90f,25.10f,"5");
            dataList.add(food);
        }
        SPUtils.setDataList("foodlist_other",dataList);

        foodMap.put("orange",new FoodDetails("orange",
                "Oranges are suitable for consumption half an hour after meals, as they help with digestion and sobering up",
                47.0f,11.75f,0.94f,0.12f,"86.75"));
        foodMap.put("banana",new FoodDetails("banana",
                "Bananas are suitable for consumption after meals or in combination with other foods. Try not to eat bananas on an empty stomach",89.0f,22.84f,1.09f,0.33f,"74.91"));
        foodMap.put("noodles",new FoodDetails("noodles",
                "Xin Lamian Noodles is nutritious and high in calories. Take proper amount of it and don't eat too much",
                460.0f,65.0f,9.0f,17.0f,"5"));

        sInstance = this;

    }

    public void addActiviy(Activity activity){
        mActivitieArray.add(activity);
    }

    public void removeActivity(Activity activity){
        mActivitieArray.remove(activity);
    }

    public void clearActivitySet(){
        for(Activity activity:mActivitieArray){
            activity.finish();
        }
    }
}
