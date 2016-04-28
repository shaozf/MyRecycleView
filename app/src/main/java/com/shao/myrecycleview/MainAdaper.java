package com.shao.myrecycleview;

import android.content.Context;

import com.shao.myrecycleview.listview.RecycleViewHolder;
import com.shao.myrecycleview.listview.RecyclerUniversalAdapter;

import java.util.List;

/**
 * Created by Swain1 on 16/4/28.
 * changeDate at 16/4/28
 */
public class MainAdaper extends RecyclerUniversalAdapter<String> {
    public MainAdaper(Context context, List<String> list, int layoutId) {
        super(context, list, layoutId);
    }

    @Override
    public void setDate(RecycleViewHolder holder, String s, int position) {
        holder.setText(R.id.tv_date, s + "所处位置:"+(position+""));
    }

    @Override
    public void setHeadDate(RecycleViewHolder holder, int position) {
        holder.setText(R.id.tv_date, "这真的就是头布局"+ ",所处位置:"+(position+""));

    }

    @Override
    public void setFootDate(RecycleViewHolder holder, int position) {

    }
}
