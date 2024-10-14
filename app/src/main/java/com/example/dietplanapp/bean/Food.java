package com.example.dietplanapp.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

public class Food extends BmobObject implements Serializable {

    public String name;
    public float kcal;
    public int num;
    public String type;

    public Food() {
        super();
    }


    public Food(String name,float Kcal,int num,String type) {
        super();
        this.name=name;
        this.kcal =Kcal;
        this.num=num;
        this.type=type;
    }






}
