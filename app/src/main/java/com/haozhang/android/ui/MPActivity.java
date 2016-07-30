package com.haozhang.android.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.haozhang.android.R;
import com.haozhang.android.rest.RESTClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MPActivity extends AppCompatActivity {
    private static final String TAG = "MPActivity";
    LineChart mLineChart;
    private Handler mHandler;
    private boolean isFirst = false;
    private TextView mTv;
    SimpleDateFormat df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler(Looper.getMainLooper());
        setContentView(R.layout.activity_mp);
        mTv = (TextView) findViewById(R.id.date);
        mLineChart = (LineChart) findViewById(R.id.mp_line);
        mLineChart.setVisibleXRangeMaximum(20);
        // 不绘制内部x轴线条
        mLineChart.getXAxis().setDrawGridLines(false);
        mLineChart.getXAxis().setDrawAxisLine(true);
        mLineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        // 不绘制x轴 进度值
        mLineChart.getXAxis().setDrawLabels(false);

        mLineChart.getAxisLeft().setDrawGridLines(false);
        mLineChart.getAxisRight().setDrawGridLines(false);
        mLineChart.getAxisRight().setDrawAxisLine(false);
        mLineChart.getAxisRight().setDrawLabels(false);
        // 关闭图标说明
        mLineChart.setDescription(null);
        // 关闭线条标签说明
        mLineChart.getLegend().setEnabled(false);
        mLineChart.getAxisLeft().setAxisMaxValue(600);
        df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    }

    private Runnable mGetRunnable = new Runnable() {
        @Override
        public void run() {
            RESTClient.getArray(new Callback<String[]>() {
                @Override
                public void onResponse(Call<String[]> call, Response<String[]> response) {
                    String[] split = response.body();
                    int l = split.length;
                    ArrayList<Entry> yVals = new ArrayList<Entry>();
                    ArrayList<String> xVals = new ArrayList<String>();
                    for (int i = 0; i < l; i++) {
                        xVals.add(i + "");
                        yVals.add(new Entry(Float.parseFloat(split[i]), i));
                    }
                    LineDataSet set1 = new LineDataSet(yVals, null);

                    set1.setDrawCubic(true);  //设置曲线为圆滑的线
                    set1.setCubicIntensity(0.2f);
                    set1.setDrawCircles(false);  //设置有圆点
                    set1.setLineWidth(1f);    //设置线的宽度
                    //触摸高亮线
//                    set1.setHighLightColor(Color.rgb(244, 117, 117));
                    set1.setHighlightEnabled(false);
//                    set1.setDrawFilled(false);
                    set1.setDrawValues(false);

                    set1.setColor(Color.GREEN);    //设置曲线的颜色
                    mLineChart.setData(new LineData(xVals, set1));
                    mLineChart.notifyDataSetChanged();
                    mLineChart.invalidate();
                    setTime();
                    mHandler.post(mGetRunnable);
                }

                @Override
                public void onFailure(Call<String[]> call, Throwable t) {
                }
            });
           /* OkHttpUtils.get().url("http://121.199.41.158:8080/test.ashx").build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {

                }

                @Override
                public void onResponse(String response, int id) {
                    String substring = response.substring(1, response.length() - 1);
                    String[] split = substring.split(",");
                    int l = split.length;
                    ArrayList<Entry> yVals = new ArrayList<Entry>();
                    ArrayList<String> xVals = new ArrayList<String>();
                    for (int i = 0; i < l; i++) {
                        xVals.add(i + "");
                        yVals.add(new Entry(Float.parseFloat(split[i]), i));
                    }
                    LineDataSet set1 = new LineDataSet(yVals, null);

                    set1.setDrawCubic(true);  //设置曲线为圆滑的线
                    set1.setCubicIntensity(0.2f);
                    set1.setDrawCircles(false);  //设置有圆点
                    set1.setLineWidth(1f);    //设置线的宽度
                    //触摸高亮线
//                    set1.setHighLightColor(Color.rgb(244, 117, 117));
                    set1.setHighlightEnabled(false);
//                    set1.setDrawFilled(false);
                    set1.setDrawValues(false);

                    set1.setColor(Color.GREEN);    //设置曲线的颜色
                    mLineChart.setData(new LineData(xVals, set1));
                    mLineChart.notifyDataSetChanged();
                    mLineChart.invalidate();
                    setTime();
                    mHandler.post(mGetRunnable);
                }
            });*/
        }
    };

    public void setTime() {
        String format = df.format(new Date());
        mTv.setText(format);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.post(mGetRunnable);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mGetRunnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mGetRunnable);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.activity_loader) {
            startActivity(new Intent(this, LoaderActivty.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
