# LineChartView

	class LineChartView extends View

# 折线图，区域图 继承View绘制。心想从简。

####代码和效果说明：mainActivity   

	LineChartView chatView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chatView= (LineChartView) findViewById(R.id.chartView);
        chatView.cleanOldLines();
        chatView.updateYAixsTitles(100,5)
                .updateXAixsTitles(demoXAixsTitles())
                .addChartLine(demo1Line())
                .addChartLine(demo2Line())
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
        for (int index = 0; index < 3; index++) {
            DataAixsPoint aixsPoint = new DataAixsPoint();
            aixsPoint.setAixsVal(new Random().nextInt(100));
            aixsPoint.setTitle(String.valueOf(aixsPoint.getAixsVal()));
            demodata.add(aixsPoint);
        }

        chartLine.setDataPoints(demodata);
        chartLine.setLineWidth(dp2px(2));
        chartLine.setLineColor(Color.parseColor("#fff000"));
        chartLine.setFillColor(Color.parseColor("#55FF4081"));
        chartLine.setDrawModel(LineChartView.FILL_STROKE);

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

#### app:xAixsPostion="LEFT" app:yAixsPostion="BUTTOM"
        
	<com.igeek.linechartview.LineChartView
        android:id="@+id/chartView"
        android:layout_width="match_parent"
        android:layout_height="300dp"
        android:layout_marginTop="16dp"
        android:layout_marginBottom="16dp"
        android:background="#ffffff"
        app:aixsWidth="1dp"
        app:lineWidth="1px"
        app:isAixsLineAlign="false"
        app:xAixsPostion="LEFT"
        app:yAixsPostion="BUTTOM"
        app:horLineColor="#44929292"
        app:verLineColor="#44929292"
        app:showHorGridLine="true"
        app:showVerGridLine="true"
        app:xAixsColor="#929292"
        app:yAixsColor="#929292"
        app:xAixsTitleColor="#929292"
        app:yAixsTitleColor="#999999"
        app:xAixsTitleSize="10sp"
        app:yAixsTitleSize="12sp"
        app:xAixsPaddingBottom="12dp"
        app:xAixsPaddingTop="12dp"
        app:yAixsPaddingLeft="8dp"
        app:yAixsPaddingRight="8dp" />   


<img src="https://github.com/igeek-YZ/LineChartView/blob/master/pics/Screenshot_20160127-221045.png" width = "549" height = "559" alt="960" align=center />
  

#### app:xAixsPostion="LEFT" app:yAixsPostion="TOP" 

	<com.igeek.linechartview.LineChartView
        android:id="@+id/chartView"
        android:layout_width="match_parent"
        android:layout_height="300dp"
        android:layout_marginTop="16dp"
        android:layout_marginBottom="16dp"
        android:background="#ffffff"
        app:aixsWidth="1dp"
        app:lineWidth="1px"
        app:isAixsLineAlign="false"
        app:xAixsPostion="LEFT"
        app:yAixsPostion="TOP"
        app:horLineColor="#44929292"
        app:verLineColor="#44929292"
        app:showHorGridLine="true"
        app:showVerGridLine="true"
        app:xAixsColor="#929292"
        app:yAixsColor="#929292"
        app:xAixsTitleColor="#929292"
        app:yAixsTitleColor="#999999"
        app:xAixsTitleSize="10sp"
        app:yAixsTitleSize="12sp"
        app:xAixsPaddingBottom="12dp"
        app:xAixsPaddingTop="12dp"
        app:yAixsPaddingLeft="8dp"
        app:yAixsPaddingRight="8dp" />   


<img src="https://github.com/igeek-YZ/LineChartView/blob/master/pics/Screenshot_20160127-221136.png" width = "549" height = "547.5" alt="960" align=center />

#### app:xAixsPostion="RIGHT" app:yAixsPostion="TOP" 

	<com.igeek.linechartview.LineChartView
        android:id="@+id/chartView"
        android:layout_width="match_parent"
        android:layout_height="300dp"
        android:layout_marginTop="16dp"
        android:layout_marginBottom="16dp"
        android:background="#ffffff"
        app:aixsWidth="1dp"
        app:lineWidth="1px"
        app:isAixsLineAlign="false"
        app:xAixsPostion="RIGHT"
        app:yAixsPostion="TOP"
        app:horLineColor="#44929292"
        app:verLineColor="#44929292"
        app:showHorGridLine="true"
        app:showVerGridLine="true"
        app:xAixsColor="#929292"
        app:yAixsColor="#929292"
        app:xAixsTitleColor="#929292"
        app:yAixsTitleColor="#999999"
        app:xAixsTitleSize="10sp"
        app:yAixsTitleSize="12sp"
        app:xAixsPaddingBottom="12dp"
        app:xAixsPaddingTop="12dp"
        app:yAixsPaddingLeft="8dp"
        app:yAixsPaddingRight="8dp" /> 


