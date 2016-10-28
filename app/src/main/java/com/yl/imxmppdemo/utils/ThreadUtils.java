package com.yl.imxmppdemo.utils;

import android.os.Handler;

/**
 * Created by Administrator on 2016/10/27 0027.
 */

public class ThreadUtils {
    public static void runInThread(Runnable r) {
        new Thread(r).start();
    }

    private static Handler handler = new Handler();
    public static void runInUIThread(Runnable r) {
        handler.post(r);
    }
}
