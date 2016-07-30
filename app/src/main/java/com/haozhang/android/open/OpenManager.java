package com.haozhang.android.open;

import android.app.Activity;
import android.content.Context;

import com.haozhang.android.utils.LogUtils;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

/**
 * 第三方平台管理
 *
 * @author HaoZhang
 * @date 2016/7/30.
 */
public class OpenManager {
    private static final String TAG = "OpenManager";
    private static volatile OpenManager mManager;
    Context mContext;


    private OpenManager(Context context) {
        this.mContext = context;
    }

    public static OpenManager getInstance(Context context) {
        OpenManager inst = mManager;
        if (null == inst) {
            synchronized (OpenManager.class) {
                inst = mManager;
                if (inst == null) {
                    inst = new OpenManager(context);
                    mManager = inst;
                }
            }
        }
        return inst;
    }

    public void loginQQ(Activity activity, QQIUiListener.QQIUiCallback callback) {
        Tencent tencent = createTencent();
        QQIUiListener listener = new QQIUiListener(mContext, callback);
        tencent.login(activity, "get_user_info,all", listener);
    }

    public void loginQQ(Activity activity, QQIUiListener.QQIUiInfoCallback callback) {
        Tencent tencent = createTencent();
        QQIUiListener listener = new QQIUiListener(mContext, callback);
        tencent.login(activity, "get_user_info,all", listener);
    }

    public IUiListener createIuiListener(final QQIUiListener.QQIUiCallback callback) {
        final IUiListener listener = new IUiListener() {
            @Override
            public void onComplete(Object o) {
                LogUtils.d(TAG, "qqcallback :" + o.toString());
                try {
                    callback.onComplete(new JSONObject(o.toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(UiError uiError) {

            }

            @Override
            public void onCancel() {

            }
        };
        return listener;
    }

    public Tencent createTencent() {
        Tencent tencent = Tencent.createInstance(OpenConfigs.APPID_QQ, mContext);
        // 读取
        String expires_in = OpenSharepreference.getInstance(mContext).getPreferenceString("qq_expires_in", null);
        String access_token = OpenSharepreference.getInstance(mContext).getPreferenceString("qq_access_token", null);
        String openid = OpenSharepreference.getInstance(mContext).getPreferenceString("qq_openid", null);
        if (null == openid || null == access_token) return tencent;
        long time = Long.parseLong(expires_in);
        time = (time - System.currentTimeMillis()) / 1000;
        if (time > 0) {
            tencent.setAccessToken(access_token, String.valueOf(time));
            tencent.setOpenId(openid);
        }
        return tencent;
    }
}
