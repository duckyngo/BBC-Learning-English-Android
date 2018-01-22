package com.duckydev.mvpdagger.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;

public class FileUtils {
    public static String initCrashLogFolder(Context context) {
        File file;
        if (Environment.getExternalStorageState().equals("mounted")) {
            file = new File(Environment.getExternalStorageDirectory() + "/InstaGet/crash/");
        } else {
            file = new File(context.getCacheDir().getAbsolutePath() + "/crash/");
        }
        if (!file.exists()) {
            file.mkdirs();
            if (!file.isDirectory()) {
                file.mkdirs();
            }
        }
        return file.getAbsolutePath();
    }

    public static String getFileName(String fullPath) {
        String fileName = fullPath;
        if (TextUtils.isEmpty(fileName)) {
            return fileName;
        }
        int startIndex = fullPath.lastIndexOf(File.separator) + 1;
        int endIndex = fullPath.lastIndexOf(".");
        if (startIndex < 0 || startIndex >= endIndex) {
            return fileName;
        }
        return fullPath.substring(startIndex, endIndex);
    }

    public static String getFolderName(String fullPath) {
        String fileName = fullPath;
        if (TextUtils.isEmpty(fileName)) {
            return fileName;
        }
        int startIndex = fullPath.lastIndexOf(File.separator) + 1;
        if (startIndex >= 0) {
            return fullPath.substring(startIndex);
        }
        return fileName;
    }

    public static void saveTalkImage(Context context, Bitmap bitmap, long talkId) {
        String imageName = talkId + ".jpg";
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = context.openFileOutput(imageName, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.close();
            Log.d("ky.nd", "Image save in: " + (context.getApplicationContext().getFileStreamPath(imageName)).getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getTalkImageFullPath(Context context, long talkId) {
        String imageName = talkId + ".jpg";
        return context.getApplicationContext().getFileStreamPath(imageName).getAbsolutePath();
    }

    public static void deleteTalkImage(Context context, long talkId) {
        String imageName = talkId + ".jpg";
        File file = context.getApplicationContext().getFileStreamPath(imageName);
        if (file.delete()) Log.d("ky.nd", talkId + " image deleted!");
    }

    public static void deleteTalkVideo(Context context, String downloadVideoUrl) {
        File file = new File(downloadVideoUrl);
        if (file.delete()) Log.d("ky.nd", downloadVideoUrl + " video delete!");
    }

    public static boolean isFileExist(String path) {
        File file = new File(path);
        return file.exists();
    }

    public static void deleteFile(String path) {
        File file = new File(path);
        if (file.delete()) Log.d("ky.nd", path + " video delete!");
    }
}
