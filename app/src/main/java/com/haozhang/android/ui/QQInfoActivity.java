package com.haozhang.android.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.haozhang.android.R;

public class QQInfoActivity extends AppCompatActivity {
    ImageView mImage;
    TextView mTv;
    TextView mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qqinfo);
        mName = (TextView) findViewById(R.id.name);
        mTv = (TextView) findViewById(R.id.msg);
        mImage = (ImageView) findViewById(R.id.img);
        String url = getIntent().getStringExtra("figureurl_qq_2");
        Glide.with(this).load(url).dontAnimate().centerCrop().into(mImage);

        String name = getIntent().getStringExtra("nickname");
        mName.setText(name);
        mTv.setText(getIntent().getStringExtra("data"));
    }
}
