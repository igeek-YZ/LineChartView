# LineChartView

	class LineChartView extends View

# 折线图，区域图 继承View绘制。

#### 效果图        

<img src="https://github.com/igeek-YZ/LineChartView/blob/master/pics/linechatview.gif" width = "539" height = "546" alt="960" align=center />



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
            <!-- x坐标轴的宽度 -->
            <attr name="xAixsWidth" format="dimension|reference"/>
            <!-- y坐标轴的宽度 -->
            <attr name="yAixsWidth" format="dimension|reference"/>
            <!-- 网格线的宽度 -->
            <attr name="lineWidth" format="dimension|reference"/>
            <!-- 指定标准线的颜色 -->
            <attr name="standardLineColor" format="color|reference"/>
            <!-- 指定标准数值的颜色 -->
            <attr name="standardValColor" format="color|reference"/>
            <!-- 指定标准线的样式 -->
            <attr name="standardLineStyle" >
                <!-- 虚线 -->
                <enum name="DASH" value="8"/>
                <!-- 实线 -->
                <enum name="SOLID" value="9"/>
            </attr>
            <!-- 指定标准线的宽度 -->
            <attr name="standardLineWidth" format="dimension|reference"/>
            <!-- x轴的标题字体大小 -->
            <attr name="xAixsTitleSize" format="dimension|reference"/>
            <!-- y轴的标题字体大小 -->
            <attr name="yAixsTitleSize" format="dimension|reference"/>
            <!-- 指定标准值的字体大小 -->
            <attr name="standardValSize" format="dimension|reference"/>
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
            <!-- 标准值的外边框填充 -->
            <attr name="standardValMargin" format="dimension|reference"/>
            <!-- 是否显示y坐标轴标题 -->
            <attr name="hideYAixsTitles" format="boolean"/>
            <!-- 是否显示指定标准线 -->
            <attr name="showStandardLine" format="boolean"/>
            <!-- 是否显示垂直网格线 -->
            <attr name="showVerGridLine" format="boolean"/>
            <!-- 是否显示水平网格线 -->
            <attr name="showHorGridLine" format="boolean"/>
            <!-- 坐标轴是否和网格线对齐 -->
            <attr name="isAixsLineAlign" format="boolean"/>
            <!-- y轴的位置 -->
            <attr name="yAixsPostion">
                <enum name="LEFT" value="0"/>
                <enum name="RIGHT" value="2"/>
            </attr>
            <!-- x轴的位置 -->
            <attr name="xAixsPostion">
                <enum name="TOP" value="1"/>
                <enum name="BUTTOM" value="3"/>
            </attr>
    </declare-styleable>

#### 联系方式 (如遇bug可通过以下联系方式联系我)

微信号：igeekcoder  

邮箱：igeek2014@hotmail.com  

谢谢您的浏览%>_<%




