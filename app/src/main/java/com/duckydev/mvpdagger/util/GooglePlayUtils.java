package com.duckydev.mvpdagger.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;

import java.util.List;

public class GooglePlayUtils {
    public static final String MY_APP_MARKTE_SHORT_URL = "https://goo.gl/KXVi45";
    public static final String MY_APP_MARKTE_URL = "https://play.google.com/store/apps/details?id=com.duckydev.tedlang";
    public static final String MY_PROMOTE_APP_MARKTE_URL = "https://play.google.com/store/apps/details?id=com.duckydev.tedlang";
    public static final String PACKAGE_GOOGLE_PLAY = "com.android.vending";
    private static GooglePlayUtils utils;

    private GooglePlayUtils() {
    }

    public static synchronized GooglePlayUtils getInstance() {
        GooglePlayUtils googlePlayUtils;
        synchronized (GooglePlayUtils.class) {
            if (utils == null) {
                utils = new GooglePlayUtils();
            }
            googlePlayUtils = utils;
        }
        return googlePlayUtils;
    }

    public static Intent gotoPlay(Context context, String url) {
        Exception e;
        Intent intent;
        try {
            Intent intent2 = new Intent("android.intent.action.VIEW", Uri.parse(url));
            try {
                intent2.setFlags(268435456);
                intent2.setPackage("com.android.vending");
                return intent2;
            } catch (Exception e2) {
                e = e2;
                intent = intent2;
                e.printStackTrace();
                intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
                intent.setFlags(268435456);
                return intent;
            }
        } catch (Exception e3) {
            e = e3;
            e.printStackTrace();
            intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
            intent.setFlags(268435456);
            return intent;
        }
    }

    public static Intent goRateToExplore(Context context, String url) {
        Exception e;
        Intent intent;
        try {
            Intent intent2 = new Intent("android.intent.action.VIEW", Uri.parse(url));
            try {
                intent2.setFlags(268435456);
                return intent2;
            } catch (Exception e2) {
                e = e2;
                intent = intent2;
                e.printStackTrace();
                intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
                intent.setFlags(268435456);
                return intent;
            }
        } catch (Exception e3) {
            e = e3;
            e.printStackTrace();
            intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
            intent.setFlags(268435456);
            return intent;
        }
    }

    public void goToGooglePlay(Context context, String url) {
        try {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse(url));
            intent.setFlags(268435456);
            if (hasGooglePlay(context)) {
                intent.setPackage("com.android.vending");
            }
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void goToAppGooglePlay(Context context) {
        String packageName = context.getPackageName();
        String url = new StringBuilder("https://play.google.com/store/apps/details?id=")
                .append(packageName).toString();

        try {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse(url));
            intent.setFlags(268435456);
            if (hasGooglePlay(context)) {
                intent.setPackage("com.android.vending");
            }
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean hasGooglePlay(Context context) {
        try {
            List<PackageInfo> packs = context.getPackageManager().getInstalledPackages(PackageManager.MATCH_UNINSTALLED_PACKAGES);
            if (packs != null && packs.size() > 0) {
                for (PackageInfo p : packs) {
                    if (!TextUtils.isEmpty(p.packageName) && p.packageName.equals("com.android.vending")) {
                        return true;
                    }
                }
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return false;
    }

}
