package com.example.a65175.myapplication;

import java.io.Serializable;

/**
 * Created by 65175 on 2018/1/21.
 */

public class Barrage implements Serializable {
    public Barrage(String title,String url){
        this.title = title;
        this.url = url;
    }
    private String title;
    private String url;
    public void setTitle(String title){
        this.title = title;
    }
    public String  getBarrageInfo(){
        return this.title;
    }


}
