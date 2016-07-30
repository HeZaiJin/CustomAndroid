package com.haozhang.android.open;

import android.content.Context;

import com.haozhang.android.utils.LogUtils;
import com.tencent.connect.UserInfo;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

/**
 * @author HaoZhang
 * @date 2016/7/30.
 */
public class QQIUiListener implements IUiListener {
    Context mContext;
    QQIUiCallback mCallback;
    QQIUiInfoCallback mInfoCallback;

    public QQIUiListener(Context context) {
        this.mContext = context;
    }

    public QQIUiListener(Context context, QQIUiCallback callback) {
        this.mContext = context;
        this.mCallback = callback;
    }

    public QQIUiListener(Context context, QQIUiInfoCallback callback) {
        this.mContext = context;
        this.mInfoCallback = callback;
    }

    /**
     * {"ret":0,"pay_token":"49DE23B74BAEEFED0A23F6BB6231501D",
     * "pf":"desktop_m_qq-10000144-android-2002-",
     * "query_authority_cost":588,"authority_cost":-1018003,
     * "openid":"46AA3BD004CA0AC7FB8B41DC3C36FE9B",
     * "expires_in":7776000,
     * "pfkey":"704d95b1a04dae25ed6cb0e14511ea08","msg":"",
     * "access_token":"E1F60FD08518C659D4497F3D917D792E","login_cost":1883}
     *
     * @param o
     */
    @Override
    public void onComplete(Object o) {
        //save
        JSONObject object = null;
        try {
            object = new JSONObject(o.toString());
            if (object.has("openid")) {
                String openid = object.getString("openid");
                if (null != openid) {
                    OpenSharepreference.getInstance(mContext).savePreferenceString("qq_openid", openid);
                }
            }

            if (object.has("access_token")) {
                String access_token = object.getString("access_token");
                if (null != access_token) {
                    OpenSharepreference.getInstance(mContext).savePreferenceString("qq_access_token", access_token);
                }
            }

            if (object.has("expires_in")) {
                String expires_in = object.getString("expires_in");
                if (null != expires_in) {
                    long time = System.currentTimeMillis() + Long.parseLong(expires_in) * 1000;
                    OpenSharepreference.getInstance(mContext).savePreferenceString("qq_expires_in", String.valueOf(time));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (null != mInfoCallback && null != object) {
            UserInfo userInfo = new UserInfo(mContext, OpenManager.getInstance(mContext).createTencent().getQQToken());
            mInfoCallback.onComplete(object, userInfo);
            userInfo.getUserInfo(mGetInfoListener);
            return;
        }

        if (null != mCallback && null != object) {
            mCallback.onComplete(object);
        }
    }

    private IUiListener mGetInfoListener = new IUiListener() {
        @Override
        public void onComplete(Object o) {
            LogUtils.d("IUI","mGetInfoListener : "+o.toString());
        }

        @Override
        public void onError(UiError uiError) {

        }

        @Override
        public void onCancel() {

        }
    };

    @Override
    public void onError(UiError uiError) {
        // show error
        if (null != mCallback) {
            mCallback.onError(uiError);
        }
    }

    @Override
    public void onCancel() {
        if (null != mCallback) {
            mCallback.onCancel();
        }
    }

    public static interface QQIUiCallback {

        /**
         * {"ret":0,
         * "pay_token":"49DE23B74BAEEFED0A23F6BB6231501D",
         * "pf":"desktop_m_qq-10000144-android-2002-",
         * "query_authority_cost":588,
         * "authority_cost":-1018003,
         * "openid":"46AA3BD004CA0AC7FB8B41DC3C36FE9B",
         * "expires_in":7776000,
         * "pfkey":"704d95b1a04dae25ed6cb0e14511ea08",
         * "msg":"",
         * "access_token":"E1F60FD08518C659D4497F3D917D792E",
         * "login_cost":1883}
         */
        public void onComplete(JSONObject obj);

        public void onError(UiError e);

        public void onCancel();

    }

    public static interface QQIUiInfoCallback {
        /**
         * {"ret":0,
         * "pay_token":"49DE23B74BAEEFED0A23F6BB6231501D",
         * "pf":"desktop_m_qq-10000144-android-2002-",
         * "query_authority_cost":588,
         * "authority_cost":-1018003,
         * "openid":"46AA3BD004CA0AC7FB8B41DC3C36FE9B",
         * "expires_in":7776000,
         * "pfkey":"704d95b1a04dae25ed6cb0e14511ea08",
         * "msg":"",
         * "access_token":"E1F60FD08518C659D4497F3D917D792E",
         * "login_cost":1883}
         */
        public void onComplete(JSONObject obj, UserInfo info);

        public void onError(UiError e);

        public void onCancel();
    }
}
