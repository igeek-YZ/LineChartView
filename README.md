# LineChartView

	class LineChartView extends View

# 折线图，区域图 继承View绘制。一个文件。没有任何添加剂

####代码和效果说明：mainActivity   

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
        app:pathModel="FILL_STROKE"
        app:xAixsPostion="LEFT"
        app:yAixsPostion="BUTTOM"
        app:pathColor="#FF4081"
        app:pathStrokeColor="#fff000"
        app:pathStrokeWidth="2dp"
        app:horLineColor="#44929292"
        app:verLineColor="#44929292"
        app:showHorGridLine="false"
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

<img src="https://github.com/igeek-YZ/LineChartView/blob/master/pics/Screenshot_20160126-165153.png" width = "300" height = "540" alt="960" align=center />
  

#### app:xAixsPostion="LEFT" app:yAixsPostion="TOP" app:pathModel="STROKE"

	<com.igeek.linechartview.LineChartView
        android:id="@+id/chartView"
        android:layout_width="match_parent"
        android:layout_height="300dp"
        android:layout_marginTop="16dp"
        android:layout_marginBottom="16dp"
        android:background="#ffffff"
        app:aixsWidth="1dp"
        app:lineWidth="1px"
        app:pathModel="STROKE"
        app:xAixsPostion="LEFT"
        app:yAixsPostion="TOP"
        app:pathColor="#FF4081"
        app:pathStrokeColor="#fff000"
        app:pathStrokeWidth="2dp"
        app:horLineColor="#44929292"
        app:verLineColor="#44929292"
        app:showHorGridLine="true"
        app:showVerGridLine="false"
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


![image](https://github.com/igeek-YZ/LineChartView/blob/master/pics/Screenshot_20160126-170354.png)   

#### app:xAixsPostion="RIGHT" app:yAixsPostion="TOP" app:pathModel="FILL"

	<com.igeek.linechartview.LineChartView
        android:id="@+id/chartView"
        android:layout_width="match_parent"
        android:layout_height="300dp"
        android:layout_marginTop="16dp"
        android:layout_marginBottom="16dp"
        android:background="#ffffff"
        app:aixsWidth="1dp"
        app:lineWidth="1px"
        app:pathModel="FILL"
        app:xAixsPostion="RIGHT"
        app:yAixsPostion="TOP"
        app:pathColor="#FF4081"
        app:pathStrokeColor="#fff000"
        app:pathStrokeWidth="2dp"
        app:horLineColor="#44929292"
        app:verLineColor="#44929292"
        app:showHorGridLine="true"
        app:showVerGridLine="false"
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


![image](https://github.com/igeek-YZ/LineChartView/blob/master/pics/Screenshot_20160126-170728.png)   


#### app:xAixsPostion="RIGHT" app:yAixsPostion="BUTTOM" app:pathModel="FILL_STROKE"

	<com.igeek.linechartview.LineChartView
        android:id="@+id/chartView"
        android:layout_width="match_parent"
        android:layout_height="300dp"
        android:layout_marginTop="16dp"
        android:layout_marginBottom="16dp"
        android:background="#ffffff"
        app:aixsWidth="1dp"
        app:lineWidth="1px"
        app:pathModel="FILL_STROKE"
        app:xAixsPostion="RIGHT"
        app:yAixsPostion="BUTTOM"
        app:pathColor="#FF4081"
        app:pathStrokeColor="#fff000"
        app:pathStrokeWidth="2dp"
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
        

![image](https://github.com/igeek-YZ/LineChartView/blob/master/pics/Screenshot_20160126-170934.png) 


#### 属性说明  

	<declare-styleable name="LineChartView">
        <!-- 路径区域填充颜色 -->
        <attr name="pathColor" format="color|reference"/>
        <!-- 路径区域边框颜色 -->
        <attr name="pathStrokeColor" format="color|reference"/>
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
        <!-- 路径区域边框宽度 -->
        <attr name="pathStrokeWidth" format="dimension|reference"/>
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
        <!-- 绘制路径区域的模式 -->
        <attr name="pathModel">
            <enum name="STROKE" value="4"/>
            <enum name="FILL" value="5"/>
            <enum name="FILL_STROKE" value="6"/>
        </attr>
    </declare-styleable>  

#### 联系方式 (如遇bug可通过以下联系方式联系我)

微信号：igeekcoder  

邮箱：igeek2014@hotmail.com  

谢谢您的浏览%>_<%




