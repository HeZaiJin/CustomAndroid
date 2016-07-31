package com.haozhang.android.open;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author HaoZhang
 * @date 2016/7/30.
 */
public class OpenSharepreference {
    public static final String SP_NAME = "open_data";
    private static volatile OpenSharepreference mPreference;
    Context mContext;
    public static final String KEY_QQ_OPENID = "qq_openid";
    public static final String KEY_QQ_ACCESS_TOKEN = "qq_access_token";
    public static final String KEY_QQ_EXPIRES_IN = "qq_expires_in";

    private OpenSharepreference() {
    }
    protected void init(Context context){
        this.mContext = context;
    }

    public static OpenSharepreference getInstance() {
        OpenSharepreference inst = mPreference;
        if (null == inst) {
            synchronized (OpenSharepreference.class) {
                inst = mPreference;
                if (inst == null) {
                    inst = new OpenSharepreference();
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
        edit.putString(key, val);
        edit.commit();
    }

    public void saveQQOpenId(String openid) {
        savePreferenceString(KEY_QQ_OPENID, openid);
    }

    public String getQQOpenid(){
        return getPreferenceString(KEY_QQ_OPENID,null);
    }

    public String getQQAccessToken(){
        return getPreferenceString(KEY_QQ_ACCESS_TOKEN,null);
    }

    public String getQQExpiresIn(){
        return getPreferenceString(KEY_QQ_EXPIRES_IN,null);
    }

    public void saveQQAccessToken(String token) {
        savePreferenceString(KEY_QQ_ACCESS_TOKEN, token);
    }

    public void saveQQExpiresIn(String exin) {
        savePreferenceString(KEY_QQ_EXPIRES_IN, exin);
    }

}