<img src="https://github.com/igeek-YZ/LineChartView/blob/master/pics/Screenshot_20160127-220857.png" width = "549" height = "558" alt="960" align=center />


#### app:xAixsPostion="RIGHT" app:yAixsPostion="BUTTOM" app:isAixsLineAlign="true"

	<com.igeek.linechartview.LineChartView
        android:id="@+id/chartView"
        android:layout_width="match_parent"
        android:layout_height="300dp"
        android:layout_marginTop="16dp"
        android:layout_marginBottom="16dp"
        android:background="#ffffff"
        app:aixsWidth="1dp"
        app:lineWidth="1px"
        app:isAixsLineAlign="true"
        app:xAixsPostion="RIGHT"
        app:yAixsPostion="BUTTOM"
        app:horLineColor="#44929292"
        app:verLineColor="#44929292"
        app:showHorGridLine="true"
        app:showVerGridLine="true"
        app:xAixsColor="#929292"
        app:yAixsColor="#929292"
        app:xAixsTitleColor="#929292"
        app:yAixsTitleColor="#999999"
        app:xAixsTitleSize="10sp"
        app:yAixsTitleSize="12sp"
        app:xAixsPaddingBottom="12dp"
        app:xAixsPaddingTop="12dp"
        app:yAixsPaddingLeft="8dp"
        app:yAixsPaddingRight="8dp" /> 
        

<img src="https://github.com/igeek-YZ/LineChartView/blob/master/pics/Screenshot_20160127-221247.png" width = "539" height = "546" alt="960" align=center />



#### 属性说明  

	<declare-styleable name="LineChartView">
        <!-- x轴的颜色 -->
        <attr name="xAixsColor" format="color|reference"/>
        <!-- Y轴的颜色 -->
        <attr name="yAixsColor" format="color|reference"/>
        <!-- 水平方向网格线的颜色 -->
        <attr name="horLineColor" format="color|reference"/>
        <!-- 垂直方向网格线的颜色 -->
        <attr name="verLineColor" format="color|reference"/>
        <!-- x轴的标题字体颜色 -->
        <attr name="xAixsTitleColor" format="color|reference"/>
        <!-- y轴的标题字体颜色 -->
        <attr name="yAixsTitleColor" format="color|reference"/>
        <!-- 坐标轴的宽度 -->
        <attr name="aixsWidth" format="dimension|reference"/>
        <!-- 网格线的宽度 -->
        <attr name="lineWidth" format="dimension|reference"/>
        <!-- x轴的标题字体大小 -->
        <attr name="xAixsTitleSize" format="dimension|reference"/>
        <!-- y轴的标题字体大小 -->
        <attr name="yAixsTitleSize" format="dimension|reference"/>
        <!-- x轴的标题填充间距 -->
        <attr name="xAixsPadding" format="dimension|reference"/>
        <!-- y轴的标题填充间距 -->
        <attr name="yAixsPadding" format="dimension|reference"/>
        <!-- x轴的标题上填充间距 -->
        <attr name="xAixsPaddingTop" format="dimension|reference"/>
        <!-- x轴的标题底部填充间距 -->
        <attr name="xAixsPaddingBottom" format="dimension|reference"/>
        <!-- y轴的标题左填充间距 -->
        <attr name="yAixsPaddingLeft" format="dimension|reference"/>
        <!-- y轴的标题右填充间距 -->
        <attr name="yAixsPaddingRight" format="dimension|reference"/>
        <!-- 是否显示垂直网格线 -->
        <attr name="showVerGridLine" format="boolean"/>
        <!-- 是否显示水平网格线 -->
        <attr name="showHorGridLine" format="boolean"/>
        <!-- 坐标轴是否和网格线对齐 -->
        <attr name="isAixsLineAlign" format="boolean"/>
        <!-- x轴的位置 -->
        <attr name="xAixsPostion">
            <enum name="LEFT" value="0"/>
            <enum name="RIGHT" value="2"/>
        </attr>
        <!-- y轴的位置 -->
        <attr name="yAixsPostion">
            <enum name="TOP" value="1"/>
            <enum name="BUTTOM" value="3"/>
        </attr>
    </declare-styleable> 

#### 联系方式 (如遇bug可通过以下联系方式联系我)

微信号：igeekcoder  

邮箱：igeek2014@hotmail.com  

谢谢您的浏览%>_<%




