package com.duckydev.mvpdagger.util.subview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.duckydev.mvpdagger.util.adapter.FeaturedPagerAdapter;
import com.viewpagerindicator.CirclePageIndicator;

/**
 * Created by duckyng on 1/2/2018.
 */

public class FeatureCirclePageIndicator extends CirclePageIndicator {
    private int mCourceSize;

    public FeatureCirclePageIndicator(Context context) {
        super(context);
    }

    public FeatureCirclePageIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FeatureCirclePageIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mViewPager != null) {
            int count = this.mViewPager.getAdapter().getCount() / FeaturedPagerAdapter.LOOP_COUNT;
            if (count == 0) {
                return;
            }

            if (mCurrentPage >= count) {
                setCurrentItem(count - 1);
                return;
            }

            int width;
            int paddingLeft;
            int paddingRight;
            int paddingTop;

            if (mOrientation == 0) {
                width = getWidth();
                paddingLeft = getPaddingLeft();
                paddingRight = getPaddingRight();
                paddingTop = getPaddingTop();
            } else {
                width = getHeight();
                paddingLeft = getPaddingTop();
                paddingRight = getPaddingBottom();
                paddingTop = getPaddingLeft();
            }

            float f = mRadius * 3.0f;
            float f2 = mRadius + (float) paddingTop;
            float f3 = ((float) paddingLeft + mRadius);
            if (mCentered) {
                f3 += (((float) ((width - paddingLeft) - paddingRight)) / 2.0f) - ((((float) count) * f) / 2.0f);
            }

            float f4 = mRadius;
            if (mPaintStroke.getStrokeWidth() > 0.0f) {
                f4 -= mPaintStroke.getStrokeWidth() / 2.0f;
            }

            for (int i = 0; i < count; i++) {
                float f5;
                float f6 = (((float) i) * f) + f3;
                if (this.mOrientation == 0) {
                    f5 = f6;
                    f6 = f2;
                } else {
                    f5 = f2;
                }
                if (this.mPaintPageFill.getAlpha() > 0) {
                    canvas.drawCircle(f5, f6, f4, this.mPaintPageFill);
                }
                if (f4 != this.mRadius) {
                    canvas.drawCircle(f5, f6, this.mRadius, this.mPaintStroke);
                }
            }
            f4 = ((float) (this.mSnap ? this.mSnapPage : this.mCurrentPage)) * f;
            if (!this.mSnap) {
                f4 += this.mPageOffset * f;
            }
            if (this.mOrientation == 0) {
                f4 += f3;
            } else {
                float f7 = f4 + f3;
                f4 = f2;
                f2 = f7;
            }
            if (this.mCurrentPage <= 0 || this.mViewPager.getCurrentItem() % mCourceSize != 0) {
                canvas.drawCircle(f4, f2, this.mRadius, this.mPaintFill);
            } else {
                canvas.drawCircle(f3, f2, this.mRadius, this.mPaintFill);
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mCurrentPage = position % mCourceSize;
        mPageOffset = positionOffset;
        invalidate();
        if (mListener != null) {
            mListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (mSnap || mScrollState == 0) {
            mCurrentPage = position % mCourceSize;
            mSnapPage = position;
            invalidate();
        }
        if (mListener != null) {
            mListener.onPageSelected(position);
        }
    }

    public void setCourceSize(int courseSize) {
        mCourceSize = courseSize;
    }
}
