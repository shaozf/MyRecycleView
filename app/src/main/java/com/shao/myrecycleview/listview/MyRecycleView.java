package com.shao.myrecycleview.listview;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shao.myrecycleview.R;


/**
 * @author Swain
 * @function RecycleView
 * @date 2015/9/16
 */
public class MyRecycleView extends LinearLayout implements SwipeRefreshLayout.OnRefreshListener {
    private Context context;
    private RecyclerUniversalAdapter<?> mAdapter;//加载更多时候尾部布局使用的loading时候传入的adapter
    private TextView tvLoading;
    private ProgressBar pbLoading;
    private SwipeRefreshLayout srLayout;
    private RecyclerView rvRv;
    private OnLoadingClickLinstener onClick;
    private OnMyRecycleScrolledLinstner onScroll;
    private DividerItemDecoration dLineV, dLineH;

    public MyRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        createView();
    }

    private void createView() {
        final View view = LayoutInflater.from(context).inflate(R.layout.recylerview_load, null, false);

        srLayout = (SwipeRefreshLayout) view.findViewById(R.id.sf_layout);
        rvRv = (RecyclerView) view.findViewById(R.id.rv_rv);
        setSRType();
        rvRv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
//		rvRv.setItemAnimator(new MyItemAnimator());
        rvRv.addOnScrollListener(new OnRcvScrollListener() {
            @Override
            public void onBottom() {
                super.onBottom();
                if (null != onClick && null != mAdapter) {
                    setLoadingStart();
                    onClick.setOnRecylerViewClick(false);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 20 && null != onScroll)
                    onScroll.setup();
                else if (dy < -20 && null != onScroll) {
                    onScroll.setDown();
                }

            }
        });
        addView(view);
    }

    /**
     * 设置Adapter
     */

    public void setAdapter(Adapter<?> mAdapter) {
        rvRv.setAdapter(mAdapter);
    }

    /**
     * 水平的listview
     */
    public void setAdapterListViewHorizontal() {
        rvRv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
    }

    /**
     * 水平的GridView
     */
    public void setAdapterGridViewHorizontal(int lineNum) {
        GridLayoutManager layoutManager = new GridLayoutManager(context, lineNum);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvRv.setLayoutManager(layoutManager);
    }

    /**
     * 垂直的GridView
     */
    public void setAdapterGridViewVertical(int lineNum) {
        GridLayoutManager layoutManager = new GridLayoutManager(context, lineNum);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvRv.setLayoutManager(layoutManager);
    }

    /**
     * 设置垂直布局所使用的水平线
     */
    public MyRecycleView setLineVertical() {
        if (null != dLineV)
            return this;
        dLineV = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST);
        rvRv.addItemDecoration(dLineV);
        return this;

    }

    /**
     * 设置水平布局所使用的垂直线
     */
    public MyRecycleView setLineHorizontal() {
        if (null != dLineH)
            return this;
        dLineH = new DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL_LIST);
        rvRv.addItemDecoration(dLineH);
        return this;
    }

    /**
     * 删除水平线
     */
    public MyRecycleView delLineVertical() {
        if (null != dLineV)
            rvRv.removeItemDecoration(dLineV);
        return this;
    }

    /**
     * 删除垂直线
     */
    public MyRecycleView delLineHorizontal() {
        if (null != dLineH)
            rvRv.removeItemDecoration(dLineH);
        return this;
    }

    /**
     * 设置颜色
     */
    @SuppressWarnings("deprecation")
    void setSRType() {
//        srLayout.setColorSchemeResources(R.color.green_0a4,R.color.gray_33);
        srLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        if (null != onClick && null != srLayout) {
            onClick.setOnRecylerViewClick(true);
        }

    }

    public void setOnLoadingClick(OnLoadingClickLinstener onClick) {
        this.onClick = onClick;
    }

    public interface OnLoadingClickLinstener {
        /**
         * type:true 刷新 false 加载更多
         */
        void setOnRecylerViewClick(boolean type);
    }

    /**
     * 开始刷新
     */
    public void startLoad() {
        if (null != srLayout && !srLayout.isRefreshing())
            srLayout.setRefreshing(true);
    }

    /**
     * 停止刷新
     */
    public void stopLoad() {
        if (null != srLayout && srLayout.isRefreshing())
            srLayout.setRefreshing(false);
    }

    /**
     * 允许刷新
     */
    public void canLoad() {
        srLayout.setEnabled(true);
    }

    /**
     * 不允许刷新
     */
    public void canNotLoad() {
        srLayout.setEnabled(false);
    }

    /**
     * 返回view
     */
    public RecyclerView getView() {
        return rvRv;
    }

    /**
     * 动态设置recycle的高度
     */
    public void setRecycleHeight(int size, int lengh, int itemHight) {
        ViewGroup.LayoutParams mParams = this.getLayoutParams();
        mParams.height = (int) Math.ceil((double) size / lengh) * itemHight;
        this.setLayoutParams(mParams);
    }

    /**
     * 添加尾部加载更多布局
     */
    public void setLoadingFootView(RecyclerUniversalAdapter<?> mAdapter) {
        this.mAdapter = mAdapter;
        View view = LayoutInflater.from(context).inflate(R.layout.item_footview, this, false);
        tvLoading = (TextView) view.findViewById(R.id.tv_loading);
        pbLoading = (ProgressBar) view.findViewById(R.id.pb_loading);
        mAdapter.addFootView(view);
    }

    public void setLoadingStart() {
        tvLoading.setVisibility(View.GONE);
        pbLoading.setVisibility(View.VISIBLE);
    }

    public void setLoadingFinish() {
        tvLoading.setVisibility(View.GONE);
        pbLoading.setVisibility(View.GONE);
    }

    public void setLoadingNoMoreDate() {
        tvLoading.setVisibility(View.VISIBLE);
        pbLoading.setVisibility(View.GONE);
    }


    public void setOnMyRecycleScrolledLinstner(OnMyRecycleScrolledLinstner onScroll) {
        this.onScroll = onScroll;
    }

    public interface OnMyRecycleScrolledLinstner {
        void setup();

        void setDown();
    }

}
