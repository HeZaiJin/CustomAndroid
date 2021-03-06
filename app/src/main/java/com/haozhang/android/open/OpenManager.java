package com.haozhang.android.open;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.haozhang.android.utils.LogUtils;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;

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
    private static final String QQ_SOCPE = "get_user_info,all";

    private OpenManager() {
    }

    public void init(Context context){
        this.mContext = context;
        OpenSharepreference.getInstance().init(context);
    }

    public static OpenManager getInstance() {
        OpenManager inst = mManager;
        if (null == inst) {
            synchronized (OpenManager.class) {
                inst = mManager;
                if (inst == null) {
                    inst = new OpenManager();
                    mManager = inst;
                }
            }
        }
        return inst;
    }

    public void loginQQ(Activity activity, QQIUiListener.QQIUiCallback callback) {
        Tencent tencent = createTencent();
        QQIUiListener listener = new QQIUiListener(mContext, callback);
        tencent.login(activity, QQ_SOCPE, listener);
    }

    public void loginQQ(Fragment fragment, QQIUiListener.QQIUiCallback callback) {
        Tencent tencent = createTencent();
        QQIUiListener listener = new QQIUiListener(mContext, callback);
        tencent.login(fragment, QQ_SOCPE, listener);
    }

    public void loginQQ(Fragment fragment, QQIUiListener.QQIUiInfoCallback callback) {
        Tencent tencent = createTencent();
        QQIUiListener listener = new QQIUiListener(mContext, callback);
        tencent.login(fragment, QQ_SOCPE, listener);
    }

    public void loginQQ(Activity activity, QQIUiListener.QQIUiInfoCallback callback) {
        Tencent tencent = createTencent();
        QQIUiListener listener = new QQIUiListener(mContext, callback);
        tencent.login(activity, QQ_SOCPE, listener);
    }

    /**
     分享消息到QQ的接口，可将新闻、图片、文字、应用等分享给QQ好友、群和讨论组。Tencent类的shareToQQ函数可直接调用，不用用户授权（使用手机QQ当前的登录态）。调用将打开分享的界面，用户选择好友、群或讨论组之后，点击确定即可完成分享，并进入与该好友进行对话的窗口。
     本接口支持3种模式，每种模式的参数设置不同，下面分别进行介绍：
     （1） 分享图文消息
     调用分享接口的示例代码如下：
     private void onClickShare() {
     final Bundle params = new Bundle();
     params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
     params.putString(QQShare.SHARE_TO_QQ_TITLE, "要分享的标题");
     params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  "要分享的摘要");
     params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  "http://www.qq.com/news/1.html");
     params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,"http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
     params.putString(QQShare.SHARE_TO_QQ_APP_NAME,  "测试应用222222");
     params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,  "其他附加功能");
     mTencent.shareToQQ(MainActivity.this, params, new BaseUiListener());
     }

     调用分享接口的params参数说明如下：
     参数	是否必传	类型	参数说明
     QQShare.SHARE_TO_QQ_KEY_TYPE	必填	Int	分享的类型。图文分享(普通分享)填Tencent.SHARE_TO_QQ_TYPE_DEFAULT
     QQShare.PARAM_TARGET_URL	必填	String	这条分享消息被好友点击后的跳转URL。
     QQShare.PARAM_TITLE	必填	String	分享的标题, 最长30个字符。
     QQShare.PARAM_SUMMARY	可选	String	分享的消息摘要，最长40个字。
     QQShare.SHARE_TO_QQ_IMAGE_URL	可选	String	分享图片的URL或者本地路径
     QQShare.SHARE_TO_QQ_APP_NAME	可选	String	手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替
     QQShare.SHARE_TO_QQ_EXT_INT	可选	Int	分享额外选项，两种类型可选（默认是不隐藏分享到QZone按钮且不自动打开分享到QZone的对话框）：
     QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN，分享时自动打开分享到QZone的对话框。
     QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE，分享时隐藏分享到QZone按钮
     （2） 分享纯图片
     调用分享接口的示例代码如下：
     private void onClickShare() {
     Bundle params = new Bundle();
     params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL,imageUrl.getText().toString());
     params.putString(QQShare.SHARE_TO_QQ_APP_NAME, appName.getText().toString());
     params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
     params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
     mTencent.shareToQQ(MainActivity.this, params, new BaseUiListener());
     }
     参数	是否必传	类型	参数说明
     QQShare.SHARE_TO_QQ_KEY_TYPE	必选	Int	分享类型，分享纯图片时填写QQShare.SHARE_TO_QQ_TYPE_IMAGE。
     QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL	必选	String	需要分享的本地图片路径。
     QQShare.SHARE_TO_QQ_APP_NAME	可选	String	手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替。
     QQShare.SHARE_TO_QQ_EXT_INT	可选	Int	分享额外选项，两种类型可选（默认是不隐藏分享到QZone按钮且不自动打开分享到QZone的对话框）：
     QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN，分享时自动打开分享到QZone的对话框。
     QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE，分享时隐藏分享到QZone按钮。
     （3） 分享音乐
     音乐分享后，发送方和接收方在聊天窗口中点击消息气泡即可开始播放音乐。
     调用分享接口的示例代码如下：
     private void onClickAudioShare() {
     final Bundle params = new Bundle();
     params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_AUDIO);
     params.putString(QQShare.SHARE_TO_QQ_TITLE, "要分享的标题");
     params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  "要分享的摘要");
     params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  "http://www.qq.com/news/1.html");
     params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
     params.putString(QQShare.SHARE_TO_QQ_AUDIO_URL, "音乐链接");
     params.putString(QQShare.SHARE_TO_QQ_APP_NAME,  "测试应用222222");
     params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
     mTencent.shareToQQ(MainActivity.this, params, new BaseUiListener());
     }
     调用分享接口的params参数说明如下：
     参数	是否必传	类型	参数说明
     QQShare.SHARE_TO_QQ_KEY_TYPE	必填	Int	分享的类型。分享音乐填Tencent.SHARE_TO_QQ_TYPE_AUDIO。
     QQShare.PARAM_TARGET_URL	必选	String	这条分享消息被好友点击后的跳转URL。
     QQShare.SHARE_TO_QQ_AUDIO_URL	必填	String	音乐文件的远程链接, 以URL的形式传入, 不支持本地音乐。
     QQShare.PARAM_TITLE	必选	String	分享的标题, 最长30个字符。
     QQShare.PARAM_SUMMARY	可选	String	分享的消息摘要，最长40个字符。
     QQShare.SHARE_TO_QQ_IMAGE_URL	可选	String	分享图片的URL或者本地路径。
     QQShare.SHARE_TO_QQ_APP_NAME	可选	String	手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替。
     QQShare.SHARE_TO_QQ_EXT_INT	可选	Int	分享额外选项，两种类型可选（默认是不隐藏分享到QZone按钮且不自动打开分享到QZone的对话框）：
     QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN，分享时自动打开分享到QZone的对话框。
     QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE，分享时隐藏分享到QZone按钮。
     （4） 分享应用
     应用分享后，发送方和接收方在聊天窗口中点击消息气泡即可进入应用的详情页。
     调用分享接口的示例代码如下：
     private void onClickAppShare() {
     final Bundle params = new Bundle();
     params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_APP);
     params.putString(QQShare.SHARE_TO_QQ_TITLE, "要分享的标题");
     params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  "要分享的摘要");
     params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
     params.putString(QQShare.SHARE_TO_QQ_APP_NAME,  "测试应用222222");
     mTencent.shareToQQ(MainActivity.this, params, new BaseUiListener());
     }
     调用分享接口的params参数说明如下：
     参数	是否必传	类型	参数说明
     QQShare.SHARE_TO_QQ_KEY_TYPE	必填	Int	分享的类型。分享音乐填Tencent.SHARE_TO_QQ_TYPE_PP。
     QQShare.PARAM_TITLE	必选	String	分享的标题, 最长30个字符。
     QQShare.PARAM_SUMMARY	可选	String	分享的消息摘要，最长40个字符。
     QQShare.SHARE_TO_QQ_IMAGE_URL	可选	String	分享图片的URL或者本地路径。
     QQShare.SHARE_TO_QQ_APP_NAME	可选	String	手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替。
     QQShare.SHARE_TO_QQ_EXT_INT	可选	Int	分享额外选项，两种类型可选（默认是不隐藏分享到QZone按钮且不自动打开分享到QZone的对话框）：
     QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN，分享时自动打开分享到QZone的对话框。
     QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE，分享时隐藏分享到QZone按钮。
     1.14 分享到QQ空间（无需QQ登录）
     完善了分享到QZone功能，分享类型参数Tencent.SHARE_TO_QQ_KEY_TYPE，目前只支持图文分享。Tencent. shareToQzone()函数可直接调用，不用用户授权（使用手机QQ当前的登录态）。调用后将打开手机QQ内QQ空间的界面，或者用浏览器打开QQ空间页面进行分享操作。
     示例代码如下：
     private void shareToQzone () {
     　　//分享类型
     　　params.putString(QzoneShare.SHARE_TO_QQ_KEY_TYPE,SHARE_TO_QZONE_TYPE_IMAGE_TEXT );
     params.putString(QzoneShare.SHARE_TO_QQ_TITLE, "标题");//必填
     params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, "摘要");//选填
     params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, "跳转URL");//必填
     params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, "图片链接ArrayList");
     mTencent.shareToQzone(activity, params, new BaseUiListener());
     }
     params参数说明如下：
     参数	是否必传	类型	参数说明
     QzoneShare.SHARE_TO_QQ_KEY_TYPE	选填	Int	SHARE_TO_QZONE_TYPE_IMAGE_TEXT（图文）
     QzoneShare.SHARE_TO_QQ_TITLE	必填	Int	分享的标题，最多200个字符。
     QzoneShare.SHARE_TO_QQ_SUMMARY	选填	String	分享的摘要，最多600字符。
     QzoneShare.SHARE_TO_QQ_TARGET_URL	必填	String	需要跳转的链接，URL字符串。
     QzoneShare.SHARE_TO_QQ_IMAGE_URL	选填	String	分享的图片, 以ArrayList<String>的类型传入，以便支持多张图片（注：图片最多支持9张图片，多余的图片会被丢弃）。
     注意:QZone接口暂不支持发送多张图片的能力，若传入多张图片，则会自动选入第一张图片作为预览图。多图的能力将会在以后支持。：
     */
    public void shareToQQ(Activity activity, Bundle bundle, IUiListener listener) {
        createTencent().shareToQQ(activity, bundle, listener);
    }

    public Tencent createTencent() {
        if (null == mContext)throw new RuntimeException("must to init context");
        Tencent tencent = Tencent.createInstance(OpenConfigs.APPID_QQ, mContext);
        // 读取
        String expires_in = OpenSharepreference.getInstance().getQQExpiresIn();
        String access_token = OpenSharepreference.getInstance().getQQAccessToken();
        String openid = OpenSharepreference.getInstance().getQQOpenid();
        if (null == openid || null == access_token) return tencent;
        LogUtils.d(TAG,"create tencent  open id :"+openid+", token :"+access_token);
        long time = Long.parseLong(expires_in);
        time = (time - System.currentTimeMillis()) / 1000;
        if (time > 0) {
            tencent.setAccessToken(access_token, String.valueOf(time));
            tencent.setOpenId(openid);
        }
        return tencent;
    }
}
