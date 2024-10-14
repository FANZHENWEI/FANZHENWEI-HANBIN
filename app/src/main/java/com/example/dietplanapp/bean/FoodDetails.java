package com.example.dietplanapp.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

public class FoodDetails extends BmobObject implements Serializable {

    public String name;
    public String advise;
    public float kcal;
    public float tanshui;
    public float danbai;
    public float zhifang;
    public String type;
    public boolean save;
    public FoodDetails() {
        super();
    }


    public FoodDetails(String name,String advise, float Kcal,float tanshui,float danbai, float zhifang, String type) {
        super();
        this.name=name;
        this.advise=advise;
        this.kcal =Kcal;
        this.tanshui=tanshui;
        this.danbai=danbai;
        this.zhifang=zhifang;
        this.type=type;
    }






}
