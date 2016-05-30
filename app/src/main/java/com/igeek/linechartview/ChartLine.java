package com.igeek.linechartview;

import java.util.List;

/**
 *
 */
public class ChartLine  {

    private List<DataAixsPoint> dataPoints;

    //折线的颜色
    private int lineColor;
    //折线的宽度
    private int lineWidth;
    //折线形成的区域的填充颜色
    private int fillColor;
    //绘制的模式
    private int drawModel;


    public List<DataAixsPoint> getDataPoints() {
        return dataPoints;
    }

    public void setDataPoints(List<DataAixsPoint> dataPoints) {
        this.dataPoints = dataPoints;
    }

    public int getLineColor() {
        return lineColor;
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
    }

    public int getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
    }

    public int getFillColor() {
        return fillColor;
    }

    public void setFillColor(int fillColor) {
        this.fillColor = fillColor;
    }

    public int getDrawModel() {
        return drawModel;
    }

    public void setDrawModel(int drawModel) {
        this.drawModel = drawModel;
    }
}
