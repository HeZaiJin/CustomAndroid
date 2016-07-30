package com.haozhang.android.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.db.chart.model.LineSet;
import com.db.chart.view.LineChartView;
import com.haozhang.android.R;


public class MainActivity extends AppCompatActivity {
    private final String[] mLabels = {"Jan", "Fev", "Mar", "Apr", "Jun", "May", "Jul", "Aug", "Sep"};
    private final float[][] mValues = {{3.5f, 4.7f, 4.3f, 8f, 6.5f, 9.9f, 7f, 8.3f, 7.0f}, {4.5f, 2.5f, 2.5f, 9f, 4.5f, 9.5f, 5f, 8.3f, 1.8f},
            {4.0f, 2.8f, 2.1f, 6f, 1f, 3f, 5f, 7f, 1.8f}, {4.5f, 2.5f, 8f, 9f, 1f, 9.5f, 5f, 2f, 1.8f}, {4.5f, 5f, 6f, 7f, 8f, 9.5f, 1f, 2f, 3f}};
    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final LineChartView view = (LineChartView) findViewById(R.id.linechart);

        LineSet dataset = new LineSet(mLabels, mValues[index]);
        dataset.setColor(Color.parseColor("#758cbb"))
                .setDotsColor(Color.parseColor("#75949a"))
                .setThickness(4)
                .setDashed(new float[]{10f, 10f});
        view.addData(dataset);
        view.show();
        Button bt = (Button) findViewById(R.id.refresh);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index++;
                if (index == mValues.length) {
                    index = 0;
                }
                view.updateValues(0, mValues[index]);
                view.notifyDataUpdate();
            }
        });
    }
}
