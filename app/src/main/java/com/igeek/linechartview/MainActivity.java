package com.igeek.linechartview;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    LineChartView chatView;


    RadioGroup yAixsTitles;
    RadioGroup yAuxAixsTitles;
    RadioGroup yAixsPos;
    RadioGroup xAixsPos;
    RadioGroup lineModel;
    RadioGroup gridLines;
    RadioGroup standardLine;
    RadioGroup standardLineStyle;

    ChartLine chartLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chatView= (LineChartView) findViewById(R.id.chartView);
        yAixsTitles= (RadioGroup) findViewById(R.id.yAixsTitles);
        yAuxAixsTitles= (RadioGroup) findViewById(R.id.yAuxAixsTitles);
        yAixsPos= (RadioGroup) findViewById(R.id.yAixsPos);
        xAixsPos= (RadioGroup) findViewById(R.id.xAixsPos);
        lineModel= (RadioGroup) findViewById(R.id.lineModel);
        gridLines= (RadioGroup) findViewById(R.id.gridLines);
        standardLine= (RadioGroup) findViewById(R.id.standardLine);
        standardLineStyle= (RadioGroup) findViewById(R.id.standardLineStyle);

        chartLine=demo1Line();

        standardLine.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                checkChecked();
            }
        });

        standardLineStyle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                checkChecked();
            }
        });

        yAixsTitles.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                checkChecked();
            }
        });

        yAuxAixsTitles.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                checkChecked();
            }
        });

        yAixsPos.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                checkChecked();
            }
        });

        xAixsPos.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                checkChecked();
            }
        });

        lineModel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                checkChecked();
            }
        });

        gridLines.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                checkChecked();
            }
        });
        checkChecked();
    }

    public void checkChecked(){
        boolean hideYAixsTitles=yAixsTitles.getCheckedRadioButtonId()==R.id.yAixsTitles_hide;
        boolean showYAuxAixsTitles=yAuxAixsTitles.getCheckedRadioButtonId()==R.id.yAuxAixsTitles_show;
        int yaixsPos=yAixsPos.getCheckedRadioButtonId()==R.id.yAixsPos_left?LineChartView.AIXS_LEFT:LineChartView.AIXS_RIGHT;
        int xaixsPos=xAixsPos.getCheckedRadioButtonId()==R.id.xAixsPos_top?LineChartView.AIXS_TOP:LineChartView.AIXS_BUTTOM;
        int drawModel=LineChartView.STROKE;
        if(lineModel.getCheckedRadioButtonId()==R.id.lineModel_fill_stroke){
             drawModel=LineChartView.FILL_STROKE;
        }else if(lineModel.getCheckedRadioButtonId()==R.id.lineModel_fill){
            drawModel=LineChartView.FILL;
        }else if(lineModel.getCheckedRadioButtonId()==R.id.lineModel_stroke_dash){
            drawModel=LineChartView.STROKE_DASH;
        }

        boolean showHorGridLine=true;
        boolean showVerGridLine=true;
        if(gridLines.getCheckedRadioButtonId()==R.id.gridLines_All){
            showHorGridLine=showVerGridLine=true;
        }else if(gridLines.getCheckedRadioButtonId()==R.id.gridLines_hor){
            showHorGridLine=true;
            showVerGridLine=false;
        }else{
            showHorGridLine=false;
            showVerGridLine=true;
        }

        boolean showStandardLine=standardLine.getCheckedRadioButtonId()==R.id.standardLine_show;
        int standardLinestyle=standardLineStyle.getCheckedRadioButtonId()==R.id.standardLine_dash?LineChartView.DASH:LineChartView.SOLID;

        updateChartView(hideYAixsTitles,yaixsPos,xaixsPos,drawModel,
                showHorGridLine,showVerGridLine,showStandardLine,standardLinestyle,showYAuxAixsTitles);
    }

    public void updateChartView(boolean hideYAixsTitles,int yAixsPos,int xAixsPos,
        int drawModel,boolean showHorGridLine,boolean showVerGridLine,boolean showStandardLine,
                                int standardLineStyle,boolean showYAuxAixsTitles){

        chartLine.setDrawModel(drawModel);

        chatView.getChartAttrs().setTouchLineColor(Color.parseColor("#fff000"));
        chatView.getChartAttrs().setTouchLineWidth(4);

        chatView.cleanOldLines();
        chatView.setAixsLineAlign(true);
        chatView.setShowHorGridLine(showHorGridLine);
        chatView.setShowVerGridLine(showVerGridLine);
        chatView.setHideYAixsTitles(hideYAixsTitles);
        chatView.setShowYAuxAixsTitles(showYAuxAixsTitles);
        chatView.setyAixsPostion(yAixsPos);
        chatView.setxAixsPostion(xAixsPos);
        chatView.setShowStandardLine(showStandardLine);
        chatView.setStandardLineStyle(standardLineStyle);
        chatView.setShowStandardVal(true);

        chatView.setStandardAixsVal(60);
        chatView.updateYAixsTitles(100,5)
                .updateYAuxAixsTitles(120)
                .updateXAixsTitles(demoXAixsTitles())
                .addChartLine(chartLine)
                .drawToUpdate();
    }

    public List<String> demoXAixsTitles() {
        List<String> demodata = new ArrayList<String>();
        for (int index = 0; index < 7; index++) {
            demodata.add("201" + index);
        }
        return demodata;
    }

    public ChartLine demo1Line(){
        ChartLine chartLine=new ChartLine();
        List<DataAixsPoint> demodata = new ArrayList<DataAixsPoint>();
        for (int index = 0; index < 7; index++) {
            DataAixsPoint aixsPoint = new DataAixsPoint();
            aixsPoint.setAixsVal(new Random().nextInt(100));
            aixsPoint.setTitle(String.valueOf(aixsPoint.getAixsVal()));
            demodata.add(aixsPoint);
        }

        chartLine.setDataPoints(demodata);
        chartLine.setLineWidth(dp2px(2));
        chartLine.setLineColor(Color.parseColor("#fff000"));
        chartLine.setFillColor(Color.parseColor("#55FF4081"));
        chartLine.setDrawModel(LineChartView.STROKE);

        return chartLine;
    }

    public ChartLine demo2Line(){
        ChartLine chartLine=new ChartLine();
        List<DataAixsPoint> demodata = new ArrayList<DataAixsPoint>();
        for (int index = 3; index < 7; index++) {
            DataAixsPoint aixsPoint = new DataAixsPoint();
            aixsPoint.setAixsVal(new Random().nextInt(100));
            aixsPoint.setxAixsTitle("201" + index);
            demodata.add(aixsPoint);
        }

        chartLine.setDataPoints(demodata);
        chartLine.setLineWidth(dp2px(2));
        chartLine.setLineColor(Color.parseColor("#FF4081"));
        chartLine.setFillColor(Color.parseColor("#55fff000"));
        chartLine.setDrawModel(LineChartView.FILL_STROKE);

        return chartLine;
    }


    /**
     * dp转px
     */
    public static int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, Resources.getSystem().getDisplayMetrics());
    }
}
