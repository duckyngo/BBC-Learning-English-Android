package com.duckydev.mvpdagger.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.duckydev.mvpdagger.Constants;

import java.util.StringTokenizer;


public class SharePreferenceUtils {
    private SharedPreferences sp;
    private Editor editor;

    public SharePreferenceUtils(Context context) {
        this.sp = context.getSharedPreferences("instaget", 0);
        editor = this.sp.edit();
    }

    public int getVersion() {
        return this.sp.getInt("version", -1);
    }

    public void setVersion(int version) {
        this.editor.putInt("version", version);
        this.editor.commit();
    }

    public boolean getShowPermissionBadge() {
        return this.sp.getBoolean("permissionbadge", true);
    }

    public void setShowPermissionBadge(boolean b) {
        this.editor.putBoolean("permissionbadge", b);
        this.editor.commit();
    }

    public int getRateCount() {
        return this.sp.getInt("rate_count", 0);
    }

    public void setRateCount(int i) {
        this.editor.putInt("rate_count", i);
        this.editor.commit();
    }

    public static int getNumRatesClick(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                Constants.BBK_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(Constants.PREF_NUM_RATES_CLICK, 0);
    }

    public static void updateNumRatesClick(Context context, int numRatesClisk) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                Constants.BBK_PREFERENCES, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putInt(Constants.PREF_NUM_RATES_CLICK, numRatesClisk);
        editor.apply();
    }

    public static int getDatabaseVersion(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                Constants.BBK_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(Constants.PREF_NUM_RATES_CLICK, 0);
    }

    public static void updateDatabaseVersion(Context context, int version) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                Constants.BBK_PREFERENCES, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putInt(Constants.PREF_DATABASE_VERSION, version);
        editor.apply();
    }

    public static void updateEpisodeHistoryId(Context context, int lessonId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                Constants.BBK_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int[] history = getEpisodeHistoryId(context);

        int existLessonIndex = HistoryDuplicateUtils.isConversationHistoryIdExist(history, lessonId);
        if (existLessonIndex != -1) {
            int valueBeingMoved = history[existLessonIndex];

            System.arraycopy(history, 0, history, 1, existLessonIndex);

            history[0] = valueBeingMoved;
        } else {

            System.arraycopy(history, 0, history, 1, history.length - 1);
            history[0] = lessonId;

        }

        StringBuilder str = new StringBuilder();
        for (int aHistory : history) {
            str.append(aHistory).append(",");
        }

        editor.putString(Constants.PREF_CONVERSATION_HISTORY_ID, str.toString());
        editor.apply();
    }

    public static int[] getEpisodeHistoryId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                Constants.BBK_PREFERENCES, Context.MODE_PRIVATE);

        String savedString = sharedPreferences.getString(Constants.PREF_CONVERSATION_HISTORY_ID, "");

        StringTokenizer st = new StringTokenizer(savedString, ",");
        int[] savedList = new int[50];
        for (int i = 0; i < 50; i++) {
            if (st.hasMoreTokens()) {
                savedList[i] = Integer.parseInt(st.nextToken());
            }
        }

        return savedList;
    }

