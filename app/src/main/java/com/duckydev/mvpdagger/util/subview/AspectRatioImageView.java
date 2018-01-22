package com.duckydev.mvpdagger.util.subview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by duckyng on 1/2/2018.
 */

public class AspectRatioImageView extends ImageView {

    public static final float RATIO_16_9 = 0.5625f;
    public static final float RATIO_4_3 = 0.75f;
    private float maAspectRatio;

    public AspectRatioImageView(Context context) {
        super(context);
    }

    public AspectRatioImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        maAspectRatio = RATIO_16_9;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = getMeasuredWidth();
        setMeasuredDimension(measuredWidth, (int) (((float) measuredWidth) * maAspectRatio));
    }

    public void setMaAspectRatio(float maAspectRatio) {
        this.maAspectRatio = maAspectRatio;
        invalidate();
    }
}
