package com.shao.myrecycleview.listview;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Swain
 * @function 自定义adapter recycleview使用
 * @date 2015/9/16
 */
public abstract class RecyclerUniversalAdapter<T> extends RecyclerView.Adapter<RecycleViewHolder> {
    protected Context context;
    protected List<T> list;
    private int layoutId;
    private RecyclerView.Adapter mAdapter;

    private ArrayList<View> mHeaderViews = new ArrayList<>();

    private ArrayList<View> mFootViews = new ArrayList<>();

    static final ArrayList<View> EMPTY_INFO_LIST = new ArrayList<View>();

    /**
     * addHead to recyclerview
     *
     * @param layoutId
     */
    public void addHeadViewId(int layoutId, ViewGroup root) {
        mHeaderViews.add(LayoutInflater.from(context).inflate(layoutId, root, false));
    }

    /**
     * addFoot to RecyclerView
     *
     * @param layoutId
     */
    public void addFootViewId(int layoutId, ViewGroup root) {
        mFootViews.add(LayoutInflater.from(context).inflate(layoutId, root, false));
    }

    /**
     * addFoot to RecyclerView
     *
     * @param view
     */
    public void addFootView(View view) {
        mFootViews.add(view);
    }


    public RecyclerUniversalAdapter(Context context, List<T> list, int layoutId) {
        this.context = context;
        this.list = list;
        this.layoutId = layoutId;
    }

    public void addListMore(List<T> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void addListItem(T list) {
        this.list.add(list);
        notifyItemInserted(getItemCount() - getFootersCount());
    }

    public void delListItem(int positation) {
        if (null == list || list.size() < 1)
            return;
        this.list.remove(positation - getHeadersCount());
        notifyDataSetChanged();
    }

    public void delList() {
        if (null == list || list.size() < 1)
            return;
        this.list.clear();
        notifyDataSetChanged();
    }

    public void setList(List<T> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    /**
     * 删除头布局
     */
    public void removeHeaderView(int position) {
        if (position < getHeadersCount()) {
            mHeaderViews.remove(position);
            notifyItemChanged(position);
        }

    }

    /**
     * 删除尾布局
     */
    public void removeFootView(int position) {
        if (position > getItemCount() - getFootersCount() && position < getItemCount()) {
            mFootViews.remove(position - getItemCount() + getFootersCount());
            notifyItemChanged(position);
        }

    }

    public List<T> getList() {
        if (null != this.list)
            return this.list;
        else return new ArrayList<>();
    }

    public int getHeadersCount() {
        return mHeaderViews.size();
    }

    public int getFootersCount() {
        return mFootViews.size();
    }

    public int getBoadysCount() {
        return null == list ? 0 : list.size();
    }

    /**
     * 设置view类型,
     */
    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public int getItemCount() {
        return null == list ? (getHeadersCount() + getFootersCount()) : (getBoadysCount() + getHeadersCount() + getFootersCount());
    }

    @Override
    public void onBindViewHolder(RecycleViewHolder holder, int position) {
        if (position < getHeadersCount())
            setHeadDate(holder, position);
        else if (position >= getHeadersCount() && position < getHeadersCount() + +getBoadysCount())
            setDate(holder, list.get(position - getHeadersCount()), position);
        else
            setFootDate(holder, position);
    }

    @Override
    public RecycleViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        if (position < getHeadersCount())//头布局
            return new RecycleViewHolder(mHeaderViews.get(position));
        else if (position >= getHeadersCount() && position < getHeadersCount() + getBoadysCount())//boday布局
            return new RecycleViewHolder(LayoutInflater.from(context).inflate(layoutId, parent, false));
        else
            return footViewHoder(position);//尾布局
    }

    /**
     * 添加尾部布局
     */
    private RecycleViewHolder footViewHoder(int position) {
        int viewPosition = position - getHeadersCount() - getBoadysCount();
        if (viewPosition >= 0 && viewPosition < getFootersCount()) {
            return new RecycleViewHolder(mFootViews.get(viewPosition));
        } else {
            return null;
        }

    }

    /**
     * 添加头部布局
     */
    private RecycleViewHolder headViewHolder(int position) {

        return null;
    }


    public abstract void setDate(RecycleViewHolder holder, T t, int position);

    public abstract void setHeadDate(RecycleViewHolder holder, int position);

    public abstract void setFootDate(RecycleViewHolder holder, int position);
}
