package com.haozhang.android.open;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author HaoZhang
 * @date 2016/7/30.
 */
public class OpenSharepreference {
    private static volatile OpenSharepreference mPreference;
    Context mContext;

    public static final String SP_NAME = "open_data";

    private OpenSharepreference(Context context) {
        this.mContext = context;
    }

    public static OpenSharepreference getInstance(Context context) {
        OpenSharepreference inst = mPreference;
        if (null == inst) {
            synchronized (OpenSharepreference.class) {
                inst = mPreference;
                if (inst == null) {
                    inst = new OpenSharepreference(context);
                    mPreference = inst;
                }
            }
        }
        return inst;
    }

    public String getPreferenceString(String key, String def) {
        SharedPreferences preferences = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return preferences.getString(key, def);
    }
    public void savePreferenceString(String key, String val) {
        SharedPreferences preferences = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString(key,val);
        edit.commit();
    }


}
