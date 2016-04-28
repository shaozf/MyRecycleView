package com.shao.myrecycleview.listview;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

/**
 * @author Swain
 * @function 自定义Holder recycle使用
 * @date 2015/9/16
 */
public class RecycleViewHolder extends ViewHolder {

	private View mView;
	private SparseArray<View> SView;

	public RecycleViewHolder(View itemView) {
		super(itemView);
		mView = itemView;
		SView = new SparseArray<View>();
	}

	/**
	 * 通过viewById 获取控件
	 * 
	 * @param viewId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends View> T getView(int viewId) {
		View view = SView.get(viewId);
		if (null == view) {
			view = mView.findViewById(viewId);
			SView.put(viewId, view);
		}
		return (T) view;
	}

	public RecycleViewHolder setText(int viewId, String str) {
		TextView tv = getView(viewId);
		tv.setText(str);
		return this;
	}

}
