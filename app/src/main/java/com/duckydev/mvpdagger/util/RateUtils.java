package com.duckydev.mvpdagger.util;

import android.app.Activity;
import android.content.Context;

import com.duckydev.mvpdagger.util.wiget.DialogManager;
import com.duckydev.mvpdagger.util.wiget.RateManager;


public class RateUtils {
    public void checkRate(Activity context) {
        SharePreferenceUtils sputil = new SharePreferenceUtils(context);
        int count = sputil.getRateCount();
        if (count == 4 || count == 9) {
            new RateManager(context, new DialogManager()).showRate();
            sputil.setRateCount(count + 1);
        } else if (count <= 10) {
            sputil.setRateCount(count + 1);
        }


    }

    public void later(Context context) {
        new SharePreferenceUtils(context).setRateCount(0);
    }
}
