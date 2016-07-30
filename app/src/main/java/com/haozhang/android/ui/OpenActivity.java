package com.haozhang.android.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.haozhang.android.R;
import com.haozhang.android.open.OpenManager;
import com.haozhang.android.open.QQIUiListener;
import com.haozhang.android.utils.LogUtils;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

public class OpenActivity extends AppCompatActivity {
    private static final String TAG = "OpenActivity";

    Button mLoginQQ;
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
                OpenManager.getInstance(OpenActivity.this).loginQQ(OpenActivity.this, mQQCallback);
            }
        });
        mDialog = new ProgressDialog(this);
        mDialog.setMessage("Loading");
    }

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

        }

        @Override
        public void onCancel() {

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
        super.onActivityResult(requestCode, resultCode, data);

    }
}
