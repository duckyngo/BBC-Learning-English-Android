package com.duckydev.mvpdagger.util.wiget;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ViewHolder {
    private final SparseArray<View> mViews = new SparseArray<>();
    private Context mContext;
    private View mConvertView;
    private int mPosition;

    private ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        mContext = context;
        mPosition = position;
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        mConvertView.setTag(this);
    }

    public static ViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new ViewHolder(context, parent, layoutId, position);
        }

        return (ViewHolder) convertView.getTag();
    }

    public View getConvertView() {
        return this.mConvertView;
    }

    public <T extends View> T getView(int viewId) {
        View view = (View) mViews.get(viewId);
        if (view != null) {
            return (T) view;
        }
        view = mConvertView.findViewById(viewId);
        mViews.put(viewId, view);
        return (T) view;
    }

    public ViewHolder setText(int viewId, String text) {
        if (TextUtils.isEmpty(text)) {
            ((TextView) getView(viewId)).setText("");
        } else {
            ((TextView) getView(viewId)).setText(text);
        }
        return this;
    }

    public ViewHolder setImageResource(int viewId, int drawableId) {
        ((ImageView) getView(viewId)).setImageResource(drawableId);
        return this;
    }

    public ViewHolder setImageBitmap(int viewId, Bitmap bm) {
        ((ImageView) getView(viewId)).setImageBitmap(bm);
        return this;
    }

    public ViewHolder setImageByUrl(int viewId, String url) {
        if (!TextUtils.isEmpty(url)) {
            Picasso.with(this.mContext).load(url).into((ImageView) getView(viewId));
        }
        return this;
    }

    public int getPosition() {
        return this.mPosition;
    }
}
