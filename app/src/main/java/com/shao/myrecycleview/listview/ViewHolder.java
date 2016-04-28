package com.shao.myrecycleview.listview;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author Swain
 * @function 自定义Holder listview使用
 * @date 2015/9/16
 */
public class ViewHolder {

	private SparseArray<View> SView;
	private int position;
	private View mView;

	public ViewHolder(Context mContext, ViewGroup parent, int layouId, int position)
	{
		this.position = position;
		this.SView = new SparseArray<View>();
		mView = LayoutInflater.from(mContext).inflate(layouId, parent, false);
		mView.setTag(this);
	}

	public static ViewHolder get(Context mContext, View view, ViewGroup parent, int layouId, int position) {
		if (null == view)
			return new ViewHolder(mContext, parent, layouId, position);
		else
		{
			ViewHolder holder = (ViewHolder) view.getTag();
			holder.position = position;
			return holder;
		}
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
		if (null == view)
		{
			view = mView.findViewById(viewId);
			SView.put(viewId, view);
		}
		return (T) view;
	}

	public View getCovertView() {
		return mView;
	}

	/** 文本设置内容 */
	public ViewHolder setText(int viewId, String str) {
		TextView tv = getView(viewId);
		tv.setText(str);
		return this;
	}

	public int getPosition() {
		return position;
	}

}
