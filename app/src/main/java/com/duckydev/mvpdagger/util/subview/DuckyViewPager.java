package com.duckydev.mvpdagger.util.subview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.duckydev.mvpdagger.R;

/**
 * Created by duckyng on 1/2/2018.
 */

public class DuckyViewPager extends ViewPager {
    public DuckyViewPager(Context context) {
        super(context);
    }

    public DuckyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int calHeight = 0;
        for (int i = 0; i < getChildCount(); i++) {
            int height;
            View childAt = getChildAt(i);
            View findViewById = childAt.findViewById(R.id.feature_img);
            if (findViewById != null) {
                findViewById.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                height = findViewById.getHeight();
            } else {
                childAt.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                height = childAt.getMeasuredHeight();
            }

            if (height > 0) {
                calHeight = height;
            }
        }

        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(calHeight, MeasureSpec.EXACTLY));
    }
}
