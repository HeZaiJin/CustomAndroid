package com.haozhang.android.utils;

import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Log 管理 如果要使用写入文件的功能 需要添加
 * <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
 */
public class LogUtils {
    public static final boolean DEBUG = true;
    public static final boolean WRITE = false;
    public static boolean isExist = false;
    public static final String DEFAULT_PATH = "sdcard/log.log";

    /**
     * log 写入文件
     *
     * @param fileName
     * @param content
     */
    public static void write(String fileName, String content) {
        FileWriter writer = null;
        try {
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            writer = new FileWriter(fileName, true);
            writer.write(content + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != writer) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void write(String content) {
        FileWriter writer = null;
        try {
            if (!isExist) {
                File file = new File(DEFAULT_PATH);
                if (!file.exists()) {
                    file.createNewFile();
                }
                isExist = true;
            }
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            writer = new FileWriter(DEFAULT_PATH, true);
            writer.write(content + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != writer) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void d(String TAG, String msg) {
        if (DEBUG) {
            Log.d(TAG, msg);
            if (WRITE) {
                write(TAG + ":" + msg);
            }
        }
    }


    public static void e(String TAG, String msg) {
        if (DEBUG) {
            Log.e(TAG, msg);
        }
    }

    public static void i(String TAG, String msg) {
        if (DEBUG) {
            Log.i(TAG, msg);
        }
    }

    public static void w(String TAG, String msg) {
        if (DEBUG) {
            Log.w(TAG, msg);
        }
    }

    public static void v(String TAG, String msg) {
        if (DEBUG) {
            Log.v(TAG, msg);
        }
    }
}
