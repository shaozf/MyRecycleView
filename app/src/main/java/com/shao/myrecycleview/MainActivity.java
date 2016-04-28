package com.shao.myrecycleview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.shao.myrecycleview.listview.MyRecycleView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MyRecycleView mvMv;
    private List<String> mList = new ArrayList<>();
    private MainAdaper mAdapter;
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mvMv = (MyRecycleView) findViewById(R.id.mv_mv);
        for (i = 0; i < 30; i++)
            mList.add(i + "");

        mAdapter = new MainAdaper(this, mList, R.layout.item_headview);
        mAdapter.addHeadViewId(R.layout.item_headview, mvMv);
        mvMv.setLoadingFootView(mAdapter);
        mvMv.setAdapter(mAdapter);
        mvMv.setLoadingNoMoreDate();
    }
}
