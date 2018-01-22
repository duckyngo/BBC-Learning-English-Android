package com.duckydev.mvpdagger.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import java.util.List;

@SuppressLint({"NewApi"})
public class EmailUtils {
    public static final String PACKAGE_EMAIL_APP = "com.android.email";
    public static final String PACKAGE_GMAIL = "com.google.android.gm";
    private static EmailUtils utils;

    private EmailUtils() {
    }

    public static synchronized EmailUtils getInstance() {
        EmailUtils emailUtils;
        synchronized (EmailUtils.class) {
            if (utils == null) {
                utils = new EmailUtils();
            }
            emailUtils = utils;
        }
        return emailUtils;
    }

    public boolean hasGmail(Context context) {
        try {
            List<PackageInfo> packs = context.getPackageManager().getInstalledPackages(PackageManager.MATCH_UNINSTALLED_PACKAGES);
            if (packs != null && packs.size() > 0) {
                for (PackageInfo p : packs) {
                    if (!TextUtils.isEmpty(p.packageName) && p.packageName.equals(PACKAGE_GMAIL)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e2) {
            e2.printStackTrace();
        }
        return false;
    }

    public boolean hasEmailApp(Context context) {
        try {
            List<PackageInfo> packs = context.getPackageManager().getInstalledPackages(PackageManager.MATCH_UNINSTALLED_PACKAGES);
            if (packs != null && packs.size() > 0) {
                for (PackageInfo p : packs) {
                    if (!TextUtils.isEmpty(p.packageName) && p.packageName.equals(PACKAGE_EMAIL_APP)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e2) {
            e2.printStackTrace();
        }
        return false;
    }
}
