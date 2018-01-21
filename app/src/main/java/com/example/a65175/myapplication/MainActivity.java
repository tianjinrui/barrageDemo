package com.example.a65175.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


        BarrageView barrageview;
        ArrayList<Barrage> date;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.barrageview_test);
            initView();
        }

        private void initView() {
            date = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                date.add(new Barrage(
                        "测试弹幕" + i, "http://pic.818today.com/imgsy/image/2016/0215/6359114592207963687677523.jpg"));
            }

            barrageview = (BarrageView) findViewById(R.id.barrageview);
            Log.d("xiaojingyu", date.size() + "");
            barrageview.setSentenceList(date);
        }
    }

