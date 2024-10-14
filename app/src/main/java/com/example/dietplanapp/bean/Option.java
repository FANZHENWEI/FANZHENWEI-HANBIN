package com.example.dietplanapp.bean;

import java.io.Serializable;

public class Option implements Serializable {

    private String id;
    private int iv;


    public Option() {
        super();
    }

    public Option(String id, int iv) {
        super();
        this.id=id;
        this.iv=iv;


    }


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setIv(int iv) {
        this.iv = iv;
    }

    public int getIv() {
        return iv;
    }




}