//    public static int getLanguageCodePosition(Context context) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(
//                Constants.BBK_PREFERENCES, Context.MODE_PRIVATE);
//        return sharedPreferences.getInt(Constants.PREF_LANGUAGE_CODE_INDEX, MapUtils.getIndexFromCountryCode());
//    }
//
//    public static void updateLanguageCodePosition(Context context, int languageCodePos) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(
//                Constants.BBK_PREFERENCES, Context.MODE_PRIVATE);
//        Editor editor = sharedPreferences.edit();
//        editor.putInt(Constants.PREF_LANGUAGE_CODE_INDEX, languageCodePos);
//        editor.commit();
//    }
//
//    public static void updateVideoDownloadQualityIndex(Context context, int downloadQualityIndex) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(
//                Constants.BBK_PREFERENCES, Context.MODE_PRIVATE);
//        Editor editor = sharedPreferences.edit();
//        editor.putInt(Constants.PREF_VIDEO_DOWNLOAD_QUALITY, downloadQualityIndex);
//        editor.commit();
//    }
//
//    public static int getVideoDownloadQualityIndex(Context context) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(
//                Constants.BBK_PREFERENCES, Context.MODE_PRIVATE);
//        return sharedPreferences.getInt(Constants.PREF_VIDEO_DOWNLOAD_QUALITY, Constants.DEFAULT_VIDEO_QUALITY);
//    }
//
//
//    public static void updateEnableDownloadSDCard(Context context, boolean isDownloadSDcard) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(
//                Constants.BBK_PREFERENCES, Context.MODE_PRIVATE);
//        Editor editor = sharedPreferences.edit();
//        editor.putBoolean(Constants.PREF_ENABLE_SD_CARD, isDownloadSDcard);
//        editor.commit();
//    }
//
//    public static boolean getEnableDownloadSDCard(Context context) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(
//                Constants.BBK_PREFERENCES, Context.MODE_PRIVATE);
//        return sharedPreferences.getBoolean(Constants.PREF_ENABLE_SD_CARD, true);
//    }
//
//    public static void updateDownloadWifiOnly(Context context, boolean isDownloadWifiOnly) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(
//                Constants.BBK_PREFERENCES, Context.MODE_PRIVATE);
//        Editor editor = sharedPreferences.edit();
//        editor.putBoolean(Constants.PREF_ENABLE_DOWNLOAD_WIFI_ONLY, isDownloadWifiOnly);
//        editor.commit();
//    }
//
//    public static boolean getDownloadWifiOnly(Context context) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(
//                Constants.BBK_PREFERENCES, Context.MODE_PRIVATE);
//        return sharedPreferences.getBoolean(Constants.PREF_ENABLE_DOWNLOAD_WIFI_ONLY, false);
//    }
//
//
//    public static void updateBackgroundAudioMode(Context context, boolean backgroundAudioMode) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(
//                Constants.BBK_PREFERENCES, Context.MODE_PRIVATE);
//        Editor editor = sharedPreferences.edit();
//        editor.putBoolean(Constants.PREF_BACKGROUND_AUDIO_MODE, backgroundAudioMode);
//        editor.commit();
//    }
//
//    public static boolean getBackgroundAudioMode(Context context) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(
//                Constants.BBK_PREFERENCES, Context.MODE_PRIVATE);
//        return sharedPreferences.getBoolean(Constants.PREF_BACKGROUND_AUDIO_MODE, true);
//    }
//
//    public static void updateEpisodeHistoryId(Context context, int lessonId) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(
//                Constants.BBK_PREFERENCES, Context.MODE_PRIVATE);
//        Editor editor = sharedPreferences.edit();
//
//        int[] history = getEpisodeHistoryId(context);
//
//        int existLessonIndex = HistoryDuplicateUtils.isConversationHistoryIdExist(history, lessonId);
//        if (existLessonIndex != -1) {
//            int valueBeingMoved = history[existLessonIndex];
//
//            for (int i = existLessonIndex; i > 0; i--) {
//                history[i] = history[i-1];
//            }
//
//            history[0] = valueBeingMoved;
//        } else {
//
//            for (int i = history.length - 1; i > 0; i--) {
//                history[i] = history[i-1];
//            }
//            history[0] = lessonId;
//
//        }
//
//
//        StringBuilder str = new StringBuilder();
//        for (int i = 0; i < history.length; i++) {
//            str.append(history[i]).append(",");
//        }
//
//        editor.putString(Constants.PREF_CONVERSATION_HISTORY_ID, str.toString());
//        editor.commit();
//    }
//
//    public static int[] getEpisodeHistoryId(Context context) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(
//                Constants.BBK_PREFERENCES, Context.MODE_PRIVATE);
//
//        String savedString = sharedPreferences.getString(Constants.PREF_CONVERSATION_HISTORY_ID, "");
//
//        StringTokenizer st = new StringTokenizer(savedString, ",");
//        int[] savedList = new int[50];
//        for (int i = 0; i < 50; i++) {
//            if (st.hasMoreTokens()) {
//                savedList[i] = Integer.parseInt(st.nextToken());
//            }
//        }
//
//        return savedList;
//    }
//
//    static public boolean getPremiumState(Context context){
//        SharedPreferences mPreferences = context.getSharedPreferences(Constants.TED_PREFERENCES, Context.MODE_PRIVATE);
//        return mPreferences.getBoolean(Constants.PREF_PREMIUM_KEY, false);
//    }
//
//    static public boolean getShowTestGuideState(Context context){
//        SharedPreferences mPreferences = context.getSharedPreferences(Constants.TED_PREFERENCES, Context.MODE_PRIVATE);
//        return mPreferences.getBoolean(Constants.PREF_IS_SHOW_TEST_GUIDE_STATE, true);
//    }
//
//    public static void updateShowTestGuideState(Context context, boolean isShowTestGuide) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(
//                Constants.BBK_PREFERENCES, Context.MODE_PRIVATE);
//        Editor editor = sharedPreferences.edit();
//        editor.putBoolean(Constants.PREF_IS_SHOW_TEST_GUIDE_STATE, isShowTestGuide);
//        editor.commit();
//    }
}
