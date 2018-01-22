package com.duckydev.mvpdagger.util.wiget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class CommonAdapter<T> extends BaseAdapter {
    protected final int mItemLayoutId;
    protected Context mContext;
    protected List<T> mDatas;

    public CommonAdapter(Context context, List<T> datas, int itemLayoutId) {
        mItemLayoutId = itemLayoutId;
        mContext = context;
        mDatas = datas;
    }

    public abstract void convert(ViewHolder viewholder, T t, int position);

    public void refreshListView(List<T> mDatasList) {
        this.mDatas = mDatasList;
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.mDatas.size();
    }

    public T getItem(int position) {
        return this.mDatas.get(position);
    }

    public long getItemId(int positon) {
        return (long) positon;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = getViewHolder(position, convertView, parent);
        convert(viewHolder, getItem(position), position);
        return viewHolder.getConvertView();
    }

    private ViewHolder getViewHolder(int position, View convertView, ViewGroup parent) {
        return ViewHolder.get(this.mContext, convertView, parent, this.mItemLayoutId, position);
    }


}
