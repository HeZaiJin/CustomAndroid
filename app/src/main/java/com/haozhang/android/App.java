package com.haozhang.android;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.haozhang.android.open.OpenManager;

/**
 * @author HaoZhang
 * @date 2016/7/24.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        OpenManager.getInstance().init(this);
    }
}
