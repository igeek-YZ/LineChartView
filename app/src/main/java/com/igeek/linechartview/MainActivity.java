package com.igeek.linechartview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    LineChartView chatView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chatView= (LineChartView) findViewById(R.id.chartView);
        chatView.updateYAixsTitles(100,5)
                .updateXAixsTitles(demoXAixsTitles())
                .updateDataPoints(demoData())
                .drawToUpdate();
    }

    public List<String> demoXAixsTitles() {

        List<String> demodata = new ArrayList<String>();
        for (int index = 0; index < 7; index++) {
            demodata.add("201" + index);
        }
        return demodata;
    }

    public List<LineChartView.DataAixsPoint> demoData() {

        List<LineChartView.DataAixsPoint> demodata = new ArrayList<LineChartView.DataAixsPoint>();
        for (int index = 0; index < 7; index++) {
            LineChartView.DataAixsPoint aixsPoint = new LineChartView.DataAixsPoint();
            aixsPoint.setAixsVal(new Random().nextInt(100));
            aixsPoint.setTitle(String.valueOf(aixsPoint.getAixsVal()));
            demodata.add(aixsPoint);
        }
        return demodata;
    }
}
