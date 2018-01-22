package com.duckydev.mvpdagger.util.subview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ScrollingView;
import android.util.AttributeSet;
import android.view.View;

public class FlingBehavior extends AppBarLayout.Behavior {

    public FlingBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onNestedFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull AppBarLayout child, @NonNull View target, float velocityX, float velocityY, boolean consumed) {
        boolean consumed1;
        if (target instanceof ScrollingView) {
            consumed1 = velocityY > 0.0f || ((ScrollingView) target).computeVerticalScrollOffset() > 0;
        } else {
            consumed1 = consumed;
        }
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed1);
    }
}
