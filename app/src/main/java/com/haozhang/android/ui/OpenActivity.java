package com.haozhang.android.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.haozhang.android.R;
import com.haozhang.android.open.OpenManager;
import com.haozhang.android.open.QQIUiListener;
import com.haozhang.android.utils.LogUtils;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

public class OpenActivity extends AppCompatActivity {
    private static final String TAG = "OpenActivity";

    Button mLoginQQ;
    Button mShareQQ;
    private ProgressDialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);
        initView();
    }

    void initView() {
        mLoginQQ = (Button) findViewById(R.id.open_login_qq);
        mLoginQQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.show();
                OpenManager.getInstance().loginQQ(OpenActivity.this, mQQCallback);
            }
        });
        mShareQQ = (Button) findViewById(R.id.open_share_qq);
        mShareQQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.show();
                final Bundle params = new Bundle();
                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
                params.putString(QQShare.SHARE_TO_QQ_TITLE, "要分享的标题");
                params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  "要分享的摘要");
                params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  "http://www.qq.com/news/1.html");
                params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,"http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
                params.putString(QQShare.SHARE_TO_QQ_APP_NAME,  "测试应用222222");
                OpenManager.getInstance().shareToQQ(OpenActivity.this,params ,mShareQQListener);
            }
        });
        mDialog = new ProgressDialog(this);
        mDialog.setMessage("Loading");
    }

    private IUiListener mShareQQListener = new IUiListener() {
        @Override
        public void onComplete(Object o) {
            LogUtils.d(TAG,"share qq onComplete return : "+o.toString());
            mDialog.dismiss();
        }

        @Override
        public void onError(UiError uiError) {
            LogUtils.d(TAG,"share qq onError : "+uiError.toString());
            mDialog.dismiss();

        }

        @Override
        public void onCancel() {
            LogUtils.d(TAG,"share qq onCancel : ");
            mDialog.dismiss();
        }
    };

    private QQIUiListener.QQIUiInfoCallback mQQCallback = new QQIUiListener.QQIUiInfoCallback() {

        @Override
        public void onCreateUserInfo(UserInfo info) {
        }

        @Override
        public void onComplete(JSONObject obj) {
            if (null == obj)return;

            LogUtils.d(TAG,"get userinfo :"+obj.toString());
            try {
                Intent intent = new Intent(OpenActivity.this,QQInfoActivity.class);
                intent.putExtra("data",obj.toString());
                if (obj.has("nickname")) {
                    intent.putExtra("nickname",obj.getString("nickname"));
                }

                if (obj.has("figureurl_qq_2")) {
                    intent.putExtra("figureurl_qq_2",obj.getString("figureurl_qq_2"));
                }
                startActivity(intent);
            }catch (Exception e){
                e.printStackTrace();
            }
            mDialog.dismiss();
        }

        @Override
        public void onError(UiError e) {
            LogUtils.d(TAG," login onError :"+e.toString());

        }

        @Override
        public void onCancel() {
            LogUtils.d(TAG," login cancle");
        }
    };

    /**
     * 意外情况
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_LOGIN) {
            Tencent.handleResultData(data, new QQIUiListener(this, mQQCallback));
        }
        // 分享时必须添加 否则无法拿到回调
        Tencent.onActivityResultData(requestCode,resultCode,data,mShareQQListener);
        super.onActivityResult(requestCode, resultCode, data);

    }
}
