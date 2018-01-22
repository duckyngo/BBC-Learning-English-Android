package com.duckydev.mvpdagger.util;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;

import com.duckydev.mvpdagger.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;


public class FeedbackUtils {
    @TargetApi(18)
    public static void sendFeedback(Context context) {
        Intent intent;
        ArrayList<Uri> uris = new ArrayList();
        File file = new File(FileUtils.initCrashLogFolder(context) + "/crash.log");
        if (file != null && file.exists()) {
            uris.add(Uri.fromFile(file));
        }
        String app_version = "";
        try {
            Properties properties = new Properties();
            try {
                properties.load(context.getAssets().open("config.properties"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            app_version = "v" + context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName + (properties.containsKey("version") ? properties.getProperty("version") : "");
        } catch (NameNotFoundException e2) {
//            GoogleAnalyticsUtils.sendExcetion(context, "EmailUtils-1", e2, false);
            e2.printStackTrace();
        }
        StringBuffer sb = new StringBuffer();
        sb.append("\n\n");
        sb.append(context.getString(R.string.feedback_mail_text));
        sb.append("(App " + app_version);
        sb.append(",Model " + Build.MODEL);
        sb.append(",OS v" + VERSION.RELEASE);
        sb.append(",Screen ");
        sb.append(context.getResources().getDisplayMetrics().widthPixels + "x" + context.getResources().getDisplayMetrics().heightPixels);
        sb.append(", ");
        Locale locale = context.getResources().getConfiguration().locale;
        sb.append(locale.getLanguage() + " _ " + locale.getCountry());
        sb.append(", ");
        sb.append(TimeZone.getDefault().getDisplayName(false, 0));
        sb.append(")");
        System.out.println(sb.toString());
        try {
            intent = new Intent("android.intent.action.SEND_MULTIPLE");
            intent.setType("application/octet-stream");
            intent.putExtra("android.intent.extra.EMAIL", new String[]{"dukky.smartphone@gmail.com"});
            intent.putExtra("android.intent.extra.SUBJECT", context.getString(R.string.feedback_email_title));
            intent.putExtra("android.intent.extra.TEXT", sb.toString());
            intent.putParcelableArrayListExtra("android.intent.extra.STREAM", uris);
            if (EmailUtils.getInstance().hasGmail(context)) {
                intent.setPackage(EmailUtils.PACKAGE_GMAIL);
            } else if (EmailUtils.getInstance().hasEmailApp(context)) {
                intent.setPackage(EmailUtils.PACKAGE_EMAIL_APP);
            }
            context.startActivity(intent);
        } catch (ActivityNotFoundException e3) {
//            GoogleAnalyticsUtils.sendExcetion(context, "EmailUtils-2", e3, false);
            try {
                e3.printStackTrace();
                intent = new Intent("android.intent.action.SEND_MULTIPLE");
                intent.setType("application/octet-stream");
                intent.putExtra("android.intent.extra.EMAIL", new String[]{"dukky.smartphone@gmail.com"});
                intent.putExtra("android.intent.extra.SUBJECT", context.getString(R.string.feedback_email_title));
                intent.putExtra("android.intent.extra.TEXT", sb.toString());
                intent.putParcelableArrayListExtra("android.intent.extra.STREAM", uris);
                context.startActivity(intent);
            } catch (ActivityNotFoundException e22) {
//                GoogleAnalyticsUtils.sendExcetion(context, "EmailUtils-3", e3, false);
                e22.printStackTrace();
            }
        }
    }
}
